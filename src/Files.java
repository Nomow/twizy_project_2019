
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Files {
	private BufferedWriter fW;
	private BufferedReader fR;
	private char mode;


	public void open(String nameOfFile, String s) throws IOException{
		mode = (s.toUpperCase()).charAt(0);
		File f = new File(nameOfFile);
		if (mode == 'R' || mode == 'L')
			fR = new BufferedReader(new FileReader(f));
		else if (mode == 'W' || mode == 'E')
			fW = new BufferedWriter(new FileWriter(f));
	}
	public void close() throws IOException {
		if (mode == 'R' || mode == 'L') fR.close();
		else if (mode == 'W' || mode == 'E') fW.close();
	}
	public String read() throws IOException {
		String chaine = fR.readLine();
		return chaine;
	}
	public void write(double tmp) throws IOException {
		String chaine = "";
		chaine = chaine.valueOf(tmp);
		if (chaine != null) {
			fW.write(chaine,0,chaine.length());
			fW.newLine();
		}
	} 
	
	public void write(String tmp) throws IOException {
		
		
			fW.write(tmp,0,tmp.length());
			fW.newLine();
		
	} 

}
