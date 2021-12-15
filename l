[1mdiff --git a/src/Jeu/Intelligence.java b/src/Jeu/Intelligence.java[m
[1mindex 8c93abe..4a39988 100644[m
[1m--- a/src/Jeu/Intelligence.java[m
[1m+++ b/src/Jeu/Intelligence.java[m
[36m@@ -117,7 +117,7 @@[m [mclass IntelligenceArtificielle implements Intelligence {[m
         System.out.println(nom_joueur + " a " + m_prudence + " points de prudence et " + m_sang_froid + " de sang-froid.");[m
     }[m
     private void deconcentration(String nom_joueur) {[m
[31m-        if (m_prudence > 2) m_prudence--;[m
[32m+[m[32m        if (m_prudence >= 2) m_prudence--;[m
         System.out.println(nom_joueur + " est déconcentré !!! Il a dorénavant " + m_prudence + " points de prudence.");[m
     }[m
     private void reconcentration(String nom_joueur) {[m
[36m@@ -128,12 +128,12 @@[m [mclass IntelligenceArtificielle implements Intelligence {[m
         int rnd = m_random.nextInt(21) - 10;[m
         if (cave != m_cave_precedente) {[m
             if (m_sang_froid == 0) deconcentration(m_nom_joueur);[m
[31m-            if (cave < m_cave_precedente) {[m
[32m+[m[32m            else if (cave < m_cave_precedente) {[m
                 if (rnd < m_sang_froid) reconcentration(m_nom_joueur);[m
                 else deconcentration(m_nom_joueur);[m
             }[m
             else {[m
[31m-                if (rnd < m_sang_froid) reconcentration(m_nom_joueur);[m
[32m+[m[32m                if (rnd > m_sang_froid) reconcentration(m_nom_joueur);[m
                 else deconcentration(m_nom_joueur);[m
             }[m
             m_cave_precedente = cave;[m
[1mdiff --git a/src/Jeu/Joueur.java b/src/Jeu/Joueur.java[m
[1mindex 303799d..774f577 100644[m
[1m--- a/src/Jeu/Joueur.java[m
[1m+++ b/src/Jeu/Joueur.java[m
[36m@@ -24,16 +24,21 @@[m [mpublic class Joueur {[m
     private ArrayList<Carte> m_main;[m
     private Etat m_etat;[m
     private final Intelligence m_intelligence;[m
[31m-    private final InterfacePoker m_interface_poker;[m
     private final int m_numero_joueur_interface;[m
     public static int nombre_de_joueurs() {[m
         int res = 1;[m
         for (Joueur joueur = donneur.get_joueur_suivant() ; joueur != donneur ; joueur = joueur.get_joueur_suivant()) res++;[m
         return res;[m
     }[m
[32m+[m
[32m+[m[32m    public int get_numero_joueur_interface() {[m
[32m+[m[32m        return m_numero_joueur_interface;[m
[32m+[m[32m    }[m
[32m+[m
     public static boolean inc_donneur() {[m
         Joueur donneur_save = donneur;[m
         donneur = donneur.get_joueur_suivant();[m
[32m+[m
         return donneur_save != donneur;[m
     }[m
     public static Stream<Joueur> stream() {[m
[36m@@ -45,16 +50,14 @@[m [mpublic class Joueur {[m
         }[m
         return builder.build();[m
     }[m
[31m-    Joueur(String nom_joueur, int cave, Joueur joueur_suivant, Intelligence intelligence,[m
[31m-           InterfacePoker interface_poker,int numero_joueur_interface) {[m
[32m+[m[32m    Joueur(String nom_joueur, int cave, Joueur joueur_suivant, Intelligence intelligence,int numero_joueur_interface) {[m
         m_nom_joueur = nom_joueur;[m
         m_main = null;[m
         m_cave = cave;[m
         m_joueur_suivant = joueur_suivant;[m
         m_intelligence = intelligence;[m
[31m-        m_interface_poker = interface_poker;[m
         m_numero_joueur_interface = numero_joueur_interface;[m
[31m-        m_interface_poker.joueurSetNom(m_numero_joueur_interface,m_nom_joueur);[m
[32m+[m[32m        Poker.interface_graphique.joueurSetNom(m_numero_joueur_interface,m_nom_joueur);[m
     }[m
     protected void init_joueur(PaquetDeCartes paquet) {[m
         ArrayList<Carte> main = new ArrayList<>();[m
[36m@@ -63,9 +66,9 @@[m [mpublic class Joueur {[m
         m_main = new ArrayList<>(main);[m
         m_etat = Etat.PEUT_MISER;[m
         m_mise = 0;[m
[31m-        if (m_numero_joueur_interface == 0) m_interface_poker.joueurSetCartesVisibles(m_numero_joueur_interface,main.get(0),main.get(1));[m
[31m-        else m_interface_poker.joueurSetCartesCachees(m_numero_joueur_interface);[m
[31m-        m_interface_poker.joueurSetJetons(m_numero_joueur_interface,Poker.jetons(m_cave));[m
[32m+[m[32m        if (m_numero_joueur_interface == 0) Poker.interface_graphique.joueurSetCartesVisibles(m_numero_joueur_interface,main.get(0),main.get(1));[m
[32m+[m[32m        else Poker.interface_graphique.joueurSetCartesCachees(m_numero_joueur_interface);[m
[32m+[m[32m        Poker.interface_graphique.joueurSetJetons(m_numero_joueur_interface,Poker.jetons(m_cave));[m
     }[m
     ArrayList<Carte> get_main() {[m
         return m_main;[m
[36m@@ -93,11 +96,11 @@[m [mpublic class Joueur {[m
     }[m
     public void coucher() {[m
         m_etat = Etat.COUCHE;[m
[31m-        m_interface_poker.joueurPasDeCarte(m_numero_joueur_interface);[m
[32m+[m[32m        Poker.interface_graphique.joueurPasDeCarte(m_numero_joueur_interface);[m
         System.out.println(m_nom_joueur + " se couche.");[m
     }[m
     public int demander_mise(int mise_demandee,ArrayList<Carte> jeu_pt,int pot,int relance_min) {[m
[31m-        m_interface_poker.raffraichit();[m
[32m+[m[32m        Poker.interface_graphique.raffraichit();[m
         return m_intelligence.demander_mise(mise_demandee,jeu_pt,pot,relance_min,m_main,m_cave,m_mise);[m
     }[m
     public void ajouter_mise(int complement,int[] pot_pt) {[m
[36m@@ -116,8 +119,8 @@[m [mpublic class Joueur {[m
         pot_pt[0] += complement;[m
         m_mise += complement;[m
         System.out.println("Sa mise est maintenant de " + m_mise + " et le pot vaut " + pot_pt[0]);[m
[31m-        m_interface_poker.joueurSetJetons(m_numero_joueur_interface,Poker.jetons(m_cave));[m
[31m-        m_interface_poker.fixeMises(Poker.jetons(pot_pt[0]));[m
[32m+[m[32m        Poker.interface_graphique.joueurSetJetons(m_numero_joueur_interface,Poker.jetons(m_cave));[m
[32m+[m[32m        Poker.interface_graphique.fixeMises(Poker.jetons(pot_pt[0]));[m
     }[m
     public void retirer_mise(int montant,int[] pot_pt,int[] retrait_pt) {[m
         if (montant < 0) throw new ComplementMiseNegatifException();[m
[36m@@ -125,11 +128,11 @@[m [mpublic class Joueur {[m
         pot_pt[0] -= montant;[m
         retrait_pt[0] += montant;[m
         m_mise -= montant;[m
[31m-        m_interface_poker.fixeMises(Poker.jetons(pot_pt[0]));[m
[32m+[m[32m        Poker.interface_graphique.fixeMises(Poker.jetons(pot_pt[0]));[m
     }[m
     public void encaisser(int montant) {[m
         m_cave += montant;[m
[31m-        m_interface_poker.joueurSetJetons(m_numero_joueur_interface,Poker.jetons(m_cave));[m
[32m+[m[32m        Poker.interface_graphique.joueurSetJetons(m_numero_joueur_interface,Poker.jetons(m_cave));[m
     }[m
     @Override[m
     public String toString() {[m
[1mdiff --git a/src/Jeu/JoueurTest.java b/src/Jeu/JoueurTest.java[m
[1mindex 3437328..727d1fe 100644[m
[1m--- a/src/Jeu/JoueurTest.java[m
[1m+++ b/src/Jeu/JoueurTest.java[m
[36m@@ -2,9 +2,7 @@[m [mpackage Jeu;[m
 [m
 import Cartes.Carte;[m
 import Cartes.PaquetDeCartes;[m
[31m-import interfaceGraphique.InterfacePoker;[m
 import org.junit.jupiter.api.Test;[m
[31m-[m
 import java.util.ArrayList;[m
 import java.util.List;[m
 import java.util.stream.Collectors;[m
[36m@@ -14,12 +12,11 @@[m [mimport static org.junit.jupiter.api.Assertions.*;[m
 class JoueurTest {[m
     @Test[m
     void joueur_stream_tri() {[m
[31m-        InterfacePoker interface_poker = new InterfacePoker(2,2,2,true);[m
[31m-        Joueur arnaud = new Joueur("Arnaud",1000,null,new IntelligenceHumaine("Arnaud"),interface_poker,0);[m
[31m-        Joueur bebert = new Joueur("Bébert",1000,arnaud,new IntelligenceHumaine("Bébert"),interface_poker,1);[m
[31m-        Joueur alice = new Joueur("Alice",1000,bebert,new IntelligenceHumaine("Alice"),interface_poker,2);[m
[31m-        Joueur emma = new Joueur("Emma",1000,alice,new IntelligenceHumaine("Emma"),interface_poker,3);[m
[31m-        Joueur pauline = new Joueur("Pauline",1000,emma,new IntelligenceHumaine("Pauline"),interface_poker,4);[m
[32m+[m[32m        Joueur arnaud = new Joueur("Arnaud",1000,null,new IntelligenceHumaine("Arnaud"),0);[m
[32m+[m[32m        Joueur bebert = new Joueur("Bébert",1000,arnaud,new IntelligenceHumaine("Bébert"),1);[m
[32m+[m[32m        Joueur alice = new Joueur("Alice",1000,bebert,new IntelligenceHumaine("Alice"),2);[m
[32m+[m[32m        Joueur emma = new Joueur("Emma",1000,alice,new IntelligenceHumaine("Emma"),3);[m
[32m+[m[32m        Joueur pauline = new Joueur("Pauline",1000,emma,new IntelligenceHumaine("Pauline"),4);[m
         Joueur.donneur = arnaud;[m
         arnaud.set_joueur_suivant(pauline);[m
         PaquetDeCartes paquet;[m
[1mdiff --git a/src/Jeu/Poker.java b/src/Jeu/Poker.java[m
[1mindex f54c2d4..c81a34d 100644[m
[1m--- a/src/Jeu/Poker.java[m
[1m+++ b/src/Jeu/Poker.java[m
[36m@@ -9,6 +9,7 @@[m [mclass NombreJoueursException extends IllegalArgumentException {[m
 }[m
 [m
 public class Poker {[m
[32m+[m[32m    static InterfacePoker interface_graphique;[m
     public static int[] jetons(int montant) {[m
         int[] res = new int[5];[m
         int reste = montant;[m
[36m@@ -22,20 +23,19 @@[m [mpublic class Poker {[m
     public static void poker(String[] noms_joueurs,int cave_initiale,int petite_blinde) {[m
         int m_petite_blinde;[m
         int m_nbtour = 0;[m
[31m-        InterfacePoker m_interface_poker;[m
         if (noms_joueurs.length < 2 || noms_joueurs.length > 8) throw new NombreJoueursException();[m
[31m-        else if (noms_joueurs.length == 2) m_interface_poker = new InterfacePoker(1,1,5,true);[m
[31m-        else m_interface_poker = new InterfacePoker(2,noms_joueurs.length/2,5,true);[m
[31m-        m_interface_poker.afficheFenetre();[m
[32m+[m[32m        else if (noms_joueurs.length == 2) interface_graphique = new InterfacePoker(1,1,5,true);[m
[32m+[m[32m        else interface_graphique = new InterfacePoker(2,noms_joueurs.length/2,5,true);[m
[32m+[m[32m        interface_graphique.afficheFenetre();[m
         Joueur.donneur = new Joueur(noms_joueurs[0],cave_initiale,null,[m
[31m-                new IntelligenceHumaine(noms_joueurs[0]),m_interface_poker,0);[m
[32m+[m[32m                new IntelligenceHumaine(noms_joueurs[0]),0);[m
         Joueur joueur_temp = Joueur.donneur;[m
         for (int i = 1 ; i < noms_joueurs.length ; i++) joueur_temp = new Joueur(noms_joueurs[i], cave_initiale,joueur_temp,[m
[31m-                new IntelligenceArtificielle(cave_initiale,noms_joueurs[i]),m_interface_poker,i);[m
[32m+[m[32m                new IntelligenceArtificielle(cave_initiale,noms_joueurs[i]),i);[m
         Joueur.donneur.set_joueur_suivant(joueur_temp);[m
         m_petite_blinde = petite_blinde;[m
         do {[m
[31m-            new TourPoker(m_petite_blinde,m_interface_poker);[m
[32m+[m[32m            new TourPoker(m_petite_blinde);[m
             m_nbtour++;[m
             if (m_nbtour % 20 == 0) {[m
                 m_petite_blinde *= 2;[m
[36m@@ -43,7 +43,7 @@[m [mpublic class Poker {[m
             }[m
         } while (Joueur.inc_donneur());[m
         System.out.println(Joueur.donneur + " a gagné la partie");[m
[31m-        m_interface_poker.ferme();[m
[32m+[m[32m        interface_graphique.ferme();[m
     }[m
 [m
     public static void main(String[] args) {[m
[1mdiff --git a/src/Jeu/TourPoker.java b/src/Jeu/TourPoker.java[m
[1mindex 4413317..61473c9 100644[m
[1m--- a/src/Jeu/TourPoker.java[m
[1m+++ b/src/Jeu/TourPoker.java[m
[36m@@ -2,7 +2,6 @@[m [mpackage Jeu;[m
 [m
 import Cartes.Carte;[m
 import Cartes.PaquetDeCartes;[m
[31m-import interfaceGraphique.InterfacePoker;[m
 import java.util.ArrayList;[m
 import java.util.stream.Collectors;[m
 [m
[36m@@ -12,10 +11,8 @@[m [mpublic class TourPoker {[m
     private int m_mise_actuelle;[m
     private PaquetDeCartes m_paquet;[m
     private ArrayList<Carte> m_jeu_pt;[m
[31m-    private final InterfacePoker m_interface_poker;[m
 [m
[31m-    TourPoker(int petite_blinde,InterfacePoker interface_poker) {[m
[31m-        m_interface_poker = interface_poker;[m
[32m+[m[32m    TourPoker(int petite_blinde) {[m
         init(petite_blinde);[m
         deroulement_du_tour();[m
     }[m
[36m@@ -38,8 +35,8 @@[m [mpublic class TourPoker {[m
         joueur_temp = joueur_temp.get_joueur_suivant();[m
         joueur_temp.ajouter_mise(grosse_blinde, m_pot_pt);[m
         System.out.println(joueur_temp + " pose la grosse blinde (" + grosse_blinde + " euros) !!");[m
[31m-        m_interface_poker.fixeFlop(new Carte[0]);[m
[31m-        m_interface_poker.raffraichit();[m
[32m+[m[32m        Poker.interface_graphique.fixeFlop(new Carte[0]);[m
[32m+[m[32m        Poker.interface_graphique.raffraichit();[m
     }[m
 [m
     private void deroulement_du_tour() {[m
[36m@@ -72,7 +69,7 @@[m [mpublic class TourPoker {[m
         ajouter_une_carte_en_jeu();[m
         ajouter_une_carte_en_jeu();[m
         ajouter_une_carte_en_jeu();[m
[31m-        m_interface_poker.fixeFlop(new Carte[] {m_jeu_pt.get(0),m_jeu_pt.get(1),m_jeu_pt.get(2)});[m
[32m+[m[32m        Poker.interface_graphique.fixeFlop(new Carte[] {m_jeu_pt.get(0),m_jeu_pt.get(1),m_jeu_pt.get(2)});[m
         tour_encheres(Joueur.donneur.get_joueur_suivant());[m
     }[m
 [m
[36m@@ -81,7 +78,7 @@[m [mpublic class TourPoker {[m
         System.out.println("****** Turn ******");[m
         System.out.println("******************");[m
         ajouter_une_carte_en_jeu();[m
[31m-        m_interface_poker.ajouteCarteFlop(m_jeu_pt.get(3));[m
[32m+[m[32m        Poker.interface_graphique.ajouteCarteFlop(m_jeu_pt.get(3));[m
         tour_encheres(Joueur.donneur.get_joueur_suivant());[m
     }[m
 [m
[36m@@ -90,7 +87,7 @@[m [mpublic class TourPoker {[m
         System.out.println("**** Rivière *****");[m
         System.out.println("******************");[m
         ajouter_une_carte_en_jeu();[m
[31m-        m_interface_poker.ajouteCarteFlop(m_jeu_pt.get(4));[m
[32m+[m[32m        Poker.interface_graphique.ajouteCarteFlop(m_jeu_pt.get(4));[m
         tour_encheres(Joueur.donneur.get_joueur_suivant());[m
     }[m
 [m
[36m@@ -168,6 +165,8 @@[m [mpublic class TourPoker {[m
             suivant = joueur.get_joueur_suivant();[m
             if (suivant.get_cave() == 0) {[m
                 System.out.println(suivant + " quitte le jeu !");[m
[32m+[m[32m                Poker.interface_graphique.joueurPasDeCarte(suivant.get_numero_joueur_interface());[m
[32m+[m[32m                Poker.interface_graphique.joueurSetAnnotation(suivant.get_numero_joueur_interface(),"(hors-jeu)");[m
                 joueur.set_joueur_suivant(suivant.get_joueur_suivant());[m
                 if (suivant == Joueur.donneur) Joueur.donneur = suivant.get_joueur_suivant();[m
             }[m
