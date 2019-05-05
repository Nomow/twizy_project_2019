import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		/** show the 3 RGB channels of an image;
		 * 
		 */

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
		/**detect the contours of a thresholded image
		 * 
		 */
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

	public static int whatIsThisImage(Mat image, ArrayList<Mat> iBDDs,ArrayList<Mat> descriptorsOfBDD,boolean affichage) {
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
	public static ArrayList<String> templateMatching(Mat imagefilmee /**<[in] the image where we want to detect the panels */,ArrayList<String> labelsBDD/**<[in] the labels of the images of the reference-panels */, ArrayList<Mat> iBDDs/**<[in] the reference-panels images */,ArrayList<Mat> descriptorsOfBDD/**<[in] the reference-panels images descriptors 
	 * @throws IOException */,boolean affichage/**<[in] true if we want to show the intermediate steps */  ){
		/**	return the list of the detected panels using ORB.
		 * 
		 */

		ArrayList<String> listOfDetected = new ArrayList<String>();


		ArrayList<Mat> signs = getCircles(imagefilmee,false);
		for (Mat sign:signs) {




			int i = Biblio.whatIsThisImage(sign, iBDDs,descriptorsOfBDD,affichage);


			if (i !=-1)
				listOfDetected.add(labelsBDD.get(i));
			else
				System.out.println("Artefact");




		}

		return listOfDetected;
	}
	public static void testPython(String path) throws IOException {
		ArrayList<String> result = new ArrayList<String>();

		String[] cmd = new String[2];
		cmd[0] = "C:\\Users\\alexa\\AppData\\Local\\Programs\\Python\\Python37-32\\python"; //very dependant of the computer
		cmd[1] = path;

		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);

		// retrieve output from python script
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		while((line = bfr.readLine()) != null) {
			// display each output line form python script
			System.out.println(line);



		}


	}


	public static short decompositionAndSave(Mat imageFilmee ) throws IOException{

		Mat hsv_image = Mat.zeros(imageFilmee.size(), imageFilmee.type());
		Imgproc.cvtColor(imageFilmee, hsv_image,Imgproc.COLOR_BGR2HSV);
		Mat threshold_img = Biblio.thresholding(hsv_image);
		List<MatOfPoint> contours = Biblio.detectContours(threshold_img);


		MatOfPoint2f moP2f = new MatOfPoint2f();
		float[] radius = new float[1];
		Point center = new Point();
		short i =0;
		ArrayList<Mat> signs = getCircles(imageFilmee,true);
		signs.addAll(getTriangles(imageFilmee,true));
		for (Mat sign:signs) {


			++i;
			Size s = new Size(50,50);
			Imgproc.resize(sign, sign, s);

			Imgcodecs.imwrite("temporaryFiles\\tempIm"+Application.iTempFiles+"s"+i+".png",sign);

		}

		return i;


	}
	public static ArrayList<String[]> createTabIndice(String signNamesFile) throws IOException {
		ArrayList<String[]> tabIndice = new ArrayList<String[]>();
		File f = new File(signNamesFile);
		BufferedReader fR = new BufferedReader(new FileReader(f));
		String chaine = fR.readLine();

		while(chaine!=null) {
			String[] champ = chaine.split(",");
			tabIndice.add(champ);
			chaine = fR.readLine();

		}
		fR.close();
		for (String[] sL:tabIndice)
		{
			for  (String s:sL)
				System.out.print(s+",");
			System.out.println("");
		}
		return tabIndice;

	}

	public static String indiceToRefName(ArrayList<String[]> tabIndice,String indice) throws IOException {

		String refName = "error";
		for (String[] champ:tabIndice) {
			if 	(champ[0].equals(indice)) {
				if (champ.length>=3) {
					return champ[2];
				}
			}
		}
		return refName;
	}

	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	private static BufferedReader getError(Process p) {
		return new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}


	public static ArrayList<String> neuronesImage(Mat imageFilmee,ArrayList<String[]> tabIndice) throws IOException, InterruptedException{
		++Application.iTempFiles;
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(new File("TemporaryFiles\\whenAreWe.txt")));
		writer2.write(""+Application.iTempFiles);
		writer2.close();

		ArrayList<String> pannelsFind = new ArrayList<String>();
		short i = decompositionAndSave(imageFilmee);

		String pythonScriptPath = "Pytorch Folder\\pred.py";
		String[] cmd = new String[2];

		cmd[0] = "D:\\ProgramData\\Anaconda3\\python"; //very dependant of the computer
		cmd[1] = pythonScriptPath;

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("TemporaryFiles\\howMany"+Application.iTempFiles+".txt")));

		writer.write(""+i);
		writer.close();

		try {
			// create runtime to execute external command
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(cmd);
			BufferedReader output = getOutput(p);
			BufferedReader error = getError(p);
			// retrieve output from python script
			String ligne = "";
			while ((ligne = output.readLine()) != null) {
				System.out.println(ligne);
			}

			while ((ligne = error.readLine()) != null) {
				System.out.println(ligne);
			}

			p.waitFor();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*ProcessBuilder pb = new ProcessBuilder(cmd[0],cmd[1],(String)Application.iTemp);
		pb.start();
		Thread.sleep(500);
		 */
		File f = new File("TemporaryFiles\\result"+Application.iTempFiles+".txt");
		BufferedReader fR = new BufferedReader(new FileReader(f));
		String chaine = fR.readLine();
		ArrayList<String> resultIndice = new ArrayList<String>();
		while(chaine!=null) {
			resultIndice.add(chaine);
			System.out.println(chaine);

			chaine = fR.readLine();

		}
		fR.close();
		int N = resultIndice.size();


		for (i=0;i<N;i++) {
			resultIndice.set(i, indiceToRefName(tabIndice,resultIndice.get(i)));
		}


		return resultIndice;
	}

	public static ArrayList<Mat> getCircles(Mat image,boolean withMarge){
		ArrayList<Mat> signFind = new ArrayList<Mat>();
		Mat threshold_img = Biblio.thresholding(image);
		List<MatOfPoint> contours = Biblio.detectContours(threshold_img);


		MatOfPoint2f moP2f = new MatOfPoint2f();
		float[] radius = new float[1];
		Point center = new Point();





		for (int c=0;c<contours.size();c++) {
			MatOfPoint contour = contours.get(c);
			double contourArea = Imgproc.contourArea(contour);
			moP2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(moP2f, center, radius);
			if ((contourArea/(Math.PI*radius[0]*radius[0]))>=0.8 && (radius[0] >20)) {
				//Imgproc.circle(image, center, (int)radius[0], new Scalar(0,255,0),2);
				Rect rect = Imgproc.boundingRect(contour);
				int margex=0,margey=0;
				if (withMarge) {
					int marge =(int)(0.25*rect.height);
					int[] longueur = {0,0,0,0};
					longueur[0] = rect.x;
					longueur[1] = rect.y;
					longueur[2] = (int) (image.size().width-(rect.x+rect.width));
					longueur[3] = (int)(image.size().height-(rect.y+rect.height));
					int margeMax=min(longueur);
					System.out.println("max"+margeMax +"|"+marge);
					if (margeMax<marge)
					{
						marge = margeMax;
						System.out.println("nouvellemarge"+marge);
					}
					margex=marge;
					margey=marge;
				}
				//Imgproc.rectangle(image, new Point(rect.x-margex,rect.y-margey),new Point(rect.x+rect.width+margex,rect.y+rect.height+margey), new Scalar(0,255,0));
				Mat tmp = image.submat(rect.y-margey,rect.y+rect.height+margey,rect.x-margex,rect.x+rect.width+margex); // decouper autour de chaque paneau
				Mat sign = Mat.zeros(tmp.size(), tmp.type());
				tmp.copyTo(sign);
				signFind.add(sign);




			}
		}
		return signFind;
	}
	public static void detectContoursByShape(List<MatOfPoint> contours, int vertices, double accuracy){
		MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
		MatOfPoint2f approxCurve = new MatOfPoint2f();

		for(int idx = contours.size() - 1; idx >= 0; idx--){
			MatOfPoint contour = contours.get(idx);

			matOfPoint2f.fromList(contour.toList());
			Imgproc.approxPolyDP(matOfPoint2f, approxCurve, Imgproc.arcLength(matOfPoint2f, true) * accuracy, true);
			long total = approxCurve.total();

			if (total != vertices)
				contours.remove(idx);
		}
	}



	public static ArrayList<Mat> getTriangles(Mat image,boolean withMarge){
		int margex=0,margey=0;
		ArrayList<Mat> signFind = new ArrayList<Mat>();
		Mat threshold_img = Biblio.thresholding(image);
		List<MatOfPoint> contours = Biblio.detectContours(threshold_img);
		detectContoursByShape(contours,3,0.05);
		MatOfPoint2f moP2f = new MatOfPoint2f();
		for (MatOfPoint mOP:contours) {
			Rect rect = Imgproc.boundingRect(mOP);
			if (withMarge) {

				int marge =(int)(0.25*rect.height);
				int[] longueur = {0,0,0,0};
				longueur[0] = rect.x;
				longueur[1] = rect.y;
				longueur[2] = (int) (image.size().width-(rect.x+rect.width));
				longueur[3] = (int)(image.size().height-(rect.y+rect.height));
				int margeMax=min(longueur);
				System.out.println("max"+margeMax +"|"+marge);
				if (margeMax<marge)
				{
					marge = margeMax;
					System.out.println("nouvellemarge"+marge);
				}
				margex=marge;
				margey=marge;
			}
			//Imgproc.rectangle(image, new Point(rect.x-margex,rect.y-margey),new Point(rect.x+rect.width+margex,rect.y+rect.height+margey), new Scalar(0,255,0));
			Mat tmp = image.submat(rect.y-margey,rect.y+rect.height+margey,rect.x-margex,rect.x+rect.width+margex); // decouper autour de chaque paneau
			Mat sign = Mat.zeros(tmp.size(), tmp.type());
			tmp.copyTo(sign);
			signFind.add(sign);

		}
		return signFind;
	}

	public static int min(int[] longueur) {
		int min = Integer.MAX_VALUE;
		for (int i:longueur)
			if (i<min)
				min=i;
		return min;
	}





}