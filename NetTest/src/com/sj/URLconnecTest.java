package com.sj;

import java.io.IOException;
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
			String urlStr = "http://www.baidu.com";
			URL url;
			URLConnection urlConnection;
			try {
				url = new URL(urlStr);
				urlConnection = url.openConnection();
				System.out.println(urlConnection.getContentLength());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
