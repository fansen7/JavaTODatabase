/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatodatabase;

import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JTextArea;
import org.json.*;

public class Webserver implements Runnable {

    ServerSocket server = null;
    static int time = 0;
    String root;
    String Postcmd;
    String[] map = {"jpg=image/jpeg", "gif=image/gif", "zip=application/zip", "pdf=application/pdf", "xls=application/vnd.ms-excel", "ppt=application/vnd.ms-powerpoint", "doc=application/msword", "htm=text/html", "html=text/html", "css=text/plain", "vbs=text/plain", "js=text/plain", "txt=text/plain", "java=text/plain"};
    FileWriter log = new FileWriter("WebServer.log", true);
    private ConnectDATA Database = null;
    private Violiant_List violiant = null;
    private int i = 0;
    private boolean A = false;
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    private boolean E = false;
    private boolean F = false;
    private boolean G = false;
    private boolean H = false;
    private boolean I = false;
    private boolean J = false;
    private boolean K = false;
    private boolean L = false;
    private boolean M = false;
    private boolean N = false;
    private JTextArea Display_ = null;

    public void setATRUE() {
        A = true;
    }

    public void setBTRUE() {
        B = true;
    }

    public void setCTRUE() {
        C = true;
    }

    public void setDTRUE() {
        D = true;
    }

    public void setETRUE() {
        E = true;
    }

    public void setFTRUE() {
        F = true;
    }

    public void setGTRUE() {
        G = true;
    }
  public void setHTRUE() {
        H = true;
    }  
  public void setITRUE() {
        I = true;
    }  
  public void setJTRUE() {
        J = true;
    }
  public void setKTRUE() {
        K = true;
    }
  public void setLTRUE() {
        L = true;
    }
  public void setMTRUE() {
        M = true;
    }
  public void setNTRUE() {
        N = true;
    }
    
  //---------------------
  public void setAFALSE() {
        A = false;
    }

    public void setBFALSE() {
        B = false;
    }

    public void setCFALSE() {
        C = false;
    }

    public void setDFALSE() {
        D = false;
    }

    public void setEFALSE() {
        E = false;
    }

    public void setFFALSE() {
        F = false;
    }

    public void setGFALSE() {
        G = false;
    }

    public void setHFALSE() {
        H = false;
    }
    public void setIFALSE() {
        I = false;
    }
    public void setJFALSE() {
        J = false;
    }
    public void setKFALSE() {
        K = false;
    }
    public void setLFALSE() {
        L = false;
    }
    public void setMFALSE() {
        M = false;
    }
    public void setNFALSE() {
        N = false;
    }

    /*public static void main(String args[]) throws Exception {
    int serverPort = 80;
    String rootPath = "";
    if (args.length >= 2) serverPort = Integer.parseInt(args[1]);
    if (args.length >= 1) rootPath = args[0];
    new Webserver(serverPort, rootPath);
  }
     */
    public Webserver(int pPort, String pRoot, JTextArea Display, ConnectDATA newD) throws IOException {
        pPort = 80;
        pRoot = "";
        Display_ = Display;
        Database = newD;
        violiant = new Violiant_List(newD);
        violiant.Print_List();

        server = new ServerSocket(pPort, 120); // 建立 ServerSocket 物件並佔領 port (預設為 80).
        File rootDir = new File(pRoot);
        root = rootDir.getCanonicalPath();
        System.out.println("port=" + pPort + " root=" + root);
        //run();
    }

