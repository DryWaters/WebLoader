import java.io.*;
import java.net.*;

public class WebWorker extends Thread {
	
	String urlString;
	WebFrame webFrame;

	public WebWorker(String url, WebFrame webFrame) {
		this.urlString =  url;
		this.webFrame = webFrame;
	}
	
	public void run() {
		System.out.println("Fetching...." +  urlString);
		InputStream input =  null;
		StringBuilder contents = null;
		try {
			URL url =  new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			connection.connect();
			input = connection.getInputStream();
			BufferedReader reader  = new BufferedReader(new InputStreamReader(input));
			char[] array =  new char[1000];
			int len;
			contents = new StringBuilder(1000);
			while ((len = reader.read(array, 0, array.length)) > 0) {
				System.out.println("Fetching...." + urlString + len);
				contents.append(array, 0, len);
				Thread.sleep(100);
			}
			FileWriter outputFile = new FileWriter(url.getHost()+".html");
			BufferedWriter writer = new BufferedWriter(outputFile);
			writer.write(contents.toString());			
			writer.close();
		} catch(MalformedURLException ignored) {
			System.out.println("Exception: " + ignored.toString());
		} catch(InterruptedException exception) {
			Thread.currentThread().interrupt();
			System.out.println("Exception: " + exception.toString());
		} catch(IOException ignored) {
			System.out.println("Exception: " + ignored.toString());
		} finally {
			try {
				if (input != null) 
					input.close();

			} catch(IOException ignored) {}
		}
		webFrame.decrementThreads();
	}
}