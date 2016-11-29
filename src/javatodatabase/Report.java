/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatodatabase;

//import org.apache.commons.io.*;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.digester.*;
import org.apache.commons.beanutils.*;
import org.apache.commons.collections.*;
import org.apache.commons.logging.*;
import org.apache.commons.javaflow.*;
import com.itextpdf.text.*;
import com.lowagie.text.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

/**
 *
 * @author Chen
 */
public class Report {
    
      
    private ConnectDATA Database_ = null;
    
    
    
    public Report(ConnectDATA DB)
    {
    Database_ = DB;
    
    }
    public void makereport(Vector<String> vec) throws Exception {
     
try{     
    System.out.println("Generating PDF...");         

JasperReport jasperReport = (JasperReport)JRLoader.loadObject(new File("report1.jasper"));
        
      
        

     Collection<Map<String, ?>> list = new ArrayList<Map<String,?>>();
             
            // put dummy records
            for (int i = 0; i < vec.size(); i++) {
   
   String Row = vec.elementAt(i);
   String[] field= Row.split(" ");
   //field[0] :車牌     field1:車牌號碼
   //field[1] :速度     field2:路段
   //field[2] :地址     field3:車速
   //field[3] :違規事件 field4:違規事件
   //field[4] :日期    field:日期時間
   //field[5] :時間
    
                   Map<String, Object> data = new HashMap<String, Object>();
                data.put("field1", field[0]);
                data.put("field2", field[2]);
                data.put("field3", field[1]);
                data.put("field4", field[3]);
                data.put("field5", field[4]+" "+field[5]);
                list.add(data);
            }
            
            JRDataSource datasource = new JRMapCollectionDataSource(list);

    JasperPrint jasperPrint = 
        JasperFillManager.fillReport(jasperReport, null, datasource);      
       SimpleDateFormat formatterfilename =new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date datefilename = new Date();
        String filenameDate = formatterfilename.format(datefilename);
    JasperExportManager.exportReportToPdfFile(jasperPrint,"Report_"+ filenameDate+ ".pdf");
                    
    System.out.println("Report has been generated!");
}
catch (JRException e){
    System.out.println(e.toString());
    //e.printStackTrace();
}
    }
    
}
