package Jeu;

import Cartes.Carte;
import Cartes.PaquetDeCartes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TourPokerTest {
    public static TourPoker init_test() {
        InterfaceUtilisateur.test_tour_manuel = true;
        String[] noms_joueurs = new String[] {"Arnaud","Loup","Ludo","Elodie","Kerry","Bettie","Peppa Pig"};
        Poker.init(noms_joueurs,1000);
        return new TourPoker(0);
    }
    @Test
    void tout_le_monde_couche_sauf_un() {
        PaquetDeCartes.set_seed(1);
        TourPoker tour = init_test();
        Joueur arnaud = Joueur.donneur;
        Joueur peppa_pig = arnaud.get_joueur_suivant();
        Joueur bettie = peppa_pig.get_joueur_suivant();
        Joueur kerry = bettie.get_joueur_suivant();
        Joueur elodie = kerry.get_joueur_suivant();
        Joueur ludo = elodie.get_joueur_suivant();
        Joueur loup = ludo.get_joueur_suivant();

        // Les mises
        elodie.ajouter_mise(400,tour.get_pot_pt());
        arnaud.coucher();
        peppa_pig.coucher();
        bettie.coucher();
        kerry.coucher();
        ludo.coucher();
        loup.coucher();

        // Tests des états d'après mise
        assertEquals(arnaud.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(loup.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(ludo.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(elodie.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(kerry.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(bettie.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(peppa_pig.get_etat(), Joueur.Etat.COUCHE);

        // Répartition des gains
        tour.repartition_gains();

        // Tests de répartition
        assertEquals(kerry.get_cave(),1000);
        assertEquals(loup.get_cave(),1000);
        assertEquals(elodie.get_cave(),1000);
        assertEquals(arnaud.get_cave(),1000);
        assertEquals(ludo.get_cave(),1000);
        assertEquals(peppa_pig.get_cave(),1000);
        assertEquals(bettie.get_cave(),1000);

        // Banqueroute
        tour.banqueroute();

        // Tests des banqueroutes
        assertEquals(arnaud.get_joueur_suivant(), peppa_pig);
        assertEquals(peppa_pig.get_joueur_suivant(), bettie);
        assertEquals(bettie.get_joueur_suivant(), kerry);
        assertEquals(kerry.get_joueur_suivant(), elodie);
        assertEquals(elodie.get_joueur_suivant(), ludo);
        assertEquals(ludo.get_joueur_suivant(), loup);
        assertEquals(loup.get_joueur_suivant(), arnaud);
        assertTrue(Joueur.inc_donneur());

        // Fermeture interface graphique
        InterfaceUtilisateur.interface_graphique.ferme();
    }
    @Test
    void tapis_repartition_et_banqueroute() {
        PaquetDeCartes.set_seed(2);
        TourPoker tour = init_test();
        Joueur arnaud = Joueur.donneur;
        Joueur peppa_pig = arnaud.get_joueur_suivant();
        Joueur bettie = peppa_pig.get_joueur_suivant();
        Joueur kerry = bettie.get_joueur_suivant();
        Joueur elodie = kerry.get_joueur_suivant();
        Joueur ludo = elodie.get_joueur_suivant();
        Joueur loup = ludo.get_joueur_suivant();

        // Ce qu'il s'est passé avant dans le jeu
        loup.encaisser(500);
        bettie.encaisser(-700);
        kerry.encaisser(-300);
        peppa_pig.encaisser(500);

        // Les mises
        arnaud.ajouter_mise(1000,tour.get_pot_pt());
        loup.ajouter_mise(1500,tour.get_pot_pt());
        ludo.ajouter_mise(1000,tour.get_pot_pt());
        elodie.coucher();
        kerry.ajouter_mise(700,tour.get_pot_pt());
        bettie.ajouter_mise(300,tour.get_pot_pt());
        peppa_pig.ajouter_mise(1500, tour.get_pot_pt());

        // Tests des états d'après mise
        assertEquals(arnaud.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(loup.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(ludo.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(elodie.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(kerry.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(bettie.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(peppa_pig.get_etat(), Joueur.Etat.TAPIS);

        // Répartition des gains
        tour.repartition_gains();

        // Tests de répartition
        assertEquals(kerry.get_cave(),3800);
        assertEquals(loup.get_cave(),2200);
        assertEquals(elodie.get_cave(),1000);
        assertEquals(arnaud.get_cave(),0);
        assertEquals(ludo.get_cave(),0);
        assertEquals(peppa_pig.get_cave(),0);
        assertEquals(bettie.get_cave(),0);

        // Banqueroute
        tour.banqueroute();

        // Tests des banqueroutes
        assertEquals(kerry.get_joueur_suivant(), elodie);
        assertEquals(elodie.get_joueur_suivant(), loup);
        assertEquals(loup.get_joueur_suivant(), kerry);
        assertEquals(Joueur.donneur,kerry);
        assertTrue(Joueur.inc_donneur());

        // Fermeture interface graphique
        InterfaceUtilisateur.interface_graphique.ferme();
    }

    @Test
    void un_seul_gagnant() {
        PaquetDeCartes.set_seed(3);
        TourPoker tour = init_test();
        Joueur arnaud = Joueur.donneur;
        Joueur peppa_pig = arnaud.get_joueur_suivant();
        Joueur bettie = peppa_pig.get_joueur_suivant();
        Joueur kerry = bettie.get_joueur_suivant();
        Joueur elodie = kerry.get_joueur_suivant();
        Joueur ludo = elodie.get_joueur_suivant();
        Joueur loup = ludo.get_joueur_suivant();

        // Les mises
        arnaud.coucher();
        loup.ajouter_mise(200,tour.get_pot_pt());
        ludo.ajouter_mise(200,tour.get_pot_pt());
        elodie.ajouter_mise(200,tour.get_pot_pt());
        kerry.coucher();
        bettie.ajouter_mise(200,tour.get_pot_pt());
        peppa_pig.ajouter_mise(200, tour.get_pot_pt());

        // Tests des états d'après mise
        assertEquals(arnaud.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(loup.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(ludo.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(elodie.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(kerry.get_etat(), Joueur.Etat.COUCHE);
        assertEquals(bettie.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(peppa_pig.get_etat(), Joueur.Etat.PEUT_MISER);

        // Répartition des gains
        tour.repartition_gains();

        // Tests de répartition
        assertEquals(kerry.get_cave(),1000);
        assertEquals(loup.get_cave(),800);
        assertEquals(elodie.get_cave(),1800);
        assertEquals(arnaud.get_cave(),1000);
        assertEquals(ludo.get_cave(),800);
        assertEquals(peppa_pig.get_cave(),800);
        assertEquals(bettie.get_cave(),800);

        // Banqueroute
        tour.banqueroute();

        // Tests des banqueroutes
        assertEquals(arnaud.get_joueur_suivant(), peppa_pig);
        assertEquals(peppa_pig.get_joueur_suivant(), bettie);
        assertEquals(bettie.get_joueur_suivant(), kerry);
        assertEquals(kerry.get_joueur_suivant(), elodie);
        assertEquals(elodie.get_joueur_suivant(), ludo);
        assertEquals(ludo.get_joueur_suivant(), loup);
        assertEquals(loup.get_joueur_suivant(), arnaud);
        assertEquals(Joueur.donneur,arnaud);
        assertTrue(Joueur.inc_donneur());

        // Fermeture interface graphique
        InterfaceUtilisateur.interface_graphique.ferme();
    }

    @Test
    void egalite() {
        PaquetDeCartes.set_seed(4);
        TourPoker tour = init_test();
        Joueur arnaud = Joueur.donneur;
        Joueur peppa_pig = arnaud.get_joueur_suivant();
        Joueur bettie = peppa_pig.get_joueur_suivant();
        Joueur kerry = bettie.get_joueur_suivant();
        Joueur elodie = kerry.get_joueur_suivant();
        Joueur ludo = elodie.get_joueur_suivant();
        Joueur loup = ludo.get_joueur_suivant();

        // Truquage des mains pour avoir égalité
        peppa_pig.get_main().clear();
        peppa_pig.get_main().add(new Carte(Carte.Valeur.Quatre, Carte.Couleur.Carreau));
        peppa_pig.get_main().add(new Carte(Carte.Valeur.Sept, Carte.Couleur.Carreau));
        bettie.get_main().clear();
        bettie.get_main().add(new Carte(Carte.Valeur.Quatre, Carte.Couleur.Coeur));
        bettie.get_main().add(new Carte(Carte.Valeur.Sept, Carte.Couleur.Coeur));

        // Les mises
        arnaud.ajouter_mise(200,tour.get_pot_pt());
        loup.ajouter_mise(200,tour.get_pot_pt());
        ludo.ajouter_mise(200,tour.get_pot_pt());
        elodie.ajouter_mise(200,tour.get_pot_pt());
        kerry.ajouter_mise(200,tour.get_pot_pt());
        bettie.ajouter_mise(200,tour.get_pot_pt());
        peppa_pig.ajouter_mise(200, tour.get_pot_pt());

        // Tests des états d'après mise
        assertEquals(arnaud.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(loup.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(ludo.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(elodie.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(kerry.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(bettie.get_etat(), Joueur.Etat.PEUT_MISER);
        assertEquals(peppa_pig.get_etat(), Joueur.Etat.PEUT_MISER);

        // Répartition des gains
        tour.repartition_gains();

        // Tests de répartition
        assertEquals(kerry.get_cave(),800);
        assertEquals(loup.get_cave(),800);
        assertEquals(elodie.get_cave(),800);
        assertEquals(arnaud.get_cave(),800);
        assertEquals(ludo.get_cave(),800);
        assertEquals(peppa_pig.get_cave(),1500);
        assertEquals(bettie.get_cave(),1500);

        // Banqueroute
        tour.banqueroute();

        // Tests des banqueroutes
        assertEquals(arnaud.get_joueur_suivant(), peppa_pig);
        assertEquals(peppa_pig.get_joueur_suivant(), bettie);
        assertEquals(bettie.get_joueur_suivant(), kerry);
        assertEquals(kerry.get_joueur_suivant(), elodie);
        assertEquals(elodie.get_joueur_suivant(), ludo);
        assertEquals(ludo.get_joueur_suivant(), loup);
        assertEquals(loup.get_joueur_suivant(), arnaud);
        assertEquals(Joueur.donneur,arnaud);
        assertTrue(Joueur.inc_donneur());

        // Fermeture interface graphique
        InterfaceUtilisateur.interface_graphique.ferme();
    }

    @Test
    void gagne_le_jeu() {
        PaquetDeCartes.set_seed(8);
        TourPoker tour = init_test();
        Joueur arnaud = Joueur.donneur;
        Joueur peppa_pig = arnaud.get_joueur_suivant();
        Joueur bettie = peppa_pig.get_joueur_suivant();
        Joueur kerry = bettie.get_joueur_suivant();
        Joueur elodie = kerry.get_joueur_suivant();
        Joueur ludo = elodie.get_joueur_suivant();
        Joueur loup = ludo.get_joueur_suivant();

        // Les mises
        arnaud.ajouter_mise(1000,tour.get_pot_pt());
        loup.ajouter_mise(1000,tour.get_pot_pt());
        ludo.ajouter_mise(1000,tour.get_pot_pt());
        elodie.ajouter_mise(1000,tour.get_pot_pt());
        kerry.ajouter_mise(1000,tour.get_pot_pt());
        bettie.ajouter_mise(1000,tour.get_pot_pt());
        peppa_pig.ajouter_mise(1000, tour.get_pot_pt());

        // Tests des états d'après mise
        assertEquals(arnaud.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(loup.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(ludo.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(elodie.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(kerry.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(bettie.get_etat(), Joueur.Etat.TAPIS);
        assertEquals(peppa_pig.get_etat(), Joueur.Etat.TAPIS);

        // Répartition des gains
        tour.repartition_gains();

        // Tests de répartition
        assertEquals(kerry.get_cave(),0);
        assertEquals(loup.get_cave(),0);
        assertEquals(elodie.get_cave(),7000);
        assertEquals(arnaud.get_cave(),0);
        assertEquals(ludo.get_cave(),0);
        assertEquals(peppa_pig.get_cave(),0);
        assertEquals(bettie.get_cave(),0);

        // Banqueroute
        tour.banqueroute();

        // Tests des banqueroutes
        assertEquals(elodie.get_joueur_suivant(), elodie);
        assertEquals(Joueur.donneur, elodie);
        assertFalse(Joueur.inc_donneur());

        // Fermeture interface graphique
        InterfaceUtilisateur.interface_graphique.ferme();
    }
}