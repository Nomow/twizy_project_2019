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



	

	public static int min(int[] longueur) {
		int min = Integer.MAX_VALUE;
		for (int i:longueur)
			if (i<min)
				min=i;
		return min;
	}
	
	

	



}