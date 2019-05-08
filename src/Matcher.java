import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public abstract class Matcher {
	public ArrayList<Rect> getWherePanels() {
		return wherePanels;
	}

	protected ArrayList<Rect> wherePanels;
	
	public ArrayList<Mat> getCircles(Mat image,boolean withMarge){
		wherePanels = new ArrayList<Rect>();
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
				wherePanels.add(rect);
				int margex=0,margey=0;
				if (withMarge) {
					int marge =(int)(0.25*rect.height);
					int[] longueur = {0,0,0,0};
					longueur[0] = rect.x;
					longueur[1] = rect.y;
					longueur[2] = (int) (image.size().width-(rect.x+rect.width));
					longueur[3] = (int)(image.size().height-(rect.y+rect.height));
					int margeMax=Biblio.min(longueur);
					System.out.println("max"+margeMax +"|"+marge);
					if (margeMax<marge)
					{
						marge = margeMax;
						System.out.println("nouvellemarge"+marge);
					}
					margex=marge;
					margey=marge;
				}
				Mat tmp = image.submat(rect.y-margey,rect.y+rect.height+margey,rect.x-margex,rect.x+rect.width+margex); // decouper autour de chaque paneau
				Mat sign = Mat.zeros(tmp.size(), tmp.type());
				tmp.copyTo(sign);
				signFind.add(sign);




			}
		}
		return signFind;
	}
	
	public ArrayList<Mat> getTriangles(Mat image,boolean withMarge){
		int margex=0,margey=0;
		ArrayList<Mat> signFind = new ArrayList<Mat>();
		Mat threshold_img = Biblio.thresholding(image);
		List<MatOfPoint> contours = Biblio.detectContours(threshold_img);
		Biblio.detectContoursByShape(contours,3,0.05);
		for (MatOfPoint mOP:contours) {
			Rect rect = Imgproc.boundingRect(mOP);
			wherePanels.add(rect);
			if (withMarge) {

				int marge =(int)(0.25*rect.height);
				int[] longueur = {0,0,0,0};
				longueur[0] = rect.x;
				longueur[1] = rect.y;
				longueur[2] = (int) (image.size().width-(rect.x+rect.width));
				longueur[3] = (int)(image.size().height-(rect.y+rect.height));
				int margeMax=Biblio.min(longueur);
				System.out.println("max"+margeMax +"|"+marge);
				if (margeMax<marge)
				{
					marge = margeMax;
					System.out.println("nouvellemarge"+marge);
				}
				margex=marge;
				margey=marge;
			}
			Mat tmp = image.submat(rect.y-margey,rect.y+rect.height+margey,rect.x-margex,rect.x+rect.width+margex); // decouper autour de chaque paneau
			Mat sign = Mat.zeros(tmp.size(), tmp.type());
			tmp.copyTo(sign);
			signFind.add(sign);

		}
		return signFind;
	}

	public ArrayList<Mat> getOctogones(Mat image,boolean withMarge){
		int margex=0,margey=0;
		ArrayList<Mat> signFind = new ArrayList<Mat>();
		Mat threshold_img = Biblio.thresholding(image);
		List<MatOfPoint> contours = Biblio.detectContours(threshold_img);
		Biblio.detectContoursByShape(contours,8,0.05);
		for (MatOfPoint mOP:contours) {
			Rect rect = Imgproc.boundingRect(mOP);
			wherePanels.add(rect);
			if (withMarge) {

				int marge =(int)(0.25*rect.height);
				int[] longueur = {0,0,0,0};
				longueur[0] = rect.x;
				longueur[1] = rect.y;
				longueur[2] = (int) (image.size().width-(rect.x+rect.width));
				longueur[3] = (int)(image.size().height-(rect.y+rect.height));
				int margeMax=Biblio.min(longueur);
				System.out.println("max"+margeMax +"|"+marge);
				if (margeMax<marge)
				{
					marge = margeMax;
					System.out.println("nouvellemarge"+marge);
				}
				margex=marge;
				margey=marge;
			}

			Mat tmp = image.submat(rect.y-margey,rect.y+rect.height+margey,rect.x-margex,rect.x+rect.width+margex); // decouper autour de chaque paneau
			Mat sign = Mat.zeros(tmp.size(), tmp.type());
			tmp.copyTo(sign);
			signFind.add(sign);

		}
		return signFind;
	}
	
	abstract public ArrayList<String> matching(Mat imagefilmee /**<[in] the image where we want to detect the panels */  ) throws IOException, InterruptedException;
	
}
