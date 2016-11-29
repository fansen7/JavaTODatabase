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
    public Violiant_Check()
    {
        Pre_longitude = Pre_latitude = 0;
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
        
        System.gc();

        if(limit>0){
            limitin_flag = new int[limit];
            for(int i = 0;i<limit;i++)
                limitin_flag[i] = 0;

        }
        else
        {
            limitin_flag = new int[1];
            limitin_flag[0] = -1;
        }
        if(left>0){
            left_flag = new int[left];
            for(int i = 0;i<left;i++)
                left_flag[i] = 0;
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
        double ale = java.lang.Math.sqrt(java.lang.Math.pow(longitude_length(x),2)+java.lang.Math.pow(latitude_length(y),2));
        double ble = java.lang.Math.sqrt(java.lang.Math.pow(newX,2)+java.lang.Math.pow(newY,2));
        System.out.println((longitude_length(x)*newX+latitude_length(y)*newY)/(ale*ble));
       
        if((longitude_length(x)*newX+latitude_length(y)*newY)/(ale*ble)<-0.3)
            return 1;
        return 0;
    }

    public int Illegal_Turn_Left(double Lon,double Lat,double x,double y,int status,double base,String now_street_name,String left_street_name)//未實作
    {
         if(Pre_longitude == 0&&Pre_latitude == 0)
        {
            Pre_longitude = Lon;
            Pre_latitude = Lat;
            return 0;
        }
        if(reverse_direction(Lon,Lat,x,y) == 1)
        {
            return 0;
        }
         switch(status)
         {
             case 1:
                 if((Lon-base)>=0.00005)
                { 
                    return 1;
                }
                 break;
             case 2:
                 if((base-Lon)>=0.00005)
                { 
                    return 1;
                }
                 break;
                 case 3:
                 if((Lat-base)>=0.00005)
                { 
                    return 1;
                }
                 break;
                 case 4:
                 if((base-Lat)>=0.00005)
                { 
                    return 1;
                }
                 break;
         }   
         if(String_Compare(left_street_name,now_street_name))
         {
             return 1;
         }
         return -1;
    }
    public int distance(double Lon,double Lat,double x,double y,int status,int num)
    {
     double dis = java.lang.Math.sqrt(java.lang.Math.pow(longitude_length(Lon)-longitude_length(x),2)+java.lang.Math.pow(latitude_length(Lat)-latitude_length(y),2));
     System.out.println(dis+" "+status);
     if(status == 1)
     {
        if(dis<=500&&dis>100&&limitin_flag[num] == 0)
        {
            limitin_flag[num]++;
            return 1;
        }
        else if(dis<50&&limitin_flag[num] == 1)
        {
            limitin_flag[num]++;
            return 2;
        }
     }
     else if(status == 2)
     {
        if(dis<=500&&dis>100&&left_flag[num] == 0)
        {
            left_flag[num]++;
            return 1;
        }
        else if(dis<50&&left_flag[num] == 1)
        {
            left_flag[num]++;
            return 2;
        } 
     }
     return 0;
    }
}

