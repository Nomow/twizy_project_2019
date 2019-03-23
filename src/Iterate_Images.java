import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Iterate_Images {

	static ArrayList<String> ImagesList = new ArrayList<String>();
	public static void fetchFiles(File dir, Consumer<File> fileConsumer) {

		
	      if (dir.isDirectory()) {
	          for (File file1 : dir.listFiles()) {
	              fetchFiles(file1, fileConsumer);
	          }
	      } else {
	          fileConsumer.accept(dir);
	      }
	  }

	  public static void main(String[] args) {
	      File file = new File("TestFolder");
	      fetchFiles(file, f -> System.out.println(f.getAbsolutePath()));
	  }
}
