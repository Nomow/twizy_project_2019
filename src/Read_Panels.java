import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Read_Panels{

	protected String file;

	protected ArrayList<String> listPanel = new ArrayList<String>();
	

	public Read_Panels(String file)  throws IOException {

		List<String> results = new ArrayList<String>();
		this.file = file;
		Files f = new Files();

		f.open(file, "R");
		String[] field = new String[10];
		String line = f.read();
	    

		while(line!=null && line.length() >= 2) {

			
			System.out.println(line);
			field = line.split(",");
			if (field != null) {
				
				File[] files = new File("TestFolder\\" + field[0]).listFiles();
				for (File fl : files) {
				    if (fl.isFile()) {
				        listPanel.add(fl.getName());
				    }
				}

			}line = f.read();
		}
		f.close();



	}
	
	public ArrayList<String> getListPanel(){
		return listPanel;
	}

	

}
