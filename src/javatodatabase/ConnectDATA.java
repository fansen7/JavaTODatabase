/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatodatabase;

import java.sql.*;
import java.util.Locale;
import javax.swing.JTextArea;


/**
 *
 * @author Chen
 */
public class ConnectDATA
{   
    private  String DB_URL;
    private  Statement sta ;
    public  ResultSet result;
    private  Connection con ;
    private  int columns ;
    private boolean isconnect_;
    private  ResultSetMetaData meta = null;
    JTextArea display = null;
    
   public ConnectDATA(JTextArea jTextArea1)
    {
        DB_URL = "jdbc:derby://localhost:1527/BUSDATA";
        con = null;
        sta = null;
        result = null;
        columns = 0;
        isconnect_ = false;
        display = jTextArea1;
        display.setLineWrap(true);        //自動換行
        display.setWrapStyleWord(true);    // 斷行不亂字
    }
public void connect() {
    
    try {
        con = DriverManager.getConnection(DB_URL, "Fansen", "12345");
        sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE);
        
        result = sta.executeQuery("select * from FANSEN.BUS");
        meta = result.getMetaData();
        columns = meta.getColumnCount();
       
        displayTOtextarea("Connect to DATABASE success.\n");
        isconnect_ = true;
        } catch (SQLException e) 
        {
        
        displayTOtextarea(e.getMessage() + "\n");
        displayTOtextarea("Connect failed.\n");
        }
    }
public void close_conn() {
    try {
       con.close();
       sta.close();
        
        displayTOtextarea("Close the Connection.\n");
    } 
    catch (SQLException e) 
    {
       e.printStackTrace();
    }
}
public void displayAll() {
    try 
    { 
        result = sta.executeQuery("select * from FANSEN.BUS");
      //  result.first();
         
    display();
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
    }
}
 public void insert( String License,double Longitude,double Latitude , double speed,
         String Address,String Data_,String Time_  ) 
 {
       
     
     String sql = String.format("Insert into FANSEN.Bus(LicensePlateNumber ,Longitude,Latitude,Speed,Address,ClientDate,ClientTime) "
        + "values ('%s',%f,%f,%f,'%s','%s','%s')",License, Longitude, Latitude,speed,Address, Data_,Time_ );
    String display_Data = String.format("values ('%s',%f,%f,%f,%s,'%s','%s')",License,Longitude,
            Latitude,speed,Address,Data_, Time_ );
      try
         {
          sta.execute(sql);
          displayTOtextarea("Add "+display_Data + " to Database.\n");
          result = sta.executeQuery("select * from Fansen.BUS");
         } 
      catch (SQLException e)
        {
         e.printStackTrace();
        }
    }   
 public void insertViolation( String License,double Longitude ,double Latitude ,double speed,
         String Address,String event,String Data_,String Time_ ) 
 {
       
     
     String sql = String.format("Insert into FANSEN.Violation(LicensePlateNumber ,Longitude,Latitude,Speed,Address,Event,ClientDate,ClientTime) "
        + "values ('%s',%f,%f,%f,'%s','%s','%s','%s')",License, Longitude, Latitude,speed,Address,event,Data_,Time_);
    String display_Data = String.format( "values ('%s',%f,%f,%f,'%s','%s','%s','%s')",
            License, Longitude, Latitude,speed,Address,event,Data_,Time_);
      try
         {
          sta.execute(sql);
          displayTOtextarea("Add "+display_Data + " to Database.\n");
          result = sta.executeQuery("select * from Fansen.BUS");
         } 
      catch (SQLException e)
        {
         e.printStackTrace();
        }
    }   
 public void displayTOtextarea(String input)
{
display.append(input);
display.setCaretPosition(display.getDocument().getLength());

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
         
    display();
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
    }
  } 
 
 public void searchvio() {
    try 
    { 
        
        String Cmd = String.format("select * from FANSEN.violation");
        
        System.out.println(Cmd);
        result = sta.executeQuery(Cmd);
        
        meta = result.getMetaData();
        columns = meta.getColumnCount();

        result.first();
         
  // display();
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
    }
  } 
public boolean checkstring(String input)
{
if(input.contains(" ")) return false;

return true;

}   
 
public boolean is_connected()
{
return isconnect_;

}   

public void display() throws SQLException
{
    while (result.next()) 
        {
   
        for (int i = 1; i <= columns; i++) 
            {
             // System.out.printf("%s ", result.getString(i)); //取出字串型態欄位之內容
           // System.out.printf("%s ", result.getObject(i)); //取出整數字串等各種型態欄位之內容
            
            
            String output;
            output = String.format("%s ", result.getObject(i));
            
             displayTOtextarea(output);
            }
 
         displayTOtextarea("\n");
        }
    displayTOtextarea("\n");


}

public void DisplayList(String[] List) throws SQLException
{
    int n = 0;
    result.first();
    do {
   String output = "";
        for (int i = 1; i <= columns; i++) 
            {
             // System.out.printf("%s ", result.getString(i)); //取出字串型態欄位之內容
           // System.out.printf("%s ", result.getObject(i)); //取出整數字串等各種型態欄位之內容
           if(meta.getColumnName(i).toString().equals("LATITUDE")||
              meta.getColumnName(i).toString().equals("LONGITUDE")
           //   meta.getColumnName(i).toString().equals("SPEED")
                   ) continue;
            output += String.format("%s ", result.getObject(i));
               
            //output+= "  ";
            }
  
     List[n] += output;
     n++;
        }while (result.next()) ;

}/*
public void insertTime( String Date_,String Time_ ) 
 {
       
     
     String sql = String.format("Insert into FANSEN.Test(ClientDate,ClientTime) "
        + "values ('%s','%s')",Date_, Time_);
    
      try
         {
          sta.execute(sql);
          result = sta.executeQuery("select * from Fansen.Test");
         } 
      catch (SQLException e)
        {
         e.printStackTrace();
        }
    }   
*/
public void deleteAll() {
    try 
    { 
      String Cmd = String.format("delete from FANSEN.BUS");
    //  System.out.println(Cmd);
      sta.execute(Cmd);
 
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
    }
  } 


public int getresultcount() {
    try 
    { 
    int size = 0;
     result.first();
     do{
        size++;
    }while(result.next());
   
return size;
    } 
    catch (SQLException e) 
    {
     return 0;
    }
  } 
}
