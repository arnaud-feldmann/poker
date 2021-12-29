package Jeu;

import Cartes.Carte;
import Cartes.CollectionDeCartes;
import Cartes.PaquetDeCartes;

import java.util.ArrayList;
import java.util.stream.Stream;

class ComplementMiseNegatifException extends IllegalArgumentException {
    ComplementMiseNegatifException() {
        super("Un complément de mise ne peut pas être négatif.");
    }
}

/*
La classe Joueur rassemble tout ce qui est individualisable dans le jeu de poker :
- Le jeton de donneur qui circule
- Le nom du joueur
- La cave actuelle dont on ôte directement les mises
- Le joueur suivant (Joueur est une structure récursive
- La mise actuelle
- Les cartes de la main
- L'état du joueur (PEUT_MISER, TAPIS ou COUCHE)
- Son Intelligence, à savoir une interface qui est soit humaine soit artificielle
- Le numéro du joueur dans l'interface graphique
 */
public class Joueur {
    protected static Joueur donneur;
    public enum Etat {PEUT_MISER,TAPIS,COUCHE}
    final private String m_nom_joueur;
    private int m_cave;
    private Joueur m_joueur_suivant;
    private int m_mise;
    private ArrayList<Carte> m_main;
    private Etat m_etat;
    final private Intelligence m_intelligence;
    final private int m_numero_joueur_interface_graphique;

    protected static int nombre_de_joueurs() {
        int res = 1;
        for (Joueur joueur = donneur.get_joueur_suivant() ; joueur != donneur ; joueur = joueur.get_joueur_suivant()) res++;
        return res;
    }

    protected int get_numero_joueur_interface() {
        return m_numero_joueur_interface_graphique;
    }
    protected ArrayList<Carte> get_main() {
        return m_main;
    }
    protected int get_cave() {
        return m_cave;
    }
    protected CollectionDeCartes get_collection(ArrayList<Carte> m_jeu_pt) {
        return new CollectionDeCartes(get_main(),m_jeu_pt);
    }
    protected int get_mise() {
        return m_mise;
    }
    protected boolean pas_couche() {
        return m_etat != Etat.COUCHE;
    }
    protected Etat get_etat() {
        return m_etat;
    }
    Joueur get_joueur_suivant() {
        return m_joueur_suivant;
    }
    protected void set_joueur_suivant(Joueur joueur_suivant) {
        m_joueur_suivant = joueur_suivant;
    }
    /* On passe le jeton au donneur suivant. Si le donneur ne change pas c'est qu'on a terminé la partie ! */
    protected static boolean inc_donneur() {
        Joueur donneur_save = donneur;
        donneur = donneur.get_joueur_suivant();

        return donneur_save != donneur;
    }
    protected void coucher() {
        m_etat = Etat.COUCHE;
        InterfaceUtilisateur.interface_graphique.joueurPasDeCarte(m_numero_joueur_interface_graphique);
        InterfaceUtilisateur.println(m_nom_joueur + " se couche.");
    }

    protected Joueur(String nom_joueur, int cave, Joueur joueur_suivant, Intelligence intelligence,int numero_joueur_interface) {
        m_nom_joueur = nom_joueur;
        m_main = null;
        m_cave = cave;
        m_joueur_suivant = joueur_suivant;
        m_intelligence = intelligence;
        m_numero_joueur_interface_graphique = numero_joueur_interface;
        InterfaceUtilisateur.interface_graphique.joueurSetNom(m_numero_joueur_interface_graphique,m_nom_joueur);
    }

    protected void init_tour_joueur(PaquetDeCartes paquet) {
        ArrayList<Carte> main = new ArrayList<>();
        main.add(paquet.piocher_une_carte());
        main.add(paquet.piocher_une_carte());
        m_main = new ArrayList<>(main);
        m_etat = Etat.PEUT_MISER;
        m_mise = 0;
        if (m_numero_joueur_interface_graphique == 0)
            InterfaceUtilisateur.interface_graphique.joueurSetCartesVisibles(m_numero_joueur_interface_graphique, main.get(0), main.get(1));
        else InterfaceUtilisateur.interface_graphique.joueurSetCartesCachees(m_numero_joueur_interface_graphique);
        InterfaceUtilisateur.interface_graphique.joueurSetJetons(m_numero_joueur_interface_graphique, InterfaceUtilisateur.jetons_ig(m_cave));
    }

