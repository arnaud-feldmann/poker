package Jeu;

public class Poker {

    public static void poker(String[] noms_joueurs,int cave_initiale,int petite_blinde) {
        int m_petite_blinde;
        int m_nbtour = 0;
        if (noms_joueurs.length > 8) throw new IllegalArgumentException("Il n'y a pas autant de gens à un poker !!!");
        Joueur.donneur = new Joueur(noms_joueurs[0],cave_initiale,null,new IntelligenceHumaine(noms_joueurs[0]));
        Joueur joueur_temp = Joueur.donneur;
        for (int i = 1 ; i < noms_joueurs.length ; i++) joueur_temp = new Joueur(noms_joueurs[i], cave_initiale,joueur_temp,new IntelligenceArtificielle(cave_initiale,noms_joueurs[i]));
        Joueur.donneur.set_joueur_suivant(joueur_temp);
        m_petite_blinde = petite_blinde;
        do {
            new TourPoker(m_petite_blinde);
            m_nbtour++;
            if (m_nbtour % 20 == 0) {
                m_petite_blinde *= 2;
                System.out.println("La petite blinde augmente à " + m_petite_blinde + " et la grosse blinde à " + m_petite_blinde * 2);
            }
        } while (Joueur.inc_donneur());
        System.out.println(Joueur.donneur + " a gagné la partie");
    }

    public static void main(String[] args) {
        poker(new String[] {"Arnaud","Loup","Ludo","Elodie","Alphonse","Robert","Cunégonde","Alfred"},1000,5);
    }
}
