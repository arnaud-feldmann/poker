package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;
import Cartes.PaquetDeCartes;
import java.util.ArrayList;
import java.util.stream.Stream;

class ComplementMiseNegatifException extends IllegalArgumentException {
    ComplementMiseNegatifException() {
        super("Un complément de mise ne peut pas être négatif.");
    }
}

public class Joueur {
    public static Joueur donneur;
    public enum Etat {PEUT_MISER,TAPIS,COUCHE}
    private final String m_nom_joueur;
    private int m_cave;
    private Joueur m_joueur_suivant;
    private int m_mise;
    private ArrayList<Carte> m_main;
    private Etat m_etat;
    private Intelligence m_intelligence;
    public static int nombre_de_joueurs() {
        int res = 1;
        for (Joueur joueur = donneur.get_joueur_suivant() ; joueur != donneur ; joueur = joueur.get_joueur_suivant()) res++;
        return res;
    }
    public static boolean inc_donneur() {
        Joueur donneur_save = donneur;
        donneur = donneur.get_joueur_suivant();
        return donneur_save != donneur;
    }
    public static Stream<Joueur> stream() {
        Joueur joueur_temp = donneur;
        Stream.Builder<Joueur> builder = Stream.builder();
        for (int i = 0 ; i < Joueur.nombre_de_joueurs() ; i++) {
            builder.add(joueur_temp);
            joueur_temp = joueur_temp.get_joueur_suivant();
        }
        return builder.build();
    }
    Joueur(String nom_joueur,int cave,Joueur joueur_suivant,Intelligence intelligence) {
        m_nom_joueur = nom_joueur;
        m_main = null;
        m_cave = cave;
        m_joueur_suivant = joueur_suivant;
        m_intelligence = intelligence;
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
    public Etat get_etat() {
        return m_etat;
    }
    void set_joueur_suivant(Joueur joueur_suivant) {
        m_joueur_suivant = joueur_suivant;
    }
    Joueur get_joueur_suivant() {
        return m_joueur_suivant;
    }
    public void coucher() {
        m_etat = Etat.COUCHE;
        System.out.println(m_nom_joueur + " se couche.");
    }
    public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min) {
        return m_intelligence.demander_mise(mise_demandee,jeu_pt,pot,relance_min,m_main,m_cave,m_mise,m_nom_joueur);
    }
    public void ajouter_mise(int complement,int[] pot_pt) {
        if (complement < 0) throw new ComplementMiseNegatifException();
        else if (complement >= m_cave) {         // Quand on suit la mise demandée est parfois supérieure à la cave
            complement = m_cave;
            System.out.println(m_nom_joueur + " a misé tout son tapis !");
            m_etat = Etat.TAPIS;
        }
        else if (complement == 0) {
            System.out.println(m_nom_joueur + " checke.");
            return;
        }
        System.out.println(m_nom_joueur + " ajoute " + complement + " dans le pot.");
        m_cave -= complement;
        pot_pt[0] += complement;
        m_mise += complement;
        System.out.println("Sa mise est maintenant de " + m_mise + " et le pot vaut " + pot_pt[0]);
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