import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class NeuronalThread implements Runnable{
	private Mat image;
	
	private JFrame frame;
	private JPanel panel;
	private Afficheur afficheur;
	ArrayList<String[]> tabIndice;

	public NeuronalThread(Mat image,ArrayList<String[]> tabIndice , Afficheur afficheur) {
		this.image=image.clone(); // l'image va etre modif
		this.afficheur=afficheur;
		this.tabIndice = tabIndice;

	}
	public void run() {

		ArrayList<String> sL = new ArrayList<String>();
		try {
			sL = Biblio.neuronesImage(image,tabIndice);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		afficheur.send(sL);
	
		//for (String s : sL) System.out.println(s);
			
		
		
		
			
			

	}
	
	

}
