package Cartes;

import Jeu.InterfaceUtilisateur;

import java.util.ArrayList;

public class Carte {
    final private Valeur m_valeur;
    final private Couleur m_couleur;

    public Carte(Valeur valeur, Couleur couleur) {
        if (valeur == null || couleur == null)
            throw new IllegalArgumentException("Les termes d'une carte ne peuvent pas être null");
        m_valeur = valeur;
        m_couleur = couleur;
    }

    public Carte(Carte carte) {
        this(carte.get_valeur(), carte.get_couleur());
    }

    public static void affiche(ArrayList<Carte> cartes, String nom_jeu) {
        if (cartes.size() == 0) {
            InterfaceUtilisateur.println(nom_jeu + " : vide");
            return;
        }
        InterfaceUtilisateur.println(nom_jeu + " : ");
        cartes.forEach(InterfaceUtilisateur::println);
    }

    public Valeur get_valeur() {
        return m_valeur;
    }

    public Couleur get_couleur() {
        return m_couleur;
    }

    public int compareTo(Carte carte) {
        return m_valeur.compareTo(carte.get_valeur());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Carte obj_carte = (Carte) obj;
        return get_couleur() == obj_carte.get_couleur() && get_valeur() == obj_carte.get_valeur();
    }

    @Override
    public String toString() {
        return get_valeur() + " de " + get_couleur();
    }

    public enum Valeur {Deux, Trois, Quatre, Cinq, Six, Sept, Huit, Neuf, Dix, Valet, Dame, Roi, As}

    public enum Couleur {Pique, Coeur, Carreau, Trefle}
}