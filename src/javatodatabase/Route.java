/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatodatabase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JTextArea;

/**
 *
 * @author Chen
 */
public class Route extends Thread{
     private  String DB_URL;
    private  Statement sta ;
    public  ResultSet result;
    private  Connection con ;
    private  int columns ;
    private boolean isconnect_;
    private  ResultSetMetaData meta = null;
    public final String USER_AGENT = "Mozilla/5.0";
        // private String url = "http://122.116.45.4/";
        private String url = "http://127.0.0.1/";
   // private String url = "http://140.118.127.50/";
    
   String LicensePlateNumber = "TEST";
   double Latitude = 0.0;
   double Longitude = 0.0;
   double Speed = 0.0;
   String Address =" "; 
  
    
           
    public static void main(String[] args) throws Exception {

      
      
		Route http = new Route();
http.connect();
http.Routesearch("2016-12-02", "11:35:44", "12:04:48");//(測試日期,開始時間,結束時間)
//("yyyy-MM-dd","hh:mm:ss","hh:mm:ss")
http.start();
	//	http.sendGet();
       
	
               //http.GetSetting();
            //    http.sendPost();
            //    http.GetMessagebyPost();
         
           

	}
    
       public Route()
    {
        DB_URL = "jdbc:derby://localhost:1527/BUSDATA";
        con = null;
        sta = null;
        result = null;
        columns = 0;
        isconnect_ = false;
        
    }
       public void connect() {
    
    try {
        con = DriverManager.getConnection(DB_URL, "Fansen", "12345");
        sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE);
        
        result = sta.executeQuery("select * from FANSEN.BUS");
        meta = result.getMetaData();
        columns = meta.getColumnCount();
       
       System.out.println("Connect to DATABASE success.\n");
        isconnect_ = true;
        } catch (SQLException e) 
        {
        
        System.out.println(e.getMessage() + "\n");
        System.out.println("Connect failed.\n");
        }
    }
public void close_conn() {
    try {
       con.close();
       sta.close();
        
        System.out.println("Close the Connection.\n");
    } 
    catch (SQLException e) 
    {
       e.printStackTrace();
    }
}

public void search(String column ,String input) {
    try 
    { 
        String Cmd = String.format("select * from FANSEN.BUS  where %s = '%s' ",column,input);
       
        System.out.println(Cmd);
        result = sta.executeQuery(Cmd);
         meta = result.getMetaData();
  
        columns = meta.getColumnCount();
      //  result.first();
         
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
    }
  } 
 
public void Routesearch(String DATE ,String beginTime,String endTime) {
    try 
    { 
        String Cmd = String.format("SELECT * FROM FANSEN.BUS WHERE CLIENTDATE = '%s' AND CLIENTTIME > '%s' AND CLIENTTIME < '%s'"
                ,DATE,beginTime,endTime);
       
        System.out.println(Cmd);
        result = sta.executeQuery(Cmd);
         meta = result.getMetaData();
  
        columns = meta.getColumnCount();
        //result.first();
         
    } 
    catch (SQLException e) 
    {
        System.out.println(e.toString());
        e.printStackTrace();
    }
  } 

public void display() throws SQLException, InterruptedException
{
    int n = 0;
   result.first();
    do {
   String output = "";
   
        for (int i = 1; i <= columns; i++) 
            {
             // System.out.printf("%s ", result.getString(i)); //取出字串型態欄位之內容
           // System.out.printf("%s ", result.getObject(i)); //取出整數字串等各種型態欄位之內容
           if(meta.getColumnName(i).toString().equals("LATITUDE"))
           {  Latitude= result.getDouble(i);
           }
           else if (meta.getColumnName(i).toString().equals("LONGITUDE"))
           { Longitude= result.getDouble(i);
           }
           else if(meta.getColumnName(i).toString().equals("SPEED"))
           {Speed = result.getDouble(i);
           }
           else if(meta.getColumnName(i).toString().equals("ADDRESS"))
           {Address = result.getString(i);
           }
      }    
           
           
           
  try{         
        
		URL obj = new URL(url);
		HttpURLConnection con_Http = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con_Http.setRequestMethod("POST");
		con_Http.setRequestProperty("User-Agent", USER_AGENT);
		con_Http.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    Date NowTime_ = new Date();
         //格式化時間字串       
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        NowTime_.setTime(System.currentTimeMillis());
        String serverTime =  formatter.format(NowTime_);
        
       
        
       Random ran = new Random();
       String jsondata;// = "{\"Data\":{\"Name\":\"MichaelChan\",\"Email\":\"XXXX@XXX.com\",\"Phone\":[1234567,0911123456]}}";
     
                 jsondata= String.format("{\"Lic\": \"%s\" , \"LOG\" : %f , \"LAT\": %f, \"Speed\": %f ,\"Address\": \"%s\" ,\"Time\": \"%s\"}",
                       LicensePlateNumber,Longitude,Latitude,Speed,Address,serverTime);
     
   jsondata = URLEncoder.encode(jsondata, "UTF-8");
		String urlParameters;// = "id=1234&pass=asd45";
		
              urlParameters ="Data "+jsondata;
                
                    //   BufferedWriter BW_ = new BufferedWriter(FW);
                   /* try (FileWriter FW = new FileWriter("C:\\Users\\Chen\\Desktop\\Assignment\\FORJAVA\\JavaTODatabase\\src\\javatodatabase\\Client.txt",true)) {
                        //   BufferedWriter BW_ = new BufferedWriter(FW);
                        
                        FW.write(urlParameters+"\r\n");
                    
                        FW.flush();
                        FW.close();
                    }*/
               
              
		// Send post request
		con_Http.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con_Http.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();


                //取得回應訊息
		int responseCode = con_Http.getResponseCode();
   

		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con_Http.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
             
                
		System.out.println(response.toString());
 
        
        }catch (Exception ex) 
        {
            System.out.println(ex.toString());
          
        }
          Thread.sleep(1000);
          // String.format("%s ", result.getObject(i));
            //output+= "  ";
         
  
     

        }while (result.next()) ;

}

 public void run() { // override Thread's run()
        for (;;) { // infinite loop to print message
                      
            try 
            {   display();
            
            } 
             catch (Exception ex) {
               
                System.out.println(ex.toString());
                
                
            }
        }
    }
}
