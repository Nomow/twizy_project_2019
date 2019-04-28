
public class SendImages {
	HttpClient httpclient = HttpClients.createDefault();
	HttpPost httppost = new HttpPost("http://www.a-domain.com/foo/");

	// Request parameters and other properties.
	List<NameValuePair> params = new ArrayList<NameValuePair>(2);
	params.add(new BasicNameValuePair("param-1", "12345"));
	params.add(new BasicNameValuePair("param-2", "Hello!"));
	httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

	//Execute and get the response.
	HttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();

	if (entity != null) {
	    try (InputStream instream = entity.getContent()) {
	        // do something useful
	    }
	}
}
