package Cartes;

import java.util.ArrayList;

// Cette classe a pour but d'implémenter l'idée d'un "jeu", dont une partie est commune entre joueurs donc m_jeu_pt
// est un pointeur
public class CollectionDeCartes {

    private final ArrayList<Carte> m_main;
    private final ArrayList<Carte> m_jeu_pt;

    public CollectionDeCartes(ArrayList<Carte> main, ArrayList<Carte> jeu_pt) throws IllegalArgumentException {
        if (main.size() != 2) throw new IllegalArgumentException("Une main a deux cartes");
        m_main = new ArrayList<>(main);
        m_jeu_pt = jeu_pt;
    }

    public ArrayList<Carte> get_collection() {
        ArrayList<Carte> res = new ArrayList<>(m_jeu_pt);
        res.addAll(m_main);
        return res;
    }

    public ArrayList<Carte> get_jeu_pt() {
        return m_jeu_pt;
    }

    public int nombre_de_cartes() {
        return 2 + m_jeu_pt.size();
    }

    public void ajouter_carte_en_jeu(Carte carte) throws IllegalStateException {
        if (m_jeu_pt.size() == 5) throw new IllegalStateException("Après la river plus de rajouts !!!");
        m_jeu_pt.add(carte);
    }

    public int compareTo(CollectionDeCartes collection) throws IllegalArgumentException {
        return new Combinaison(get_collection()).compareTo(new Combinaison(collection.get_collection()));
    }

    public double probaVict(int nombre_autres_joueurs) {
        final int NB_SIMULATIONS = 1000;
        CollectionDeCartes collection_joueur_ref;
        CollectionDeCartes collection_autre_joueur;
        ArrayList<Carte> main_autre_joueur;
        ArrayList<Carte> jeu_pt;
        boolean victoire;
        int nombre_de_victoires = 0;
        PaquetDeCartes paquet;
        for (int i = 0; i < NB_SIMULATIONS; i++) {
            jeu_pt = new ArrayList<>(get_jeu_pt());
            collection_joueur_ref = new CollectionDeCartes(m_main, jeu_pt);
            paquet = new PaquetDeCartes();
            paquet.melanger_cartes();
            paquet.retirer_des_cartes_du_jeu(get_collection());
            while (jeu_pt.size() != 5) collection_joueur_ref.ajouter_carte_en_jeu(paquet.piocher_une_carte());
            victoire = true;
            for (int j = 0; j < nombre_autres_joueurs; j++) {
                main_autre_joueur = new ArrayList<>();
                main_autre_joueur.add(paquet.piocher_une_carte());
                main_autre_joueur.add(paquet.piocher_une_carte());
                collection_autre_joueur = new CollectionDeCartes(main_autre_joueur, jeu_pt);
                if (collection_joueur_ref.compareTo(collection_autre_joueur) < 0) victoire = false;
            }
            if (victoire) nombre_de_victoires++;
        }
        return ((double) nombre_de_victoires) / (double) NB_SIMULATIONS;
    }

    public void afficher() {
        Carte.affiche(m_jeu_pt, "jeu");
        Carte.affiche(m_main, "main");
    }
}