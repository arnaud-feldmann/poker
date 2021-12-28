package Jeu;

import Cartes.Carte;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IntelligenceHumaineTest {
    public static void init_test() {
        InterfaceUtilisateur.test_cacher_interface_graphique = true;
        InterfaceUtilisateur.test_tour_manuel = false;
        String[] noms_joueurs = new String[] {"Arnaud","Loup","Ludo","Elodie","Kerry","Bettie","Peppa Pig"};
        Poker.init(noms_joueurs,1000);
    }
    @Test
    void demander_mise() {
        init_test();
        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();

        IntelligenceHumaine intelligence_humaine = new IntelligenceHumaine("Arnaud");
        ArrayList<Carte> jeu_pt = new ArrayList<>();
        jeu_pt.add(new Carte(Carte.Valeur.As, Carte.Couleur.Pique));
        jeu_pt.add(new Carte(Carte.Valeur.As, Carte.Couleur.Coeur));
        jeu_pt.add(new Carte(Carte.Valeur.As, Carte.Couleur.Trefle));
        jeu_pt.add(new Carte(Carte.Valeur.As, Carte.Couleur.Carreau));
        jeu_pt.add(new Carte(Carte.Valeur.Deux, Carte.Couleur.Carreau));
        ArrayList<Carte> main = new ArrayList<>();
        main.add(new Carte(Carte.Valeur.Roi, Carte.Couleur.Pique));
        main.add(new Carte(Carte.Valeur.Roi, Carte.Couleur.Carreau));
        InterfaceUtilisateur.test_mock_nextline.add("a"); //choix ignoré car pas le bon format
        InterfaceUtilisateur.test_mock_nextline.add("0"); //choix ignoré car trop petit
        InterfaceUtilisateur.test_mock_nextline.add("5"); //choix ignoré car trop grand
        InterfaceUtilisateur.test_mock_nextline.add("1");
        assertEquals(intelligence_humaine.demander_mise(200,jeu_pt,2000,20,main,300,0),-1);
        InterfaceUtilisateur.test_mock_nextline.add("2");
        assertEquals(intelligence_humaine.demander_mise(200,jeu_pt,2000,20,main,300,0),300);
        InterfaceUtilisateur.test_mock_nextline.add("3");
        assertEquals(intelligence_humaine.demander_mise(200,jeu_pt,2000,20,main,300,0),200);
        InterfaceUtilisateur.test_mock_nextline.add("3");
        assertEquals(intelligence_humaine.demander_mise(100,jeu_pt,2000,20,main,300,100),100);
        InterfaceUtilisateur.test_mock_nextline.add("4");
        InterfaceUtilisateur.test_mock_nextline.add("a"); //mise ignorée car pas le bon format
        InterfaceUtilisateur.test_mock_nextline.add("10"); //mise ignorée car inférieure à la relance minimale
        InterfaceUtilisateur.test_mock_nextline.add("30");
        assertEquals(intelligence_humaine.demander_mise(100,jeu_pt,2000,20,main,300,100),130);
    }
}