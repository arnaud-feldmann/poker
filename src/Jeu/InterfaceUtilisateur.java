package Jeu;

import interfaceGraphique.InterfacePoker;

import java.util.Scanner;

public class InterfaceUtilisateur {
    protected static InterfacePoker interface_graphique;
    final protected  static Scanner entree_terminal = new Scanner(System.in);
    protected static boolean test_mode = false;

    public static <T> void println(T object) {
        if (test_mode) return;
        System.out.println(object);
    }
    
    protected static void println() {
        if (test_mode) return;
        System.out.println();
    }

    protected static String nextLine() {
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
