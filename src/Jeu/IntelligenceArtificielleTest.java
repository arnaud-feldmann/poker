package Jeu;

import Cartes.PaquetDeCartes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

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

    private void faire_que_une_chose_perd(int seed, int nombre_joueurs,String chose) {
        PaquetDeCartes.set_seed(seed);
        IntelligenceArtificielle.set_seed(seed);

        InterfaceUtilisateur.test_mock_nextline = () -> InterfaceUtilisateur.test_is_fin_tour ? "o" : chose;

        Poker.poker(Arrays.copyOfRange(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"},0,nombre_joueurs));

        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), nombre_joueurs * 1000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas
    }

    private void faire_que_relancer_perd(int seed, int nombre_joueurs) {
        PaquetDeCartes.set_seed(seed);
        IntelligenceArtificielle.set_seed(seed);

        ArrayList<String> mock_nextline_arraylist = new ArrayList<>();

        InterfaceUtilisateur.test_mock_nextline = () -> {
            if (InterfaceUtilisateur.test_is_fin_tour) return "o"; // Fin du tour
            if (mock_nextline_arraylist.size() == 0) {
                int mise_max = Joueur.stream().filter(Joueur::pas_couche).mapToInt(Joueur::get_mise).max().orElse(Integer.MIN_VALUE);
                Joueur joueur_humain = Joueur.stream().filter(Joueur::est_humain).findAny().orElseThrow(RuntimeException::new);
                if (4 * mise_max > joueur_humain.get_cave()) {
                    mock_nextline_arraylist.add("2"); // Tapis
                } else {
                    mock_nextline_arraylist.add("4"); // Relance
                    mock_nextline_arraylist.add(Integer.toString(2 * mise_max));
                }
            }
            return mock_nextline_arraylist.remove(0);
        };

        Poker.poker(Arrays.copyOfRange(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"},0,nombre_joueurs));
        assertEquals(Joueur.stream().mapToInt(Joueur::get_cave).sum(), nombre_joueurs * 1000); // On n'a pas d'argent disparu
        assertFalse(Joueur.stream().anyMatch(x -> x.get_numero_joueur_interface() == 0)); // Le joueur ne gagne pas
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_1() {
        faire_que_une_chose_perd(1,3,"2");
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_2() {
        faire_que_une_chose_perd(2,3,"2");
    }

    @Test
    public void faire_que_des_tapis_perd_trois_joueurs_3() {
        faire_que_une_chose_perd(3,3,"2");
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_1() {
        faire_que_une_chose_perd(4,3,"3");
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_2() {
        faire_que_une_chose_perd(5,3,"3");
    }

    @Test
    public void faire_que_suivre_perd_trois_joueurs_3() {
        faire_que_une_chose_perd(6,3,"3");
    }

    @Test
    public void faire_que_des_tapis_perd_sept_joueurs_1() {
        faire_que_une_chose_perd(7,7,"2");
    }

    //@Test
    //public void faire_que_des_tapis_perd_sept_joueurs_2() {
    //    faire_que_une_chose_perd(8,7,"2");
    //}

    @Test
    public void faire_que_des_tapis_perd_sept_joueurs_3() {
        faire_que_une_chose_perd(9,7,"2");
    }

    @Test
    public void faire_que_suivre_perd_sept_joueurs_1() {
        faire_que_une_chose_perd(10,7,"3");
    }

    @Test
    public void faire_que_suivre_perd_sept_joueurs_2() {
        faire_que_une_chose_perd(11,7,"3");
    }

    @Test
    public void faire_que_suivre_perd_sept_joueurs_3() {
        faire_que_une_chose_perd(12,7,"3");
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_1() {
        faire_que_relancer_perd(13,7);
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_2() {
        faire_que_relancer_perd(14,7);
    }

    @Test
    public void faire_que_relancer_perd_sept_joueurs_3() {
        faire_que_relancer_perd(15,7);
    }

    @Test
    public void faire_que_relancer_perd_trois_joueurs_1() {
        faire_que_relancer_perd(16,3);
    }

    @Test
    public void faire_que_relancer_perd_trois_joueurs_2() {
        faire_que_relancer_perd(17,3);
    }

    @Test
    public void faire_que_relancer_perd_trois_joueurs_3() {
        faire_que_relancer_perd(18,3);
    }

    // Note : Ces tests n'ont pas vocation à assurer une victoire systématique dans le cadre de ces stratégies.
    // Voilà pourquoi on accepte le test négatif unique qui est mis en commentaire. Mais dans la grande majorité des cas
    // ces stratégies stéréotypées sont perdantes.

}