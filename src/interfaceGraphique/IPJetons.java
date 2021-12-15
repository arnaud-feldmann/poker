package interfaceGraphique;
import java.awt.*;
import javax.swing.*;



class IPJetons{
	protected static int[] couleurs = {0,1,2,3,4};
	protected static int nbCol, larg;
	protected static ImageIcon[][] piles = new ImageIcon[5][25];
	public static boolean enChiffres;
	private int[] lesjetons;
	private int posX, posY;
	IPJetons(int x, int y){
		posX = x;
		posY = y;
		if (nbCol == 0)
			lesjetons = new int[1];
		else
			lesjetons = new int[nbCol];
	}
	IPJetons(int x, int y, int[] nj){
		posX = x;
		posY = y;
		fixe(nj); 
	}
	protected static int largeur(){
		return larg;
	}
	protected static boolean enChiffres(){
		return nbCol == 0;
	}
	protected static void init(int ni){
		nbCol = ni;
		larg = 70*ni;
		char[] col = {'r','j','b','w','g'};
		for (int i=0; i<5; i++){
			for (int j=0;j<25; j++){
				piles[i][j]=new ImageIcon("icons/pile_" + col[i] + "_" +
						(j+1) + ".gif");
			}
		}	
	}
	protected static void afficheUneCouleur(JPanel jf,Graphics g,int dx, 
			int dy, int j, int idx){
		if (j>150){
			j = 150;
		}else if (j == 0){
			return;
		}
		if (j<=25){
			piles[idx][j-1].paintIcon(jf,g,dx,dy);
			return;
		}else{
			piles[idx][24].paintIcon(jf,g,dx,dy);
			if (j<=50){
				piles[idx][j-26].paintIcon(jf,g,dx+33,dy);
				return;
			}else{
				piles[idx][24].paintIcon(jf,g,dx+33,dy);
				afficheUneCouleur(jf,g,dx+4,dy+8,j-50,idx);
			}
		}
	}
	protected void affiche(JPanel jf,Graphics g){
		if (enChiffres())
			g.drawString(""+lesjetons[0],posX,posY);
		else{
			for (int i=nbCol-1; i>=0; i--){
				afficheUneCouleur(jf,g,posX+70*i,posY,lesjetons[i],
						couleurs[i]);
			}
		}
	}
	protected void fixe(int[] js){
		lesjetons = new int[js.length];
		for (int i=0; i<js.length; i++){
			lesjetons[i] = js[i];
		}
	}
	protected void modifie(int[] js){
		for (int i=0; i<IPJetons.nbCol; i++){
			lesjetons[i] = lesjetons[i] + js[i];
		}
		if (nbCol == 0)
			lesjetons[0] = lesjetons[0] + js[0];
	}
	protected int donneChiffre(){
		return lesjetons[0];
	}
}

