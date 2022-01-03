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
    }

    @AfterEach
    public void after() {
        InterfaceUtilisateur.test_cacher_interface_graphique = false;
        InterfaceUtilisateur.test_mock_nextline = null;
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_1() {
        PaquetDeCartes.set_seed(1);
        IntelligenceArtificielle.set_seed(1);

        InterfaceUtilisateur.test_mock_nextline = () -> "2"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_2() {
        PaquetDeCartes.set_seed(2);
        IntelligenceArtificielle.set_seed(2);

        InterfaceUtilisateur.test_mock_nextline = () -> "2"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_3() {
        PaquetDeCartes.set_seed(3);
        IntelligenceArtificielle.set_seed(3);

        InterfaceUtilisateur.test_mock_nextline = () -> "2"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_4() {
        PaquetDeCartes.set_seed(4);
        IntelligenceArtificielle.set_seed(4);

        InterfaceUtilisateur.test_mock_nextline = () -> "2"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_1() {
        PaquetDeCartes.set_seed(5);
        IntelligenceArtificielle.set_seed(5);

        InterfaceUtilisateur.test_mock_nextline = () -> "3"; // que des suivi

        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_2() {
        PaquetDeCartes.set_seed(6);
        IntelligenceArtificielle.set_seed(6);

        InterfaceUtilisateur.test_mock_nextline = () -> "3"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_3() {
        PaquetDeCartes.set_seed(7);
        IntelligenceArtificielle.set_seed(7);

        InterfaceUtilisateur.test_mock_nextline = () -> "3"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_4() {
        PaquetDeCartes.set_seed(8);
        IntelligenceArtificielle.set_seed(8);

        InterfaceUtilisateur.test_mock_nextline = () -> "3"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 3000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }


    @Test
    public void faire_que_suivre_perd_sept_joueurs() {
        PaquetDeCartes.set_seed(9);
        IntelligenceArtificielle.set_seed(9);

        InterfaceUtilisateur.test_mock_nextline = () -> "3"; // que des suivi

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 7000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_des_tapis_perd_sept_joueurs() {
        PaquetDeCartes.set_seed(10);
        IntelligenceArtificielle.set_seed(10);

        InterfaceUtilisateur.test_mock_nextline = () -> "2";

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 7000); // On n'a pas d'argent disparu

        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_1() {
        PaquetDeCartes.set_seed(11);
        IntelligenceArtificielle.set_seed(11);

        ArrayList<String> mock_nextline_arraylist = new ArrayList<>();

        InterfaceUtilisateur.test_mock_nextline = () -> {
            if (mock_nextline_arraylist.size() == 0) {
                int mise_max = Joueur.stream().filter(Joueur::pas_couche).mapToInt(Joueur::get_mise).max().orElse(Integer.MIN_VALUE);
                Joueur joueur_humain = Joueur.stream().filter(Joueur::est_humain).findAny().orElseThrow(RuntimeException::new);
                if (4 * mise_max > joueur_humain.get_cave()) {
                    mock_nextline_arraylist.add("2"); // Tapis
                } else {
                    mock_nextline_arraylist.add("4");
                    mock_nextline_arraylist.add(Integer.toString(2 * mise_max));
                }
            }
            return mock_nextline_arraylist.remove(0);
        };

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 7000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_2() {
        PaquetDeCartes.set_seed(12);
        IntelligenceArtificielle.set_seed(12);

        ArrayList<String> mock_nextline_arraylist = new ArrayList<>();

        InterfaceUtilisateur.test_mock_nextline = () -> {
            if (mock_nextline_arraylist.size() == 0) {
                int mise_max = Joueur.stream().filter(Joueur::pas_couche).mapToInt(Joueur::get_mise).max().orElse(Integer.MIN_VALUE);
                Joueur joueur_humain = Joueur.stream().filter(Joueur::est_humain).findAny().orElseThrow(RuntimeException::new);
                if (4 * mise_max > joueur_humain.get_cave()) {
                    mock_nextline_arraylist.add("2"); // Tapis
                } else {
                    mock_nextline_arraylist.add("4");
                    mock_nextline_arraylist.add(Integer.toString(2 * mise_max));
                }
            }
            return mock_nextline_arraylist.remove(0);
        };

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 7000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_3() {
        PaquetDeCartes.set_seed(13);
        IntelligenceArtificielle.set_seed(13);

        ArrayList<String> mock_nextline_arraylist = new ArrayList<>();

        InterfaceUtilisateur.test_mock_nextline = () -> {
            if (mock_nextline_arraylist.size() == 0) {
                int mise_max = Joueur.stream().filter(Joueur::pas_couche).mapToInt(Joueur::get_mise).max().orElse(Integer.MIN_VALUE);
                Joueur joueur_humain = Joueur.stream().filter(Joueur::est_humain).findAny().orElseThrow(RuntimeException::new);
                if (4 * mise_max > joueur_humain.get_cave()) {
                    mock_nextline_arraylist.add("2"); // Tapis
                } else {
                    mock_nextline_arraylist.add("4");
                    mock_nextline_arraylist.add(Integer.toString(2 * mise_max));
                }
            }
            return mock_nextline_arraylist.remove(0);
        };

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 7000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_4() {
        PaquetDeCartes.set_seed(14);
        IntelligenceArtificielle.set_seed(14);

        ArrayList<String> mock_nextline_arraylist = new ArrayList<>();

        InterfaceUtilisateur.test_mock_nextline = () -> {
            if (mock_nextline_arraylist.size() == 0) {
                int mise_max = Joueur.stream().filter(Joueur::pas_couche).mapToInt(Joueur::get_mise).max().orElse(Integer.MIN_VALUE);
                Joueur joueur_humain = Joueur.stream().filter(Joueur::est_humain).findAny().orElseThrow(RuntimeException::new);
                if (4 * mise_max > joueur_humain.get_cave()) {
                    mock_nextline_arraylist.add("2"); // Tapis
                } else {
                    mock_nextline_arraylist.add("4");
                    mock_nextline_arraylist.add(Integer.toString(2 * mise_max));
                }
            }
            return mock_nextline_arraylist.remove(0);
        };

        Poker.poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), 7000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas en faisant que des tapis
    }

    // Note : à trois joueur le relanceur sy

}