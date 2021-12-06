package Cartes;

import java.util.ArrayList;

public class Combinaison {
    public enum Niveau {QUINTEFLUSHROYALE,QUINTEFLUSH,CARRE,MAINPLEINE,COULEUR,SUITE,BRELAN,DOUBLEPAIRE,PAIRE,CARTEHAUTE}
    private Niveau m_niveau;
    private ArrayList<Carte.Valeur> m_rangs = new ArrayList<>();
    public ArrayList<Carte.Valeur> get_rangs() {
        return m_rangs;
    }
    public Niveau get_niveau() {
        return m_niveau;
    }
    public Combinaison(ArrayList<Carte> cartes) {
        if (cartes.size() != 7) throw new IllegalArgumentException("Une main pleine contient 7 cartes");
        final int[] tab_couleurs = new int[Carte.Couleur.values().length];
        final int[] tab_valeurs = new int[Carte.Valeur.values().length];
        final int[][] tab_couleurs_valeurs = new int[Carte.Couleur.values().length][Carte.Valeur.values().length];
        for (Carte carte : cartes) {
            tab_couleurs[carte.get_couleur().ordinal()]++;
            tab_valeurs[carte.get_valeur().ordinal()]++;
            tab_couleurs_valeurs[carte.get_couleur().ordinal()][carte.get_valeur().ordinal()]++;
        }

        detecte_quinte_flush(tab_couleurs,tab_couleurs_valeurs);
        if (m_niveau != null) return;
        detecte_carre(tab_valeurs);
        if (m_niveau != null) return;
        detecte_mainpleine(tab_valeurs);
        if (m_niveau != null) return;
        detecte_flush(tab_couleurs,tab_couleurs_valeurs);
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
    Carte.Couleur check_couleur(int[] tab_couleurs) {
        Carte.Couleur res = null;
        for (int i = 0;i < tab_couleurs.length;i++) {
            if (tab_couleurs[i] >= 5) {
                res = Carte.Couleur.values()[i];
                break;
            }
        }
        return res;
    }
    void detecte_quinte_flush(int[] tab_couleurs,int[][] tab_couleurs_valeurs) {
        m_niveau = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null)  {
            detecte_suite(tab_couleurs_valeurs[mono_couleur.ordinal()]);
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
    boolean detecter_multiples(int[] tab_valeurs,int[] multiples) {
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
        if (detecter_multiples(tab_valeurs,new int[] {4,1})) m_niveau = Niveau.CARRE;
    }
    void detecte_mainpleine(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {3,2})) m_niveau = Niveau.MAINPLEINE;
    }
    void detecte_brelan(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {3,1,1})) m_niveau = Niveau.BRELAN;
    }
    void detecte_double_paire(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {2,2,1})) m_niveau = Niveau.DOUBLEPAIRE;
    }
    void detecte_paire(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {2,1,1,1})) m_niveau = Niveau.PAIRE;
    }
    void detecte_cartehaute(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {1,1,1,1,1})) m_niveau = Niveau.CARTEHAUTE;
    }
    void detecte_suite(int[] tab_valeurs) {
        m_niveau = null;
        Carte.Valeur val_suite = null;
        for (int i = Carte.Valeur.As.ordinal(),cartes_successives = 0; i >=-1 ; i--) {
            if (i == -1) {
                if (tab_valeurs[Carte.Valeur.As.ordinal()] != 0) cartes_successives++;
            }
            else if (tab_valeurs[i] != 0) cartes_successives++;
            else cartes_successives = 0;
            if (cartes_successives == 5) {
                val_suite = Carte.Valeur.values()[i+4];
                break;
            }
        }
        if (val_suite != null) {
            m_niveau = Niveau.SUITE;
            m_rangs = new ArrayList<>();
            m_rangs.add(val_suite);
        }
    }
    void detecte_flush(int[] tab_couleurs,int[][] tab_couleurs_valeurs) {
        m_niveau = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null)  {
            detecte_cartehaute(tab_couleurs_valeurs[mono_couleur.ordinal()]);
            m_niveau = Niveau.COULEUR;
        }
    }
}
