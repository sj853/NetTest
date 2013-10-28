package com.sj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author shang
 *URLconnection 试用
 */
public class URLconnecTest {

	public static void main(String[] args) {
			String urlStr = "http://localhost/iTunes64Setup.exe";
			URL url;
			HttpURLConnection urlConnection;
			try {
				url = new URL(urlStr);
				OutputStream os;
				InputStream is;
				urlConnection = (HttpURLConnection)url.openConnection();
				urlConnection.setConnectTimeout(5000); 
				if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
					long totalLenth = urlConnection.getContentLength();
					System.out.println("total-size: "+totalLenth);
					is = urlConnection.getInputStream();
					FileOutputStream fileOutputStream = new FileOutputStream("D:/new.exe");
					os = new BufferedOutputStream(fileOutputStream);
					long currentLenth = 0;
					int count  = 0;
					int bite = 0;
					while(count == 0){
						count  = is.available();
					}
					byte[] b = new byte[count];
					while(currentLenth < totalLenth){
						bite = is.read(b);
						os.flush();
						os.write(bite);
						currentLenth += bite;
						System.out.println("currentLenth: "+currentLenth);
					}
				 
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
			}
	}

}
