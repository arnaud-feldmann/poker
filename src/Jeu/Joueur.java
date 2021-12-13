package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;
import Cartes.PaquetDeCartes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

class ComplementMiseNegatifException extends IllegalArgumentException {
    ComplementMiseNegatifException() {
        super("Un complément de mise ne peut pas être négatif.");
    }
}

public class Joueur {
    static int nombre_de_joueurs;
    static int nombre_de_joueurs_pouvant_relancer;
    public enum Etat {PEUT_MISER,MISE_TROP_GRANDE,COUCHE}
    private static Scanner entree_terminal = new Scanner(System.in);
    private final String m_nom_joueur;
    private int m_tapis;
    private Joueur m_joueur_suivant;
    private Joueur m_joueur_precedent;
    private int m_mise;
    private ArrayList<Carte> m_main;
    private Etat m_etat;
    Joueur(String nom_joueur,int tapis,Joueur joueur_suivant) {
        m_nom_joueur = nom_joueur;
        m_main = null;
        m_tapis = tapis;
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

    public String get_nom() {
        return m_nom_joueur;
    }

    public int get_mise() {
        return m_mise;
    }
    public Etat get_etat() {
        return m_etat;
    }
    void set_etat(Etat etat) {
        m_etat = etat;
    }
    void set_joueur_suivant(Joueur joueur_suivant) {
        m_joueur_suivant = joueur_suivant;
    }
    Joueur get_joueur_suivant() {
        return m_joueur_suivant;
    }
    void set_joueur_precedent(Joueur joueur_precedent) {
        m_joueur_precedent = joueur_precedent;
    }
    Joueur get_joueur_precedent() {
        return m_joueur_precedent;
    }
    private static int prompt_action(int mise_actuelle,boolean peut_relancer,boolean check) {
        int res;
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1) Me coucher");
        if (check) System.out.println("2) Checker");
        else System.out.println("2) Suivre");
        if (peut_relancer) System.out.println("3) Relancer");
        while (true) {
            System.out.print("> ");
            try {
                res = Integer.parseInt(entree_terminal.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= 1 && res < 3 + (peut_relancer?1:0)) break;
            System.out.println("Un peu de sérieux !");
        }
        return res;
    }
    private static void prompt_tour_joueur(String nom_joueur) {
        int res = 0;
        System.out.println("----------------------------------------------");
        System.out.println("C'est le tour du joueur " + nom_joueur + " !");
        System.out.println("Entrez oui pour valider :");
        System.out.println("1) Oui");
        System.out.println("2) Non");
        while (true) {
            System.out.print("> ");
            try {
                res = Integer.parseInt(entree_terminal.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res == 1) break;
            else if (res == 2) System.out.println("Bon ok j'attends un petit peu !");
            else System.out.println("Un peu de sérieux !");
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
    public int demander_mise(int mise_actuelle,ArrayList<Carte> jeu_pt,int pot,int relance_minimale) {
        int res;
        CollectionDeCartes collection = new CollectionDeCartes(m_main,jeu_pt);
        prompt_tour_joueur(m_nom_joueur);
        System.out.println("Votre mise actuelle est de " + m_mise + " et la mise actuelle du jeu est de " + mise_actuelle);
        collection.afficher();
        System.out.println("Votre probabilité indicative de l'emporter avec cette main est de " +
                Math.round(collection.probaVict(Joueur.nombre_de_joueurs-1) * 100) + " %");
        int action = prompt_action(mise_actuelle,mise_actuelle-m_mise + relance_minimale < m_tapis,mise_actuelle == m_mise);
        switch (action) {
            case 2:
                res = mise_actuelle;
                break;
            case 3:
                res = mise_actuelle + prompt_relance(relance_minimale,m_tapis-mise_actuelle);
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }
    public void coucher() {
        m_etat = Etat.COUCHE;
        Joueur.nombre_de_joueurs_pouvant_relancer--;
    }
    public void ajouter_mise(int complement,int[] pot_pt) {
        if (complement < 0) throw new ComplementMiseNegatifException();
        if (complement >= m_tapis) {
            complement = m_tapis;
            System.out.println("La mise maximale est atteinte");
            m_etat = Etat.MISE_TROP_GRANDE;
            Joueur.nombre_de_joueurs_pouvant_relancer--;
        }
        m_tapis -= complement;
        pot_pt[0] += complement;
        m_mise += complement;
    }
    public void forEach(Consumer<Joueur> action) {
        Joueur joueur_temp = this;
        for (int i = 0 ; i < Joueur.nombre_de_joueurs ; i++) {
            action.accept(joueur_temp);
            joueur_temp = joueur_temp.get_joueur_suivant();
        }
    }
    @Override
    public String toString() {
        return m_nom_joueur;
    }
}