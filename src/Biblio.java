import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Feature2D;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Biblio {
	

	public static void show3RGBChannel(Mat m,Mat dst) {

		Vector<Mat> channels= new Vector<Mat>();
		Core.split(m, channels);
		/* for (int i=0; i<channels.size();i++) {
	    	  Biblio.ImShow(Integer.toString(i),channels.get(i));
	      }
		 */
		Vector<Mat> chans = new Vector<Mat>();
		Mat empty = Mat.zeros(m.size(), CvType.CV_8UC1);
		for (int i = 0 ; i < channels.size();i++) {
			//Biblio.ImShow(Integer.toString(i), channels.get(i));
			chans.removeAllElements();
			for(int j=0 ; j<channels.size();j++) {
				if (j != i) chans.add(empty);
				else chans.add(channels.get(i));
			}
			Core.merge(chans, dst);
			HighGui.imshow(Integer.toString(i), dst);
		}

	}

	public static Mat thresholding(Mat m) {
		/**Thresholding the image by red.
		 * 
		 */

		Mat hsv_image = Mat.zeros(m.size(),m.type());
		Imgproc.cvtColor(m,hsv_image,Imgproc.COLOR_BGR2HSV);
		Mat treshold_img1 = new Mat();
		Mat treshold_img2 = new Mat();
		Mat treshold_img = new Mat();
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), treshold_img1);
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), treshold_img2);
		Core.bitwise_or(treshold_img1,treshold_img2,treshold_img);


		Imgproc.GaussianBlur(treshold_img, treshold_img, new Size(9,9), 2,2);
		return treshold_img;

	}

	public static List<MatOfPoint> detectContours(Mat threshold_img) {
		int tresh = 100;
		Mat canny_output = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		MatOfInt4 hierarchy = new MatOfInt4();
		Imgproc.Canny(threshold_img,canny_output,tresh,tresh*2);
		Imgproc.findContours(canny_output, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		return contours;
	}



	public static double comparison(Mat compare,Mat ImComparateur,Mat descriptorOfComparator,boolean affichage) { //Rque : va refaire a chaque fois comparateur
		/**return a criteria which describe the similiraty beetween 2 images.
		 * 
		 */

		Imgproc.resize(compare, compare, ImComparateur.size());



		ORB detector = ORB.create(30);
		MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
		Mat descriptors1 = new Mat();
		detector.detectAndCompute(compare, new Mat(), keypoints1, descriptors1);
		//detector.detectAndCompute(ImComparateur, new Mat(), keypoints2, descriptors2);

		MatOfDMatch matchs = new MatOfDMatch();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(descriptors1, descriptorOfComparator, matchs);
		//System.out.println(matchs.dump());

		/*if (affichage) {
			Mat matchedImage = new Mat(ImComparateur.rows(),ImComparateur.cols()*2,ImComparateur.type());
			Features2d.drawMatches(compare,keypoints1,ImComparateur,keyComparator,matchs,matchedImage);

			HighGui.imshow("titre", matchedImage);
			HighGui.waitKey(0);

		}*/

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
		//System.out.println(dist);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return dist;





	}

	public static int whatIsThisImage(Mat image, ArrayList<Mat> iBDDs,ArrayList<Mat> descriptorsOfBDD,boolean affichage) {
		/**	return the indice of the panel which looks likes the most to the images, return -1 if it is not a panel.
		 * 
		 */
		double criteremax = 0;
		int cest = -1;
		double dist;

		for (int iImage =0; iImage < iBDDs.size();iImage++) {
			Mat iBDD = iBDDs.get(iImage);
			Mat descriptorOfIBDD = descriptorsOfBDD.get(iImage);
			double critere = comparison(image,iBDD,descriptorOfIBDD,affichage);
			if (critere > criteremax) {
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
	public static ArrayList<String> templateMatching(Mat imagefilmee /**<[in] the image where we want to detect the panels */,ArrayList<String> labelsBDD/**<[in] the labels of the images of the reference-panels */, ArrayList<Mat> iBDDs/**<[in] the reference-panels images */,ArrayList<Mat> descriptorsOfBDD/**<[in] the reference-panels images descriptors */,boolean affichage/**<[in] true if we want to show the intermediate steps */  ){
		/**	return the list of the detected panels using ORB.
		 * 
		 */
		
		ArrayList<String> listOfDetected = new ArrayList<String>();


		Mat hsv_image = Mat.zeros(imagefilmee.size(), imagefilmee.type());
		Imgproc.cvtColor(imagefilmee, hsv_image,Imgproc.COLOR_BGR2HSV);
		Mat threshold_img = Biblio.thresholding(hsv_image);
		if (affichage) {
			HighGui.imshow("hsv",hsv_image);
			HighGui.waitKey(0);
			HighGui.imshow("threshold", threshold_img);
			HighGui.waitKey(0);
		}

		List<MatOfPoint> contours = Biblio.detectContours(threshold_img);


		MatOfPoint2f moP2f = new MatOfPoint2f();
		float[] radius = new float[1];
		Point center = new Point();





		for (int c=0;c<contours.size();c++) {
			MatOfPoint contour = contours.get(c);
			double contourArea = Imgproc.contourArea(contour);
			moP2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(moP2f, center, radius);
			if ((contourArea/(Math.PI*radius[0]*radius[0]))>=0.5 && (radius[0] >20)) {
				
				System.out.println("rayon - "+radius[0]);
				//Imgproc.circle(imagefilmee, center, (int)radius[0], new Scalar(0,255,0),2);
				Rect rect = Imgproc.boundingRect(contour);
				Imgproc.rectangle(imagefilmee, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(0,255,0));
				Mat tmp = imagefilmee .submat(rect.y,rect.y+rect.height,rect.x,rect.x+rect.width);
				Mat sign = Mat.zeros(tmp.size(), tmp.type());
				tmp.copyTo(sign);
				if (affichage) {
					HighGui.imshow("what is this ?", sign);
					HighGui.waitKey(0);
				}
				

				int i = Biblio.whatIsThisImage(sign, iBDDs,descriptorsOfBDD,affichage);


				if (i !=-1)
					listOfDetected.add(labelsBDD.get(i));
				else
					System.out.println("Artefact");
			}



		}

		return listOfDetected;
	}

}