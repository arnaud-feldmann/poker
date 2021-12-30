package Cartes;

import java.util.ArrayList;

public class CollectionDeCartes {

    private final ArrayList<Carte> m_main;
    private final ArrayList<Carte> m_jeu_pt;

    public CollectionDeCartes(ArrayList<Carte> main, ArrayList<Carte> reference_jeu) throws IllegalArgumentException {
        if (main.size() != 2) throw new IllegalArgumentException("Une main a deux cartes");
        m_main = new ArrayList<>(main);
        m_jeu_pt = reference_jeu;
    }

    public ArrayList<Carte> get_collection() {
        ArrayList<Carte> res = new ArrayList<>(m_jeu_pt);
        res.addAll(m_main);
        return res;
    }

    public ArrayList<Carte> get_main() {
        return new ArrayList<>(m_main);
    }

    public ArrayList<Carte> get_reference_jeu() {
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
        ArrayList<Carte> reference_jeu;
        boolean victoire;
        int nombre_de_victoires = 0;
        PaquetDeCartes paquet;
        for (int i = 0; i < NB_SIMULATIONS; i++) {
            reference_jeu = new ArrayList<>(get_reference_jeu());
            collection_joueur_ref = new CollectionDeCartes(get_main(), reference_jeu);
            paquet = new PaquetDeCartes();
            paquet.melanger_cartes();
            paquet.retirer_des_cartes_du_jeu(get_collection());
            while (reference_jeu.size() != 5) collection_joueur_ref.ajouter_carte_en_jeu(paquet.piocher_une_carte());
            victoire = true;
            for (int j = 0; j < nombre_autres_joueurs; j++) {
                main_autre_joueur = new ArrayList<>();
                main_autre_joueur.add(paquet.piocher_une_carte());
                main_autre_joueur.add(paquet.piocher_une_carte());
                collection_autre_joueur = new CollectionDeCartes(main_autre_joueur, reference_jeu);
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