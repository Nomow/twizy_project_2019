import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class TestImage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String testedImage = "p10.jpg";
		Mat p10 = Imgcodecs.imread(testedImage);
		Mat p10Thresh = Biblio.thresholding(p10);
		HighGui.imshow("Thresh", p10Thresh);
		HighGui.waitKey();
		ArrayList<Mat> aLM = Biblio.getCircles(p10,true);
		for (Mat m:aLM) {
			HighGui.imshow("coupe", m);
			HighGui.waitKey();
		}
	}

}
