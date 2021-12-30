package Jeu;

import Cartes.PaquetDeCartes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    public void faire_que_des_tapis_perd_trois_joueurs_1() {
        PaquetDeCartes.set_seed(1);
        IntelligenceArtificielle.set_seed(1);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_2() {
        PaquetDeCartes.set_seed(2);
        IntelligenceArtificielle.set_seed(2);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_3() {
        PaquetDeCartes.set_seed(3);
        IntelligenceArtificielle.set_seed(3);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_deux_joueurs_1() {
        PaquetDeCartes.set_seed(4);
        IntelligenceArtificielle.set_seed(4);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 2000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_deux_joueurs_2() {
        PaquetDeCartes.set_seed(5);
        IntelligenceArtificielle.set_seed(4);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 2000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_deux_joueurs_3() {
        PaquetDeCartes.set_seed(6);
        IntelligenceArtificielle.set_seed(4);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 2000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_4() {
        PaquetDeCartes.set_seed(4);
        IntelligenceArtificielle.set_seed(4);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("2"); // que des tapis
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_1() {
        PaquetDeCartes.set_seed(1);
        IntelligenceArtificielle.set_seed(1);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }
    @Test
    public void faire_que_suivre_perd_trois_joueurs_2() {
        PaquetDeCartes.set_seed(2);
        IntelligenceArtificielle.set_seed(2);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_3() {
        PaquetDeCartes.set_seed(3);
        IntelligenceArtificielle.set_seed(3);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_4() {
        PaquetDeCartes.set_seed(4);
        IntelligenceArtificielle.set_seed(4);

        InterfaceUtilisateur.test_mock_nextline = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("3"); // que des suivi
            InterfaceUtilisateur.test_mock_nextline.add("o"); // validation à la fin du tour
        }
        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }
}