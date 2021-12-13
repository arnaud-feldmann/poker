package Jeu;

public class Poker {
    private int m_petite_blinde;
    TourPoker m_tour;
    Poker(String[] noms_joueurs,int tapis_initial,int petite_blinde) {
        Joueur.donneur = new Joueur(noms_joueurs[0],tapis_initial,null);
        Joueur.nombre_de_joueurs = noms_joueurs.length;
        Joueur joueur_temp = Joueur.donneur;
        for (int i = 1 ; i < noms_joueurs.length ; i++) joueur_temp = new Joueur(noms_joueurs[i], tapis_initial,joueur_temp);
        Joueur.donneur.set_joueur_suivant(joueur_temp);
        m_petite_blinde = petite_blinde;
        while (Joueur.stream().filter(joueur -> joueur.get_cave() > 0).count() != 1) {
            System.out.println();
            nouveau_tour();
            Joueur.donneur = Joueur.donneur.get_joueur_suivant();
            System.out.println("Le donneur est maintenant " + Joueur.donneur);
        }
    }

    public void nouveau_tour() {
        m_tour = new TourPoker(m_petite_blinde);
    }

    public static void main(String[] args) {
        Poker test = new Poker(new String[] {"Arnaud","Loup","Ludo"},1000,5);
    }
}
