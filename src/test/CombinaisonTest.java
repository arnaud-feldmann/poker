package test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import Cartes.*;
import static org.junit.jupiter.api.Assertions.*;
class CombinaisonTest {

    @Test
    void quinte_flush_royale() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.As, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dix, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre, Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.QUINTEFLUSHROYALE);
        assertEquals(comb.get_rangs(), new ArrayList<Carte.Valeur>());
    }

    @Test
    void quinte_flush() {
        ArrayList<Carte> c;
        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dix,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.QUINTEFLUSH);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Roi);
        assertEquals(comb.get_rangs(), res);

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Cinq,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Trois,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.QUINTEFLUSH);
        res = new ArrayList<>();
        res.add(Carte.Valeur.Cinq);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void carre() {
        ArrayList<Carte> c;
        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Pique));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.CARRE);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Roi);
        res.add(Carte.Valeur.As);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void main_pleine() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Pique));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.MAINPLEINE);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Roi);
        res.add(Carte.Valeur.As);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void couleur() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Dix,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Cinq,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.COULEUR);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Dix);
        res.add(Carte.Valeur.Huit);
        res.add(Carte.Valeur.Sept);
        res.add(Carte.Valeur.Cinq);
        res.add(Carte.Valeur.Deux);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void suite() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Cinq,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.SUITE);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Neuf);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void brelan() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.BRELAN);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Six);
        res.add(Carte.Valeur.Neuf);
        res.add(Carte.Valeur.Huit);
        assertEquals(comb.get_rangs(), res);
    }
    @Test
    void double_paire() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Carreau));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.DOUBLEPAIRE);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Huit);
        res.add(Carte.Valeur.Sept);
        res.add(Carte.Valeur.Six);
        assertEquals(comb.get_rangs(), res);

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.DOUBLEPAIRE);
        assertEquals(comb.get_rangs(), res);

    }
    @Test
    void paire() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.PAIRE);

        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.Huit);
        res.add(Carte.Valeur.As);
        res.add(Carte.Valeur.Dame);
        res.add(Carte.Valeur.Valet);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void carte_haute() {

        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.get_niveau(), Combinaison.Niveau.CARTEHAUTE);
        ArrayList<Carte.Valeur> res = new ArrayList<>();
        res.add(Carte.Valeur.As);
        res.add(Carte.Valeur.Roi);
        res.add(Carte.Valeur.Dame);
        res.add(Carte.Valeur.Valet);
        res.add(Carte.Valeur.Huit);
        assertEquals(comb.get_rangs(), res);
    }

    @Test
    void compareTo() {
        ArrayList<Carte> c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.As, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dix, Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre, Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        assertEquals(comb.compareTo(comb),0);

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dix,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        Combinaison comb2 = new Combinaison(c);
        assertEquals(comb.compareTo(comb2),1);
        assertEquals(comb2.compareTo(comb),-1);
    }
}