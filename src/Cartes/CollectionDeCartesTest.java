package Cartes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CollectionDeCartesTest {
    @Test
    void probaVict() {
        PaquetDeCartes.set_seed(1);
        ArrayList<Carte> main = new ArrayList<>();
        main.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        main.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        CollectionDeCartes c = new CollectionDeCartes(main,new ArrayList<>());
        assertEquals(c.probaVict(2)*100,74,3);
        assertEquals(c.probaVict(3)*100,64,3);
        assertEquals(c.probaVict(4)*100,57,3);
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.As,Carte.Couleur.Coeur));
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        assertEquals(c.probaVict(2)*100,93,3);
        assertEquals(c.probaVict(3)*100,90,3);
        assertEquals(c.probaVict(4)*100,87,3);
        main = new ArrayList<>();
        main.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        main.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c = new CollectionDeCartes(main,new ArrayList<>());
        assertEquals(c.probaVict(2)*100,33,3);
        assertEquals(c.probaVict(3)*100,25,3);
        assertEquals(c.probaVict(4)*100,20,3);
    }
    @Test
    void nombre_de_carte() {
        ArrayList<Carte> main = new ArrayList<>();
        main.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        main.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        CollectionDeCartes c = new CollectionDeCartes(main,new ArrayList<>());
        assertEquals(c.nombre_de_cartes(),2);
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        assertEquals(c.nombre_de_cartes(),3);
    }
}