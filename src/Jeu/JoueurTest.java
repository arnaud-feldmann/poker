package Jeu;

import Cartes.Carte;
import Cartes.PaquetDeCartes;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {
    @Test
    void joueur_stream_tri() {
        Joueur arnaud = new Joueur("Arnaud",1000,null,new IntelligenceHumaine("Arnaud"),0);
        Joueur bebert = new Joueur("Bébert",1000,arnaud,new IntelligenceHumaine("Bébert"),1);
        Joueur alice = new Joueur("Alice",1000,bebert,new IntelligenceHumaine("Alice"),2);
        Joueur emma = new Joueur("Emma",1000,alice,new IntelligenceHumaine("Emma"),3);
        Joueur pauline = new Joueur("Pauline",1000,emma,new IntelligenceHumaine("Pauline"),4);
        Joueur.donneur = arnaud;
        arnaud.set_joueur_suivant(pauline);
        PaquetDeCartes paquet;
        paquet = new PaquetDeCartes();
        PaquetDeCartes.set_seed(1);
        paquet.melanger_cartes();
        arnaud.init_joueur(paquet);
        bebert.init_joueur(paquet);
        alice.init_joueur(paquet);
        emma.init_joueur(paquet);
        pauline.init_joueur(paquet);
        ArrayList<Carte> m_jeu_pt = new ArrayList<>();
        m_jeu_pt.add(paquet.piocher_une_carte());
        m_jeu_pt.add(paquet.piocher_une_carte());
        m_jeu_pt.add(paquet.piocher_une_carte());
        m_jeu_pt.add(paquet.piocher_une_carte());
        m_jeu_pt.add(paquet.piocher_une_carte());
        List<Joueur> joueurs =
                Joueur
                        .stream()
                        .sorted((x,y) -> x.get_collection(m_jeu_pt).compareTo(y.get_collection(m_jeu_pt)))
                        .collect(Collectors.toList());
        assertTrue(joueurs.get(0).get_collection(m_jeu_pt).compareTo(joueurs.get(1).get_collection(m_jeu_pt)) < 0);
        assertTrue(joueurs.get(1).get_collection(m_jeu_pt).compareTo(joueurs.get(2).get_collection(m_jeu_pt)) < 0);
        assertTrue(joueurs.get(2).get_collection(m_jeu_pt).compareTo(joueurs.get(3).get_collection(m_jeu_pt)) < 0);
        assertTrue(joueurs.get(3).get_collection(m_jeu_pt).compareTo(joueurs.get(4).get_collection(m_jeu_pt)) < 0);
    }
}