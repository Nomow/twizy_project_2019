import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;


public class Application extends JFrame {
	
	public static short iTempFiles=0;
	private JPanel contentPane;
	private static PanelVideo panel;
	private static Afficheur panAff;
	static ArrayList<String> lPanneau = new ArrayList<String>();
	static JButton Bouton = new JButton("...");
	static JTextPane textPane = new JTextPane();
	static String videoName;
	static boolean debut =false;
	static JButton  btnStart = new JButton("START");
;
	
	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() {
			boolean debut=false;
			public void run() {
				try {
					Application frame = new Application();
					frame.setVisible(true);
					Bouton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

							int returnValue = jfc.showOpenDialog(null);
							// int returnValue = jfc.showSaveDialog(null);

							if (returnValue == JFileChooser.APPROVE_OPTION) {
								File selectedFile = jfc.getSelectedFile();
								videoName=selectedFile.getName();
								textPane.setText(videoName);
								
								
								

						}}
						
					});
					btnStart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

								Launch L=new Launch(videoName,panel,panAff,Launch.NEURALMODE);
								Thread T=new Thread(L);
								T.start();
								
						}
						
					});
					
	
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	

	}

	/**
	 * Create the frame.
	 */
	public Application() {
		Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(0,0,screen.width, screen.height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new PanelVideo(null);
		panel.setBounds(10, 157, 1420, 800);
		panel.setBackground(Color.black);
		contentPane.add(panel);
	
		panAff = new Afficheur();
		panAff.setBounds(1439, 230, 400, 400);
		panAff.setBackground(Color.BLUE);
		contentPane.add(panAff);
		
		
		Bouton.setBounds(18, 11, 107, 49);
		contentPane.add(Bouton);
		
		
		textPane.setBounds(135, 24, 600, 26);
		contentPane.add(textPane);
		
		btnStart.setBounds(18, 91, 99, 43);
		contentPane.add(btnStart);
	
	}
	 public static BufferedImage scale(BufferedImage bImage, double factor) {
	        int destWidth=(int) (bImage.getWidth() * factor);
	        int destHeight=(int) (bImage.getHeight() * factor);
	//créer l'image de destination
	        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
	        Graphics2D graphics = bImageNew.createGraphics();
	        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	        //dessiner l'image de destination
	        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
	        graphics.dispose();
	 
	        return bImageNew;
	    }
}
