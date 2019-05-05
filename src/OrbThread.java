import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class OrbThread implements Runnable{
	private Mat image;
	private ArrayList<String> refs;
	private ArrayList<Mat> iBDDs;
	private ArrayList<Mat> listOfDescriptors;
	private JFrame frame;
	private JPanel panel;
	private Afficheur afficheur;
	

	public OrbThread(Mat image, ArrayList<String> refs, ArrayList<Mat> iBDDs, ArrayList<Mat> listOfDescriptors,Afficheur afficheur) {
		this.image=image.clone(); // l'image va etre modif
		this.refs=refs;
		this.iBDDs=iBDDs;
		this.listOfDescriptors=listOfDescriptors;
		this.afficheur=afficheur;

		


	}
	public void run() {

		ArrayList<String> sL = Biblio.templateMatching(image,refs,iBDDs,listOfDescriptors,false);
		afficheur.send(sL);
	
		//for (String s : sL) System.out.println(s);
			
		
		
		
			
			

	}
	
	

}
