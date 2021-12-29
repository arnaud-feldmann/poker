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
        assertTrue(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).equals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique)));
        assertFalse(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).equals(new Carte(Carte.Valeur.Trois, Carte.Couleur.Pique)));
        assertFalse(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).equals(new Carte(Carte.Valeur.Deux, Carte.Couleur.Coeur)));
        Carte c = null;
        assertFalse(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).equals(c));
        assertFalse(new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique).equals("String"));
    }

    @Test
    void testToString() {
        assertEquals(new Carte(Carte.Valeur.Sept, Carte.Couleur.Pique).toString(),
                "Sept de Pique");
    }
}