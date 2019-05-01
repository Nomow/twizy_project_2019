import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendImages {/*
  public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  OkHttpClient client = new OkHttpClient();

  String post(String url, String json) throws IOException {
    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }

  String bowlingJson(String player1, String player2) {
    return "{'winCondition':'HIGH_SCORE',"
        + "'name':'Bowling',"
        + "'round':4,"
        + "'lastSaved':1367702411696,"
        + "'dateStarted':1367702378785,"
        + "'players':["
        + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
        + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
        + "]}";
  }
  
  String imageJson(String imagePath) throws IOException {
	  ClassLoader classLoader = getClass().getClassLoader();
      File inputFile = new File(classLoader
        .getResource(imagePath)
        .getFile());
        
	  byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath));
	  String encodedString = Base64.getEncoder().encodeToString(fileContent);
	  
	  return "{'input' : " + encodedString + "}";

  }

  public static void main(String[] args) throws IOException {
	byte[] fileContent = FileUtils.readFileToByteArray(new File("C:\\Users\\guill\\Downloads\\dataDL\\inferences\\test10.jpg"));
	String encodedString = Base64.getEncoder().encodeToString(fileContent);
	System.out.println(encodedString);
	
   SendImages example = new SendImages();
    String json = example.imageJson("C:\\Users\\guill\\Downloads\\dataDL\\inferences\\test10.jpg");
    String response = example.post("https://api.panini.ai/ftly4iqc4tpg1skzwhklu3m2zz03-cnntwi/predict", json);
    System.out.println(response);
  }*/
	
	public static void main(String[] args) {
	}
	
}