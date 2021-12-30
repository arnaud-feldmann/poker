package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;

import java.util.ArrayList;
import java.util.Arrays;
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
                      ArrayList<Carte> main, int cave_non_misee, int mise_deja_en_jeu);
    int type_intelligence();
}

class IntelligenceHumaine implements Intelligence {
    String m_nom_joueur;

    IntelligenceHumaine(String nom_joueur) {
        m_nom_joueur = nom_joueur;
    }

    public int type_intelligence() {
        return 0;
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

class IntelligenceArtificielle implements Intelligence {

    static Random random = new Random();
    final static int AUDACE_MAX = 5;
    int m_audace; // Un entier déterminé en fonction de statistiques de gain, qui décide le degré de prises de risques de l'IA.
    // Si elle joue face à quelqu'un qui ne fait que des tapis, en particulier, elle est obligé de prendre des risques.
    int[] statistiques_de_gain = new int[AUDACE_MAX];
    final int m_bluffeur; // Le nombre de coups joués avant un bluff.
    Object m_bluff_sur_ce_jeu = null; // Lors d'un bluff on garde en mémoire l'adresse du jeu considéré pour bluffer jusqu'à la fin
    int m_cave_initiale;
    int m_cave_precedente;
    String m_nom_joueur;

    IntelligenceArtificielle(int cave_initiale, String nom_joueur) {
        m_bluffeur = 10 + random.nextInt(50);
        m_audace = 0;
        m_cave_initiale = cave_initiale;
        m_cave_precedente = cave_initiale;
        m_nom_joueur = nom_joueur;
    }

    public int type_intelligence() {
        return 1;
    }

    public static void set_seed(long seed) {
        random.setSeed(seed);
    }

    private void changements_audace(int cave) {
        if (cave != m_cave_precedente) {
            if (m_bluff_sur_ce_jeu == null) {
                statistiques_de_gain[m_audace] += cave - m_cave_precedente;
                for (int i = 0; i < AUDACE_MAX; i++) statistiques_de_gain[i] -= (cave - m_cave_precedente) / AUDACE_MAX;

                for (int i = 0, max = Integer.MIN_VALUE; i < AUDACE_MAX; i++) {
                    if (max < statistiques_de_gain[i]) {
                        m_audace = i;
                        max = statistiques_de_gain[i];
                    }
                }
                InterfaceUtilisateur.println(m_nom_joueur + " a maintenant " + m_audace + " points d'audace'");
                InterfaceUtilisateur.println(Arrays.toString(statistiques_de_gain));
            }
            m_cave_precedente = cave;
        }
    }

    @Override
    public int demander_mise(int mise_demandee, ArrayList<Carte> jeu_pt, int pot, int relance_min,
                             ArrayList<Carte> main, int cave_non_misee, int mise_deja_en_jeu) {
        int res;
        changements_audace(cave_non_misee + mise_deja_en_jeu);
        double proba = new CollectionDeCartes(main, jeu_pt).probaVict(Joueur.nombre_de_joueurs() - 1);
        double proba_modif = Math.min(Math.pow(proba,2) * Joueur.nombre_de_joueurs(),proba);
        // Le but de la modification est de pénaliser les probabilités inférieures à 1/nombre_de_joueurs
        // car alors même dans le cas d'une forte espérance, on aurait une variance trop importante.
        double esperance_actuelle = proba_modif * (double) (pot + mise_demandee) - (double) mise_demandee;
        // L'espérance actuelle mais souvent faible en début de tours même sur des bonnes mains.
        double esperance_suivi = proba_modif * (double) (pot + Joueur.stream()
                .filter(Joueur::pas_couche)
                .mapToInt(joueur -> mise_demandee-joueur.get_mise())
                .sum()) -
                (double) mise_demandee;
        // L'espérance si tout le monde suit
        double esperance_tapis = proba_modif * (double) (pot + Joueur.stream()
                .filter(Joueur::pas_couche)
                .mapToInt(joueur -> cave_non_misee + mise_deja_en_jeu - joueur.get_mise())
                .sum()) -
                (double) cave_non_misee -
                (double) mise_deja_en_jeu;
        // On applique cette correction sinon l'espérance du tapis compte trop fort dans la moyenne.
        res = (int) ((esperance_actuelle + esperance_suivi + esperance_tapis) / 3 *
                Math.pow(2,m_audace));
        InterfaceUtilisateur.println("RES = " + res);
        InterfaceUtilisateur.println(proba);
        if (m_bluff_sur_ce_jeu != null) {
            if (m_bluff_sur_ce_jeu != jeu_pt) m_bluff_sur_ce_jeu = null; // C'est un nouveau coup
            if (res < mise_demandee) res += m_cave_initiale / 10;
            InterfaceUtilisateur.println("BLUFF CONTINUE !!!" + res);
        } else if (random.nextInt(m_bluffeur) == 0) {
            if (res < mise_demandee) res += m_cave_initiale / 10;
            InterfaceUtilisateur.println("BLUFF DEBUT !!!" + res);
            m_bluff_sur_ce_jeu = jeu_pt;
        }
        if (res < mise_deja_en_jeu) res = mise_deja_en_jeu; // On checke toujours par défaut
        return res;
    }
}
