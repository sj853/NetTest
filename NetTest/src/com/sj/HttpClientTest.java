package com.sj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientTest {
	
	public static String regStr = "www.aizhan.com/reg.php";
	
	
	public static boolean register() throws ClientProtocolException, IOException{
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 
		 HttpHost target = new HttpHost("www.aizhan.com");
         HttpHost proxy = new HttpHost("14.18.16.69", 80, "http");

         RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

//         HttpGet httpGet = new HttpGet("/reg.php");
//         httpGet.setConfig(config);
//         System.out.println("executing request to " + target + " via " + proxy);
//         CloseableHttpResponse httpResponse =  httpClient.execute(target,httpGet);
//         System.out.println(httpResponse.getEntity().toString());
//		return false;
         
         HttpPost httpPost= new HttpPost("/reg.php");
         httpPost.setConfig(config);
		 
		 int randQQ = new Random().nextInt(1000000000);
			 ArrayList<NameValuePair> params = new ArrayList <NameValuePair>();
			 params.add(new BasicNameValuePair("email", randQQ+"@qq.com"));
			 params.add(new BasicNameValuePair("password", String.valueOf(randQQ)));
			 params.add(new BasicNameValuePair("password2", String.valueOf(randQQ)));
			 httpPost.setEntity(new UrlEncodedFormEntity(params));
			 CloseableHttpResponse httpResponse =  httpClient.execute(target,httpPost);
		//	 if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				 System.out.println("用户名: " + randQQ+"@qq.com");
				 System.out.println("密码: " + randQQ);
				 System.out.println(httpResponse.toString());
				 InputStream inputStream = httpResponse.getEntity().getContent();
//				 int count = 0;
//				 while(count == 0){
//					 count = inputStream.available();
//				 }
//				 	byte[] buffer = new bte[count];
//				 	int bits =  inputStream.read(buffer);
				 	String returnString = "";
				 	BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream)); //读入输入流  
				  
				      String temp;  
				      while (null != (temp = bf.readLine())) {  
				          returnString = returnString + temp;  
				      }  
				      System.out.println(returnString);
				      bf.close();  
				      inputStream.close();  
			// }else{
			//	 System.out.println(httpResponse.getStatusLine().getStatusCode());
		//	 }
		return false;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		register();
	}
}
