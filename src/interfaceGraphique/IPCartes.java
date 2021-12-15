package interfaceGraphique;
import java.awt.*;
import javax.swing.*;

class IPCartes{
	protected static ImageIcon[][] cartes = new ImageIcon[4][13];
	protected static ImageIcon aPlat = new ImageIcon("cards/back-90.gif");
	protected static ImageIcon cCachees = new ImageIcon("icons/fjeu2.gif");
	protected static ImageIcon deDos = new ImageIcon("cards/back.gif");
	protected static int nbCartes;
	protected static void init(){
		String suits = "cdhs";
		String faces = "a23456789tjqk";
		int cardPosition = 0;
		cartes = new ImageIcon[4][13];
		for (int suit=0 ; suit<suits.length() ; suit++) {
			for (int face=0 ; face<13 ; face++) {
				cartes[suit][face] =  new ImageIcon("cards/"
						+ faces.charAt(face)
						+ suits.charAt(suit) 
						+ ".gif");
			}
		}
	}
	protected static ImageIcon donneCarte(int n){
		switch(n) {
		case 1000:
			return cCachees;
		case 2000:
			return deDos;
		case 3000:
			return aPlat;
		default:
			return cartes[n/100][n%100-1];
		}
	}
	protected static void affiche(JPanel jp, Graphics g,int[] lcs,int dx, 
			int dy){
		for (int i=0; i<lcs.length; i++){
			donneCarte(lcs[i]).paintIcon(jp,g,dx+(i*76),dy);
		} 
	}
	protected static void afficheCompact(JPanel jp, Graphics g,int[] lcs,
			int dx, int dy){
		if (lcs != null) {
			if (nbCartes == 2){
				affiche(jp,g,lcs,dx,dy);
			}else{
				for (int i=0; i<lcs.length; i++){
					donneCarte(lcs[i]).paintIcon(jp,g,dx+(i*30),dy+(i*5));
				} 
			}
		}
	}
}
