import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TestPython {

	public static void main(String[] args) throws IOException, ScriptException {
		// TODO Auto-generated method stub
		ProcessBuilder pb = new ProcessBuilder("C:\\Users\\alexa\\AppData\\Local\\Programs\\Python\\Python37-32\\python","Pytorch Folder\\pred.py");
		Process p = pb.start();
		
	}

}
