package com.sj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 * 
 * @author shang URLconnection 试用
 */
public class URLconnecTest {

	public static long totalLength = 0; 
	
	
	static long currentLength = -1;
	
	static int currentUseTime = 0;

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
				totalLength = urlConnection.getContentLength();
				System.out.println("total-size: " + totalLength);
				is = urlConnection.getInputStream();
				fileOutputStream = new FileOutputStream(
						"D:/iTunes64Setup.exe");
				int count = 0;
				int bite = 0;
				while (count == 0) {
					count = is.available();
					System.out.println("count:" + count);
					byte[] b = new byte[count];
					count = -1;
					if (currentLength < totalLength) {
						long startTime = System.currentTimeMillis();
						bite = is.read(b);
						long useTime = System.currentTimeMillis() - startTime;
						currentUseTime += useTime;
						if (bite == -1) {
							fileOutputStream.flush();
						}
						fileOutputStream.write(b);
						currentLength += bite;
						System.out.println("currentLenth: " + currentLength
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
	
	public static int secondeParse(long time){
		return (int)(time/1000);
	}
	
	

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				URLconnecTest.download();
				long useTime = System.currentTimeMillis() - startTime;
//				System.out.println("total size : "+sizeParse(totalLenth)+" total use time: " + secondeParse(useTime)
//						+ " avg speed: " + (URLconnecTest.totalLenth / secondeParse(useTime)));
			}
		}).start();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int time = 0;
			@Override
			public void run() {
				time++;
				try {
					System.out.println("currentSpeed: "+currentLength/time);
				} catch (Exception e) {
				}
			}
		},0, 1000);
		
	}

}
