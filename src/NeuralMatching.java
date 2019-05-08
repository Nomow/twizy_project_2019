import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class NeuralMatching extends Matcher{
	ArrayList<String[]> tabIndice;
	
	public NeuralMatching(String fichierIndice) throws IOException {
		this.tabIndice = createTabIndice(fichierIndice);
		wherePanels = new ArrayList<Rect>();
		
	}
	
	private short decompositionAndSave(Mat imageFilmee,int iApp ) throws IOException{

		short i =0;
		ArrayList<Mat> signs = this.getCircles(imageFilmee,true);
		signs.addAll(getTriangles(imageFilmee,true));
		signs.addAll(getOctogones(imageFilmee,true));
		for (Mat sign:signs) {


			++i;
			Size s = new Size(50,50);
			Imgproc.resize(sign, sign, s);

			Imgcodecs.imwrite("temporaryFiles\\tempIm"+iApp+"s"+i+".png",sign);

		}

		return i;


	}


	
	public ArrayList<String[]> createTabIndice(String signNamesFile) throws IOException {
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

	public String indiceToRefName(ArrayList<String[]> tabIndice,String indice) throws IOException {

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

	

	public ArrayList<String> matching(Mat imageFilmee) throws IOException, InterruptedException{
		
		ArrayList<String> resultIndice = new ArrayList<String>();
		int iApp = Application.iTempFiles++;
		short i = this.decompositionAndSave(imageFilmee,iApp);
		if (i==0) return resultIndice;
		
		/*BufferedWriter writer2 = new BufferedWriter(new FileWriter(new File("TemporaryFiles\\whenAreWe.txt")));
		writer2.write(""+Application.iTempFiles);
		writer2.close();*/
		

		String pythonScriptPath = "Pytorch Folder\\pred.py";
		String[] cmd = new String[4];

		cmd[0] = "D:\\ProgramData\\Anaconda3\\python"; //very dependant of the computer
		cmd[1] = pythonScriptPath;
		cmd[2] = ""+ iApp;
		cmd[3] = ""+i;

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
		
		File f = new File("TemporaryFiles\\result"+iApp+".txt");
		BufferedReader fR = new BufferedReader(new FileReader(f));
		String chaine = fR.readLine();
		
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

		++Application.iTempFiles;
		return resultIndice;
	}

	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	private static BufferedReader getError(Process p) {
		return new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}



	
}
