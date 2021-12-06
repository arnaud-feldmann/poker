package Cartes;/* La méthode de comparaison beats ne renvoie pas un booléen mais un double qui va de zéro à 1, qui est une probabilité
de victoire sur 1000 essais. Cette méthode est préférée à la comparaison booléenne simple car elle est générique peu
importe le nombre de carte dévoilées et elle permet d'anticiper la construction d'une IA sommaire */

import java.util.ArrayList;

public class CollectionDeCartes {
    private final ArrayList<Carte> m_collection;
    public CollectionDeCartes(ArrayList<Carte> main) {
        if (main.size() != 2) throw new IllegalArgumentException("Une main doit avoir 2 cartes");
        m_collection = main;
    }
    public void ajouter_carte(Carte carte) {
        if (m_collection.size() == 7) throw new IllegalStateException("Après la river plus de rajouts !!!");
        m_collection.add(carte);
    }
    public int compareTo(CollectionDeCartes collection) {
        if (m_collection.size() != 7) throw new IllegalStateException("On ne peut comparer que les collections complètes");
        return 1;
    }
}
