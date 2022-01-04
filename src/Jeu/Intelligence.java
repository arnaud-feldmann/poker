package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;

import java.util.ArrayList;
import java.util.Random;

/*
Une Intelligence est définie comme la réponse à la question "Combien voulez-vous miser ?" en fonction de la mise
demandée, du jeu, du pot, de la main, de la cave non-misée, de la mise déjà en jeu et de la relance minimale.
Puisque les implémentations ne sont pas statiques, il est aussi envisageable de faire un peu évoluer le
comportement des Intelligences artificielles au cours du jeu en fonction des défaites et victoires. C'est ce qui est
fait ici avec les statistiques de gain et l'audace. Faire un peu varier (un peu) le comportement des
IA empêche de reproduire à l'infini une technique gagnante.
 */
public interface Intelligence {
    int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                      ArrayList<Carte> main, int cave_non_misee, int mise_deja_en_jeu);

    int type_intelligence();
}

class IntelligenceHumaine implements Intelligence {
    final String m_nom_joueur;

    protected IntelligenceHumaine(String nom_joueur) {
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

    public int type_intelligence() {
        return 0;
    }

    private void prompt_tour_joueur() {
        InterfaceUtilisateur.println("----------------------------------------------");
        InterfaceUtilisateur.println("C'est le tour du joueur " + m_nom_joueur + " !");
    }

    @Override
    public int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                             ArrayList<Carte> main, int cave_non_misee, int mise_deja_en_jeu) {
        int res;
        int relance_max = cave_non_misee - mise_demandee;
        CollectionDeCartes collection = new CollectionDeCartes(main, jeu_pt);
        prompt_tour_joueur();
        InterfaceUtilisateur.println("Votre mise actuelle est de " + mise_deja_en_jeu + ".");
        InterfaceUtilisateur.println("Il vous reste " + cave_non_misee + " dans votre cave.");
        InterfaceUtilisateur.println("Le pot actuel est de " + pot);
        InterfaceUtilisateur.println("La mise actuelle demandée en jeu est de " + mise_demandee + ".");
        collection.afficher();
        InterfaceUtilisateur.println("Votre probabilité indicative de l'emporter avec cette main est de " +
                Math.round(collection.probaVict(Joueur.nombre_de_joueurs() - 1) * 100) + " %");
        int action = prompt_action(
                relance_min < relance_max,
                mise_demandee == mise_deja_en_jeu);
        switch (action) {
            case 2:
                res = cave_non_misee + mise_deja_en_jeu;
                break;
            case 3:
                res = Math.max(mise_demandee, mise_deja_en_jeu);
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

/*
L'IA sommaire est principalement appuyée sur ces points
* La méthode probaVict de CollectionDeCartes qui simule 10000 lancers et donne une probabilité de victoire pour une main.
* A partir de cette probabilité de victoire on calcule 3 taux de gain :
    - un calculé sans suivi, pour pouvoir inclure les cas où l'on suit mais tout le monde se couche puis on perd.
    - un calculé dans le cas où tout le monde suit (c'est ce que le parieur espère la plupart du temps)
    - un calculé dans le cas où tout le monde fait tapis (c'est pour représenter l'espoir de siphoner tout le tapis
    de l'adversaire et ça permet d'avoir une IA agressive plus drôle à jouer que si elle n'attaque jamais)
* On fait une moyenne de ces 3 espérances
* On multiplie ce montant par une variable d'audace qui représente le degré de prise
de risque d'une IA : Elle va parier une mise maximale plus forte mais sur des cartes différentes. Cette variable peut
augmenter en fonction des circonstances. Sans cela, si l'IA était trop raisonnable, on pourrait gagner contre elle trop
facilement en faisant que des tapis. Et puis elle serait nettement moins drôle. Là on a une IA qui attaque et ne fait
pas que défendre en attendant la fin du jeu.

Le calcul ne prétend pas du tout être mathématiquement juste, aussi l'IA reste largement battable : en particulier,
rien n'est fait pour estimer le nombre de personnes qui se couchent. L'IA ne fait que faire systématiquement la même
moyenne.

Il y a aussi deux modifications annexes pour rendre le jeu moins mécanique:
* A chaque fois que l'IA joue, elle a une chance sur m_bluffeur de "bluffer" en rajoutant  m_cave_initiale / 10 à ses
mises. Ce comportement est fait pour donner la possiblité à l'IA de jouer des mauvais coups.
* La méthode d'opacité du choix fait que la probabilité de rester augmente aléatoirement pendant le tour, pour éviter
qu'un joueur humain puisse trop facilement battre l'IA en sachant qu'elle se couche systématiquement si on la relance.
 */

class IntelligenceArtificielle implements Intelligence {

    final static Random random = new Random();
    final static int AUDACE_MAX = 4; // L'audace décide de la prise de risque
    final int[] statistiques_de_gain = new int[AUDACE_MAX]; // Les stats de jeu qui décident de la prise de risque
    final int m_bluffeur; // Le nombre de coups joués avant un (rare) bluff.
    final int m_cave_initiale; // La cave initiale du joueur
    final String m_nom_joueur; // Le nom du joueur
    int m_audace; // La prise de risque, logarithmique, du joueur.
    ArrayList<Carte> m_bluff_sur_ce_jeu = null; // Utilisé en tant que pointeur pour identifier un jeu bluffé
    ArrayList<Carte> m_prorata_fixe_sur_ce_jeu = null; // Utilisé en tant que pointeur pour identifier un jeu pour lequel
    // on a déjà déterminé un prorata d'opacité du choix, qu'on ne peut qu'augmenter
    double m_prorata; // Le prorata pour l'opacité du choix
    int m_cave_precedente; // La cave précédente, pour savoir réagir quand on perd ou gagne de l'argent

    protected IntelligenceArtificielle(int cave_initiale, String nom_joueur) {
        m_bluffeur = 20 + random.nextInt(50);
        m_audace = 0;
        m_cave_initiale = cave_initiale;
        m_cave_precedente = cave_initiale;
        m_nom_joueur = nom_joueur;
    }

    public static void set_seed(long seed) {
        random.setSeed(seed);
    }

    public int type_intelligence() {
        return 1;
    }

    private void changements_audace(int cave, ArrayList<Carte> jeu_pt) {
        if (cave != m_cave_precedente) {
            if (m_bluff_sur_ce_jeu != jeu_pt) { // On ne fait pas de statistiques sur un bluff.
                statistiques_de_gain[m_audace] += cave - m_cave_precedente;
                for (int i = 0; i < AUDACE_MAX; i++) statistiques_de_gain[i] -= (cave - m_cave_precedente) / AUDACE_MAX;

                for (int i = 0, max = Integer.MIN_VALUE; i < AUDACE_MAX; i++) {
                    if (max < statistiques_de_gain[i]) {
                        m_audace = i;
                        max = statistiques_de_gain[i];
                    }
                }
            }
            m_cave_precedente = cave;
        }
    }

    /* Tous les m_bluffeur tours, on ajoute m_cave_initiale / 10 à res */
    private int bluff(int res, int mise_demandee, ArrayList<Carte> jeu_pt) {
        boolean bluff;
        if (m_bluff_sur_ce_jeu != jeu_pt) {
            if (random.nextInt(m_bluffeur) == 0) {
                bluff = true;
                m_bluff_sur_ce_jeu = jeu_pt;
            } else bluff = false;
        } else bluff = true;
        if (bluff && res < mise_demandee) res += m_cave_initiale / 10;
        return res;
    }

    private double esperance(int gain, int perte, double proba) {
        return proba * (double) gain - (double) perte;
    }

    private int gain(int pot, int mise) {
        return pot +
                Joueur.stream()
                        .filter(Joueur::pas_couche)
                        .mapToInt(joueur -> Math.max(Math.min(mise, joueur.get_cave() + joueur.get_mise()) - joueur.get_mise(), 0))
                        .sum();
    }

    /* Cette méthode calcul une estimation du taux de gain, à savoir l'espérance divisée par la mise */
    private double calcul_taux_de_gain(int mise_demandee, ArrayList<Carte> jeu_pt, int pot,
                                       ArrayList<Carte> main, int cave_non_misee, int mise_deja_en_jeu) {
        final double proba = new CollectionDeCartes(main, jeu_pt).probaVict(Joueur.nombre_de_joueurs() - 1);
        final int mise_demandee_tronquee = Math.min(mise_demandee, cave_non_misee + mise_deja_en_jeu);
        final double non_suivi = (proba * (double) (pot + Math.max(mise_demandee_tronquee - mise_deja_en_jeu, 0)) - (double) mise_demandee_tronquee) /
                mise_demandee_tronquee;
        final double suivi = esperance(gain(pot, mise_demandee), mise_demandee_tronquee, proba) /
                mise_demandee_tronquee;
        final int mise_tapis = cave_non_misee + mise_deja_en_jeu;
        final double tapis = esperance(gain(pot, mise_tapis), mise_tapis, proba) / mise_tapis;

        return (non_suivi + suivi + tapis) / 3d;
    }

    /* On se rapproche d'un coefficient de 1 progressivement pendant le tour */
    int opacite_du_choix(int res, ArrayList<Carte> jeu_pt) {
        if (m_prorata_fixe_sur_ce_jeu != jeu_pt) {
            m_prorata = Math.pow(random.nextDouble(), 2);
            m_prorata_fixe_sur_ce_jeu = jeu_pt;
        } else m_prorata += random.nextDouble() * (1 - m_prorata);
        return Math.toIntExact(Math.round(m_prorata * res));
    }

    @Override
    public int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                             ArrayList<Carte> main, int cave_non_misee, int mise_deja_en_jeu) {
        int res;
        changements_audace(cave_non_misee + mise_deja_en_jeu, jeu_pt);
        res = (int) (
                calcul_taux_de_gain(mise_demandee, jeu_pt, pot, main, cave_non_misee, mise_deja_en_jeu) *
                        (cave_non_misee + mise_deja_en_jeu) * 4 *
                        Math.pow(2, m_audace));
        res = bluff(res, mise_demandee, jeu_pt);
        res = opacite_du_choix(res, jeu_pt);
        if (res < mise_deja_en_jeu) res = mise_deja_en_jeu; // On checke toujours par défaut
        return res;
    }
}