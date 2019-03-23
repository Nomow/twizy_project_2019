import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;


public class TestVideo {

	public static void main(String[] args) {  //in this class , we test the detection of panels in a video with ORB
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		ArrayList<String> refs = new ArrayList<String>();

		ArrayList<Mat> iBDDs = new ArrayList<Mat>();
		ArrayList<Mat> listOfDescriptors = new ArrayList<Mat>();

		refs.add("ref110.jpg");refs.add("ref30.jpg");refs.add("ref50.jpg");refs.add("ref70.jpg");refs.add("ref90.jpg");
		refs.add("refdouble.jpg");
		ORB detector = ORB.create(30);
		for (String s : refs) {
			Mat i = Imgcodecs.imread(s);
			iBDDs.add(i);
			MatOfKeyPoint keypoints = new MatOfKeyPoint();
			Mat descriptors = new Mat();

			detector.detectAndCompute(i, new Mat(), keypoints, descriptors);
			listOfDescriptors.add(descriptors);
		}
		

		VideoCapture cap = new VideoCapture("video1.mp4");

		int frameRate =(int) cap.get(5);
		System.out.println("frameRate "+frameRate);
		Mat img = new Mat();
		boolean readen = cap.read(img); //est ce que la vidéo est lue ?
		System.out.println("readen "+readen);
		//capture pictures from video
		int i=0;
		Boolean fini = false;
		while(!fini){
			if(cap.read(img)){
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HighGui.imshow("img", img);
				HighGui.waitKey((int)((1.0/frameRate)*1000));
				i++;
				if (i==frameRate) {
					i=0;
					OrbThread oT = new OrbThread(img,refs,iBDDs,listOfDescriptors);
					Thread th = new Thread(oT);
					th.start();
				}
			}else{
				System.out.println("erreur donc fin");
				fini = true;
			}
			
			
		}

	}
}
