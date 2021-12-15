package testeIGPoker;
import java.util.Scanner;
import interfaceGraphique.InterfacePoker;
import interfaceGraphique.IGPokerable;
/**
 * @author Francois Barthelemy
 *
 * Classe permettant d'afficher les éléments d'une partie
 * de poker. Les joueurs sont disposés en deux endroits: le
 * joueur numéro 0 est affiché en bas de la fenêtre; les autres
 * joueurs sont affichés dans une grille en haut de l'écran. 
 * 
 * Les jetons peuvent être affichés sous forme de piles de jetons
 * de plusieurs couleurs ou sous forme d'un nombre unique.
 * 
 * Les annotations permettent de préciser des éléments qui varient
 * en cours de partie (par exemple le bouton, la petite et la grosse
 * blend).
 * 
 * Chaque joueur a un nom, des cartes (ou pas), des jetons et une annotation.
 * En plus des joueurs, l'interface contient un espace pour les cartes 
 * communes des joueurs (flop, turn, river) et les jetons misés.
 * 
 */
public class TestIGPoker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfacePoker ti;
		Scanner scan = new Scanner(System.in);
		int[] jets = {23,41,100};
		int[] mss = {30,10,9};
		IGPokerable[] lj = {new PseudoCarte(110),new PseudoCarte(5)};
		IGPokerable[] fl = {new PseudoCarte(05), new PseudoCarte(105),
				new PseudoCarte(211)};
		IGPokerable[] jtb = {new PseudoCarte(201),
				new PseudoCarte(201)};
		ti = new InterfacePoker(2,2,3,true);
		ti.joueurSetNom(0,"Bibi");
		ti.joueurSetNom(1,"Robert");
		ti.joueurSetNom(2,"Alain");
		ti.joueurSetNom(3,"Gerard");
		ti.joueurSetJetons(0,jets);
		ti.joueurSetJetons(1,jets);
		ti.joueurSetJetons(2,jets);
		ti.joueurSetJetons(3,jets);
		ti.joueurSetCartesCachees(1);
		ti.joueurSetCartesCachees(2);
		ti.joueurSetCartesCachees(3);
		ti.joueurSetCartesVisibles(0,lj[0],lj[1]);
		ti.fixeMises(mss);
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.afficheFenetre();
		System.out.println("Une annotation va apparaitre");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.joueurSetAnnotation(1,"Big Blend");
		ti.raffraichit();
		System.out.println("Alain va se coucher");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.joueurPasDeCarte(2);
		ti.raffraichit();
		System.out.println("Voici un flop");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.fixeFlop(fl);
		ti.raffraichit();
		System.out.println("Mises à deux piles de jetons");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.fixeMises(new int[] {8,15,0});
		ti.raffraichit();
		System.out.println("Ajout du turn");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.ajouteCarteFlop(new PseudoCarte(305));
		ti.raffraichit();
		System.out.println("Retrait de 12 jetons à Robert");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.joueurSetJetons(1,new int[] {11,41,100});
		ti.raffraichit();
		System.out.println("L'interface ne garantit pas la coherence");
		System.out.println("Appuyez sur 'entree' pour afficher la fenetre");
		scan.nextLine();
		ti.joueurSetCartesVisibles(0,jtb[0],jtb[0]);
		ti.joueurSetCartesVisibles(1,jtb[0],jtb[0]);
		ti.joueurSetCartesVisibles(2,jtb[0],jtb[0]);
		ti.joueurSetCartesVisibles(3,jtb[0],jtb[0]);
		ti.raffraichit();
		System.out.println("La fenêtre va se fermer");
		scan.nextLine();
		System.out.println("Bye");
		ti.ferme();
		scan.close();
	}

}
