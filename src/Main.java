import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String imageATest = "p3.jpg";
		Mat m = Imgcodecs.imread(imageATest);
		ArrayList<String> refs = new ArrayList<String>();
		refs.add("ref110.jpg");refs.add("ref30.jpg");refs.add("ref50.jpg");refs.add("ref70.jpg");refs.add("ref90.jpg");
		refs.add("refdouble.jpg");
		ArrayList<String> sL = Biblio.templateMatching(m,refs,false);
		for (String s : sL) {
			System.out.println(s);
			HighGui.imshow("on trouve nottament", Imgcodecs.imread(s));
			HighGui.waitKey(0);
			
		}
		System.exit(0);
		
	}

}
