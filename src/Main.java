import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String testedImage = "p1.jpg";
		Mat m = Imgcodecs.imread(testedImage);
		ArrayList<String> refs = new ArrayList<String>();
		try {
			Read_Panels RP = new Read_Panels("Database");
			refs = RP.getListPanel();
			System.out.println(refs.get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//refs.add("ref110.jpg");refs.add("ref30.jpg");refs.add("ref50.jpg");refs.add("ref70.jpg");refs.add("ref90.jpg");
		//refs.add("refdouble.jpg");
		ArrayList<String> sL = Biblio.templateMatching(m,refs,false);
		for (String s : sL) {
			System.out.println(s);
			HighGui.imshow("we find in particular" , Imgcodecs.imread(s));
			HighGui.waitKey(0);
			
		}
		System.exit(0);
		
	}

}
