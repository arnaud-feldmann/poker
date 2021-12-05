public class Carte {
    public enum Valeur {Deux,Trois,Quatre,Cinq,Six,Sept,Huit,Neuf,Dix,Valet,Dame,Roi,As}
    public enum Couleur {Pique,Coeur,Carreau,Trefle}
    final private Valeur m_valeur;
    final private Couleur m_couleur;
    Carte(Valeur valeur,Couleur couleur) {
        m_valeur = valeur;
        m_couleur = couleur;
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
    public String toString() {
        return get_valeur() + " de " + get_couleur();
    }

}