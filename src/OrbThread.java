import java.util.ArrayList;

import org.opencv.core.Mat;

public class OrbThread implements Runnable{
	private Mat image;
	private ArrayList<String> refs;
	private ArrayList<Mat> iBDDs;
	private ArrayList<Mat> listOfDescriptors;
	

	public OrbThread(Mat image, ArrayList<String> refs, ArrayList<Mat> iBDDs, ArrayList<Mat> listOfDescriptors) {
		this.image=image.clone(); // l'image va etre modif
		this.refs=refs;
		this.iBDDs=iBDDs;
		this.listOfDescriptors=listOfDescriptors;
	}
	public void run() {

		ArrayList<String> sL = Biblio.templateMatching(image,refs,iBDDs,listOfDescriptors,false);
		for (String s : sL)
			System.out.println(s);

	}
}
