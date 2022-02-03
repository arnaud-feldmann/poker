package Jeu;

import java.util.Scanner;
import java.util.function.Supplier;

public class InterfaceUtilisateur {
    final protected static Scanner entree_terminal = new Scanner(System.in);
    protected static Supplier<String> test_mock_nextline = null;
    protected static boolean test_tour_manuel;
    protected static boolean test_is_fin_tour = false;

    public static <T> void println(T object) {
        System.out.println(object);
    }

    public static <T> void print(T object) {
        System.out.print(object);
    }

    public static void println() {
        System.out.println();
    }

    public static void prompt_fin_du_tour() {
        String res;
        test_is_fin_tour = true;
        println("validez (o/n) :");
        while (true) {
            print("> ");
            res = nextLine();
            if (res.equals("o") || res.equals("O")) break;
            else if (res.equals("n") || res.equals("N")) println("Ok j'attends un peu!");
            else println("Un peu de sérieux !");
        }
        test_is_fin_tour = false;
    }

    public static String nextLine() {
        String res;
        if (test_mock_nextline != null) {
            res = test_mock_nextline.get();
            println(res);
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
