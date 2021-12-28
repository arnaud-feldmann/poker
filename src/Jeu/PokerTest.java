package Jeu;

import Cartes.PaquetDeCartes;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PokerTest {

    @Test
    void poker() {
        InterfaceUtilisateur.test_cacher_interface_graphique = true;
        InterfaceUtilisateur.test_tour_manuel = false;

        PaquetDeCartes.set_seed(1);
        IntelligenceArtificielle.set_seed(1);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0 ; i < 1000 ; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertFalse(Joueur.inc_donneur()); // Quelqu'un a gagné la partie.
        assertEquals(Joueur.donneur.get_cave(),3000); // On n'a pas d'argent disparu
    }
}