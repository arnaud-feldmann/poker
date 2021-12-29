package Cartes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaquetDeCartesTest {
    @Test
    void creation() {
        PaquetDeCartes paquet;

        paquet = new PaquetDeCartes();
        assertEquals(paquet.nombre_de_cartes(), 52);
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Trois, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Quatre, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Cinq, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Six, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Sept, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Huit, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Neuf, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Dix, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Valet, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Dame, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Roi, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.As, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Coeur));
        assertEquals(paquet.nombre_de_cartes(), 38);
    }

    @Test
    void melanger() {
        PaquetDeCartes paquet;
        paquet = new PaquetDeCartes();
        PaquetDeCartes.set_seed(1);
        paquet.melanger_cartes();
        assertEquals(paquet.nombre_de_cartes(), 52);

        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Dame, Carte.Couleur.Trefle));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Cinq, Carte.Couleur.Coeur));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Roi, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Huit, Carte.Couleur.Carreau));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Roi, Carte.Couleur.Coeur));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Dame, Carte.Couleur.Coeur));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Valet, Carte.Couleur.Trefle));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Sept, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Quatre, Carte.Couleur.Trefle));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Huit, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Six, Carte.Couleur.Coeur));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Carreau));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Valet, Carte.Couleur.Carreau));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Trois, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Sept, Carte.Couleur.Trefle));
    }

    @Test
    void bruler() {
        PaquetDeCartes paquet;
        paquet = new PaquetDeCartes();
        paquet.bruler_une_carte();
        paquet.bruler_une_carte();
        assertEquals(paquet.nombre_de_cartes(), 50);
    }

    @Test
    void reveler_une_carte() {
        PaquetDeCartes paquet;
        paquet = new PaquetDeCartes();
        assertEquals(paquet.reveler_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique));
        assertEquals(paquet.reveler_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique));
        assertEquals(paquet.nombre_de_cartes(), 52);
    }

    @Test
    void retirer_des_cartes_du_jeu() {
        PaquetDeCartes paquet;
        paquet = new PaquetDeCartes();
        ArrayList<Carte> cartes = new ArrayList<>();
        cartes.add(new Carte(Carte.Valeur.Trois, Carte.Couleur.Pique));
        cartes.add(new Carte(Carte.Valeur.Cinq, Carte.Couleur.Pique));
        cartes.add(new Carte(Carte.Valeur.As, Carte.Couleur.Pique));
        cartes.add(new Carte(Carte.Valeur.Dix, Carte.Couleur.Pique));
        paquet.retirer_des_cartes_du_jeu(cartes);
        assertEquals(paquet.nombre_de_cartes(), 48);
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Quatre, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Six, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Sept, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Huit, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Neuf, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Valet, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Dame, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Roi, Carte.Couleur.Pique));
        assertEquals(paquet.piocher_une_carte(), new Carte(Carte.Valeur.Deux, Carte.Couleur.Coeur));

    }
}