package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;
import Cartes.PaquetDeCartes;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

class ComplementMiseNegatifException extends IllegalArgumentException {
    ComplementMiseNegatifException() {
        super("Un complément de mise ne peut pas être négatif.");
    }
}

public class Joueur {
    static int nombre_de_joueurs;
    public static Joueur donneur;
    public enum Etat {PEUT_MISER,TAPIS,COUCHE}
    private final static Scanner entree_terminal = new Scanner(System.in);
    private final String m_nom_joueur;
    private int m_cave;
    private Joueur m_joueur_suivant;
    private int m_mise;
    private ArrayList<Carte> m_main;
    private Etat m_etat;
    public static Stream<Joueur> stream() {
        Joueur joueur_temp = donneur;
        Stream.Builder<Joueur> builder = Stream.builder();
        for (int i = 0 ; i < Joueur.nombre_de_joueurs ; i++) {
            builder.add(joueur_temp);
            joueur_temp = joueur_temp.get_joueur_suivant();
        }
        return builder.build();
    }
    public static int nombre_de_joueurs_pouvant_relancer() {
        return Math.toIntExact(
                stream()
                        .filter(Joueur::pas_couche)
                        .filter(Joueur::pas_tapis)
                        .count()
        );
    }
    Joueur(String nom_joueur,int cave,Joueur joueur_suivant) {
        m_nom_joueur = nom_joueur;
        m_main = null;
        m_cave = cave;
        m_joueur_suivant = joueur_suivant;
    }
    protected void init_joueur(PaquetDeCartes paquet) {
        ArrayList<Carte> main = new ArrayList<>();
        main.add(paquet.piocher_une_carte());
        main.add(paquet.piocher_une_carte());
        m_main = new ArrayList<>(main);
        m_etat = Etat.PEUT_MISER;
        m_mise = 0;
    }
    ArrayList<Carte> get_main() {
        return m_main;
    }
    int get_cave() {
        return m_cave;
    }
    CollectionDeCartes get_collection(ArrayList<Carte> m_jeu_pt) {
        return new CollectionDeCartes(get_main(),m_jeu_pt);
    }
    public int get_mise() {
        return m_mise;
    }
    public boolean pas_couche() {
        return m_etat != Etat.COUCHE;
    }
    public boolean pas_tapis() {
        return m_etat != Etat.TAPIS;
    }
    public Etat get_etat() {
        return m_etat;
    }
    void set_joueur_suivant(Joueur joueur_suivant) {
        m_joueur_suivant = joueur_suivant;
    }
    Joueur get_joueur_suivant() {
        return m_joueur_suivant;
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
    private static void prompt_tour_joueur(String nom_joueur) {
        System.out.println("----------------------------------------------");
        System.out.println("C'est le tour du joueur " + nom_joueur + " !");
        System.out.println("Appuyez sur ENTREE");
        entree_terminal.nextLine();
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
    public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min) {
        int res;
        int relance_max = m_cave-mise_demandee;
        CollectionDeCartes collection = new CollectionDeCartes(m_main,jeu_pt);
        prompt_tour_joueur(m_nom_joueur);
        System.out.println("Votre mise actuelle est de " + m_mise + ".");
        System.out.println("Le pot actuel est de " + pot);
        System.out.println("La mise actuelle demandée en jeu est de " + mise_demandee + ".");
        collection.afficher();
        System.out.println("Votre probabilité indicative de l'emporter avec cette main est de " +
                Math.round(collection.probaVict(Joueur.nombre_de_joueurs-1) * 100) + " %");
        int action = prompt_action(
                relance_min < relance_max,
                mise_demandee == m_mise);
        switch (action) {
            case 2:
                res = relance_max;
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
    public void coucher() {
        m_etat = Etat.COUCHE;
    }
    public void ajouter_mise(int complement,int[] pot_pt) {
        if (complement < 0) throw new ComplementMiseNegatifException();
        if (complement >= m_cave) {
            complement = m_cave;
            System.out.println("La mise maximale est atteinte");
            m_etat = Etat.TAPIS;
        }
        m_cave -= complement;
        pot_pt[0] += complement;
        m_mise += complement;
    }
    public void retirer_mise(int montant,int[] pot_pt,int[] retrait_pt) {
        if (montant < 0) throw new ComplementMiseNegatifException();
        if (montant >= m_mise) montant = m_mise;
        pot_pt[0] -= montant;
        retrait_pt[0] += montant;
        m_mise -= montant;
    }
    public void encaisser(int montant) {
        m_cave += montant;
    }
    @Override
    public String toString() {
        return m_nom_joueur;
    }
}