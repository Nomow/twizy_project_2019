import java.awt.Rectangle;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Rect;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OrbMatching extends Matcher{
	private ArrayList<Mat> iBDDs;
	private ArrayList<Mat> descriptorsOfBDD;
	private ArrayList<String> labelsBDD;
	
	private boolean affichage;
	
	public ArrayList<Rect> getWherePanels(){
		return this.wherePanels;
	}
	
	public OrbMatching(ArrayList<Mat> iBDDs,ArrayList<Mat> descriptorsOfBDD,ArrayList<String> labelsBDD,boolean affichage) {
		this.iBDDs = iBDDs;
		this.descriptorsOfBDD = descriptorsOfBDD;
		this.labelsBDD = labelsBDD;
		this.affichage = affichage;
		wherePanels = new ArrayList<Rect>();
	}

		

		private static double comparison(Mat compare,Mat ImComparateur,Mat descriptorOfComparator,boolean affichage) { 
		/**return a criteria which describe the similiraty beetween 2 images.
		 * 
		 */

		Imgproc.resize(compare, compare, ImComparateur.size());

		ORB detector = ORB.create(50);
		MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
		Mat descriptors1 = new Mat();
		detector.detectAndCompute(compare, new Mat(), keypoints1, descriptors1);

		MatOfDMatch matchs = new MatOfDMatch();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(descriptors1, descriptorOfComparator, matchs);

		double dist =0;
		int size = matchs.size(0);
		for (int i = 0; i < size; i++)
		{
			double d1 = matchs.get(i, 0)[3];
			for (int j = 0; j<size;j++) {
				double d2 = matchs.get(j, 0)[3];
				if (d1 < 0.75*d2 ) { 	//using the way to find good matches of the following tutorial:
					//https://docs.opencv.org/3.0-beta/doc/py_tutorials/py_feature2d/py_matcher/py_matcher.html#matcher

					dist += 1/(d1);
				}
			}


		}

		return dist;

	}

	public int whatIsThisImage(Mat image) {
		/**	return the indice of the panel which looks likes the most to the images, return -1 if it is not a panel.
		 * 
		 */
		double criteremax = 0;
		int cest = -1;


		for (int iImage =0; iImage < iBDDs.size();iImage++) {
			Mat iBDD = iBDDs.get(iImage);
			Mat descriptorOfIBDD = descriptorsOfBDD.get(iImage);
			double critere = comparison(image,iBDD,descriptorOfIBDD,affichage);
			if (critere > criteremax && critere > 0.1) {
				criteremax = critere;
				cest  = iImage;
			}

		}
		if (cest!=-1) 
			System.out.println("score - "+criteremax);
		if (affichage)
		{
			if (cest!=-1) {
				HighGui.imshow("it's im", iBDDs.get(cest));
			}
			else {
				HighGui.imshow("just an artifact", Imgcodecs.imread("artefact.jpg"));
			}
		}



		return cest;
	}
	public ArrayList<String> matching(Mat imagefilmee /**<[in] the image where we want to detect the panels */  ){
		/**	return the list of the detected panels using ORB.
		 * 
		 */

		ArrayList<String> listOfDetected = new ArrayList<String>();


		ArrayList<Mat> signs = this.getCircles(imagefilmee,false);
		for (Mat sign:signs) {

			int i = whatIsThisImage(sign);


			if (i !=-1)
				listOfDetected.add(labelsBDD.get(i));
			else
				System.out.println("Artefact");




		}

		return listOfDetected;
	}


	
}
