/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatodatabase;

/**
 *
 * @author FantasyStar
 */
import java.io.BufferedReader;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author FantasyStar
 */

class Violiant_Check
{
    private double Pre_longitude,Pre_latitude;
    private int[] limitin_flag,left_flag;
    private double[][] Pre_distance;
    public Violiant_Check()
    {
        Pre_longitude = Pre_latitude = 0;
        Pre_distance = new double[2][];
    }
    
    private boolean String_Compare(String majorStr,String minorStr)
    {
        for(int i = 0;i<majorStr.length();i++)
        {
            if(minorStr.length()<i)
                return false;
            if(majorStr.charAt(i)!=minorStr.charAt(i))
                return false;
        }
        return true;
    }
    
    public void clean_flag(int limit,int left)
    {
        limitin_flag = null;
        left_flag = null;
        Pre_distance[0] = null;
        Pre_distance[1] = null;

        System.gc();

        if(limit>0){
            limitin_flag = new int[limit];
            Pre_distance[0] = new double[limit];
            for(int i = 0;i<limit;i++){
                limitin_flag[i] = 0;
                Pre_distance[0][i] = 0;
            }
        }
        else
        {
            limitin_flag = new int[1];
            limitin_flag[0] = -1;
        }
        if(left>0){
            left_flag = new int[left];
            Pre_distance[1] = new double [left];
            for(int i = 0;i<left;i++){
                left_flag[i] = 0;
                Pre_distance[1][i] = 0;
            }
        }
        else
        {
            left_flag = new int[1];
            left_flag[0] = -1;
        }
    }
    
    public int Getleftflag(int num)
    {
        if(left_flag[0] == -1)
            return 3;
        return left_flag[num];
    }
    
    public int Getlimitinflag(int num)
    {
        if(limitin_flag[0] == -1)
            return 3;
        return limitin_flag[num];
    }
    
    public double longitude_length(double x)
    {
     return java.lang.Math.abs(x)/0.00000900901*0.9063;
    }

    public double latitude_length(double y)
    {
     return java.lang.Math.abs(y)/0.00000900901;
    }
    
    public double GetLastLon()
    {
        return Pre_longitude;
    }
    
    public double GetLastLat()
    {
        return Pre_latitude;
    }

    public void change(double Lon,double Lat)
    {
        Pre_longitude = Lon;
        Pre_latitude = Lat;
    }
    
    public int CountSpeed()//未實做
    {
        return 1;
    }
    
    public int OverSpeed(double mySpeed,double LimitSpeed)
    {
        if(LimitSpeed-10<mySpeed && mySpeed<LimitSpeed)
            return 1;
        else if(mySpeed>LimitSpeed)
            return 2;
        return 0;
    }
    
    public int reverse_direction(double Lon,double Lat,double x,double y)
    {
        //System.out.println( Pre_longitude+", "+Pre_latitude);
        if(Pre_longitude == 0&&Pre_latitude == 0)
        {
            Pre_longitude = Lon;
            Pre_latitude = Lat;
            return 0;
        }
       double newX = longitude_length(Lon)-longitude_length(Pre_longitude);
        double newY = latitude_length(Lat)-latitude_length(Pre_latitude);
        /*System.out.println(newX+","+newY);
        System.out.println(x+","+y);*/
        double ale = java.lang.Math.sqrt(java.lang.Math.pow(longitude_length(x),2)+java.lang.Math.pow(latitude_length(y),2));
        double ble = java.lang.Math.sqrt(java.lang.Math.pow(newX,2)+java.lang.Math.pow(newY,2));
        double newXO = longitude_length(x),newYO = latitude_length(y);
        if(x<0)
            newXO = -newXO;
        if(y<0)
            newYO = -newYO;
        System.out.println((newXO*newX+newYO*newY)/(ale*ble));
        
        if((newXO*newX+newYO*newY)/(ale*ble)<-0.3)
            return 1;
        return 0;
    }

