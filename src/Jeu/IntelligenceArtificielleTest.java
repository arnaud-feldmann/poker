package Jeu;

import Cartes.PaquetDeCartes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class IntelligenceArtificielleTest {

    @BeforeEach
    public void before() {
        InterfaceUtilisateur.test_cacher_interface_graphique = true;
        InterfaceUtilisateur.test_arreter_si_humain_a_perdu = true;
    }

    @AfterEach
    public void after() {
        InterfaceUtilisateur.test_cacher_interface_graphique = false;
        InterfaceUtilisateur.test_arreter_si_humain_a_perdu = false;
    }

    @Test
    public void faire_que_des_tapis_perd() {
        boolean joueur_gagne;
        PaquetDeCartes.set_seed(1);
        IntelligenceArtificielle.set_seed(1);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.nombre_de_joueurs() == 1); // Le joueur ne gagne pas en faisant que des tapis
    }
}