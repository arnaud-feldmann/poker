import java.util.ArrayList;
import java.util.Collections;

public class Combinaison {
    public enum Type {QUINTEFLUSHROYALE,QUINTEFLUSH,CARRE,MAINPLEINE,COULEUR,SUITE,BRELAN,DOUBLEPAIRE,PAIRE,CARTE}
    private Type m_type;
    private ArrayList<Carte.Valeur> m_complement = new ArrayList<>();
    @Override
    public String toString() {
        return m_type + m_complement.toString();
    }
    Combinaison(ArrayList<Carte> cartes) {
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
        if (m_type != null) return;
        detecte_carre(tab_valeurs);
        if (m_type != null) return;
        detecte_mainpleine(tab_valeurs);
        if (m_type != null) return;
        detecte_flush(tab_couleurs,tab_couleurs_valeurs);
        if (m_type != null) return;
        detecte_suite(tab_valeurs);
        if (m_type != null) return;
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
        m_type = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null)  {
            detecte_suite(tab_couleurs_valeurs[mono_couleur.ordinal()]);
            if (m_type != null) {
                if (m_complement.get(0) == Carte.Valeur.As) {
                    m_type = Type.QUINTEFLUSHROYALE;
                    return;
                } else {
                    m_type = Type.QUINTEFLUSH;
                    return;
                }
            }
        }
    }
    void detecte_multiples(int[] tab_valeurs,int[] multiples) {
        Carte.Valeur[] valeurs = new Carte.Valeur[multiples.length];
        boolean tous_determines;
        for (int i = tab_valeurs.length-1;i >= 0;i--) {
            tous_determines = true;
            for (int j = 0 ;j < multiples.length;j++) {
                if (tab_valeurs[i] >= multiples[j]) valeurs[j] = Carte.Valeur.values()[i];
                else if (valeurs[j] == null) tous_determines = false;
            }
            if (tous_determines) break;
        }
        Collections.addAll(m_complement,valeurs);
    }
    void detecte_carre(int[] tab_valeurs) {
        m_type = null;
        Carte.Valeur val_carre = null;
        Carte.Valeur val_kicker = null;
        for (int i = tab_valeurs.length-1;i >= 0;i--) {
            if (tab_valeurs[i] >= 4) {
                val_carre = Carte.Valeur.values()[i];
                if (val_kicker != null) break;
            } else if (tab_valeurs[i] != 0) {
                val_kicker = Carte.Valeur.values()[i];
                if (val_carre != null) break;
            }
        }
        if (val_carre != null) {
            m_type = Type.CARRE;
            m_complement = new ArrayList<>();
            m_complement.add(val_carre);
            m_complement.add(val_kicker);
        }
    }
    void detecte_mainpleine(int[] tab_valeurs) {
        m_type = null;
        Carte.Valeur val_brelan = null;
        Carte.Valeur val_paire = null;
        for (int i = tab_valeurs.length-1;i >= 0;i--) {
            if (tab_valeurs[i] >= 3) {
                val_brelan = Carte.Valeur.values()[i];
                if (val_paire != null) break;
            } else if (tab_valeurs[i] >= 2) {
                val_paire = Carte.Valeur.values()[i];
                if (val_brelan != null) break;
            }
        }
        if (val_brelan != null && val_paire != null) {
            m_type = Type.MAINPLEINE;
            m_complement = new ArrayList<>();
            m_complement.add(val_brelan);
            m_complement.add(val_paire);
        }
    }
    void detecte_suite(int[] tab_valeurs) {
        m_type = null;
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
            m_type = Type.SUITE;
            m_complement = new ArrayList<>();
            m_complement.add(val_suite);
        }
    }
    void detecte_flush(int[] tab_couleurs,int[][] tab_couleurs_valeurs) {
        m_type = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null)  {
            detecte_carte(tab_couleurs_valeurs[mono_couleur.ordinal()]);
            m_type = Type.COULEUR;
        }
    }
    void detecte_carte(int[] tab_valeurs) {
        m_type = Type.CARTE;
        m_complement = new ArrayList<>();
        for (int i = Carte.Valeur.As.ordinal(),nombre_cartes = 0; i >=-1 ; i--) {
            if (tab_valeurs[i] != 0) {
                m_complement.add(Carte.Valeur.values()[i]);
                nombre_cartes++;
                if (nombre_cartes == 5) break;
            }
        }
    }

    public static void main(String[] args) {

        ArrayList c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dix,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        Combinaison comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("QuinteFlushRoyale[As] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dix,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("QuinteFlush[Roi] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Cinq,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Trois,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("QuinteFlush[Cinq] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Pique));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Carré[Roi,As] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Pique));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Main Pleine[Roi,As] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Dix,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Cinq,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Couleur[Dix, Huit, Sept, Cinq, Deux] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Deux,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Cinq,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Suite[Neuf] :" + comb);
        System.out.println();
    }
}
