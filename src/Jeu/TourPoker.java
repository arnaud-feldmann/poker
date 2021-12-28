package Jeu;

import Cartes.Carte;
import Cartes.PaquetDeCartes;
import java.util.ArrayList;
import java.util.stream.Collectors;

class MethodeDeTestException extends IllegalStateException {
    MethodeDeTestException() {
        super("Cette méthode ne peut être appelée qu'en mode de test.");
    }
}

public class TourPoker {
    /*
    Cette classe représente le déroulement d'un tour de Poker.
    - m_pot_pt est un pointeur vers le pot, pour pouvoir communiquer avec les joueurs.
    - m_relance_minimale est la relance minimale acceptée selon ce que j'ai compris des règles du poker ; à savoir d'abord
    la blinde puis la dernière relance
    - m_mise_actuelle est la somme qu'il faut miser pour rester en jeu
    - m_jeu_pt est l'arraylist des cartes en jeu
     */
    private int[] m_pot_pt;
    private int m_relance_minimale;
    private int m_mise_actuelle;
    private PaquetDeCartes m_paquet;
    private ArrayList<Carte> m_jeu_pt;

    protected TourPoker(int petite_blinde) {
        init_tour(petite_blinde);
        deroulement_du_tour();
    }

    /*
    Quelques initialisations à faire chaque tour
     */
    private void init_tour(int petite_blinde) {
        final int grosse_blinde = 2 * petite_blinde;
        final Joueur joueur_petite_blinde = Joueur.donneur.get_joueur_suivant();
        final Joueur joueur_grosse_blinde = joueur_petite_blinde.get_joueur_suivant();
        m_pot_pt = new int[]{0};
        m_mise_actuelle = grosse_blinde;
        m_relance_minimale = grosse_blinde;
        m_jeu_pt = new ArrayList<>();
        m_paquet = new PaquetDeCartes();
        m_paquet.melanger_cartes();
        m_paquet.bruler_une_carte();
        Joueur.stream().forEach(joueur -> joueur.init_tour_joueur(m_paquet));

        joueur_petite_blinde.ajouter_mise(petite_blinde, m_pot_pt);
        joueur_grosse_blinde.ajouter_mise(grosse_blinde, m_pot_pt);
        InterfaceUtilisateur.println(Joueur.donneur + " est le donneur !!");
        InterfaceUtilisateur.println(joueur_petite_blinde + " pose la petite blinde (" + petite_blinde + " euros) !!");
        InterfaceUtilisateur.println(joueur_grosse_blinde + " pose la grosse blinde (" + grosse_blinde + " euros) !!");
        InterfaceUtilisateur.interface_graphique.fixeFlop(new Carte[0]);
        InterfaceUtilisateur.interface_graphique.raffraichit();
    }

    private void deroulement_du_tour() {
        tour_pre_flop();
        tour_flop();
        tour_turn();
        tour_riviere();
        if (InterfaceUtilisateur.test_tour_manuel) return;
        abattage();
        banqueroute();
    }

    private void tour_pre_flop() {
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.println("**** Pre-flop ****");
        InterfaceUtilisateur.println("******************");
        tour_encheres(Joueur.donneur.get_joueur_suivant().get_joueur_suivant().get_joueur_suivant());
    }

    protected void ajouter_une_carte_en_jeu() {
        Carte carte;
        m_paquet.bruler_une_carte();
        carte = m_paquet.piocher_une_carte();
        m_jeu_pt.add(carte);
    }

    private void tour_flop() {
        ajouter_une_carte_en_jeu();
        ajouter_une_carte_en_jeu();
        ajouter_une_carte_en_jeu();
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.println("****** Flop ******");
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.interface_graphique.fixeFlop(new Carte[] {m_jeu_pt.get(0),m_jeu_pt.get(1),m_jeu_pt.get(2)});
        tour_encheres(Joueur.donneur.get_joueur_suivant());
    }

    private void tour_turn() {
        ajouter_une_carte_en_jeu();
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.println("****** Turn ******");
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.interface_graphique.ajouteCarteFlop(m_jeu_pt.get(3));
        tour_encheres(Joueur.donneur.get_joueur_suivant());
    }

    private void tour_riviere() {
        ajouter_une_carte_en_jeu();
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.println("**** Rivière *****");
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.interface_graphique.ajouteCarteFlop(m_jeu_pt.get(4));
        tour_encheres(Joueur.donneur.get_joueur_suivant());
    }

