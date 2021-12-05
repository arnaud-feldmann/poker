import java.util.ArrayList;

public class Combinaison {
    public enum Type {QUINTEFLUSHROYALE,QUINTEFLUSH,CARRE,MAINPLEINE,COULEUR,SUITE,BRELAN,DOUBLEPAIRE,PAIRE,CARTEHAUTE}
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
        detecte_brelan(tab_valeurs);
        if (m_type != null) return;
        detecte_double_paire(tab_valeurs);
        if (m_type != null) return;
        detecte_paire(tab_valeurs);
        if (m_type != null) return;
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
        m_type = null;
        final Carte.Couleur mono_couleur;
        mono_couleur = check_couleur(tab_couleurs);
        if (mono_couleur != null)  {
            detecte_suite(tab_couleurs_valeurs[mono_couleur.ordinal()]);
            if (m_type != null) {
                if (m_complement.get(0) == Carte.Valeur.As) {
                    m_type = Type.QUINTEFLUSHROYALE;
                } else {
                    m_type = Type.QUINTEFLUSH;
                }
            }
        }
    }
    boolean detecter_multiples(int[] tab_valeurs,int[] multiples) {
        m_complement = new ArrayList<>();
        for (int multiple : multiples) {
            for (int i = tab_valeurs.length - 1; i >= 0; i--) {
                Carte.Valeur valeur = Carte.Valeur.values()[i];
                if (tab_valeurs[i] >= multiple && !m_complement.contains(valeur)) {
                    m_complement.add(valeur);
                    break;
                }
            }
        }
        return m_complement.size() == multiples.length;
    }
    void detecte_carre(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {4,1})) m_type = Type.CARRE;
    }
    void detecte_mainpleine(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {3,2})) m_type = Type.MAINPLEINE;
    }
    void detecte_brelan(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {3,1,1})) m_type = Type.BRELAN;
    }
    void detecte_double_paire(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {2,2,1})) m_type = Type.DOUBLEPAIRE;
    }
    void detecte_paire(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {2,1,1,1})) m_type = Type.PAIRE;
    }
    void detecte_cartehaute(int[] tab_valeurs) {
        if (detecter_multiples(tab_valeurs,new int[] {1,1,1,1,1})) m_type = Type.CARTEHAUTE;
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
            detecte_cartehaute(tab_couleurs_valeurs[mono_couleur.ordinal()]);
            m_type = Type.COULEUR;
        }
    }

    public static void main(String[] args) {

        ArrayList<Carte> c = new ArrayList<>();
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

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Neuf,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Coeur));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Brelan[Six,Neuf,Huit] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Carreau));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Double Paire[Huit,Sept,Six] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Six,Carte.Couleur.Carreau));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Double Paire[Huit,Sept,Six] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Paire[Huit,As,Dame,Valet] :" + comb);
        System.out.println();

        c = new ArrayList<>();
        c.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Roi,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        c.add(new Carte(Carte.Valeur.Dame,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        c.add(new Carte(Carte.Valeur.Valet,Carte.Couleur.Carreau));
        comb = new Combinaison(c);
        System.out.println(c);
        System.out.println("Carte Haute[As,Dame,Roi,Valet,Huit] :" + comb);
        System.out.println();
    }
}