    public void run() {
        try {
            Socket socket = server.accept();      // 接受瀏覽器的連線。注意: accept() 是一個等待型呼叫，會一直等到有連線進來才完成執行。
            Thread service = new Thread(this);    // 建立一個新的 WebServer 執行緒。
            service.start();                      // 啟動它以以便處理下一個請求。
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // 取得傳送資料的輸出檔。
            DataInputStream in = new DataInputStream(socket.getInputStream());     // 取得接收資料的輸入檔。

            String request = request(in);         // 讀取瀏覽器傳來的請求訊息。

            postParse(out);
            //response(socket, request, out);       // 回應訊息給對方的瀏覽器。

            socket.close();                       // 關閉該連線。
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //處理 POST GET

    String request(DataInputStream in) {
        String request = "";
        try {
            // 讀取到第一個空白行為止，這是標頭訊息。

            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                request += line + "\r\n";
                if (line.length() == 0) {
                    break;
                }
            }

            // 根據 Content-Length: ，讀取到第一個空白行後面的 POST 表單訊息。
            String lengthStr = innerText(request.toLowerCase(), "content-length:", "\r\n");

            if (lengthStr != null) {
                int contentLength = Integer.parseInt(lengthStr.trim());//轉int
                byte[] bytes = new byte[contentLength];
                in.read(bytes);
                String post = new String(bytes);
                post = java.net.URLDecoder.decode(post, "UTF-8");
                request = java.net.URLDecoder.decode(request, "UTF-8");

                //   request += post;
                this.Postcmd = "";
                this.Postcmd = post;

                /*  if(!post.equals("Get Message"))
       {
           try (FileWriter FW = new FileWriter("C:\\Users\\Chen\\Desktop\\Assignment\\FORJAVA\\JavaTODatabase\\src\\javatodatabase\\Server.txt",true)) {
                        //   BufferedWriter BW_ = new BufferedWriter(FW);
                        
                        FW.write(post+"\r\n");
                    
                        FW.flush();
                        FW.close();
                    }
       }*/
                System.out.println(post);
                // System.out.println(request);
            }
        } catch (Exception e) {
        }
        return request;
    }

    public static String innerText(String pText, String beginMark, String endMark) {
        int beginStart = pText.indexOf(beginMark);
        if (beginStart < 0) {
            return null;
        }
        int beginEnd = beginStart + beginMark.length();
        int endStart = pText.indexOf(endMark, beginEnd);
        if (endStart < 0) {
            return null;
        }

        return pText.substring(beginEnd, endStart);
    }

//回應的方式
    void response(Socket socket, String request, DataOutputStream out) throws Exception {
        System.out.println("========response()==========");
        try {

            //  String path = innerText(request, "POST ", "HTTP/").trim(); // 取得檔案路徑 : POST 版。
            //  path = java.net.URLDecoder.decode(path, "UTF-8");  	// 將檔案路徑解碼以 Unicode 方式解碼。
            // log.write((new Date()).toString()+"\t,"+socket.getRemoteSocketAddress()+"\t,"+path+"\r\n");
            // log.flush();
            // String fullPath = root+path;                       	// 將路徑轉為絕對路徑。
            //  String fullPath = root;
            //  System.out.println(fullPath);
            // if (fullPath.indexOf(".")<0) fullPath += "\\src\\javatodatabase\\b.php";
            //    File file = new File(fullPath);                    	// 開啟檔案。
            //  if (!file.exists()) throw new Exception("File not found !");
            output(out, "HTTP/1.0 200 OK");                   	// 傳回成功訊息。
            //  output(out, "Content-Type: "+type(fullPath));      	// 傳回檔案類型。
            //  output(out, "Content-Length: " + file.length());   	// 傳回內容長度。
            output(out, "");                                  	// 空行代表標頭段結束。
            output(out, "123456789ASDFGHJKL:");

// 以區塊為單位回傳檔案。
            /*
      byte[] buffer = new byte[4096];
      FileInputStream is = new FileInputStream(fullPath);
      while (true) {
        int len = is.read(buffer);
        out.write(buffer, 0, len);
        if (len < 4096) break;
      }
             */
        } catch (Exception e) { // 有錯，傳回錯誤訊息。
            output(out, "HTTP/1.0 404 " + e.getMessage());
            // output(out, "Content-Type: text/html");
            output(out, "");
        }
        out.flush();
    }

    String type(String path) {
        String type = "*/*";
        path = path.toLowerCase();
        for (int i = 0; i < map.length; i++) {
            String[] tokens = map[i].split("=");
            String ext = tokens[0], mime = tokens[1];
            if (path.endsWith("." + ext)) {
                type = mime;
            }
        }
        return type;
    }

    void output(DataOutputStream out, String str) throws Exception {
        out.writeBytes(str + "\r\n");
        System.out.println(str);
    }

    public void displayTOtextarea(String input) {
        Display_.append(input);
        Display_.setCaretPosition(Display_.getDocument().getLength());

    }

    public void postParse(DataOutputStream out) throws JSONException, Exception {
        try {
            String Data = this.Postcmd;
            int cmdindex = Data.indexOf(" ", 0);
            String cmd = Data.substring(0, cmdindex);
            String content = Data.substring(cmdindex, Data.length());
            //       System.out.println(cmd +"\n\n\n");
            if (cmd.equals("Get")) {
                output(out, "HTTP/1.0 200 OK");
                output(out, "");

                if (content.equals(" Message")) {
                    String jsonMessage;

                  //  jsonMessage= String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}",true,true,true,true,true,true,true);
    jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b, \"H\": %b, \"I\": %b, \"J\": %b, \"O\": %b, \"L\": %b, \"M\": %b, \"N\": %b}"
            , A, B, false, D, E, F, G,H,I,J,K,L,M,N);
//                    switch (i % 7) {
//                        case 0:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", true, false, false, false, false, false, false);
//                            break;
//                        case 1:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", false, true, false, false, false, false, false);
//                            break;
//                        case 2:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", false, false, true, false, false, false, false);
//                            break;
//                        case 3:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", false, false, false, true, false, false, false);
//                            break;
//                        case 4:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", false, false, false, false, true, false, false);
//                            break;
//                        case 5:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", false, false, false, false, false, true, false);
//                            break;
//                        case 6:
//                            jsonMessage = String.format("{\"A\" : %b , \"B\": %b ,\"C\": %b, \"D\": %b, \"E\": %b, \"F\": %b, \"G\": %b}", false, false, false, false, false, false, true);
//                            break;
//                        default:
//                            jsonMessage = "";
//                            break;
//                    }
//                    i++;

                    output(out, jsonMessage);

                    if (A) {
                        this.setAFALSE();
                    }
                    if (B) {
                        this.setBFALSE();
                    }
                    if (C) {
                        this.setCFALSE();
                    }
                    if (D) {
                        this.setDFALSE();
                    }
                    if (E) {
                        this.setEFALSE();
                    }
                    if (F) {
                        this.setFFALSE();
                    }
                    if (G) {
                        this.setGFALSE();
                    }
                    if (H) {
                        this.setHFALSE();
                    }
                    if (I) {
                        this.setIFALSE();
                    }
                    if (J) {
                        this.setJFALSE();
                    }
                    if (K) {
                        this.setKFALSE();
                    }
                    if (L) {
                        this.setLFALSE();
                    }
                    if (M) {
                        this.setMFALSE();
                    }
                    if (N) {
                        this.setNFALSE();
                    }
                } else if (content.equals(" Setting")) {
                    String jsonMessage;
                    int sampling = 1500;
                    int get_time = 1500;
                    jsonMessage = String.format("{\"sampling\" : %d , \"get_time\": %d }", sampling, get_time);
                    output(out, jsonMessage);

                }
            } else if (cmd.trim().equals("Data")) {
                // System.out.println("1");
                JSONObject j;
                j = new JSONObject(content);
                Object License = j.get("Lic");
                Object Longitude = j.get("LOG");
                Object Latitude = j.get("LAT");
                Object Speed = j.get("Speed");
                Object Time = j.get("Time");
                Object Address = j.get("Address");

                SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
                SimpleDateFormat dateClient = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeClient = new SimpleDateFormat("HH:mm:ss");
                Date clientdate = null;
                try {
                    clientdate = dt.parse(Time.toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println("1");
                String Client_D = dateClient.format(clientdate);
                String Client_T = timeClient.format(clientdate);
                displayTOtextarea("Longitude:" + Longitude);
                displayTOtextarea(" Latitude:" + Latitude);
                displayTOtextarea(" Speed:" + Speed + "\n\n");

                displayTOtextarea(" Time:" + clientdate.toString() + "\n\n");

                output(out, "HTTP/1.0 200 OK");
                output(out, "");
                output(out, "ACK");
                Database.insert(License.toString(), (double) Longitude, (double) Latitude,
                        (double) Speed, Address.toString(), Client_D, Client_T);

                violiant_re(violiant.VioliantCheck(License.toString(),
                        (double) Longitude,(double) Latitude, (double) Speed,
                        Address.toString(), Client_D, Client_T));

            } else {
                output(out, "HTTP/1.0 200 OK");
                output(out, "");
                output(out, "Error Input.");
            }

        } catch (Exception e) { // 有錯，傳回錯誤訊息。
            //    output(out, "HTTP/1.0 404 " + e.getMessage());
            // output(out, "Content-Type: text/html");
            // output(out, "");
            System.out.println(e.toString());
            /* output(out, "HTTP/1.0 200 OK");     
        output(out, "");
        output(out, "Error Input.");*/
        }
        out.flush();
    }

    /*public void violiant_re(String chk) {
        System.out.println(chk);
        String[] chkC = chk.split(",");
        for (int i = 0; i < chkC.length; i++) {
            if (chkC[i].equals("即將超速")) {
                this.setATRUE();
            } else if (chkC[i].equals("超速")) {
                this.setBTRUE();
            } else if (chkC[i].equals("逆向行駛")) {
                this.setCTRUE();
            } else if (chkC[i].equals("禁行區域")) {
                this.setDTRUE();
            } else if (chkC[i].equals("違規左轉")) {
                this.setETRUE();
            } else if (chkC[i].equals("前方路段禁止進入區域")) {
                this.setFTRUE();
            } else if (chkC[i].equals("前方路段禁止左轉")) {
                this.setGTRUE();
            }
        }
    }*/
    
     public void violiant_re(String chk)
 {
     System.out.println(chk);
     //System.err.print("debug _ re");
     String[] chkC = chk.split(",");
     for(int i = 0;i<chkC.length;i++)
     {
         if(chkC[i].equals("即將超速"))
             this.setATRUE();
         else if(chkC[i].equals("超速"))
             this.setBTRUE();
         else if(chkC[i].equals("逆向行駛"))
             this.setCTRUE();
         else if(chkC[i].equals("禁行區域"))
             this.setDTRUE();
         else if(chkC[i].equals("違規左轉"))
             this.setETRUE();
         else if(chkC[i].equals("違規右轉"))
             this.setFTRUE();
         else if(chkC[i].equals("前方路段有禁止進入區域"))
             this.setGTRUE();
         else if(chkC[i].equals("前方路段禁止左轉"))
             this.setHTRUE();
         else if(chkC[i].equals("前方路段禁止右轉"))
             this.setITRUE();
         else if(chkC[i].equals("前方路段禁止轉彎"))
             this.setJTRUE();
         else if(chkC[i].equals("前方300有禁止進入區域"))
             this.setKTRUE();
         else if(chkC[i].equals("前方300禁止左轉"))
             this.setLTRUE();
         else if(chkC[i].equals("前方300禁止右彎"))
             this.setMTRUE();
         else if(chkC[i].equals("前方300禁止轉彎"))
             this.setNTRUE();
     }
 }
}
