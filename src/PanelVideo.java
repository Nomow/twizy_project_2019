import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelVideo extends JPanel{
	
private Image I;
	public PanelVideo(Image I) {
		// TODO Auto-generated constructor stub
		this.I=I;
	}

	public void refresh(Image I) {
		// TODO Auto-generated method stub

		this.I=I;
		this.repaint();
	}
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(I, 0, 0, this.getWidth(), this.getHeight(),null);
	}

}
