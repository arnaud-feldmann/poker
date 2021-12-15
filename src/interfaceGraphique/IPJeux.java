package interfaceGraphique;
import java.awt.*;
import javax.swing.*;

class IPJeux{
	static ImageIcon[] jeu = new ImageIcon[6];
	static void init(){
		for (int i=0; i<=5; i++){
			jeu[i] = new ImageIcon("icons/fjeu" + i + ".gif");
		}
	}
}

