
package javatodatabase;
import javax.swing.JLabel;
import java.util.Date;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


class ThreadTime extends Thread 
{
private Date NowDate_ = null;
private JLabel DisplayTime_ = null;
    public ThreadTime(Date inputDate,JLabel TimeLabel) 
    {
        NowDate_ = inputDate;
        DisplayTime_ = TimeLabel;
    }
    
  public ThreadTime(){}
  public ThreadTime(Date inputDate){NowDate_ = inputDate;}
  
    public void run() { // override Thread's run()
        for (;;) { // infinite loop to print message
                      
            try 
            {
                TimeReFresh();
                Thread.sleep(500);
            } 
            catch (InterruptedException ex) {
              
            }
        }
    }
public void TimeReFresh()
  {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        NowDate_.setTime(System.currentTimeMillis());
        DisplayTime_.setText(formatter.format(NowDate_));
  }

}
