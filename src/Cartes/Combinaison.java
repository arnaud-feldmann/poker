/*
Les void en detecte_*  modifient le membre niveau et le membre rang qui correspond aux Valeurs pour faire la
différence. Si le membre niveau est non null c'est que la détection est positive.
J'aurais pu tout aussi bien retourner des booléens mais je n'aime pas les fonctions qui mélangent effets de bord et
retour ; je préfère soit l'un soit l'autre.

*/
package Cartes;

import java.util.ArrayList;

public class Combinaison {
    private Niveau m_niveau;
    private ArrayList<Carte.Valeur> m_rangs = new ArrayList<>();
    public Combinaison(ArrayList<Carte> cartes) throws IllegalArgumentException {
        if (cartes.size() != 7) throw new IllegalArgumentException("On ne peut comparer que les collections complètes");
        final int[] tab_couleurs = new int[Carte.Couleur.values().length];
        final int[] tab_valeurs = new int[Carte.Valeur.values().length];
        final int[] tab_valeurs_couleur_unique;
        Carte.Couleur couleur_unique;
        for (Carte carte : cartes) {
            tab_couleurs[carte.get_couleur().ordinal()]++;
            tab_valeurs[carte.get_valeur().ordinal()]++;
        }
        couleur_unique = check_couleur(tab_couleurs);
        if (couleur_unique == null) tab_valeurs_couleur_unique = null;
        else {
            tab_valeurs_couleur_unique = new int[Carte.Valeur.values().length];
            for (Carte carte : cartes) {
                if (carte.get_couleur() == couleur_unique) tab_valeurs_couleur_unique[carte.get_valeur().ordinal()]++;
            }
        }

        detecte_quinte_flush(tab_couleurs, tab_valeurs_couleur_unique);
        if (m_niveau != null) return;
        detecte_carre(tab_valeurs);
        if (m_niveau != null) return;
        detecte_mainpleine(tab_valeurs);
        if (m_niveau != null) return;
        detecte_flush(tab_couleurs, tab_valeurs_couleur_unique);
        if (m_niveau != null) return;
        detecte_suite(tab_valeurs);
        if (m_niveau != null) return;
        detecte_brelan(tab_valeurs);
        if (m_niveau != null) return;
        detecte_double_paire(tab_valeurs);
        if (m_niveau != null) return;
        detecte_paire(tab_valeurs);
        if (m_niveau != null) return;
        detecte_cartehaute(tab_valeurs);
    }

    public ArrayList<Carte.Valeur> get_rangs() {
        return m_rangs;
    }

    public Niveau get_niveau() {
        return m_niveau;
    }

    Carte.Couleur check_couleur(int[] tab_couleurs) {
        Carte.Couleur res = null;
        for (int i = 0; i < tab_couleurs.length; i++) {
            if (tab_couleurs[i] >= 5) {
                res = Carte.Couleur.values()[i];
                break;
            }
        }
        return res;
    }

    void detecte_quinte_flush(int[] tab_couleurs, int[] tab_valeurs_couleur_unique) {
        m_niveau = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null) {
            detecte_suite(tab_valeurs_couleur_unique);
            if (m_niveau != null) {
                if (m_rangs.get(0) == Carte.Valeur.As) {
                    m_rangs = new ArrayList<>();
                    m_niveau = Niveau.QUINTEFLUSHROYALE;
                } else {
                    m_niveau = Niveau.QUINTEFLUSH;
                }
            }
        }
    }

    boolean detecter_multiples(int[] tab_valeurs, int[] multiples) {
        m_rangs = new ArrayList<>();
        for (int multiple : multiples) {
            for (int i = tab_valeurs.length - 1; i >= 0; i--) {
                Carte.Valeur valeur = Carte.Valeur.values()[i];
                if (tab_valeurs[i] >= multiple && !m_rangs.contains(valeur)) {
                    m_rangs.add(valeur);
                    break;
                }
            }
        }
        return m_rangs.size() == multiples.length;
    }

    void detecte_carre(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs, new int[]{4, 1})) m_niveau = Niveau.CARRE;
    }

    void detecte_mainpleine(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs, new int[]{3, 2})) m_niveau = Niveau.MAINPLEINE;
    }

    void detecte_brelan(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs, new int[]{3, 1, 1})) m_niveau = Niveau.BRELAN;
    }

    void detecte_double_paire(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs, new int[]{2, 2, 1})) m_niveau = Niveau.DOUBLEPAIRE;
    }

    void detecte_paire(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs, new int[]{2, 1, 1, 1})) m_niveau = Niveau.PAIRE;
    }

    void detecte_cartehaute(int[] tab_valeurs) {
        detecter_multiples(tab_valeurs, new int[]{1, 1, 1, 1, 1});
        m_niveau = Niveau.CARTEHAUTE;
    }

    void detecte_suite(int[] tab_valeurs) {
        m_niveau = null;
        Carte.Valeur val_suite = null;
        // C'est une boucle for qui descend selon l'ordre des cartes et checke la présence de suites de cartes successives :
        // - Si la suite est rompue, alors le compteur de cartes successives recommence à zéro
        // - Si la suite atteint 5, pas la peine de continuer la boucle for car on a bien détecté notre suite la plus haute.
        // donc on pose un break.
        for (int i = Carte.Valeur.As.ordinal(), cartes_successives = 0; i >= -1; i--) {
            if (i == -1) {
                if (tab_valeurs[Carte.Valeur.As.ordinal()] != 0) cartes_successives++;
            } // La petite exception avec l'as qui peut servir de carte faible pour les suites
            else if (tab_valeurs[i] != 0) cartes_successives++;
            else cartes_successives = 0;
            if (cartes_successives == 5) {
                val_suite = Carte.Valeur.values()[i + 4];
                break;
            }
        }
        if (val_suite != null) {
            m_niveau = Niveau.SUITE;
            m_rangs = new ArrayList<>();
            m_rangs.add(val_suite);
        }
    }

    void detecte_flush(int[] tab_couleurs, int[] tab_valeurs_couleur_unique) {
        m_niveau = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null) {
            detecte_cartehaute(tab_valeurs_couleur_unique);
            m_niveau = Niveau.COULEUR;
        }
    }

    public int compareTo(Combinaison combinaison) {
        int res = m_niveau.compareTo(combinaison.get_niveau());
        for (int i = 0; res == 0 && i < m_rangs.size(); i++) {
            res = m_rangs.get(i).compareTo(combinaison.get_rangs().get(i));
        }
        return res;
    }

    public enum Niveau {CARTEHAUTE, PAIRE, DOUBLEPAIRE, BRELAN, SUITE, COULEUR, MAINPLEINE, CARRE, QUINTEFLUSH, QUINTEFLUSHROYALE}
}
