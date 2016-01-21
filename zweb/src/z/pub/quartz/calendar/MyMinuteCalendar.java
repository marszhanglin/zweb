package z.pub.quartz.calendar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;


/**
 * 排除某些分钟Calendar
 * @author EVECOM-PC
 *
 */
public class MyMinuteCalendar extends BaseCalendar {
	
	private List excludedMinutes =new ArrayList();

	public MyMinuteCalendar() {
		super();
	}

	public MyMinuteCalendar(Calendar baseCalendar) {
		super(baseCalendar);
	}
	
	
    public void setMinuteExcluded(int minute) {   
    	  
        if (isMinuteExcluded(minute))   
             return;   

        excludedMinutes.add(new Integer(minute));   
   }
    
    public boolean isMinuteExcluded(int minute) {   
    	  
        Iterator iter = excludedMinutes.iterator();   
        while (iter.hasNext()) {   
             Integer excludedMin = (Integer) iter.next();   

             if (minute == excludedMin.intValue()) {   
                  return true;   
             }   

             continue;   
        }   
        return false;   
   } 
    
    
    
    /**
     * 1.重写 是否已经包含了这个时间搓
     */
    public boolean isTimeIncluded(long timeStamp) {   
    	  
        if (super.isTimeIncluded(timeStamp) == false) {   
             return false;   
        }   

        java.util.Calendar cal = getJavaCalendar(timeStamp);   
        int minute = cal.get(java.util.Calendar.MINUTE);   

        return !(isMinuteExcluded(minute));   
   }   

    
    /**
     * 1.重写 BaseCalendar的getNextIncludedTime   MINUTE 其他组件获取时间
     */
   public long getNextIncludedTime(long timeStamp) {   
        // Call base calendar implementation first   
        long baseTime = super.getNextIncludedTime(timeStamp);   
        if ((baseTime > 0) && (baseTime > timeStamp))   
            timeStamp = baseTime;   

        // Get timestamp for 00:00:00   
        long newTimeStamp = buildHoliday(timeStamp);   
        java.util.Calendar cal = getJavaCalendar(newTimeStamp);   
        int minute = cal.get(java.util.Calendar.MINUTE);   

        if (isMinuteExcluded(minute) == false)   
             return timeStamp; // return the   
        // original value   

        while (isMinuteExcluded(minute) == true) {   
             cal.add(java.util.Calendar.MINUTE, 1);   //boss:这一分钟不执行  
        }   
        return cal.getTime().getTime();   
  }   

 
}
