package com.sj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author shang URLconnection 试用
 */
public class URLconnecTest {

	public static long totalLenth = 0; 
	
	

	public static void download() {

		String urlStr = "http://localhost/iTunes64Setup.exe";
		URL url;
		HttpURLConnection urlConnection;
		InputStream is = null;
		FileOutputStream fileOutputStream = null;
		try {
			url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(5000);
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				totalLenth = urlConnection.getContentLength();
				System.out.println("total-size: " + totalLenth);
				is = urlConnection.getInputStream();
				fileOutputStream = new FileOutputStream(
						"D:/iTunes64Setup.exe");
				long currentLenth = 0;
				int count = 0;
				int bite = 0;
				while (count == 0) {
					count = is.available();
					System.out.println("count:" + count);
					byte[] b = new byte[count];
					count = -1;
					if (currentLenth < totalLenth) {
						long startTime = System.currentTimeMillis();
						bite = is.read(b);
						long useTime = System.currentTimeMillis() - startTime;
						if (bite == -1) {
							fileOutputStream.flush();
						}
						fileOutputStream.write(b);
						currentLenth += bite;
						System.out.println("currentLenth: " + currentLenth
								+ " speed: " );
						count = 0;
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static int sizeParse(long bitLen){
		return (int) (bitLen/8/1024/1024);
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				URLconnecTest.download();
				long useTime = System.currentTimeMillis() - startTime;
				System.out.println("total size : "+sizeParse(totalLenth)+" total use time: " + useTime
						+ " avg speed: " + (URLconnecTest.totalLenth / useTime));
			}
		}).start();
	}

}
