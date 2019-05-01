
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ImageToSend {

	public void send() throws IOException {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("https://api.panini.ai/ftly4iqc4tpg1skzwhklu3m2zz03-cnntwi/predict");

		/*ClassLoader classLoader = getClass().getClassLoader();
		File inputFile = new File(
				classLoader.getResource("test10.jpg").getFile());*/

		byte[] fileContent = FileUtils.readFileToByteArray(new File("test10.jpg"));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		System.out.println(encodedString);
		
		/*BufferedImage img = ImageIO.read(new File("filename.jpg"));             
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		Base64 base = new Base64(false);
		String encodedImage = base.encodeToString(baos.toByteArray());
		baos.close();
		encodedImage = java.net.URLEncoder.encode(encodedImage, "ISO-8859-1");
		*/
		// Request parameters and other properties.

		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("input", encodedString));
		httppost.setEntity(new StringFromEntity(params, "UTF-8"));

		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String htmlResponse = EntityUtils.toString(entity);
		System.out.println(htmlResponse);
		
		if (entity != null) {
			
			/*try (InputStream instream = entity.getContent()) {

				StringBuffer result = new StringBuffer();
				int line;
				while ((line = instream.read()) != 0) {
					result.append(line);
				}
			}*/
		}
	}
	
	public static void main(String[] args) throws IOException {
		ImageToSend im = new ImageToSend();
		im.send();
	}

}
