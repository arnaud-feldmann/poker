package Jeu;

class NombreJoueursException extends IllegalArgumentException {
    NombreJoueursException() {
        super("Le nombre de joueurs doit être entre 2 et 8!!!");
    }
}

public class Poker {
    final private static int CAVE_INITIALE = 1000;
    final private static int PETITE_BLINDE_INITIALE = 5;

    protected static void init(String[] noms_joueurs, int CAVE_INITIALE) {
        if (noms_joueurs.length < 2 || noms_joueurs.length > 8) throw new NombreJoueursException();
        Joueur.donneur = new Joueur(noms_joueurs[0], CAVE_INITIALE, null,
                new IntelligenceHumaine(noms_joueurs[0]), 0);
        Joueur joueur_temp = Joueur.donneur;
        for (int i = 1; i < noms_joueurs.length; i++)
            joueur_temp = new Joueur(noms_joueurs[i], CAVE_INITIALE, joueur_temp,
                    new IntelligenceArtificielle(CAVE_INITIALE, noms_joueurs[i]), i);
        Joueur.donneur.set_joueur_suivant(joueur_temp);
    }

    /* La méthode statique qui lance le jeu */
    protected static void poker(String[] noms_joueurs) {
        int petite_blinde = PETITE_BLINDE_INITIALE;
        int nbtour = 0;

        init(noms_joueurs, CAVE_INITIALE);

        do {
            new TourPoker(petite_blinde);
            nbtour++;
            if (nbtour % 20 == 0) {
                petite_blinde *= 2;
                InterfaceUtilisateur.println("La petite blinde augmente à " + petite_blinde + " et la grosse blinde à " + petite_blinde * 2);
            }
        } while (Joueur.inc_donneur());
        if (Joueur.nombre_de_joueurs() == 1) InterfaceUtilisateur.println(Joueur.donneur + " a gagné la partie");
        else InterfaceUtilisateur.println(noms_joueurs[0] + " a perdu la partie");
    }

    public static void main(String[] args) {
        poker(new String[]{"Arnaud", "Loup", "Ludo", "Elodie", "Kerry", "Bettie", "Peppa Pig"});
    }
}
