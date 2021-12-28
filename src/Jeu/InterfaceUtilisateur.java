package Jeu;

import interfaceGraphique.InterfacePoker;

import java.util.ArrayList;
import java.util.Scanner;

public class InterfaceUtilisateur {
    protected static InterfacePoker interface_graphique;
    final protected  static Scanner entree_terminal = new Scanner(System.in);
    protected static boolean test_desactiver_terminal = false;
    protected static ArrayList<String> test_mock_nextline = null;
    protected static boolean test_tour_manuel;

    public static <T> void println(T object) {
        if (test_desactiver_terminal) return;
        System.out.println(object);
    }

    public static <T> void print(T object) {
        if (test_desactiver_terminal) return;
        System.out.print(object);
    }
    
    protected static void println() {
        if (test_desactiver_terminal) return;
        System.out.println();
    }

    protected static String nextLine() {
        if (test_desactiver_terminal) return test_mock_nextline.remove(0);
        return entree_terminal.nextLine();
    }

    /* Bon, on va dire que les jetons sont immédiatement changés à la banque pour faire des jolis sets */
    protected static int[] jetons_ig(int montant) {
        int[] res = new int[5];
        int reste = montant;
        final int[] jetons = {500,100,25,5,1};
        for (int i = 0 ; i<jetons.length ; i++) {
            res[i] = reste/jetons[i];
            reste %= jetons[i];
        }
        return res;
    }
}
