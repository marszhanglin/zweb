package z.pub.quartz.calendar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;

public class MyHourCalendar extends BaseCalendar {

	private List excludHouse =new ArrayList(); 
	
    public MyHourCalendar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyHourCalendar(Calendar baseCalendar) {
		super(baseCalendar);
		// TODO Auto-generated constructor stub
	}

    public void setHourExcluded(int hour) {   
  	  
        if (isHourExcluded(hour))   
             return;   

        excludHouse.add(new Integer(hour));   
   }
	
	
	public boolean isHourExcluded(int hour) {   
  	  
        Iterator iter = excludHouse.iterator();   
        while (iter.hasNext()) {   
             Integer excludedHou = (Integer) iter.next();   

             if (hour == excludedHou.intValue()) {   
                  return true;   
             }   

             continue;   
        }   
        return false;   
   } 
    
	/**
     * 2.重写 是否已经包含了这个时间搓
     */
    public boolean isTimeIncluded(long timeStamp) {   
    	  
        if (super.isTimeIncluded(timeStamp) == false) {   
             return false;   
        }   

        java.util.Calendar cal = getJavaCalendar(timeStamp);   
        int hour = cal.get(java.util.Calendar.HOUR);   

        return !(isHourExcluded(hour));   
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
        int hour = cal.get(java.util.Calendar.HOUR);   

        if (isHourExcluded(hour) == false)   
             return timeStamp; // return the   
        // original value   

        while (isHourExcluded(hour) == true) {   
             cal.add(java.util.Calendar.HOUR, 1);   //boss:这一HOUR不执行  
             hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
        }   
        return cal.getTime().getTime();   
  }   
}
