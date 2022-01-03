package Jeu;

import interfaceGraphique.InterfacePoker;

import java.util.Scanner;
import java.util.function.Supplier;

public class InterfaceUtilisateur {
    final protected static Scanner entree_terminal = new Scanner(System.in);
    protected static InterfacePoker interface_graphique;
    protected static Supplier<String> test_mock_nextline = null;
    protected static boolean test_tour_manuel;
    protected static boolean test_cacher_interface_graphique;

    public static <T> void println(T object) {
        System.out.println(object);
    }

    public static <T> void print(T object) {
        System.out.print(object);
    }

    protected static void println() {
        System.out.println();
    }

    protected static String nextLine() {
        String res;
        if (test_mock_nextline != null) {
            return test_mock_nextline.get();
        } else {
            res = entree_terminal.nextLine();
        }
        return res;
    }

    /* Bon, on va dire que les jetons sont immédiatement changés à la banque pour faire des jolis sets */
    protected static int[] jetons_ig(int montant) {
        int[] res = new int[5];
        int reste = montant;
        final int[] jetons = {500, 100, 25, 5, 1};
        for (int i = 0; i < jetons.length; i++) {
            res[i] = reste / jetons[i];
            reste %= jetons[i];
        }
        return res;
    }
}
