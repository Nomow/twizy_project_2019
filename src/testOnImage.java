import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class testOnImage {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m = Imgcodecs.imread("p3.jpg");
		Matcher neumach = new NeuralMatching("C:\\Users\\alexa\\Desktop\\LEBONGIT\\twizy_project_2019\\Pytorch Folder\\signnames.csv");
		ArrayList<String> result = neumach.matching(m);
		for (String r:result)
			System.out.println(r);
		
		Mat m2 = Imgcodecs.imread("p7.jpg");
		ArrayList<Mat> aL = new ArrayList<Mat>();
		aL.addAll(neumach.getTriangles(m2, true));
		for (Mat mat:aL)
		{
			HighGui.imshow("coupe", mat);
			HighGui.waitKey(0);
		}
		

	}

}
