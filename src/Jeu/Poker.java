package Jeu;

public class Poker {
    private int m_petite_blinde;
    TourPoker m_tour;
    Poker(String[] noms_joueurs,int cave_initiale,int petite_blinde) {
        Joueur.donneur = new Joueur(noms_joueurs[0],cave_initiale,null,new IntelligenceHumaine());
        Joueur joueur_temp = Joueur.donneur;
        for (int i = 1 ; i < noms_joueurs.length ; i++) joueur_temp = new Joueur(noms_joueurs[i], cave_initiale,joueur_temp,new IntelligenceArtificielle(cave_initiale));
        Joueur.donneur.set_joueur_suivant(joueur_temp);
        m_petite_blinde = petite_blinde;
        do nouveau_tour(); while (Joueur.inc_donneur());
        System.out.println(Joueur.donneur + " a gagné la partie");
    }

    public void nouveau_tour() {
        m_tour = new TourPoker(m_petite_blinde);
    }

    public static void main(String[] args) {
        new Poker(new String[] {"Arnaud","Loup","Ludo"},1000,5);
    }
}
