package com.sj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomFlowRun {

	// 用户想浪费的流量 单位MB 3.15
	private static float flowsToRun = 14.25F;

	private static boolean isStop = false;

	public static long totalLength = 0;

	public static long currentLength = 0;

	public static int currentUseTime = 0;

	public static String fileName = "download.17173.com_WOW54.rar";

	public static String domain = "http://cdn1.download.17173.com/a47200687d295491007cbfa16a0c5200519fa25f430020d5/2013/";

	public static String downloadTemp = "d:/";
	
	
	static long SIZE_KB = 1024;
	static long SIZE_MB = SIZE_KB * 1024;
	static long SIZE_GB = SIZE_MB * 1024;
	/**
	 * MB转换为Byte
	 * 
	 * @param flowToRun
	 * @return
	 */
	public static Long MBtoByte(float ftr) {
		return (long) (ftr * SIZE_MB);
	}

	public static void download() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String urlStr = domain + fileName;
				URL url = null;
				HttpURLConnection urlConnection = null;
				InputStream is = null;
				FileOutputStream fileOutputStream = null;
				try {
					url = new URL(urlStr);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				while (!isStop) {
					try {
						urlConnection = (HttpURLConnection) url
								.openConnection();
						urlConnection.setConnectTimeout(15000);
						if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
							totalLength = urlConnection.getContentLengthLong();
							System.out.println("total-size: " + totalLength);
							is = urlConnection.getInputStream();
							fileOutputStream = new FileOutputStream(
									downloadTemp + fileName);
							int count = 0;
							int bite = 0;
							long startTime = 0;
							long useTime = 0;
							while (count == 0) {
								startTime = System.currentTimeMillis();
								count = is.available();
								if (currentLength >= MBtoByte(flowsToRun)) {
									isStop = true;
									continue;
								}
								if (currentLength < totalLength) {
									if (count == 0)
										continue;
									byte[] b = new byte[count];
									bite = is.read(b);
									if (bite == -1) {
										fileOutputStream.flush();
										count = -1;
									} else {
										fileOutputStream.write(b);
										useTime = System.currentTimeMillis()
												- startTime;
										currentUseTime += useTime;
										currentLength += bite;
										count = 0;
										System.out.println("flowsToRun: "
												+ MBtoByte(flowsToRun) + " currentFlow:"
												+ currentLength + " -- "
												+ (MBtoByte(flowsToRun) - currentLength));
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
							urlConnection.disconnect();
							is.close();
							fileOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}).start();
	}

	public static void main(String[] args) {
		download();
	}
}
