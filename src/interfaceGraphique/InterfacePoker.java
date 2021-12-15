package interfaceGraphique;
import java.awt.*;
import javax.swing.*;

/**
 * @author Francois Barthelemy
 *
 */
public class InterfacePoker extends JFrame {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPJoueur[]  joueur; 
	private int nbj = 0;
	private IPMises mises;
	private IPFlop flop;
	private SpecialPanel jpane;
	private int xGrille, yGrille, largJ, hauteurJ, larg, haut;

	/**
	 * @param cols nombre de colonnes pour afficher les joueurs automatiques
	 * @param ligs nombre de lignes pour afficher les joueurs automatiques
	 * @param nbCol nombre de couleurs de jetons (maximum 5)
	 * @param jetons booléen précisant si les jetons sont représentés graphiquement.
	 * 
	 * Ce constructeur crée un objet Java représentant une fenêtre graphique
	 * initialement cachée.
	 */
	public InterfacePoker(int cols, int ligs, int nbCol, boolean jetons) {
		IPJetons.enChiffres = !jetons;
		this.setTitle("Table de Poker");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jpane = new SpecialPanel();
		this.setContentPane(jpane);
		// initialisation de variables
		xGrille = cols;
		yGrille = ligs;
		joueur = new IPJoueur[xGrille*yGrille+1];
		// calcul taille de la fenêtre
		int largeurFlop, largeurMise;
		largeurFlop=380;
		IPCartes.nbCartes = 2;
		if (nbCol != 0){
			largeurMise=(70*nbCol*2)+30;
			larg = 20+xGrille*(200+70*nbCol+20);
			haut = yGrille*(150)+270;
			hauteurJ=150;
			largJ = 200+70*nbCol;
			if (larg < 380+(70*nbCol)+40)
				larg = 380+(70*nbCol)+40;
		}else{
			largeurMise = 150;
			hauteurJ = 190;
			largJ =  200;
			larg = 20+xGrille*220;
			haut = yGrille*190+270;
			if (larg < 420)
				larg = 420;
		}
		if (larg < largeurFlop+largeurMise + 20)
			larg = largeurFlop+largeurMise + 20;
		jpane.setPreferredSize(new Dimension(larg, haut));
		jpane.setBackground(Color.green);
		// initialisation de classes
		IPJeux.init();
		IPJetons.init(nbCol);
		mises = new IPMises(largeurFlop+20,hauteurJ*yGrille+20);
		//joueur[0] = new IPHumain((larg-(IPCartes.nbCartes*76)-IPJetons.largeur())/2,
		//		yGrille*hauteurJ+140);
		joueur[0] = new IPJoueur((larg-(IPCartes.nbCartes*76)-IPJetons.largeur())/2,
				yGrille*hauteurJ+140);
		for (int i=1; i<joueur.length; i++) {
			joueur[i] = new IPJoueur(10+((i-1)%xGrille)*(largJ+20),
					10+((i-1)/xGrille)*hauteurJ);

		}
		flop = new IPFlop(10,yGrille*hauteurJ+20);
		IPCartes.init();
	}
	private class SpecialPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Font font;
		public void paintComponent(Graphics g) {
			super.paintComponent(g);   // Required
			if (font == null){
				font = new Font(g.getFont().getName(),
						g.getFont().getStyle(),
						g.getFont().getSize()*2);
			}
			g.setFont(font);
			for (int i=0; i<joueur.length; i++)
				joueur[i].affiche(this,g);
			flop.affiche(this,g);
			mises.affiche(this,g);
		}
	}
	/**
	 *  Affiche à l'écran la fenêtre si elle n'y est pas déjà. 
	 *  Ne fait rien sinon.
	 */
	public void afficheFenetre(){
		this.pack();
		this.setVisible(true);
	}
	/**
	 * @param n numéro de joueur
	 * @param str nom du joueur
	 * 
	 * Inscrit un nom pour le joueur numéro n dans la fenêtre graphique.
	 * Ce nom deviendra visible au prochain rafraichissement de la
	 * fenêtre.
	 * 
	 */
	public void joueurSetNom(int n, String str) {
		joueur[n].setString(str);
	}
	/**
	 * @param n numéro de joueur
	 * @param jetons est un tableau d'entiers contenant le nombre de jetons
	 * de chaque couleur possédé par le joueur. Si les jetons sont affichés
	 * sous forme de nombre, le tableau n'a qu'une case.
	 * 
	 */
	public void joueurSetJetons(int n, int[] jetons) {
		joueur[n].fixeNbJetons(jetons);
	}
	/**
	 * @param n numéro du joueur
	 * @param carte1 la première carte à afficher
	 * @param carte2 la deuxième carte à afficher
	 * Cette méthode affiche deux cartes faces visibles pour le
	 * joueur numéro n. 
	 * 
	 */
	public void joueurSetCartesVisibles(int n, IGPokerable carte1, IGPokerable carte2){
		int[] tab = {carte1.toIGPInt(), carte2.toIGPInt()};
		joueur[n].setCartes(tab);
	}
	/**
	 * @param n numéro du joueur.
	 * Affiche deux cartes vues de dos pour le joueur numéro n.
	 */
	public void joueurSetCartesCachees(int n) {
		joueur[n].setCartesCachees();
	}
	/**
	 * @param lms tableau d'entiers spécifiant le nombre de jetons de chaque couleur
	 * misés. Ce sont les jetons posés au centre de la table, que se partageront les
	 * vainqueurs du coup.
	 */
	public void fixeMises(int[] lms){
		mises.fixeJetons(lms);
	}
	/**
	 * @param nc un tableau de cartes (type IGPokerable). Précise
	 * plusieurs cartes à étaler au flop (en pratique, ce seront les 
	 * trois cartes d'un flop.
	 */
	public void fixeFlop(IGPokerable[] nc){
		int[] tab = new int[nc.length];
		for (int i=0; i<tab.length; i++) {
			tab[i]=nc[i].toIGPInt();
		}
		flop.fixeJeu(tab);
	}
	/**
	 * @param nc une nouvelle carte à ajouter au flop (turn ou river)
	 */
	public void ajouteCarteFlop(IGPokerable nc){
		flop.addCarte(nc.toIGPInt());
	}
	/**
	 * Cette méthode ferme la fenêtre graphique proprement.
	 */
	public void ferme(){
		this.dispose();
	}
	/**
	 * Méthode qui efface les annotations de tous les joueurs
	 * de la table. 
	 */
	public void effaceToutesAnnotations(){
		for (int i=0; i<joueur.length; i++){
			joueur[i].fixeAnnotation("");
		}
	}
	/**
	 * @param n numéro du joueur
	 * @param an texte de l'annotation
	 */
	public void joueurSetAnnotation(int n, String an) {
		joueur[n].fixeAnnotation(an);
	}
	/**
	 *  Cette méthode refait l'affichage complet de la fenêtre 
	 *  graphique. Les autres méthodes enregistrent les modifications
	 *  dans l'objet Java, sans changer immédiatement l'affichage. 
	 *  L'appel à la méthode doit être fait pour que les modifications
	 *  soient visibles.
	 */
	public void raffraichit() {
		repaint();
	}
	/**
	 * @param n numéro du joueur.
	 * Spécifie qu'un joueur n'a pas de carte. Cela arrive quand un joueur
	 * se couche.
	 */
	public void joueurPasDeCarte(int n) {
		joueur[n].pasDeCartes();		
	}

}