    public int Illegal_Turn_Left(int reverseflag,int status,int now_Block,int Turn_Block,String now_street_name,String Turn_street_name)
    {
        int is_reverse = reverseflag;
        System.out.println(is_reverse);
        if(String_Compare(Turn_street_name,now_street_name)&&now_Block == Turn_Block)
        {
            if(is_reverse == 0)
            {
                if(status == 1)
                    return 1;
                else
                    return 2;
            }
            else
            {
                if(status == 1)
                    return 2;
                else
                    return 1;
            }
        }
        return 0;
    }
    public int distance(double Lon,double Lat,double x,double y,int status,int num,double xD,double yD,int T)
    {
     double dis = java.lang.Math.sqrt(java.lang.Math.pow(longitude_length(Lon)-longitude_length(x),2)+java.lang.Math.pow(latitude_length(Lat)-latitude_length(y),2));
     System.out.println(dis+" "+Pre_distance[status-1][num]);
     if(Pre_distance[status-1][num] == 0||dis>=Pre_distance[status-1][num])
     {
        Pre_distance[status-1][num] = dis;
        //System.out.println("debug");
        return 0;
     }
     Pre_distance[status-1][num] = dis;
     
     if(status == 1)
     {
        if(dis<=500&&dis>100&&limitin_flag[num] == 0)
        {
            limitin_flag[num]++;
            return 1;
        }
        else if(dis<50&&limitin_flag[num] < 2)
        {
            limitin_flag[num]++;
            return 1;
        }
     }
     else if(status == 2)
     {
        int is_inverse = reverse_direction(Lon,Lat,xD,yD);
        System.out.println(is_inverse);
        if(dis<=500&&dis>100&&left_flag[num] == 0)
        {
            left_flag[num]++;
            if(T == 1&&is_inverse == 0||T == 2&&is_inverse == 1)
                return 1;
            else if(T == 1&&is_inverse == 1||T == 2&&is_inverse == 0)
                return 2;
            else
            {
                return 3;  
            }
        }
        else if(dis<50&&left_flag[num] < 2)
        {
            left_flag[num]++;
            if(T == 1&&is_inverse == 0||T == 2&&is_inverse == 1)
                return 1;
            else if(T == 1&&is_inverse == 1||T == 2&&is_inverse == 0)
                return 2;
            else
                return 3;
        } 
     }
     return 0;
    }
}

public class Violiant_List extends Violiant_Check
{
    private String File_Path_List = "JSONc.txt";
    
    private String[] Street_Name;
    private double[] Street_Limit_Speed;
    
    private int[][] Street_Mode;
    private int[] street_block_len;
    private double[][][] Block_Start;
    private double[][][] Block_End;
    
    private double[][] StreetX,StreetY;
    
    private int[][] streetTurnL;
    private int[][][] StreetTurn;
    private int[][][] street_Turn_block;
    private String[][][] street_Turn_road;
    
    private int[][][] limitinS,leftS;
    private double[][][] street_limitin_lon,street_limitin_lat;
    private double[][][] street_left_lon,street_left_lat;
    private int[][] limitS_len,leftS_len;
    
    private int List_Length;
    
    private String now_road;
    private int now_block;
    
    private boolean flag;
    private int TurnL,TurnRe;
    private int[] Turn;
    private String[] Turn_road;
    private int[] Turn_block;
    private int ExtraTurn;
    
    private ConnectDATA Database = null;
    
    
    public Violiant_List(ConnectDATA newD) 
    {
        super();
        Database = newD;
        
        now_road = "2345";
        now_block = -1;
        
        flag = true;
        ExtraTurn = 0;
        
        //Datainput();

        JSONanlize();
    }
    
    
    
