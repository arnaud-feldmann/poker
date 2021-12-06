package Cartes;

import java.util.ArrayList;
import java.util.Random;

public class PaquetDeCartes {
    private final ArrayList<Carte> m_cartes = new ArrayList<>();
    private final static Random piece = new Random();

    private static boolean pile_ou_face() {
        return piece.nextInt(2) == 1;
    }

    PaquetDeCartes() {
        for (Carte.Couleur couleur : Carte.Couleur.values()) {
            for (Carte.Valeur valeur : Carte.Valeur.values()) {
                m_cartes.add(new Carte(valeur, couleur));
            }
        }
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

    public Carte reveler_une_carte() {
        return m_cartes.get(0);
    }

    public int nombre_de_cartes() {
        return m_cartes.size();
    }

    @Override
    public String toString() {
        return "Cartes.PaquetDeCartes " + m_cartes;
    }

    public static void main(String[] args) {
        PaquetDeCartes paquet;

        paquet = new PaquetDeCartes();
        System.out.println("Un paquet tout neuf :");
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");
        System.out.println("Le paquet coupé ");
        paquet.couper(0,25,51);
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        paquet = new PaquetDeCartes();
        System.out.println("Un paquet tout neuf :");
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");
        System.out.println("Le paquet coupé sur la deuxième moité du paquet :");
        paquet.couper(26,38,51);
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        paquet = new PaquetDeCartes();
        System.out.println("Un paquet tout neuf :");
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");
        System.out.println("Le paquet après riffle :");
        paquet.riffle_parfait(0,25,51);
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        paquet = new PaquetDeCartes();
        System.out.println("Un paquet tout neuf :");
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");
        System.out.println("Le paquet après riffle sur la deuxième moitié du paquet :");
        paquet.riffle_parfait(26,38,51);
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        System.out.println();
        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println("Le paquet généré au début :");
        paquet = new PaquetDeCartes();
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        piece.setSeed(2L);

        System.out.println();
        System.out.println("Le paquet mélangé :");
        paquet.melanger_cartes();
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        System.out.println();
        System.out.println("Le paquet avec une carte brûlée :");
        paquet.bruler_une_carte();
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        System.out.println();
        Carte carte_piochee_1 = paquet.piocher_une_carte();
        Carte carte_piochee_2 = paquet.piocher_une_carte();
        System.out.println("On a pioché deux cartes :" + carte_piochee_1 + " et " + carte_piochee_2);
        if (carte_piochee_1.compareTo(carte_piochee_2) > 0) System.out.println("La première carte piochée est plus forte.");
        if (carte_piochee_1.compareTo(carte_piochee_2) == 0) System.out.println("La première carte piochée est de valeur égale à la deuxième.");
        if (carte_piochee_1.compareTo(carte_piochee_2) < 0) System.out.println("La première carte piochée est plus faible.");
        System.out.println("Le paquet après ces 2 carte piochées :");
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");

        System.out.println();
        System.out.println("On a révélé une carte :" + paquet.reveler_une_carte());
        System.out.println("Le paquet avec une carte révélée :");
        System.out.println(paquet);
        System.out.println("Le paquet a " + paquet.nombre_de_cartes() + " cartes");
    }
}
