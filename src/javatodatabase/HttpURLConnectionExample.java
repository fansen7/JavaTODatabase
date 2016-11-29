/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatodatabase;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import org.json.*;

public class HttpURLConnectionExample extends Thread  {

	public final String USER_AGENT = "Mozilla/5.0";
        private Date NowTime_ = new Date();
        
       private static HttpURLConnectionExample http = new HttpURLConnectionExample();
       private static int time = 0; //封包內容正確的次數
       private static int fail = 0;
       private static int put = 0; //丟封包的次數
       private static int ResponseT = 0;   //    Response code == 200 
       private int sampling = 1000;
       private int get_time = 1000;
       
       
    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

      
      
	//	HttpURLConnectionExample http = new HttpURLConnectionExample();

	//	System.out.println("Testing 1 - Send Http GET request");
	//	http.sendGet();
        
        int amount = 100;
        HttpURLConnectionExample[] cookie = new HttpURLConnectionExample[amount];
        
		for(int i =0;i<amount;i++)
                {
                cookie[i] =  new HttpURLConnectionExample();
                cookie[i].start();
                
                
                }
		System.out.println("\nTesting 2 - Send Http POST request");
               /* http.GetSetting();
                http.sendPost();
                http.GetMessagebyPost();
                http.start();
           
*/
	}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url = "http://www.google.com/search?q=mkyong";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "http://122.116.45.4/";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
try{
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
   
         //格式化時間字串       
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        NowTime_.setTime(System.currentTimeMillis());
        String serverTime =  formatter.format(NowTime_);
        
       
        
       Random ran = new Random();
       String jsondata;// = "{\"Data\":{\"Name\":\"MichaelChan\",\"Email\":\"XXXX@XXX.com\",\"Phone\":[1234567,0911123456]}}";
     
       jsondata= String.format("{\"LOG\" : %d , \"LAT\": %d ,\"Time\": \"%s\",\"NAMe\": \"%s\"}",
        ran.nextInt(100),ran.nextInt(100),serverTime,"台北市");
     

		String urlParameters;// = "id=1234&pass=asd45";
		
              urlParameters ="Data "+jsondata;
                
                    //   BufferedWriter BW_ = new BufferedWriter(FW);
                    try (FileWriter FW = new FileWriter("C:\\Users\\Chen\\Desktop\\Assignment\\FORJAVA\\JavaTODatabase\\src\\javatodatabase\\Client.txt",true)) {
                        //   BufferedWriter BW_ = new BufferedWriter(FW);
                        
                        FW.write(urlParameters+"\r\n");
                    
                        FW.flush();
                        FW.close();
                    }
               
              
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

  put++;
                //取得回應訊息
		int responseCode = con.getResponseCode();
   if(responseCode== 200) ResponseT++;

		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
                if(response.toString().equals("ACK")) time++;
                
		System.out.println(response.toString());
 
        
        }catch (Exception ex) 
        {
       
            throw ex;
        }

}

	

      public void run() { // override Thread's run()
        for (int i=0;i<1000;i++) { // infinite loop to print message
                      
            try 
            {
                sendPost();
                Thread.sleep(sampling);
                GetMessagebyPost();
                Thread.sleep(get_time);
            } 
             catch (Exception ex) {
                 fail++;
                System.out.println(ex.toString());
                
                
            }
        }
    }
      
     private void GetMessagebyPost() throws Exception {

         try{
		String url = "http://122.116.45.4/";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters;// = "id=1234&pass=asd45";
		
                urlParameters ="Get Message";
                
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
        
                    
      put++;
                //取得回應訊息
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 if(responseCode== 200) ResponseT++;
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        
        String JsonResponse; 
        JsonResponse = response.toString();
     
      /* JSONObject j;
        j = new JSONObject(JsonResponse);
        Object jsonOb = j.get("A");
        System.out.println("A-:" + jsonOb+"\n");
        jsonOb = j.get("B");
        System.out.println("B-:" + jsonOb+"\n");  
        jsonOb = j.get("C");
        System.out.println("C-:" + jsonOb+"\n");
	*/
        time++;
         System.out.println("TIME:" + time);
         System.out.println("fail:" + fail);
         System.out.println("PUT:" + put);
        System.out.println("response:" + ResponseT);
        in.close();
		
		//print result
		System.out.println(response.toString());
         }
      catch (Exception ex) {
          throw ex;
      }
//                 fail++;
//                System.out.println(ex.toString());
     }

    
     private void GetSetting() throws Exception {

		String url = "http://122.116.45.4/";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters;
		
                urlParameters ="Get Setting";
                
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
      
                //取得回應訊息
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        
        String JsonResponse; 
        JsonResponse = response.toString();
     System.out.println("setting--------------");
       JSONObject j;
        j = new JSONObject(JsonResponse);
        Object jsonOb = j.get("sampling");
        this.sampling = (int)jsonOb;
        System.out.println("sampling-:" + jsonOb+"\n");
        jsonOb = j.get("get_time");
        this.get_time = (int)jsonOb;
        System.out.println("get_time-:" + jsonOb+"\n");  
        System.out.println("setting--------------");
	
        in.close();
		
		//print result
		System.out.println(response.toString());

	}

} 


