package Jeu;

public class Poker {
    private Joueur m_donneur;
    private int m_petite_blinde;
    TourPoker m_tour;
    Poker(String[] noms_joueurs,int tapis_initial,int petite_blinde) {
        m_donneur = new Joueur(noms_joueurs[0],tapis_initial,null);
        Joueur.nombre_de_joueurs = noms_joueurs.length;
        Joueur joueur_temp = m_donneur;
        for (int i = 1 ; i < noms_joueurs.length ; i++) joueur_temp = new Joueur(noms_joueurs[i], tapis_initial,joueur_temp);
        m_donneur.set_joueur_suivant(joueur_temp);
        joueur_temp.set_joueur_precedent(m_donneur);
        joueur_temp.stream().forEach(joueur -> joueur.get_joueur_suivant().set_joueur_precedent(joueur));
        m_petite_blinde = petite_blinde;
        nouveau_tour();
    }

    public void nouveau_tour() {
        m_tour = new TourPoker(m_donneur,m_petite_blinde);
    }

    public static void main(String[] args) {
        Poker test = new Poker(new String[] {"Arnaud","Loup","Ludo"},1000,5);
    }
}
