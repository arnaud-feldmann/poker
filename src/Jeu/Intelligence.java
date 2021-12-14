package Jeu;
import Cartes.Carte;
import Cartes.CollectionDeCartes;
import java.util.ArrayList;
import java.util.Scanner;

public interface Intelligence {
    int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min,
                             ArrayList<Carte> main,int cave,int mise,String nom_joueur);
}

class IntelligenceHumaine implements Intelligence {
    private final static Scanner entree_terminal = new Scanner(System.in);
    private static void prompt_tour_joueur(String nom_joueur) {
        System.out.println("----------------------------------------------");
        System.out.println("C'est le tour du joueur " + nom_joueur + " !");
        System.out.println("Appuyez sur ENTREE");
        while (true) {
            if (entree_terminal.hasNextLine() && entree_terminal.nextLine().isEmpty()) break;
        }
    }
    private static int prompt_relance(int relance_min,int relance_max) {
        int res;
        System.out.println("De combien voulez-vous relancer (de " +
                relance_min + " à " + relance_max + ") ?");
        while (true) {
            System.out.print("> ");
            try {
                res = Integer.parseInt(entree_terminal.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= relance_min && res <= relance_max) break;
            System.out.println("Un peu de sérieux !");
        }
        return res;
    }
    private static int prompt_action(boolean peut_relancer,boolean check) {
        int res;
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1) Me coucher");
        System.out.println("2) TAPIS !");
        if (check) System.out.println("3) Checker");
        else System.out.println("3) Suivre");
        if (peut_relancer) System.out.println("4) Relancer");
        while (true) {
            System.out.print("> ");
            try {
                res = Integer.parseInt(entree_terminal.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= 1 && res < 4 + (peut_relancer?1:0)) break;
            System.out.println("Un peu de sérieux !");
        }
        return res;
    }
    public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min,
                                    ArrayList<Carte> main,int cave,int mise,String nom_joueur) {
        int res;
        int relance_max = cave-mise_demandee;
        CollectionDeCartes collection = new CollectionDeCartes(main,jeu_pt);
        prompt_tour_joueur(nom_joueur);
        System.out.println("Votre mise actuelle est de " + mise + ".");
        System.out.println("Il vous reste " + cave + " dans votre cave.");
        System.out.println("Le pot actuel est de " + pot);
        System.out.println("La mise actuelle demandée en jeu est de " + mise_demandee + ".");
        collection.afficher();
        System.out.println("Votre probabilité indicative de l'emporter avec cette main est de " +
                Math.round(collection.probaVict(Joueur.nombre_de_joueurs()-1) * 100) + " %");
        int action = prompt_action(
                relance_min < relance_max,
                mise_demandee == mise);
        switch (action) {
            case 2:
                res = cave + mise;
                break;
            case 3:
                res = mise_demandee;
                break;
            case 4:
                res = mise_demandee + prompt_relance(relance_min,relance_max);
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }
}
/*

class IntelligenceArtificielle implements Intelligence {
    @Override
    public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min,
                             ArrayList<Carte> main,int cave,int mise,String nom_joueur) {
        r
    }
}*/
