package Cartes;

/* La méthode de comparaison beat ne renvoie pas un booléen mais un double qui va de zéro à 1, qui est une probabilité
de victoire sur 10000 essais. Cette méthode est préférée à la comparaison booléenne simple car elle est générique peu
importe le nombre de carte dévoilées et elle permet d'anticiper la construction d'une IA sommaire */

import java.util.ArrayList;

public class CollectionDeCartes {

    private final ArrayList<Carte> m_main;
    private final ArrayList<Carte> m_reference_jeu;

    public CollectionDeCartes(ArrayList<Carte> main,ArrayList<Carte> reference_jeu) throws IllegalArgumentException {
        if (main.size() != 2) throw new IllegalArgumentException("Une main a deux cartes");
        m_main = new ArrayList<>(main);
        m_reference_jeu = reference_jeu;
    }
    public  ArrayList<Carte> get_collection() {
        ArrayList<Carte> res = new ArrayList<>(m_reference_jeu);
        res.addAll(m_main);
        return res;
    }

    public ArrayList<Carte> get_main() {
        return m_main;
    }

    public ArrayList<Carte> get_reference_jeu() {
        return m_reference_jeu;
    }

    public int size() {
        return m_main.size() + m_reference_jeu.size();
    }

    public void ajouter_carte_en_jeu(Carte carte) throws IllegalStateException {
        if (m_reference_jeu.size() == 5) throw new IllegalStateException("Après la river plus de rajouts !!!");
        m_reference_jeu.add(carte);
    }

    public int compareTo(CollectionDeCartes collection) throws IllegalArgumentException {
        return new Combinaison(get_collection()).compareTo(new Combinaison(collection.get_collection()));
    }

    public double probaVict(int nombre_autres_joueurs) {
        CollectionDeCartes collection_joueur_ref;
        CollectionDeCartes collection_autre_joueur;
        ArrayList<Carte> main_autre_joueur;
        ArrayList<Carte> reference_jeu;
        boolean victoire;
        int nombre_de_victoires = 0;
        PaquetDeCartes paquet;
        for (int i = 0; i < 10000; i++) {
            reference_jeu = new ArrayList<>(get_reference_jeu());
            collection_joueur_ref  = new CollectionDeCartes(get_main(),reference_jeu);
            paquet = new PaquetDeCartes();
            paquet.melanger_cartes();
            paquet.retirer_des_cartes_du_jeu(get_collection());
            while (reference_jeu.size() != 5) collection_joueur_ref.ajouter_carte_en_jeu(paquet.piocher_une_carte());
            victoire = true;
            for (int j = 0; j < nombre_autres_joueurs; j++) {
                main_autre_joueur = new ArrayList<>();
                main_autre_joueur.add(paquet.piocher_une_carte());
                main_autre_joueur.add(paquet.piocher_une_carte());
                collection_autre_joueur = new CollectionDeCartes(main_autre_joueur,reference_jeu);
                if (collection_joueur_ref.compareTo(collection_autre_joueur) < 0) victoire = false;
            }
            if (victoire) nombre_de_victoires++;
        }
        return ((double)nombre_de_victoires)/10000d;
    }

    public static void main(String[] args) {
        ArrayList<Carte> main = new ArrayList<>();
        main.add(new Carte(Carte.Valeur.As,Carte.Couleur.Carreau));
        main.add(new Carte(Carte.Valeur.As,Carte.Couleur.Trefle));
        CollectionDeCartes c = new CollectionDeCartes(main,new ArrayList<>());
        System.out.println("Proba de victoire avec deux as et 2 joueurs :" + c.probaVict(2)*100);
        System.out.println("Proba de victoire avec deux as et 3 joueurs :" + c.probaVict(3)*100);
        System.out.println("Proba de victoire avec deux as et 4 joueurs :" + c.probaVict(4)*100);
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.As,Carte.Couleur.Coeur));
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.Roi,Carte.Couleur.Trefle));
        c.ajouter_carte_en_jeu(new Carte(Carte.Valeur.Quatre,Carte.Couleur.Trefle));
        System.out.println("Proba de victoire avec trois as et 2 joueurs :" + c.probaVict(2)*100);
        System.out.println("Proba de victoire avec trois as et 3 joueurs :" + c.probaVict(3)*100);
        System.out.println("Proba de victoire avec trois as et 4 joueurs :" + c.probaVict(4)*100);
        main = new ArrayList<>();
        main.add(new Carte(Carte.Valeur.Sept,Carte.Couleur.Carreau));
        main.add(new Carte(Carte.Valeur.Huit,Carte.Couleur.Trefle));
        c = new CollectionDeCartes(main,new ArrayList<>());
        System.out.println("Proba de victoire avec Sept et Huit et 2 joueurs :" + c.probaVict(2)*100);
        System.out.println("Proba de victoire avec Sept et Huit et 3 joueurs :" + c.probaVict(3)*100);
        System.out.println("Proba de victoire avec Sept et Huit et 4 joueurs :" + c.probaVict(4)*100);
    }
}