    /*
    Cette méthode est utile étant donné que les joueurs sont implémentés en structure récursive circulaire,
    je trouve ici que c'est plus joli que des for (et puis je voulais aussi découvrir un peu l'interface fonctionnelle
    de Java)
     */
    protected static Stream<Joueur> stream() {
        Joueur joueur_temp = donneur;
        Stream.Builder<Joueur> builder = Stream.builder();
        for (int i = 0 ; i < Joueur.nombre_de_joueurs() ; i++) {
            builder.add(joueur_temp);
            joueur_temp = joueur_temp.get_joueur_suivant();
        }
        return builder.build();
    }

    /*
    Cette méthode est en fait la seule différence entre un joueur virtuel et réel, c'est pour ça que son implémentation
     est laissée à une interface. On pourrait rajouter d'autres méthodes tant qu'elles implémente cette méthode.
     On demande la mise que souhaite miser l'Intelligence ; si cette mise est inférieure à la mise nécessaire pour
     rester en jeu, le joueur se couche.
     */
    protected int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min) {
        InterfaceUtilisateur.interface_graphique.raffraichit();
        return m_intelligence.demander_mise(mise_demandee,jeu_pt,pot,relance_min,m_main,m_cave,m_mise);
    }

    /* Cette méthode met de côté les mises en attendant le partage des gains, et les ajoute également au pot */
    protected void ajouter_mise(int complement,int[] pot_pt) {
        if (complement < 0) throw new ComplementMiseNegatifException();
        else if (complement >= m_cave) {         // Quand on suit la mise demandée est parfois supérieure à la cave
            complement = m_cave;
            m_etat = Etat.TAPIS;
            InterfaceUtilisateur.println(m_nom_joueur + " a misé tout son tapis !");
        } else if (complement == 0) {
            InterfaceUtilisateur.println(m_nom_joueur + " checke.");
            return;
        }
        InterfaceUtilisateur.println(m_nom_joueur + " ajoute " + complement + " dans le pot.");
        m_cave -= complement;
        pot_pt[0] += complement;
        m_mise += complement;
        InterfaceUtilisateur.println("Sa mise est maintenant de " + m_mise + " et le pot vaut " + pot_pt[0]);
        InterfaceUtilisateur.interface_graphique.joueurSetJetons(m_numero_joueur_interface_graphique, InterfaceUtilisateur.jetons_ig(m_cave));
        InterfaceUtilisateur.interface_graphique.fixeMises(InterfaceUtilisateur.jetons_ig(pot_pt[0]));
    }

    /*
    Cette méthode retire un certain montant de la mise (et du pot) et déverse cela  dans le pointeur de retrait, afin
    de pouvoir distribuer les gains
     */
    protected void retirer_mise(int montant,int[] pot_pt,int[] retrait_pt) {
        if (montant < 0) throw new ComplementMiseNegatifException();
        if (montant >= m_mise) montant = m_mise;
        pot_pt[0] -= montant;
        retrait_pt[0] += montant;
        m_mise -= montant;
        InterfaceUtilisateur.interface_graphique.fixeMises(InterfaceUtilisateur.jetons_ig(pot_pt[0]));
    }

    /* cette méthode encaisse les gains */
    protected void encaisser(int montant) {
        m_cave += montant;
        InterfaceUtilisateur.interface_graphique.joueurSetJetons(m_numero_joueur_interface_graphique,InterfaceUtilisateur.jetons_ig(m_cave));
    }

    /* Cette méthode révèle la main à la fin d'un tour */
    protected void affiche_main() {
        Carte.affiche(m_main,"Main de " + m_nom_joueur);
        InterfaceUtilisateur.interface_graphique.joueurSetCartesVisibles(m_numero_joueur_interface_graphique,
                m_main.get(0),m_main.get(1));
    }

    @Override
    public String toString() {
        return m_nom_joueur;
    }
}