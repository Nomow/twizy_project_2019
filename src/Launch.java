import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Launch implements Runnable{
	static final short ORBMODE = 1;
	static final short NEURALMODE = 2;
	static final short PROBABILISTICMODE = 3;
	static final short VIDEOMODE = 1;
	static final short CAMMODE = 2;
	String videoName;
	PanelVideo panel;
	Afficheur panAff;
	private short mode;
	private short vidMode;
	private Matcher matcher;
	public Launch(String videoName,PanelVideo panel,Afficheur panAff,short mode,short vidMode) {
		// TODO Auto-generated constructor stub
		this.videoName=videoName;
		this.panel=panel;
		this.panAff=panAff;
		this.mode = mode;
		this.vidMode=vidMode;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ArrayList<String> refs = new ArrayList<String>();

		ArrayList<Mat> iBDDs = new ArrayList<Mat>();
		ArrayList<Mat> listOfDescriptors = new ArrayList<Mat>();
		ArrayList<String[]> tabIndice = new ArrayList<String[]>();
		
		if (mode==ORBMODE) {
		

		refs.add("ref110.jpg");refs.add("ref30.jpg");refs.add("ref50.jpg");refs.add("ref70.jpg");refs.add("ref90.jpg");
		refs.add("refdouble.jpg");
		ORB detector = ORB.create(50);
		for (String s : refs) {
			Mat i = Imgcodecs.imread(s);
			iBDDs.add(i);
			MatOfKeyPoint keypoints = new MatOfKeyPoint();
			Mat descriptors = new Mat();

			detector.detectAndCompute(i, new Mat(), keypoints, descriptors);
			listOfDescriptors.add(descriptors);
		}
		
		matcher = new OrbMatching(iBDDs,listOfDescriptors,refs,false);
		}
		if (mode==NEURALMODE) {
			try {
				matcher = new NeuralMatching("C:\\Users\\alexa\\Desktop\\LEBONGIT\\twizy_project_2019\\Pytorch Folder\\signnames.csv");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			VideoCapture cap = new VideoCapture(0); //the webcam by default
			
			if (vidMode == VIDEOMODE)
				 cap= new VideoCapture(videoName);
			int frameRate =(int) cap.get(5);
			System.out.println("frameRate "+frameRate);
			Mat img = new Mat();
			boolean readen = cap.read(img); //is the video readen ?
			System.out.println("readen "+readen);
			//capture pictures from video
			int i=0;
			Boolean fini = false;
			while(!fini){
				if(cap.read(img)){

					try {
						Thread.sleep(1000/frameRate);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					ArrayList<Rect> rectangles = matcher.getWherePanels();
					for (Rect r:rectangles)
						Imgproc.rectangle(img, new Point(r.x,r.y),new Point(r.x+r.width,r.y+r.height), new Scalar(0,255,0));
					Image I= HighGui.toBufferedImage(img);
					

					
			
					
					panel.refresh(I);
				
					
					
					
					i++;
					MatcherThread mT = new MatcherThread(img,matcher,panAff);
					if (mode==ORBMODE) {
					if (i==frameRate/10) {
						i=0;
						
						Thread th = new Thread(mT);
						th.start();
						
						
					}}
					else if (mode==NEURALMODE) {
						if (i==frameRate/2) {
							i=0;
							Thread thn = new Thread(mT);
							thn.start();
						}
					}
				}else{
					System.out.println("end of video");
					fini = true;
				}


			}}	
		
		
		
	
		catch( java.lang.Exception ie) {
			
		}
		
	}

}
