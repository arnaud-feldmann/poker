package interfaceGraphique;

import java.awt.Graphics;

import javax.swing.JPanel;

class IPJoueur{
	IPJetons jetons;
	String nom ="";
	String annotation = "";
	int posX, posY;
	int[] cartes;
	int nbCartes;
	IPJoueur(int dx, int dy){
		posX = dx;
		posY = dy;
		if (IPJetons.enChiffres())
			jetons = new IPJetons(posX+10,posY+145);
		else
			jetons = new IPJetons(posX+200,posY+25);
	}
//		cache = true;
//		enjeu = true;
//		nbCartes = IPCartes.nbCartes;
//	}
	void affiche(JPanel jf, Graphics g){
		IPCartes.afficheCompact(jf,g,cartes,posX,posY);
		if (IPJetons.enChiffres()){
			g.drawString(nom + " " + annotation + " " + jetons.donneChiffre(),
					posX+20,posY+155);

		}else{
			g.drawString(nom + " " + annotation,posX+200,posY+15);
			jetons.affiche(jf,g);
		}
	}
	void fixeNbJetons(int[] nbj){
		jetons.fixe(nbj);
	}
	void fixeAnnotation(String a){
		annotation = a;
	}
	public void setString(String str) {
		nom = str;	
	}
	public void setCartes(int[] tab) {
		cartes = tab;
	}
	public void setCartesCachees() {
		cartes = new int[]{1000};
	}
	public void pasDeCartes() {
		cartes = new int[] {};
	}
}

