package Jeu;

import interfaceGraphique.InterfacePoker;

class NombreJoueursException extends IllegalArgumentException {
    NombreJoueursException() {
        super("Le nombre de joueurs doit être entre 2 et 8!!!");
    }
}

public class Poker {
    public static void poker(String[] noms_joueurs,int cave_initiale,int petite_blinde) {
        int m_petite_blinde;
        int m_nbtour = 0;
        InterfacePoker m_interface_poker;
        switch (noms_joueurs.length) {
            case 2:
                m_interface_poker = new InterfacePoker(1,1,1,true);
                break;
            case 3:
                m_interface_poker = new InterfacePoker(2,1,1,true);
                break;
            case 4:
            case 5:
                m_interface_poker = new InterfacePoker(2,2,1,true);
                break;
            case 6:
            case 7:
                m_interface_poker = new InterfacePoker(3,2,1,true);
                break;
            case 8:
                m_interface_poker = new InterfacePoker(4,2,1,true);
                break;
            default:
                throw new NombreJoueursException();
        }
        m_interface_poker.afficheFenetre();
        Joueur.donneur = new Joueur(noms_joueurs[0],cave_initiale,null,
                new IntelligenceHumaine(noms_joueurs[0]),m_interface_poker,0);
        Joueur joueur_temp = Joueur.donneur;
        for (int i = 1 ; i < noms_joueurs.length ; i++) joueur_temp = new Joueur(noms_joueurs[i], cave_initiale,joueur_temp,
                new IntelligenceArtificielle(cave_initiale,noms_joueurs[i]),m_interface_poker,i);
        Joueur.donneur.set_joueur_suivant(joueur_temp);
        m_petite_blinde = petite_blinde;
        do {
            new TourPoker(m_petite_blinde,m_interface_poker);
            m_nbtour++;
            if (m_nbtour % 20 == 0) {
                m_petite_blinde *= 2;
                System.out.println("La petite blinde augmente à " + m_petite_blinde + " et la grosse blinde à " + m_petite_blinde * 2);
            }
        } while (Joueur.inc_donneur());
        System.out.println(Joueur.donneur + " a gagné la partie");
        m_interface_poker.ferme();
    }

    public static void main(String[] args) {
        poker(new String[] {"Arnaud","Loup","Ludo","Elodie","Alphonse","Robert","Cunégonde","Alfred"},1000,5);
    }
}
