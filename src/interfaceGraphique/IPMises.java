package interfaceGraphique;
import java.awt.*;
import javax.swing.*;
class IPMises{
	protected IPJetons jetons;
	protected IPJetons nouvelle;
	protected boolean empty, jet;
	protected int nbCartes,posX,posY;
	IPMises(int x, int y){
		empty = true;
		jet = false;
		posX = x;
		posY = y;
		if (IPJetons.enChiffres()){
			jetons = new IPJetons(posX,posY+40);
			nouvelle = new IPJetons(posX+100,posY+40);
		}else{
			jetons = new IPJetons(posX,posY);
			nouvelle = new IPJetons(posX+IPJetons.largeur()+10,posY);
		}
	}
	protected void fixeJetons(int[] j){
		jetons.fixe(j);
	}
	protected void jetteCartes(int n){
		empty = false;
		jet = true;
		nbCartes = n;
	}
	void modifieJetons(int[] lj){
		jetons.modifie(lj);
	}
	protected void nouvelleMise(int[] ls){
		empty = false;
		jet = false;
		nouvelle.fixe(ls);
	}
	protected void affiche(JPanel jf,Graphics g){
		jetons.affiche(jf,g);
		int dx;
		if (IPJetons.enChiffres()){
			dx = posX+70;
		}else{
			dx = posX + jetons.largeur() + 10;
		}
		if (! empty){
			if (jet){
				for (int i=0; i<nbCartes; i++){
					IPCartes.aPlat.paintIcon(jf,g,dx+(3*i),posY-(3*i));
				}
			}else{
				nouvelle.affiche(jf,g);
			}
		}
	}
}