    private void Datainput()
    {
        int i = -1;
        int B = 0;
        int k = 0;
        try
        {
            String a  = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(File_Path_List),"big5"));
            while((a = br.readLine()) != null)
            {
                if(flag)
                {
                    List_Length = Integer.parseInt(a);
                    Street_Name = new String[List_Length];
                    Street_Limit_Speed = new double[List_Length];
                    street_block_len = new int[List_Length];

                    Block_Start = new double[List_Length][][];
                    Block_End = new double[List_Length][][];
                    Street_Mode = new int[List_Length][];

                    StreetX = new double[List_Length][];
                    StreetY = new double[List_Length][];
        
                    StreetTurn = new int[List_Length][][];
                    streetTurnL = new int[List_Length][];
                    street_Turn_road = new String[List_Length][][];
                    street_Turn_block = new int[List_Length][][];
        
                    limitinS = new int[List_Length][][];
                    leftS = new int[List_Length][][];
                    street_limitin_lon = new double[List_Length][][];
                    street_limitin_lat = new double[List_Length][][];
                    street_left_lon = new double[List_Length][][];
                    street_left_lat = new double[List_Length][][];
                    limitS_len = new int[List_Length][];
                    leftS_len = new int[List_Length][];
                    flag = false;
                }
                else
                {
                    String[] chk = a.split(",");
                    if(chk.length <= 3)
                    {
                        i++;
                        //System.out.println(i);
                        Street_Name[i] = chk[0];
                        Street_Limit_Speed[i] = Double.parseDouble(chk[1]);
                        street_block_len[i] = Integer.parseInt(chk[2]);
                        B = Integer.parseInt(chk[2]);
                        k = 0;
                        Street_Mode[i] = new int[B];
                        Block_Start[i] = new double[B][2];
                        Block_End[i] = new double[B][2];
                
                        StreetX[i] = new double[B];
                        StreetY[i] = new double[B];
                        
                        streetTurnL[i] = new int[B];
                        StreetTurn[i] = new int[B][];
                        street_Turn_block[i] = new int [B][];
                        street_Turn_road[i] = new String [B][];

                        limitinS[i] = new int [B][];
                        leftS[i] = new int [B][];
                        street_limitin_lon[i] = new double[B][];
                        street_limitin_lat[i] = new double[B][];
                        street_left_lon[i] = new double[B][];
                        street_left_lat[i] = new double[B][];
                        limitS_len[i] = new int[B];
                        leftS_len[i] = new int[B];
                    }
                    else
                    {
                        if(k<B)
                        {
                            int regester = 9;
                            Block_Start[i][k][0] = Double.parseDouble(chk[0]);
                            Block_Start[i][k][1] = Double.parseDouble(chk[1]);
                            Block_End[i][k][0] = Double.parseDouble(chk[2]);
                            Block_End[i][k][1] = Double.parseDouble(chk[3]);
                            Street_Mode[i][k] = Integer.parseInt(chk[4]);
                            if(Integer.parseInt(chk[5])>0){
                                StreetX[i][k] = Double.parseDouble(chk[regester]);
                                StreetY[i][k] = Double.parseDouble(chk[regester+1]);
                                //System.out.println(regester);
                                regester+=2;
                            }
                            if(Integer.parseInt(chk[6])>0){
                                streetTurnL[i][k] = Integer.parseInt(chk[6]);
                                StreetTurn[i][k] = new int[Integer.parseInt(chk[6])];
                                street_Turn_block[i][k] = new int[Integer.parseInt(chk[6])];
                                street_Turn_road[i][k] = new String[Integer.parseInt(chk[6])];
                                for(int z = 0;z<Integer.parseInt(chk[6]);z++)
                                {
                                    StreetTurn[i][k][z] = Integer.parseInt(chk[regester]);
                                    street_Turn_block[i][k][z] = Integer.parseInt(chk[regester+1]);
                                    street_Turn_road[i][k][z] = chk[regester+2];
                                    //System.out.println(regester);
                                    regester+=3;
                                }
                            }
                             if(Integer.parseInt(chk[7])>0){
                                limitS_len[i][k] = Integer.parseInt(chk[7]);
                                limitinS[i][k] = new int[Integer.parseInt(chk[7])];
                                street_limitin_lon[i][k] = new double[Integer.parseInt(chk[7])];
                                street_limitin_lat[i][k] = new double[Integer.parseInt(chk[7])];
                                for(int z = 0;z<Integer.parseInt(chk[7]);z++)
                                {
                                    limitinS[i][k][z] = Integer.parseInt(chk[regester]);
                                    street_limitin_lon[i][k][z] = Double.parseDouble(chk[regester+1]);
                                    street_limitin_lat[i][k][z] = Double.parseDouble(chk[regester+2]);
                                    //System.out.println(regester);
                                    regester+=3;
                                }
                            }
                              if(Integer.parseInt(chk[8])>0){
                                leftS_len[i][k] = Integer.parseInt(chk[8]);
                                leftS[i][k] = new int[Integer.parseInt(chk[8])];
                                street_left_lon[i][k] = new double[Integer.parseInt(chk[8])];
                                street_left_lat[i][k] = new double[Integer.parseInt(chk[8])];
                                for(int z = 0;z<Integer.parseInt(chk[8]);z++)
                                {
                                    leftS[i][k][z] = Integer.parseInt(chk[regester]);
                                    street_left_lon[i][k][z] = Double.parseDouble(chk[regester+1]);
                                    street_left_lat[i][k][z] = Double.parseDouble(chk[regester+2]);
                                    //System.out.println(regester);
                                    regester+=3;
                                }
                            }
                            k++;
                        }
                    }
                }
            }
        }
        catch(IOException e) {System.out.println(e);}
    }
    
    private void JSONanlize()
    {
        String line = "";
        try
        {
            String a  = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(File_Path_List),"big5"));
            while((a = br.readLine()) != null)
                line += a;
        }
        catch(IOException e) {System.out.println(e);}
        //System.out.println(line);
        try
        {
            JSONArray j = new JSONObject(line).getJSONArray("Street");
            List_Length = j.length();

            Street_Name = new String[j.length()];
            Street_Limit_Speed = new double[j.length()];
            street_block_len = new int[j.length()];

            Block_Start = new double[j.length()][][];
            Block_End = new double[j.length()][][];
            Street_Mode = new int[j.length()][];

            StreetX = new double[j.length()][];
            StreetY = new double[j.length()][];
        
            streetTurnL = new int[j.length()][];
            StreetTurn = new int[j.length()][][];
            street_Turn_road = new String[j.length()][][];
            street_Turn_block = new int[j.length()][][];
        
            limitS_len = new int[j.length()][];
            leftS_len = new int[j.length()][];
            limitinS = new int[j.length()][][];
            leftS = new int[j.length()][][];
            street_limitin_lon = new double[j.length()][][];
            street_limitin_lat = new double[j.length()][][];
            street_left_lon = new double[j.length()][][];
            street_left_lat = new double[j.length()][][];
        
            for(int i = 0;i<j.length();i++)
            {
                JSONObject jj = (JSONObject)j.get(i);
                Street_Name[i] = jj.getString(("StreetName"));
                Street_Limit_Speed[i] = jj.getDouble("LimitSpeed");

                JSONArray ja = jj.getJSONArray("StreetBlock");
                street_block_len[i] = ja.length();
                
                Street_Mode[i] = new int[ja.length()];
                Block_Start[i] = new double[ja.length()][2];
                Block_End[i] = new double[ja.length()][2];
                
                StreetX[i] = new double[ja.length()];
                StreetY[i] = new double[ja.length()];

                streetTurnL[i] = new int[ja.length()];
                StreetTurn[i] = new int[ja.length()][];
                street_Turn_block[i] = new int [ja.length()][];
                street_Turn_road[i] = new String [ja.length()][];

                limitS_len[i] = new int[ja.length()];
                leftS_len[i] = new int[ja.length()];
                limitinS[i] = new int [ja.length()][];
                leftS[i] = new int [ja.length()][];
                street_limitin_lon[i] = new double[ja.length()][];
                street_limitin_lat[i] = new double[ja.length()][];
                street_left_lon[i] = new double[ja.length()][];
                street_left_lat[i] = new double[ja.length()][];
   
                for(int k = 0;k<ja.length();k++)
                {
                    JSONObject jjj = (JSONObject)ja.get(k);
                    Block_Start[i][k][0] = jjj.getJSONArray("Sll").getDouble(0);
                    Block_Start[i][k][1] = jjj.getJSONArray("Sll").getDouble(1);
                    Block_End[i][k][0] = jjj.getJSONArray("Ell").getDouble(0);
                    Block_End[i][k][1] = jjj.getJSONArray("Ell").getDouble(1);
                    Street_Mode[i][k] = jjj.getInt("VioliantStatus");

                    if(jjj.has("Dir"))
                    {
                        StreetX[i][k] = jjj.getJSONArray("Dir").getDouble(0);
                        StreetY[i][k] = jjj.getJSONArray("Dir").getDouble(1);
                    }
                    else
                    {
                        StreetX[i][k] = -2;
                        StreetY[i][k] = -2;
                    }

                    if(jjj.has("VioStreetName"))
                    {
                        streetTurnL[i][k] = jjj.getJSONArray("VioStreetName").length()/3;
                        StreetTurn[i][k] = new int[jjj.getJSONArray("VioStreetName").length()/3];
                        street_Turn_block[i][k] = new int[jjj.getJSONArray("VioStreetName").length()/3];
                        street_Turn_road[i][k] = new String[jjj.getJSONArray("VioStreetName").length()/3];
                        for(int l = 0;(l<jjj.getJSONArray("VioStreetName").length()/3);l++){
                            StreetTurn[i][k][l] = jjj.getJSONArray("VioStreetName").getInt(3*l);
                            street_Turn_block[i][k][l] = jjj.getJSONArray("VioStreetName").getInt(3*l+1);
                            street_Turn_road[i][k][l] = jjj.getJSONArray("VioStreetName").getString(3*l+2);
                        }   
                    }

                    if(jjj.has("LimitStreet"))
                    {
                        limitS_len[i][k] = jjj.getJSONArray("LimitStreet").length()/3;
                        limitinS[i][k] = new int[jjj.getJSONArray("LimitStreet").length()/3];
                        street_limitin_lon[i][k] = new double[jjj.getJSONArray("LimitStreet").length()/3];
                        street_limitin_lat[i][k] = new double[jjj.getJSONArray("LimitStreet").length()/3];
                        for(int l = 0;l<(jjj.getJSONArray("LimitStreet").length()/3);l++){
                            limitinS[i][k][l] = jjj.getJSONArray("LimitStreet").getInt(3*l);
                            street_limitin_lon[i][k][l] = jjj.getJSONArray("LimitStreet").getDouble(3*l+1);
                            street_limitin_lat[i][k][l] = jjj.getJSONArray("LimitStreet").getDouble(3*l+2);
                        }   
                    }
                    
                    if(jjj.has("TurnStreet"))
                    {
                        leftS_len[i][k] =  jjj.getJSONArray("TurnStreet").length()/3;
                        leftS[i][k] = new int[jjj.getJSONArray("TurnStreet").length()/3];
                        street_left_lon[i][k] = new double[jjj.getJSONArray("TurnStreet").length()/3];
                        street_left_lat[i][k] = new double[jjj.getJSONArray("TurnStreet").length()/3];
                        for(int l = 0;l<(jjj.getJSONArray("TurnStreet").length()/3);l++){
                            leftS[i][k][l] = jjj.getJSONArray("TurnStreet").getInt(3*l);
                            street_left_lon[i][k][l] = jjj.getJSONArray("TurnStreet").getDouble(3*l+1);
                            street_left_lat[i][k][l] = jjj.getJSONArray("TurnStreet").getDouble(3*l+2);
                        }   
                    }
                }
            }
        }catch(JSONException e) {System.out.println(e);}
    }
    
    public int Block_check(int road,double Lon,double Lat)
    {
        double minl = 10000;
        int out = 0;
        for(int i = 0;i<street_block_len[road];i++)
        {
            //計算內積
            double newx1 = super.longitude_length(Lon)-super.longitude_length(Block_Start[road][i][0]),newx2 = super.longitude_length(Lon)-super.longitude_length(Block_End[road][i][0]);
            double newy1 = super.latitude_length(Lat)-super.latitude_length(Block_Start[road][i][1]),newy2 = super.latitude_length(Lat)-super.latitude_length(Block_End[road][i][1]);
            double len1 = java.lang.Math.sqrt(java.lang.Math.pow(newx1, 2)+java.lang.Math.pow(newy1, 2));
            double len2 = java.lang.Math.sqrt(java.lang.Math.pow(newx2, 2)+java.lang.Math.pow(newy2, 2));
            if((newx1*newx2+newy1*newy2)/(len1*len2)<0)
                return i;
            //計算與BLOCK之間的距離
            
            if(minl>len1)
            {
                minl = len1;
                out = i;
            }
            if(minl>len2)
            {
                minl = len2;
                out = i;
            }
        }
        return out;
    }
    
    public String Get_Street_Name(double longitude,double latitude)
    {
        
        String StreetName = null;
        try {
           
            String StrURL = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+longitude+","+latitude+"&sensor=false&language=zh-TW";
            URL url = new URL(StrURL);
            URLConnection connection = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"big5"));
            while ((line = reader.readLine()) != null) {builder.append(line);}
            JSONObject json = new JSONObject(builder.toString()); //轉換json格式
            JSONArray ja = json.getJSONArray("results");//取得json的Array物件
            StreetName = ja.getJSONObject(0).getString("formatted_address");
            /* for (int i = 0; i < ja.length(); i++) {
                  System.out.print("地址：" + ja.getJSONObject(i).getString("formatted_address") + " ");
                  System.out.print("緯度：" + ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat") + " ");
                  System.out.print("經度：" + ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                  System.out.println("");
            }*/
        } 
        catch (JSONException ex) {
            System.out.println(ex);
        } 
         catch (IOException ex) {
           System.out.println(ex);
        }
        return StreetName;
    }
    
    private boolean String_Compare(String majorStr,String minorStr)
    {
        for(int i = 0;i<majorStr.length();i++)
        {
            
            if(minorStr.length()<i)
                return false;
            if(majorStr.charAt(i)!=minorStr.charAt(i))
                return false;
        }
        return true;
    }
    
    public String VioliantCheck(String License,double longitude,double latitude,double speed,String Street_N,String Date_,String Time_)
    {
        int[] Vio = new int[6];
        String name =Street_N,output ="";
        if(name == null)
        {
            return "";
        }
        //找出此位置是否在我們的範圍清單內
        int i  = 0;
        for(int j = 0;j<List_Length;j++)
        {
            //System.out.println("*"+Street_Name[i]+"\n"+name);
            if(name.contains(Street_Name[j])){
                i = j;
                j = List_Length;
                break;
            }
            if(j == List_Length-1){
                super.change(longitude,latitude);
                return "";
            }
        }
        int B = Block_check(i,longitude,latitude);
        if(!now_road.contains(Street_Name[i])||B != now_block)
        {
            now_road = Street_Name[i];
            now_block = B;
            super.clean_flag(limitS_len[i][B],leftS_len[i][B]);
            if(flag)
                ExtraTurn = 2;
        }
        System.out.println("road: "+now_road+",Block: "+now_block);
        //判斷超速
        Vio[0] = super.OverSpeed(speed,Street_Limit_Speed[i]);
        //依照每條街的額外違規事項進行判斷
        Vio[1] = Vio[2] = Vio[3] = Vio[4] = Vio[5] = 0;
        //街道產生變化時依照上一條街是否有違規轉彎之有無來判斷是否要進行左右轉違規判斷
        if(ExtraTurn>0)
        {
            for(int z = 0;z<TurnL;z++)
                Vio[3] = super.Illegal_Turn_Left(TurnRe,Turn[z],now_block,Turn_block[z],now_road,Turn_road[z]);
            flag = false;
            if(Vio[3]>0)
                ExtraTurn = 0;
            ExtraTurn--;
        }
        switch(Street_Mode[i][B])
        {
            //0->沒有特殊違規事件需判斷
            case 0:
                break;
            //1->判斷逆向
            /*case 1:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i][B],StreetY[i][B]);
                break;*/
            //2->判斷進入禁止區域
            case 2:
                Vio[2] = 1;
                break;
            //3->判斷違規左轉
            case 3:
                flag = true;
                for(int z = 0;z<leftS_len[i][B]&&Getleftflag(z)<3;z++)
                {
                    if(Vio[5]>0)
                        break;
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][B][z], street_left_lat[i][B][z],2,z,StreetX[i][B],StreetY[i][B],leftS[i][B][z]);
                }
                break;
            //4->判斷逆向+進入禁止區域
            /*case 4:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i][B],StreetY[i][B]);
                Vio[2] = 1;
               // Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i], street_limitin_lat[i]);
                break;
            //5->判斷進入禁止區域+違規左轉
            case 5:
                Vio[2] = 1;
                flag = true;
               // Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i], street_limitin_lat[i]);
                for(int z = 0;z<leftS_len[i][B]&&Getleftflag(z)<3;z++)
                {
                    if(Vio[5]>0)
                        break;
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][B][z], street_left_lat[i][B][z],2,z,StreetX[i][B],StreetY[i][B],leftS[i][B][z]);
                }
                break;
            //6->判斷逆向+違規左轉
            case 6:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i][B],StreetY[i][B]);
                flag = true;
                for(int z = 0;z<leftS_len[i][B]&&Getleftflag(z)<3;z++)
                {
                    if(Vio[5]>0)
                        break;
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][B][z], street_left_lat[i][B][z],2,z,StreetX[i][B],StreetY[i][B],leftS[i][B][z]);
                }
                break;
            //7->判斷逆向+進入禁止區域+違規左轉
            case 7:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i][B],StreetY[i][B]);
                Vio[2] = 1;
                flag = true;
               // Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i], street_limitin_lat[i]);
                for(int z = 0;z<leftS_len[i][B]&&Getleftflag(z)<3;z++)
                {
                    if(Vio[5]>0)
                        break;
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][B][z], street_left_lat[i][B][z],2,z,StreetX[i][B],StreetY[i][B],leftS[i][B][z]);
                }
                break;
            default:break;*/
        }
        for(int z = 0;z<limitS_len[i][B]&&Getlimitinflag(z)<3;z++)
        {
            if(Vio[4]>0)
                break;
            Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i][B][z], street_limitin_lat[i][B][z],1,z,0,0,limitinS[i][B][z]);
        }  
        if(flag)
        {
            TurnRe = super.reverse_direction(longitude, latitude,StreetX[i][B],StreetY[i][B]);
            TurnL = streetTurnL[i][B];
            Turn = StreetTurn[i][B];
            Turn_road = street_Turn_road[i][B];
            Turn_block = street_Turn_block[i][B];
        }
        Violiant_To_Database(Vio,License,name,longitude,latitude,speed, Date_, Time_); 
        output = Violiant_List_Maker(Vio);
        super.change(longitude,latitude);
        return output;
    }
    
    private String Violiant_List_Maker(int Vio[])
    {
        //System.out.println("1");
        String output = "";
        switch(Vio[0])
        {
            case 0:output += "未超速,";break;
            case 1:output += "即將超速,";break;
            case 2:output += "超速,";break;
        }
        /*if(Vio[1] == 1)
            output += "逆向行駛,";*/
        if(Vio[2] == 1)
            output += "禁行區域,";
        if(Vio[3] == 1)
            output += "違規左轉,";
        else if(Vio[3] == 2)
            output += "違規右轉,";
        if(Vio[4] == 1)
            output += "前方路段有禁止進入區域,";
        switch(Vio[5])
        {
            case 1:output += "前方路段禁止左轉,";break;
            case 2:output += "前方路段禁止右轉,";break;
            case 3:output += "前方路段禁止轉彎,";break;
        }
        return output;
    }
    
    private void Violiant_To_Database(int[] vio,String License,String Street_N,double longitude,double latitude,double speed,String DATE_,String Time_)
    {
        if(vio[0] == 2)
             Database.insertViolation(License,(double)longitude, (double)latitude,
                 (double)speed, Street_N,"超速", DATE_,Time_);
        if(vio[1] == 1)
             Database.insertViolation(License,(double)longitude, (double)latitude, 
                 (double)speed, Street_N,"逆向行駛", DATE_,Time_);
        if(vio[2] == 1)
             Database.insertViolation(License,(double)longitude, (double)latitude, 
                 (double)speed, Street_N,"禁止區域",DATE_,Time_);
        if(vio[3] == 1)
             Database.insertViolation(License,(double)longitude, (double)latitude,
                 (double)speed, Street_N,"違規左轉",DATE_,Time_);
        if(vio[3] == 2)
             Database.insertViolation(License,(double)longitude,(double)latitude, 
                 (double)speed, Street_N,"違規右轉",DATE_,Time_);
    }
    
    public void Print_List()
    {
       for(int i = 0;i<List_Length;i++)
        {
            System.out.println(Street_Name[i]+" "+Street_Limit_Speed[i]);
            System.out.println(street_block_len[i]);
            for(int k = 0;k<street_block_len[i];k++)
            {
                System.out.println("Block"+k+":");
                System.out.println("Start Lon,Lat:\n"+Block_Start[i][k][0]+" "+Block_Start[i][k][1]);
                System.out.println("End Lon,Lat:\n"+Block_End[i][k][0]+" "+Block_End[i][k][1]);
                System.out.println("VioliantStaus: "+Street_Mode[i][k]);
                if(StreetX[i][k] != -2&&StreetY[i][k] != -2)
                    System.out.println("DirX"+StreetX[i][k]+" DirY"+StreetY[i][k]);
                if(StreetTurn[i][k] != null)
                {
                    for(int z = 0;z<StreetTurn[i][k].length;z++)
                    {
                        if(StreetTurn[i][k][z] == 1)
                            System.out.print("左,");
                        else
                            System.out.print("右,");
                        System.out.println("Road: "+street_Turn_road[i][k][z]+",Block: "+street_Turn_block[i][k][z]);
                    }
                }
                if(limitinS[i][k] != null)
                {
                    for(int z = 0;z<limitinS[i][k].length;z++)
                    {
                        System.out.println(limitinS[i][k][z]+"\n"+street_limitin_lon[i][k][z]+", "+street_limitin_lat[i][k][z]);
                    }
                }
                if(leftS[i][k] != null)
                {
                    for(int z = 0;z<leftS[i][k].length;z++)
                    {
                        System.out.println(leftS[i][k][z]+"\n"+street_left_lon[i][k][z]+", "+street_left_lat[i][k][z]);
                    }
                }
            }
        }
    }
    
    
}
