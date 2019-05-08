import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class TestImage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread("p10.jpg");
		Mat TImg = Biblio.thresholding(img);
		//Imgcodecs.imwrite("thresholded.jpg",TImg);
		Matcher match = new OrbMatching(null, null, null, false);
		ArrayList<Mat> aM = match.getCircles(img, false);
		
	}

}
