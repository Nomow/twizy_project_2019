import java.awt.Color;
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
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//System.err.println("LUL");
		int j=0;
		int Long=this.getWidth()/4;
		for (String x : sL) { 
			System.err.println(x);
			Image I=new ImageIcon(x).getImage();
			
			g.drawImage(I, 0, j*Long, this.getWidth()/4, this.getHeight()/4,null);
			j++;
		}





	}
}
