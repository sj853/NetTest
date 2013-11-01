package com.sj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.SliderUI;

import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 * 
 * @author shang URLconnection 试用
 */
public class URLconnecTest {

	public static long totalLength = 0;

	static long currentLength = 0;

	static int currentUseTime = 0;

	public static String fileName = "pcmaster_5.02.zip";

	public static String domain = "http://down.ruanmei.com/pcmaster/";

	public static void download() {
		String urlStr = domain + fileName;
		URL url;
		HttpURLConnection urlConnection;
		InputStream is = null;
		FileOutputStream fileOutputStream = null;
		try {
			url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(15000);
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				totalLength = urlConnection.getContentLength();
				System.out.println("total-size: " + totalLength);
				is = urlConnection.getInputStream();
				fileOutputStream = new FileOutputStream("D:/" + fileName);
				int count = 0;
				int bite = 0;
				long startTime = 0;
				long useTime = 0;
				while (count == 0) {
					startTime = System.currentTimeMillis();
					count = is.available();
					if (currentLength < totalLength) {
						if (count == 0)
							continue;
//						System.out.println("count : " + count);
						byte[] b = new byte[count];
						bite = is.read(b);
						if (bite == -1) {
							fileOutputStream.flush();
							count = -1;
						} else {
							fileOutputStream.write(b);
							useTime = System.currentTimeMillis() - startTime;
							currentUseTime += useTime;
//							System.out.println("useTime: " + useTime);
							currentLength += bite;
							try {
								System.out
										.println("bite: "
												+ bite
												+ "usetime: "
												+useTime
												+ " currentLenth: "
												+ currentLength
												+ " currentTime: "
												+ currentUseTime
												+ " speed: "
												+ sizeParse(currentLength
														/ currentUseTime * 1000)
												+ "/S");
							} catch (Exception e) {
							}
							count = 0;
						}
					} else {
						count = -1;
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

	public static String sizeParse(long bitLen) {
		long SIZE_KB = 1024;
		long SIZE_MB = SIZE_KB * 1024;
		long SIZE_GB = SIZE_MB * 1024;

		if (bitLen < SIZE_KB) {
			return String.format("%d B", bitLen);
		} else if (bitLen < SIZE_MB) {
			return String.format("%.2f KB", (float) bitLen / SIZE_KB);
		} else if (bitLen < SIZE_GB) {
			return String.format("%.2f MB", (float) bitLen / SIZE_MB);
		} else {
			return String.format("%.2f GB", (float) bitLen / SIZE_GB);
		}

	}

	public static long secondeParse(long time) {
		return time / 1000;
	}

	static long furtherLength = 0;

	public static void main(String[] args) {
//		Timer timer = new Timer(true);
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				try {
//					if (furtherLength == totalLength)
//						this.cancel();
//					System.out.println("currentSpeed: "
//							+ sizeParse(currentLength - furtherLength));
//					furtherLength = currentLength;
//				} catch (Exception e) {
//				}
//			}
//		}, 0, 1000);

		new Thread(new Runnable() {

			@Override
			public void run() {

				long startTime = System.currentTimeMillis();
				URLconnecTest.download();
				long useTime = System.currentTimeMillis() - startTime;
				try {
					System.out.println("total size : "
							+ sizeParse(totalLength)
							+ " total use time: "
							+ useTime
							+ " avg speed: "
							+ sizeParse(URLconnecTest.totalLength / useTime
									* 1000) + "/S");
				} catch (Exception e) {
				}
			}
		}).start();

	}

}
