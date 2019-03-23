import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {  //in this class , we test the detection of panels in an image with ORB

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String testedImage = "00002_00023.jpg";
		Mat m = Imgcodecs.imread(testedImage);
		
		// Read all files in folders and subfolders of given paths and return an ArrayList of the files
		Read_Panels RP = new Read_Panels("Database");
		for (String s : RP.getListPanel()) {
			System.out.println(s);
		}
		// Useful when we want to feed our Neural Network
		

		
		ArrayList<Mat> iBDDs = new ArrayList<Mat>();
		ArrayList<Mat> listOfDescriptors = new ArrayList<Mat>();
		ArrayList<String> refs = new ArrayList<String>();
		
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
		ArrayList<String> sL = Biblio.templateMatching(m,refs,iBDDs,listOfDescriptors,true);
		for (String s : sL) {
			System.out.println(s);
			HighGui.imshow("we find in particular" , Imgcodecs.imread(s));
			HighGui.waitKey(0);
			
		}
		System.exit(0);
		
	}

}
