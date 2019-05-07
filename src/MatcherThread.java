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

public class MatcherThread implements Runnable{
	private Mat image;
	private Matcher matcher;
	private Afficheur afficheur;
	

	public MatcherThread(Mat image, Matcher matcher,Afficheur afficheur) {
		this.image=image.clone();
		this.matcher = matcher;
		this.afficheur=afficheur;

		
	}
	public void run() {
		
		ArrayList<String> sL = new ArrayList<String>();
		try {
			sL = matcher.matching(image);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		afficheur.send(sL);
			
	}
	
	

}
