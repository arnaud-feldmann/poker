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
    int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min,
                      ArrayList<Carte> main,int cave,int mise);
}

class IntelligenceHumaine implements Intelligence {
    String m_nom_joueur;
    IntelligenceHumaine(String nom_joueur) {
        m_nom_joueur = nom_joueur;
    }
    private void prompt_tour_joueur() {
        Poker.println("----------------------------------------------");
        Poker.println("C'est le tour du joueur " + m_nom_joueur + " !");
    }
    private static int prompt_relance(int relance_min,int relance_max) {
        int res;
        Poker.println("De combien voulez-vous relancer (de " +
                relance_min + " à " + relance_max + ") ?");
        while (true) {
            System.out.print("> ");
            try {
                res = Integer.parseInt(Poker.entree_terminal.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= relance_min && res <= relance_max) break;
            Poker.println("Un peu de sérieux !");
        }
        return res;
    }
    private static int prompt_action(boolean peut_relancer,boolean check) {
        int res;
        Poker.println("Que voulez-vous faire ?");
        Poker.println("1) Me coucher");
        Poker.println("2) TAPIS !");
        if (check) Poker.println("3) Checker");
        else Poker.println("3) Suivre");
        if (peut_relancer) Poker.println("4) Relancer");
        while (true) {
            System.out.print("> ");
            try {
                res = Integer.parseInt(Poker.entree_terminal.nextLine());
            } catch (NumberFormatException e) {
                res = -1;
            }
            if (res >= 1 && res < 4 + (peut_relancer?1:0)) break;
            Poker.println("Un peu de sérieux !");
        }
        return res;
    }
    @Override
    public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min,
                             ArrayList<Carte> main,int cave,int mise) {
        int res;
        int relance_max = cave-mise_demandee;
        CollectionDeCartes collection = new CollectionDeCartes(main,jeu_pt);
        prompt_tour_joueur();
        Poker.println("Votre mise actuelle est de " + mise + ".");
        Poker.println("Il vous reste " + cave + " dans votre cave.");
        Poker.println("Le pot actuel est de " + pot);
        Poker.println("La mise actuelle demandée en jeu est de " + mise_demandee + ".");
        collection.afficher();
        Poker.println("Votre probabilité indicative de l'emporter avec cette main est de " +
                Math.round(collection.probaVict(Joueur.nombre_de_joueurs()-1) * 100) + " %");
        int action = prompt_action(
                relance_min < relance_max,
                mise_demandee == mise);
        switch (action) {
            case 2:
                res = cave + mise;
                break;
            case 3:
                res = Math.max(mise_demandee,mise);
                break;
            case 4:
                res = mise_demandee + prompt_relance(relance_min,relance_max);
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
    Random m_random;
    int m_prudence;
    int m_sang_froid;
    int m_cave_initiale;
    int m_cave_precedente;
    String m_nom_joueur;
    IntelligenceArtificielle(int cave_initiale,String nom_joueur) {
        m_random = new Random();
        m_prudence = m_random.nextInt(16) + 10;
        m_sang_froid = m_random.nextInt(21) - 10;
        m_cave_initiale = cave_initiale;
        m_cave_precedente = cave_initiale;
        m_nom_joueur = nom_joueur;
        Poker.println(nom_joueur + " a " + m_prudence + " points de prudence et " + m_sang_froid + " de sang-froid.");
    }
    private void deconcentration(String nom_joueur) {
        if (m_prudence >= 2) m_prudence--;
        Poker.println(nom_joueur + " est déconcentré !!! Il a dorénavant " + m_prudence + " points de prudence.");
    }
    private void reconcentration(String nom_joueur) {
        if (m_prudence < 20) m_prudence++;
        Poker.println(nom_joueur + " se concentre !!! Il a dorénavant " + m_prudence + " points de prudence.");
    }
    private void changements_de_prudence(int cave) {
        int rnd = m_random.nextInt(21) - 10;
        if (cave != m_cave_precedente) {
            if (m_sang_froid == 0) deconcentration(m_nom_joueur);
            else if (cave < m_cave_precedente) {
                if (rnd < m_sang_froid) reconcentration(m_nom_joueur);
                else deconcentration(m_nom_joueur);
            }
            else {
                if (rnd > m_sang_froid) reconcentration(m_nom_joueur);
                else deconcentration(m_nom_joueur);
            }
            m_cave_precedente = cave;
        }
    }
    @Override
    public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min,
                             ArrayList<Carte> main,int cave,int mise) {
        changements_de_prudence(cave+mise);
        double proba = new CollectionDeCartes(main,jeu_pt).probaVict(Joueur.nombre_de_joueurs()-1);
        int res = (int) (proba*(double) m_cave_initiale/(double) (m_prudence+m_random.nextInt(5)));
        if (m_random.nextInt(m_prudence) == 0) {
            res *= m_random.nextInt(10)+1;
            if (res < mise_demandee) res = mise_demandee;
        }
        if (m_random.nextInt(m_prudence) == 0) res /= m_random.nextInt(10)+1;
        if (res < mise) res = mise; // On checke toujours par défaut
        return res;
    }
}
