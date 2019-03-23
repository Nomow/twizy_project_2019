import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {  //in this class , we test the detection of panels in an image with ORB

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String testedImage = "p3.jpg";
		Mat m = Imgcodecs.imread(testedImage);
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
		ArrayList<String> sL = Biblio.templateMatching(m,refs,iBDDs,listOfDescriptors,false);
		for (String s : sL) {
			System.out.println(s);
			HighGui.imshow("we find in particular" , Imgcodecs.imread(s));
			HighGui.waitKey(0);
			
		}
		System.exit(0);
		
	}

}
