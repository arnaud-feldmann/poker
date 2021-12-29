package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;

import java.util.ArrayList;
import java.util.Random;

/*
Une Intelligence est définie comme la réponse à la question "Combien voulez-vous miser ?" en fonction de la mise
demandée, du jeu, du pot, de la main, de la cave, de la mise déjà en jeu et de la relance minimale.
Dans la mesure où les implémentations ne sont pas statiques, il est aussi envisageable de faire un peu évoluer le
comportement des Intelligences artificielles au cours du jeu en fonction des défaites et victoires. C'est ce qui est
fait très sommairement avec les changements de "prudence" via le "sang-froid". Faire un peu varier le comportement des
IA empêche de reproduire à l'infini une technique gagnante.
 */
public interface Intelligence {
    int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                      ArrayList<Carte> main, int cave, int mise_precedente);
}

class IntelligenceHumaine implements Intelligence {
    String m_nom_joueur;

    IntelligenceHumaine(String nom_joueur) {
        m_nom_joueur = nom_joueur;
    }

    private static int prompt_relance(int relance_min, int relance_max) {
        int res;
        InterfaceUtilisateur.println("De combien voulez-vous relancer (de " +
                relance_min + " à " + relance_max + ") ?");
        while (true) {
            InterfaceUtilisateur.print("> ");
            try {
                res = Integer.parseInt(InterfaceUtilisateur.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= relance_min && res <= relance_max) break;
            InterfaceUtilisateur.println("Un peu de sérieux !");
        }
        return res;
    }

    private static int prompt_action(boolean peut_relancer, boolean check) {
        int res;
        InterfaceUtilisateur.println("Que voulez-vous faire ?");
        InterfaceUtilisateur.println("1) Me coucher");
        InterfaceUtilisateur.println("2) TAPIS !");
        if (check) InterfaceUtilisateur.println("3) Checker");
        else InterfaceUtilisateur.println("3) Suivre");
        if (peut_relancer) InterfaceUtilisateur.println("4) Relancer");
        while (true) {
            InterfaceUtilisateur.print("> ");
            try {
                res = Integer.parseInt(InterfaceUtilisateur.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= 1 && res < 4 + (peut_relancer ? 1 : 0)) break;
            InterfaceUtilisateur.println("Un peu de sérieux !");
        }
        return res;
    }

    private void prompt_tour_joueur() {
        InterfaceUtilisateur.println("----------------------------------------------");
        InterfaceUtilisateur.println("C'est le tour du joueur " + m_nom_joueur + " !");
    }

    @Override
    public int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                             ArrayList<Carte> main, int cave, int mise_precedente) {
        int res;
        int relance_max = cave - mise_demandee;
        CollectionDeCartes collection = new CollectionDeCartes(main, jeu_pt);
        prompt_tour_joueur();
        InterfaceUtilisateur.println("Votre mise actuelle est de " + mise_precedente + ".");
        InterfaceUtilisateur.println("Il vous reste " + cave + " dans votre cave.");
        InterfaceUtilisateur.println("Le pot actuel est de " + pot);
        InterfaceUtilisateur.println("La mise actuelle demandée en jeu est de " + mise_demandee + ".");
        collection.afficher();
        InterfaceUtilisateur.println("Votre probabilité indicative de l'emporter avec cette main est de " +
                Math.round(collection.probaVict(Joueur.nombre_de_joueurs() - 1) * 100) + " %");
        int action = prompt_action(
                relance_min < relance_max,
                mise_demandee == mise_precedente);
        switch (action) {
            case 2:
                res = cave + mise_precedente;
                break;
            case 3:
                res = Math.max(mise_demandee, mise_precedente);
                break;
            case 4:
                res = mise_demandee + prompt_relance(relance_min, relance_max);
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }
}

/* Les IA sont sommaires, mais sont définies par 2 paramètres :
-une prudence qui définit la propension à parier dangereusement
-un sang-froid qui fait évoluer un peu la prudence en fonction des défaites et des victoires (quelqu'un qui n'a pas
de sang-froid se déconcentre quand il perd et vice-versa).

J'ai également laissé pas mal de hasard pour émuler un peu les bluffs et éviter que tout soit devinable
- une fois sur prudence le pari est multiplié de 1 à 10 et rajoute encore si ce n'est pas assez pour suivre.
- Une fois sur prudence au contraire le pari est divisé de 1 à 10
D'autres part, si le résultat est inférieur à la mise précédente, le résultat n'est pas pris en compte : l'ordinateur
va toujours checker quand ça ne lui coute rien.
 */
class IntelligenceArtificielle implements Intelligence {
    static Random random = new Random();
    final int PRUDENCE_MAX = 32; // une puissance de 2
    int m_prudence;
    int m_cave_initiale;
    int m_cave_precedente;
    int[] statistiques_de_gain = new int[PRUDENCE_MAX];
    String m_nom_joueur;

    IntelligenceArtificielle(int cave_initiale, String nom_joueur) {
        m_prudence = random.nextInt(PRUDENCE_MAX) + 1;
        m_cave_initiale = cave_initiale;
        m_cave_precedente = cave_initiale;
        m_nom_joueur = nom_joueur;
        InterfaceUtilisateur.println(nom_joueur + " a " + m_prudence + " points de prudence.");
    }

    public static void set_seed(long seed) {
        random.setSeed(seed);
    }

    private int choisir_nouvelle_prudence(int debut, int fin) {
        if (debut == fin) return debut;
        int meandeb = 0;
        int meanfin = 0;
        final int milieu = debut + (fin - debut + 1) / 2 - 1;
        for (int i = debut; i <= milieu; i++) meandeb += statistiques_de_gain[i];
        meandeb /= (milieu - debut + 1);
        for (int i = milieu + 1; i <= fin; i++) meanfin += statistiques_de_gain[i];
        meanfin /= (fin - milieu);
        if (meandeb > meanfin) return choisir_nouvelle_prudence(debut, milieu);
        else if (meandeb < meanfin) return choisir_nouvelle_prudence(milieu + 1, fin);
        else {
            if (random.nextBoolean()) return choisir_nouvelle_prudence(debut, milieu);
            else return choisir_nouvelle_prudence(milieu + 1, fin);
        }
    }

    private void changements_de_prudence(int cave) {
        if (cave != m_cave_precedente) {
            statistiques_de_gain[m_prudence - 1] += cave - m_cave_precedente;
            m_prudence = choisir_nouvelle_prudence(0, PRUDENCE_MAX - 1) + 1;
            InterfaceUtilisateur.println(m_nom_joueur + " a maintenant " + m_prudence + " points de prudence");
            m_cave_precedente = cave;
        }
    }

    @Override
    public int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                             ArrayList<Carte> main, int cave, int mise_precedente) {
        changements_de_prudence(cave + mise_precedente);
        double proba = new CollectionDeCartes(main, jeu_pt).probaVict(Joueur.nombre_de_joueurs() - 1);
        int res = (int) (proba * (double) m_cave_initiale / (double) (m_prudence + random.nextInt(5)));
        if (random.nextInt(m_prudence) == 0) {
            res *= random.nextInt(10) + 1;
            if (res < mise_demandee) res = mise_demandee;
        }
        if (random.nextInt(m_prudence) == 0) res /= random.nextInt(10) + 1;
        if (res < mise_precedente) res = mise_precedente; // On checke toujours par défaut
        return res;
    }
}
