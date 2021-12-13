package Jeu;

import Cartes.Carte;
import Cartes.PaquetDeCartes;

import java.util.ArrayList;

public class TourPoker {
    private int[] m_pot_pt;
    private int m_relance_minimale;
    private int m_mise_actuelle;
    private PaquetDeCartes paquet;
    private ArrayList<Carte> m_jeu_pt;
    TourPoker(int petite_blinde) {
        init(petite_blinde);
        deroulement_du_tour();
    }
    private void init(int petite_blinde) {
        Joueur joueur_temp;
        final int grosse_blinde = 2*petite_blinde;
        m_pot_pt = new int[] {0};
        m_mise_actuelle = grosse_blinde;
        m_relance_minimale = grosse_blinde;
        m_jeu_pt = new ArrayList<>();
        paquet=new PaquetDeCartes();
        paquet.melanger_cartes();
        paquet.bruler_une_carte();
        Joueur.stream().forEach(joueur -> joueur.init_joueur(paquet));
        System.out.println(Joueur.donneur + " est le donneur !!");
        joueur_temp = Joueur.donneur.get_joueur_suivant();
        joueur_temp.ajouter_mise(petite_blinde,m_pot_pt);
        System.out.println(joueur_temp + " pose la petite blinde (" + petite_blinde + " euros) !!");
        joueur_temp = joueur_temp.get_joueur_suivant();
        joueur_temp.ajouter_mise(grosse_blinde,m_pot_pt);
        System.out.println(joueur_temp + " pose la grosse blinde (" + grosse_blinde + " euros) !!");
    }
    private void deroulement_du_tour() {
        tour_pre_flop();
        tour_flop();
        tour_turn();
        tour_riviere();
        abattage();
    }
    private void tour_pre_flop() {
        System.out.println("******************");
        System.out.println("**** Pre-flop ****");
        System.out.println("******************");
        tour_encheres(Joueur.donneur.get_joueur_suivant().get_joueur_suivant().get_joueur_suivant());
    }
    private void tour_flop() {
        System.out.println("******************");
        System.out.println("****** Flop ******");
        System.out.println("******************");
        paquet.bruler_une_carte();
        m_jeu_pt.add(paquet.piocher_une_carte());
        paquet.bruler_une_carte();
        m_jeu_pt.add(paquet.piocher_une_carte());
        paquet.bruler_une_carte();
        m_jeu_pt.add(paquet.piocher_une_carte());
        tour_encheres(Joueur.donneur.get_joueur_suivant());
    }
    private void tour_turn() {
        System.out.println("******************");
        System.out.println("****** Turn ******");
        System.out.println("******************");
        paquet.bruler_une_carte();
        m_jeu_pt.add(paquet.piocher_une_carte());
        tour_encheres(Joueur.donneur.get_joueur_suivant());
    }
    private void tour_riviere() {
        System.out.println("******************");
        System.out.println("**** Rivière *****");
        System.out.println("******************");
        paquet.bruler_une_carte();
        m_jeu_pt.add(paquet.piocher_une_carte());
        tour_encheres(Joueur.donneur.get_joueur_suivant());
    }
    private void tour_encheres(Joueur premier_joueur) {
        Joueur joueur_fin = premier_joueur;
        Joueur joueur_actuel = premier_joueur;
        int mise;
        Carte.affiche(m_jeu_pt,"jeu");
        if (Joueur.donneur.nombre_de_joueurs_pouvant_relancer() == 1) return;
        do {
            if (joueur_actuel.get_etat() == Joueur.Etat.PEUT_MISER) {
                mise = joueur_actuel.demander_mise(m_mise_actuelle, m_jeu_pt, m_pot_pt[0], m_relance_minimale);
                if (mise == -1) joueur_actuel.coucher();
                else {
                    if (mise > m_mise_actuelle) {
                        joueur_fin = joueur_actuel;
                        m_relance_minimale = mise-m_mise_actuelle;
                        m_mise_actuelle = mise;
                    }
                    joueur_actuel.ajouter_mise(mise - joueur_actuel.get_mise(), m_pot_pt);
                }
            }
            joueur_actuel = joueur_actuel.get_joueur_suivant();
            if (Joueur.donneur.nombre_de_joueurs_pouvant_relancer() == 1) {
                System.out.println("Il n'y a plus qu'un seul joueur pouvant miser");
                break;
            }
        } while (joueur_actuel != joueur_fin);
    }
    private void abattage() {
        Joueur
                        .stream()
                        .filter(Joueur::pas_couche)
                        .sorted((x,y) -> y.get_collection(m_jeu_pt).compareTo(x.get_collection(m_jeu_pt)))
                        .forEach(gagnant -> {
                            int[] retrait_pt = new int[] {0};
                            int mise = gagnant.get_mise();
                            Joueur.stream().forEach(joueur -> joueur.retirer_mise(mise,m_pot_pt,retrait_pt));
                            if (retrait_pt[0] == 0) System.out.println(gagnant + " ne gagne rien !!");
                            else {
                                gagnant.encaisser(retrait_pt[0]);
                                System.out.println(gagnant + " encaisse " + retrait_pt[0] + " euros !!");
                            }
                        });
        Joueur.stream().
                forEach(joueur -> System.out.println(joueur + " a maintenant " + joueur.get_cave() + " euros."));
    }
}
