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
        InterfaceUtilisateur.interface_graphique.fixeFlop(new Carte[]{m_jeu_pt.get(0), m_jeu_pt.get(1), m_jeu_pt.get(2)});
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
                if (mise >= m_mise_actuelle + m_relance_minimale && m_mise_actuelle <= joueur_actuel.get_cave()) {
                    joueur_fin = joueur_actuel;
                    joueur_actuel.ajouter_mise(mise - joueur_actuel.get_mise(), m_pot_pt);
                    m_relance_minimale = joueur_actuel.get_mise() - m_mise_actuelle;
                    m_mise_actuelle = joueur_actuel.get_mise();
                } else if (mise >= joueur_actuel.get_cave()) {
                    joueur_actuel.ajouter_mise(mise - joueur_actuel.get_mise(), m_pot_pt);
                } else if (mise < m_mise_actuelle) joueur_actuel.coucher();
                else joueur_actuel.ajouter_mise(m_mise_actuelle - joueur_actuel.get_mise(), m_pot_pt);
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

    // Au départ j'avais redistribué avec un filtre sur les pas couchés, mais il y a un cas exceptionnel où
    // La personne couchée a parié plus que le meilleur gagnant. Si on rencontre ce cas, il faut exceptionnellement
    // redistribuer à la personne couchée. Donc la manière la plus triviale de le dire est de les ajouter à la relation
    // d'ordre.
    // Cette relation est dans l'ordre décroissant car on veut redistribuer dans l'ordre décroissant des valeurs de
    // jeux.
    private int ordre_des_mains(Joueur joueur1, Joueur joueur2) {
        if (joueur1.get_etat() == Joueur.Etat.COUCHE) {
            if (joueur2.get_etat() == Joueur.Etat.COUCHE) return 0;
            else return 1;
        } else if (joueur2.get_etat() == Joueur.Etat.COUCHE) return -1;
        else return joueur2.get_collection(m_jeu_pt).compareTo(joueur1.get_collection(m_jeu_pt));
    }

    private int ordre_des_gains(Joueur joueur1, Joueur joueur2) {
        int res = ordre_des_mains(joueur1, joueur2);
        if (res == 0) res = Double.compare(joueur1.get_mise(), joueur2.get_mise());
        // On met les mises les plus petites en premier comme ça en cas d'égalité et de pots différents, on
        // peut acquitter les tas à la suite cf fonction suivante
        return res;
    }

    private void joueur_et_ex_aequos_empochent_leur_gain(Joueur gagnant) {
        int gains;
        int reste;
        int[] retrait_pt;
        int mise = gagnant.get_mise();
        if (mise != 0) {
            retrait_pt = new int[]{0};
            ArrayList<Joueur> ex_aequos =
                    Joueur.stream()
                            .filter(x -> ordre_des_mains(x, gagnant) == 0 && x.get_mise() != 0) // Les petites mises sont déjà acquitées
                            .collect(Collectors.toCollection(ArrayList<Joueur>::new));
            Joueur.stream().forEach(joueur -> joueur.retirer_mise(mise, m_pot_pt, retrait_pt));
            gains = retrait_pt[0] / ex_aequos.size();
            for (Joueur ex_aequo : ex_aequos) {
                ex_aequo.encaisser(gains);
                InterfaceUtilisateur.println(ex_aequo + " encaisse " + gains + " euros !!");
            }
            reste = retrait_pt[0] - ex_aequos.size() * gains;
            if (reste != 0) {
                ex_aequos.get(0).encaisser(reste);
                InterfaceUtilisateur.println(ex_aequos.get(0) + " encaisse un petit reste de division de " + reste + " euros car il est le premier après le dealer !!");
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

        InterfaceUtilisateur.println("-----------");
        InterfaceUtilisateur.println("|LES GAINS|");
        InterfaceUtilisateur.println("-----------");
        InterfaceUtilisateur.println();

        Joueur
                .stream()
                .sorted(this::ordre_des_gains)
                .forEach(this::joueur_et_ex_aequos_empochent_leur_gain);
        Joueur.stream().
                forEach(joueur -> InterfaceUtilisateur.println(joueur + " a maintenant " + joueur.get_cave() + " euros."));
        InterfaceUtilisateur.interface_graphique.raffraichit();
    }

    private void abattage() {
        affichage_mains();
        repartition_gains();
        InterfaceUtilisateur.prompt_fin_du_tour();
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
                InterfaceUtilisateur.interface_graphique.joueurSetAnnotation(suivant.get_numero_joueur_interface(), "(hors-jeu)");
                joueur.set_joueur_suivant(suivant.get_joueur_suivant());
                if (suivant == Joueur.donneur) Joueur.donneur = suivant.get_joueur_suivant();
            } else {
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