    /* C'est le déroulement d'un tour d'enchères. C'est une boucle do-while. Elle est breakée si tout le monde est
    couché à l'exception d'un joueur ; si on ne le faisait pas non seulement on ferait des demandes aux joueurs pour rien
    mais en plus on aurait un bug si tout le monde est couché (comment redistribuer les gains ?) */
    private void tour_encheres(Joueur premier_joueur) {
        if (InterfaceUtilisateur.test_tour_manuel) return;
        Joueur joueur_fin = premier_joueur;
        Joueur joueur_actuel = premier_joueur;
        int mise;
        Carte.affiche(m_jeu_pt, "jeu");
        do {
            if (Joueur.stream().filter(Joueur::pas_couche).count() == 1) break;
            if (joueur_actuel.get_etat() == Joueur.Etat.PEUT_MISER) {
                mise = joueur_actuel.demander_mise(m_mise_actuelle, m_jeu_pt, m_pot_pt[0], m_relance_minimale);
                if (mise < m_mise_actuelle) joueur_actuel.coucher();
                else if (mise >= Math.min(m_mise_actuelle + m_relance_minimale,joueur_actuel.get_cave())) {
                    joueur_fin = joueur_actuel;
                    m_relance_minimale = mise - m_mise_actuelle;
                    m_mise_actuelle = mise;
                    joueur_actuel.ajouter_mise(mise - joueur_actuel.get_mise(), m_pot_pt);
                }
                else {
                    joueur_actuel.ajouter_mise(m_mise_actuelle - joueur_actuel.get_mise(), m_pot_pt);
                }
            }
            joueur_actuel = joueur_actuel.get_joueur_suivant();
        } while (joueur_actuel != joueur_fin);
    }

    private void affichage_mains() {
        InterfaceUtilisateur.println("******************");
        InterfaceUtilisateur.println("**** Abattage ****");
        InterfaceUtilisateur.println("******************");
        Joueur.stream().filter(Joueur::pas_couche).forEach(Joueur::affiche_main);
        InterfaceUtilisateur.println();
    }

    private void joueur_et_ex_aequos_empochent_leur_gain(Joueur gagnant) {
        int gains;
        int[] retrait_pt;
        int mise = gagnant.get_mise();
        if (mise != 0) {
            retrait_pt = new int[]{0};
            Joueur.stream().forEach(joueur -> joueur.retirer_mise(mise, m_pot_pt, retrait_pt));
            ArrayList<Joueur> ex_aequos =
                    Joueur.stream()
                            .filter(x -> x.get_collection(m_jeu_pt).compareTo(gagnant.get_collection(m_jeu_pt)) == 0)
                            .collect(Collectors.toCollection(ArrayList<Joueur>::new));
            gains = retrait_pt[0] / ex_aequos.size();
            for (Joueur ex_aequo : ex_aequos) {
                ex_aequo.encaisser(gains);
                InterfaceUtilisateur.println(ex_aequo + " encaisse " + gains + " euros !!");
            }
        }
    }

    /*
    Pour partager les gains selon les règles du poker, on classe tous les joueurs selon le compareTo de collection,
    dans le sens de la descente.
    Ensuite, on parcourt les joueurs en faisant attention à ne pas faire gagner plus que ce que le joueur a parié.
    D'autre part, en cas d'égalité, les mises sont partagées via la méthode
    joueur_et_ex_aequos_empochent_leur_gain.
    La méthode de stream est implémentée dans la classe Joueur
     */

    protected void repartition_gains() {
        String res;

        InterfaceUtilisateur.println("-----------");
        InterfaceUtilisateur.println("|LES GAINS|");
        InterfaceUtilisateur.println("-----------");
        InterfaceUtilisateur.println();

        Joueur
                .stream()
                .filter(Joueur::pas_couche)
                .sorted((x, y) -> y.get_collection(m_jeu_pt).compareTo(x.get_collection(m_jeu_pt)))
                .forEach(this::joueur_et_ex_aequos_empochent_leur_gain);
        Joueur.stream().
                forEach(joueur -> InterfaceUtilisateur.println(joueur + " a maintenant " + joueur.get_cave() + " euros."));
        InterfaceUtilisateur.interface_graphique.raffraichit();
        InterfaceUtilisateur.println("validez (o/n) :");
        if (!InterfaceUtilisateur.test_tour_manuel) {
            while (true) {
                System.out.print("> ");
                res = InterfaceUtilisateur.nextLine();
                if (res.equals("o") || res.equals("O")) break;
                else if (res.equals("n") || res.equals("N")) InterfaceUtilisateur.println("Ok j'attends un peu!");
                else InterfaceUtilisateur.println("Un peu de sérieux !");
            }
        }
    }

    private void abattage() {
        affichage_mains();
        repartition_gains();
    }

    /* La méthode qui met hors-jeu les joueurs qui ont tout perdu à la fin du tour */
    protected void banqueroute() {
        Joueur joueur = Joueur.donneur;
        Joueur suivant;
        while (true) {
            suivant = joueur.get_joueur_suivant();
            if (suivant.get_cave() == 0) {
                InterfaceUtilisateur.println(suivant + " quitte le jeu !");
                InterfaceUtilisateur.interface_graphique.joueurPasDeCarte(suivant.get_numero_joueur_interface());
                InterfaceUtilisateur.interface_graphique.joueurSetAnnotation(suivant.get_numero_joueur_interface(),"(hors-jeu)");
                joueur.set_joueur_suivant(suivant.get_joueur_suivant());
                if (suivant == Joueur.donneur) Joueur.donneur = suivant.get_joueur_suivant();
            }
            else {
                joueur = joueur.get_joueur_suivant();
                if (joueur == Joueur.donneur) break;
            }
        }
    }

    /* Accesseur de test */

    public int[] get_pot_pt() {
        if (!InterfaceUtilisateur.test_tour_manuel) throw new MethodeDeTestException();
        return m_pot_pt;
    }
}

