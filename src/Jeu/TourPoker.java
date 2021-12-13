package Jeu;

import Cartes.Carte;
import Cartes.PaquetDeCartes;

import java.util.ArrayList;

public class TourPoker {
    private Joueur m_donneur;
    private int[] m_pot_pt;
    private int m_relance_minimale;
    private int m_mise_actuelle;
    private PaquetDeCartes paquet;
    private ArrayList<Carte> m_jeu_pt;
    TourPoker(Joueur donneur, int petite_blinde) {
        init(donneur,petite_blinde);
        deroulement_du_tour();
    }
    private void init(Joueur donneur, int petite_blinde) {
        Joueur joueur_temp;
        final int grosse_blinde = 2*petite_blinde;
        m_pot_pt = new int[] {0};
        m_mise_actuelle = grosse_blinde;
        m_relance_minimale = grosse_blinde;
        Joueur.nombre_de_joueurs_pouvant_relancer = 0;
        m_jeu_pt = new ArrayList<>();
        paquet=new PaquetDeCartes();
        paquet.melanger_cartes();
        m_donneur = donneur;
        System.out.println(m_donneur + " est le donneur !!");
        joueur_temp = donneur.get_joueur_suivant();
        joueur_temp.ajouter_mise(petite_blinde,m_pot_pt);
        System.out.println(joueur_temp + " pose la petite blinde (" + petite_blinde + " euros) !!");
        joueur_temp = joueur_temp.get_joueur_suivant();
        joueur_temp.ajouter_mise(grosse_blinde,m_pot_pt);
        System.out.println(joueur_temp + " pose la grosse blinde (" + grosse_blinde + " euros) !!");
        paquet.bruler_une_carte();
        m_donneur.stream().forEach(joueur -> joueur.init_joueur(paquet));
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
        tour_encheres(m_donneur.get_joueur_suivant().get_joueur_suivant().get_joueur_suivant());
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
        tour_encheres(m_donneur.get_joueur_suivant());
    }
    private void tour_turn() {
        System.out.println("******************");
        System.out.println("****** Turn ******");
        System.out.println("******************");
        paquet.bruler_une_carte();
        m_jeu_pt.add(paquet.piocher_une_carte());
        tour_encheres(m_donneur.get_joueur_suivant());
    }
    private void tour_riviere() {
        System.out.println("******************");
        System.out.println("**** Rivière *****");
        System.out.println("******************");
        tour_turn();
    }
    private void tour_encheres(Joueur premier_joueur) {
        Joueur joueur_fin = premier_joueur;
        Joueur joueur_actuel = premier_joueur;
        int mise;
        Carte.affiche(m_jeu_pt,"jeu");
        if (Joueur.nombre_de_joueurs_pouvant_relancer == 1) return;
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
            if (Joueur.nombre_de_joueurs_pouvant_relancer == 1) {
                System.out.println("Il n'y a plus qu'un seul joueur pouvant miser");
                break;
            }
        } while (joueur_actuel != joueur_fin);
    }
    private void abattage() {
        m_donneur.stream().forEach(joueur -> {
            if (joueur.get_etat() == Joueur.Etat.COUCHE) return;
            Carte.affiche(joueur.get_main(),joueur.get_nom());
        });

    }
}
