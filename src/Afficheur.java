import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Afficheur extends JPanel {

	ArrayList<String> sL;
	public Afficheur() {
		// TODO Auto-generated constructor stub
		sL=new ArrayList<String>();
	}

	public void send(ArrayList<String> sL) {
		this.sL=sL;
		if (sL.size()!=0)
				this.repaint();



	}
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.err.println("LUL");
		int j=0;
		int Long=this.getWidth()/4;
		for (String x : sL) { 
			System.err.println(x);
			Image I=new ImageIcon(x).getImage();
			j++;
			g.drawImage(I, 0, j*Long, this.getWidth()/4, this.getHeight()/4,null);
		}





	}
}
