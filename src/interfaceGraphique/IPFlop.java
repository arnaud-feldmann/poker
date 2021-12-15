package interfaceGraphique;
import java.awt.*;
import javax.swing.*;
class IPFlop{
	protected int[] cartes = new int[0];
	private int posX, posY;
	IPFlop(int x, int y){
		posX = x;
		posY = y;
	}
	protected void fixeJeu(int[] j){
		cartes = j;
	}
	protected void addCarte(int ni){
		int[] newc = new int[cartes.length+1];
		for (int i=0; i<cartes.length; i++)
			newc[i] = cartes[i];
		newc[cartes.length] = ni;
		cartes = newc;
	}
	protected void affiche(JPanel jp, Graphics g){
		IPCartes.affiche(jp,g,cartes,posX,posY);
	}
}

