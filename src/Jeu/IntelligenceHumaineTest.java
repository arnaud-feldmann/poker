package Jeu;

import Cartes.Carte;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntelligenceHumaineTest {
    @BeforeEach
    public void before() {
        String[] noms_joueurs = new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"};
        Poker.init(noms_joueurs, 1000);
    }

    @AfterEach
    public void after() {
        InterfaceUtilisateur.test_mock_nextline = null;
    }

    @Test
    void demander_mise() {

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

        ArrayList<String> mock_nextline_arraylist = new ArrayList<>();

        InterfaceUtilisateur.test_mock_nextline = () -> mock_nextline_arraylist.remove(0);

        mock_nextline_arraylist.add("a"); //choix ignoré car pas le bon format
        mock_nextline_arraylist.add("0"); //choix ignoré car trop petit
        mock_nextline_arraylist.add("5"); //choix ignoré car trop grand
        mock_nextline_arraylist.add("1");
        assertEquals(intelligence_humaine.demander_mise(200, jeu_pt, 2000, 20, main, 300, 0), -1);
        mock_nextline_arraylist.add("2");
        assertEquals(intelligence_humaine.demander_mise(200, jeu_pt, 2000, 20, main, 300, 0), 300);
        mock_nextline_arraylist.add("3");
        assertEquals(intelligence_humaine.demander_mise(200, jeu_pt, 2000, 20, main, 300, 0), 200);
        mock_nextline_arraylist.add("3");
        assertEquals(intelligence_humaine.demander_mise(100, jeu_pt, 2000, 20, main, 300, 100), 100);
        mock_nextline_arraylist.add("4");
        mock_nextline_arraylist.add("a"); //mise ignorée car pas le bon format
        mock_nextline_arraylist.add("10"); //mise ignorée car inférieure à la relance minimale
        mock_nextline_arraylist.add("30");
        assertEquals(intelligence_humaine.demander_mise(100, jeu_pt, 2000, 20, main, 300, 100), 130);
    }
}