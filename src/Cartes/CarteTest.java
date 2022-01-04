package Cartes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {

    @Test
    void compareTo() {
        assertEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).
                compareTo(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique)), 0);
        assertEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).
                compareTo(new Carte(Carte.Valeur.Deux, Carte.Couleur.Trefle)), 0);
        assertEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).
                compareTo(new Carte(Carte.Valeur.Trois, Carte.Couleur.Trefle)), -1);
        assertEquals(new Carte(Carte.Valeur.Trois, Carte.Couleur.Pique).
                compareTo(new Carte(Carte.Valeur.Deux, Carte.Couleur.Trefle)), 1);
        assertEquals(new Carte(Carte.Valeur.As, Carte.Couleur.Coeur).
                compareTo(new Carte(Carte.Valeur.Roi, Carte.Couleur.Trefle)), 1);
    }

    @Test
    void testEquals() {
        assertEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique), new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique));
        assertNotEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique), new Carte(Carte.Valeur.Trois, Carte.Couleur.Pique));
        assertNotEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique), new Carte(Carte.Valeur.Deux, Carte.Couleur.Coeur));
        assertNotEquals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique), null);
    }

    @Test
    void testToString() {
        assertEquals(new Carte(Carte.Valeur.Sept, Carte.Couleur.Pique).toString(),
                "Sept de Pique");
    }
}