public class Violiant_List extends Violiant_Check
{
    private String File_Path_List = "CityStreetList.txt";
    private String[] Street_Name;
    private int[] Street_Limit_Speed,Street_Mode;
    private double[] StreetX,StreetY;
    private int[] StreetStatus;
    private double[]  street_left_coordinate;
    private String[] street_left_road_name;
    private int List_Length;
    private boolean flag;
    private String left_road;
    private ConnectDATA Database = null;
    private int[] limitinlen,leftlen;
    private double[][] street_limitin_lon,street_limitin_lat;
    private double[][] street_left_lon,street_left_lat;
    private String now_road;
    
    
    public Violiant_List(ConnectDATA newD) 
    {
     
        super();
        Street_Name = new String[100];
        Street_Limit_Speed = new int[100];
        Street_Mode = new int[100];
        StreetX = new double[100];
        StreetY = new double[100];
        StreetStatus = new int[100];
        street_left_coordinate = new double[100];
        street_left_road_name = new String[100];
        limitinlen = new int [100];
        leftlen = new int [100];
        street_limitin_lon = new double[100][];
        street_limitin_lat = new double[100][];
        street_left_lon = new double[100][];
        street_left_lat = new double[100][];
        Database = newD;
        now_road = " ";
        flag = false;

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(File_Path_List),"big5"));
            String line;
            for(List_Length = 0;(line=br.readLine()) != null;List_Length++)
            {
                String[] chk = line.split(",");
                if(chk.length >= 10)
                {
                    int length = 10;
                    //System.out.println(chk[0]+" "+chk[1]+" "+chk[2]+"\n");
                    Street_Name[List_Length] = chk[0];
                    Street_Limit_Speed[List_Length] = Integer.parseInt(chk[1]);
                    Street_Mode[List_Length] = Integer.parseInt(chk[2]);
                    StreetX[List_Length] = Double.parseDouble(chk[3]);
                    StreetY[List_Length] = Double.parseDouble(chk[4]);
                    StreetStatus[List_Length] = Integer.parseInt(chk[5]);
                    street_left_coordinate[List_Length] = Double.parseDouble(chk[6]);
                    street_left_road_name[List_Length] = chk[7];
                    limitinlen[List_Length] = Integer.parseInt(chk[8]);
                    leftlen[List_Length] = Integer.parseInt(chk[9]);
                    if(limitinlen[List_Length]>0)
                    {
                        street_limitin_lon[List_Length] = new double[Integer.parseInt(chk[8])];
                        street_limitin_lat[List_Length] = new double[Integer.parseInt(chk[8])];
                        for(int i = 0;i<Integer.parseInt(chk[8]);i++)
                        {
                            street_limitin_lon[List_Length][i] = Double.parseDouble(chk[length++]);
                            street_limitin_lat[List_Length][i] = Double.parseDouble(chk[length++]);
                            if(length>chk.length)
                                break;
                        }
                    }
                    if(leftlen[List_Length]>0)
                    {
                        street_left_lon[List_Length] = new double[Integer.parseInt(chk[9])];
                        street_left_lat[List_Length] = new double[Integer.parseInt(chk[9])];
                        for(int i = 0;i<Integer.parseInt(chk[9]);i++)
                        {
                            street_left_lon[List_Length][i] = Double.parseDouble(chk[length++]);
                            street_left_lat[List_Length][i] = Double.parseDouble(chk[length++]);
                            if(length>chk.length)
                                break;
                        }
                    }
                }
            }
        }
        catch(IOException e) {System.out.println(e);}
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
        //找出此位置是否在我們的範圍清單內
        int i ;
        for(i = 0;i<List_Length;i++)
        {
            //System.out.println("*"+Street_Name[i]+"\n"+name);
            if(String_Compare(Street_Name[i],name))
                break;
            if(i == List_Length-1){
                super.change(longitude,latitude);
                return "";
            }
        }
        if(!String_Compare(Street_Name[i],now_road))
        {
            now_road = Street_Name[i];
            super.clean_flag(limitinlen[i],leftlen[i]);
        }
        //判斷超速
        Vio[0] = super.OverSpeed(speed,Street_Limit_Speed[i]);
        //依照每條街的額外違規事項進行判斷
        Vio[1] = Vio[2] = Vio[3] = Vio[4] = Vio[5] = 0;
        switch(Street_Mode[i])
        {
            //0->沒有特殊違規事件需判斷
            case 0:
                break;
            //1->判斷逆向
            case 1:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i],StreetY[i]);
                break;
            //2->判斷進入禁止區域
            case 2:
                Vio[2] = 1;
                break;
            //3->判斷違規左轉
            case 3:
                Vio[3] = super.Illegal_Turn_Left(longitude,latitude,StreetX[i],StreetY[i],StreetStatus[i],street_left_coordinate[i],Street_Name[i],street_left_road_name[i]);
                for(int z = 0;z<leftlen[i]&&Getleftflag(z)<3;i++)
                {
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][z], street_left_lat[i][z],2,z);
                }
                break;
            //4->判斷逆向+進入禁止區域
            case 4:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i],StreetY[i]);
                Vio[2] = 1;
               // Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i], street_limitin_lat[i]);
                break;
            //5->判斷進入禁止區域+違規左轉
            case 5:
                Vio[2] = 1;
                Vio[3] = super.Illegal_Turn_Left(longitude,latitude,StreetX[i],StreetY[i],StreetStatus[i],street_left_coordinate[i],Street_Name[i],street_left_road_name[i]);
               // Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i], street_limitin_lat[i]);
                for(int z = 0;z<leftlen[i]&&Getleftflag(z)<3;i++)
                {
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][z], street_left_lat[i][z],2,z);
                }
                break;
            //6->判斷逆向+違規左轉
            case 6:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i],StreetY[i]);
                Vio[3] = super.Illegal_Turn_Left(longitude,latitude,StreetX[i],StreetY[i],StreetStatus[i],street_left_coordinate[i],Street_Name[i],street_left_road_name[i]);
                for(int z = 0;z<leftlen[i]&&Getleftflag(z)<3;i++)
                {
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][z], street_left_lat[i][z],2,z);
                }
                break;
            //7->判斷逆向+進入禁止區域+違規左轉
            case 7:
                Vio[1] = super.reverse_direction(longitude,latitude,StreetX[i],StreetY[i]);
                Vio[2] = 1;
                Vio[3] = super.Illegal_Turn_Left(longitude,latitude,StreetX[i],StreetY[i],StreetStatus[i],street_left_coordinate[i],Street_Name[i],street_left_road_name[i]);
               // Vio[4] = super.distance(longitude, latitude, street_limitin_lon[i], street_limitin_lat[i]);
                for(int z = 0;z<leftlen[i]&&Getleftflag(z)<3;i++)
                {
                    Vio[5] = super.distance(longitude, latitude, street_left_lon[i][z], street_left_lat[i][z],2,z);
                }
                break;
            default:break;
        }
        for(int z = 0;z<limitinlen[i]&&Getlimitinflag(z)<3;i++)
        {
            Vio[5] = super.distance(longitude, latitude, street_limitin_lon[i][z], street_limitin_lat[i][z],1,z);
        }
        /*if(street_left_lon[i] != 0)
            Vio[5] = super.distance(longitude, latitude, street_left_lon[i], street_left_lat[i]);        */        
        if(flag)
        {
            Vio[3] = super.Illegal_Turn_Left(longitude,latitude,StreetX[i],StreetY[i],StreetStatus[i],street_left_coordinate[i],Street_Name[i],left_road);
            flag = false;
        }
        if(Vio[3] == -1)
        {
            left_road = street_left_road_name[i];
            flag = true;
        }
        Violiant_To_Database(Vio,License,name,longitude,latitude,speed, Date_, Time_); 
        output = Violiant_List_Maker(Vio,4);
        super.change(longitude,latitude);
        return output;
    }
    
    private String Violiant_List_Maker(int Vio[],int len)
    {
        //System.out.println("1");
        String output = "";
        switch(Vio[0])
        {
            case 0:output += "未超速,";break;
            case 1:output += "即將超速,";break;
            case 2:output += "超速,";break;
        }
        if(Vio[1] == 1)
            output += "逆向行駛,";
        if(Vio[2] == 1)
            output += "禁行區域,";
        if(Vio[3] == 1)
            output += "違規左轉,";
        switch(Vio[4])
        {
            case 1:output += "前方路段有禁止進入區域,";break;
            case 2:output += "前方路段有禁止進入區域,";break;
        }
        switch(Vio[5])
        {
            case 1:output += "前方路段禁止左轉,";break;
            case 2:output += "前方路段禁止左轉,";break;
        }
        return output;
    }
    
    private void Violiant_To_Database(int[] vio,String License,String Street_N,double longitude,double latitude,double speed,String DATE_,String Time_)
    {
        if(vio[0] == 2)
             Database.insertViolation(License,(double)longitude,(double)latitude,
                 (double)speed, Street_N,"超速", DATE_,Time_);
        if(vio[1] == 1)
             Database.insertViolation(License,(double)longitude,(double)latitude,
                 (double)speed, Street_N,"逆向行駛", DATE_,Time_);
        if(vio[2] == 1)
             Database.insertViolation(License,(double)longitude,(double)latitude,
                 (double)speed, Street_N,"禁止區域",DATE_,Time_);
        if(vio[3] == 1)
             Database.insertViolation(License,(double)longitude,(double)latitude, 
                 (double)speed, Street_N,"違規左轉",DATE_,Time_);
    }
    
    public void Print_List()
    {
       for(int i = 0;i<List_Length;i++)
        {
            System.out.println(Street_Name[i]+" "+Street_Limit_Speed[i]+" "+Street_Mode[i]);
            System.out.println(StreetX[i]+" "+StreetY[i]);
            System.out.println(StreetStatus[i]+" "+street_left_coordinate[i]+" "+street_left_road_name[i]);
            System.out.println();
            for(int j = 0;j<limitinlen[i];j++)
                System.out.println(street_limitin_lon[i][j]+" "+street_limitin_lat[i][j]);
            System.out.println();
            for(int j = 0;j<leftlen[i];j++)
                System.out.println(street_left_lon[i][j]+" "+street_left_lat[i][j]);
            System.out.println();
        }
    }
    
    
}
