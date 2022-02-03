package Cartes;

import java.util.ArrayList;
import java.util.Random;

public class PaquetDeCartes {
    private final static Random piece = new Random();
    private final ArrayList<Carte> m_cartes = new ArrayList<>();

    public PaquetDeCartes() {
        for (Carte.Couleur couleur : Carte.Couleur.values()) {
            for (Carte.Valeur valeur : Carte.Valeur.values()) {
                m_cartes.add(new Carte(valeur, couleur));
            }
        }
    }

    public static void set_seed(long seed) {
        piece.setSeed(seed);
    }

    private static boolean pile_ou_face() {
        return piece.nextBoolean();
    }

    private void couper(int debut, int milieu, int fin) {
        for (int i = 0; i <= fin - milieu - 1; i++) {
            m_cartes.add(debut, m_cartes.remove(fin));
        }
    }

    private void riffle_parfait(int debut, int milieu, int fin) {
        for (int i = 0; i <= fin - milieu - 1; i++) {
            m_cartes.add(debut + 2 * i, m_cartes.remove(milieu + 1 + i));
        }
    }

    private void super_melange_recursion(int debut, int fin) {
        if (debut == fin) return;
        final int milieu = debut + (fin - debut + 1) / 2 - 1;
        if (pile_ou_face()) couper(debut, milieu, fin);
        riffle_parfait(debut, milieu, fin);
        super_melange_recursion(debut, milieu);
        super_melange_recursion(milieu + 1, fin);
    }

    public void melanger_cartes() {
        for (int i = 0; i < 5; i++) super_melange_recursion(0, m_cartes.size() - 1);
    }

    public Carte piocher_une_carte() {
        return m_cartes.remove(0);
    }

    public void bruler_une_carte() {
        piocher_une_carte();
    }

    public void retirer_des_cartes_du_jeu(ArrayList<Carte> cartes) {
        m_cartes.removeAll(cartes);
    }

    public Carte reveler_une_carte() {
        return new Carte(m_cartes.get(0));
    }

    public int nombre_de_cartes() {
        return m_cartes.size();
    }

}
