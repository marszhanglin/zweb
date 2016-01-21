package z.pub.quartz.scheduler;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

import z.pub.quartz.job.ScanDirectoryJob;
/**
 * 
 * @author EVECOM-PC
 * Scheduler 在每次执行时都会为 Job 创建新的实例
 */
public class SchedulerManager {
     static Log logger = LogFactory.getLog(SchedulerManager.class);       
     public static String JOBGROUP="DEFAULT_GROUP";
     
     public static void main(String[] args) throws ParseException, ClassNotFoundException, SchedulerException {        
    	 
    	 calendarStudy();
     }       

     /**
      * 假日不执行calender学习
      * @throws ParseException
      * @throws ClassNotFoundException
      * @throws SchedulerException
      */
     public static void calendarStudy() throws ParseException, ClassNotFoundException, SchedulerException {
    	 Map map=new HashMap<String, Object>();
    	 map.put("DIR_MAP_KEY", "D:\\myeclipse\\gsecdsworkspace\\SpringConf"); 
    	 Scheduler  scheduler= SchedulerManager.createScheduler();
    	 
    	 
    	 //设置日期calendar
    	 AnnualCalendar annualCalendar = new AnnualCalendar();  //最小作用到日期   最大作用到日期   年跟时不行
    	 //排查某个时间段不执行
    	 Calendar gregorianCalendar1= GregorianCalendar.getInstance();
    	 gregorianCalendar1.set(Calendar.MONTH, Calendar.OCTOBER); //10月
    	 gregorianCalendar1.set(Calendar.DATE, 14);                //16日
//    	 gregorianCalendar1.set(Calendar.HOUR, 9);                //10点   多余
//    	 gregorianCalendar1.set(Calendar.MINUTE, 59); 			  //20分  多余
    	 annualCalendar.setDayExcluded(gregorianCalendar1, true);
    	 Calendar gregorianCalendar2= GregorianCalendar.getInstance();
    	 gregorianCalendar2.set(Calendar.MONTH, Calendar.OCTOBER); //10月
    	 gregorianCalendar2.set(Calendar.DATE, 15);                //17日
//    	 gregorianCalendar1.set(Calendar.HOUR, 9);                //10点   多余
//    	 gregorianCalendar1.set(Calendar.MINUTE, 59); 			  //20分  多余
    	 annualCalendar.setDayExcluded(gregorianCalendar2, true);
    	 //添加到scheduler
    	 scheduler.addCalendar("10-16", annualCalendar, true, true); 
    	 
    	 /*//设置分钟calendar
    	 MyMinuteCalendar myMinuteCalendar =new MyMinuteCalendar();
    	 myMinuteCalendar.setMinuteExcluded(43);   //43分钟不执行
    	 scheduler.addCalendar("minute", myMinuteCalendar, true, true);*/
    	 
    	/* //设置分钟calendar  不管用
    	 MyHourCalendar myHourCalendar = new MyHourCalendar();
    	 myHourCalendar.setHourExcluded(15);
    	 scheduler.addCalendar("Hour", myHourCalendar, true, true); */
    	 
    	 SchedulerManager.schedulerAddJob(scheduler, ScanDirectoryJob.JOBNAME,
    			 Class.forName("z.pub.quartz.job.ScanDirectoryJob"),
    			 "5/3 * * * * ? ","10-16", map); //5秒开始每
    	 SchedulerManager.startScheduler(scheduler);
     }
     
     
     /**
      * 1、创建Scheduler
      * @return
      */
     public static  Scheduler createScheduler(){
    	 Scheduler scheduler = null; 
    	// Get a Scheduler instance from the Factory       
         try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return scheduler;
     }
     
     /**
      * 2、给Scheduler添加job
      * 一个 Job 在同一个 Scheduler 实例中通过名称和组名能唯一被标识。
      * <br>假如你增加两个具体相同名称和组名的 Job，】
      * <br>程序会抛出 ObjectAlreadyExistsException 的异常。
      * @param scheduler
      * @param name
      * @param group
      * @param jobClass
      * @param time
      * @param map
      * @return
      * @throws ParseException
      */
     public static Scheduler schedulerAddJob(Scheduler scheduler,String name, String group,
    		 Class jobClass,String time,Map<String,Object> map) throws ParseException{ 
    	 //jobDetail作用是描述job并给job提供数据
    	 JobDetail jobDetail = new JobDetail(name,group,jobClass); 
         jobDetail.getJobDataMap().putAll(map);//可在job中获取map       
         //创建扫描方式的触发器  时间格式time
         CronTrigger cronTrigger = new CronTrigger(name, group, time);  
         // Associate the trigger with the job in the scheduler       
         try {
			scheduler.scheduleJob(jobDetail, cronTrigger);//添加JOB到Scheduler
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
         return scheduler;
     }
     
     
     
     /**
      * 2、给Scheduler添加job
      * 一个 Job 在同一个 Scheduler 实例中通过名称和组名能唯一被标识。
      * <br>假如你增加两个具体相同名称和组名的 Job，】
      * <br>程序会抛出 ObjectAlreadyExistsException 的异常。
      * @param scheduler
      * @param name 
      * @param jobClass
      * @param time
      * @param map
      * @return
      * @throws ParseException
      */
     public static Scheduler schedulerAddJob(Scheduler scheduler,String name,
    		 Class jobClass,String time,String calendarName,Map map) throws ParseException{ 
    	 //jobDetail作用是描述job并给job提供数据
    	 JobDetail jobDetail = new JobDetail(name,SchedulerManager.JOBGROUP,jobClass); 
         jobDetail.getJobDataMap().putAll(map);//可在job中获取map       
         //创建扫描方式的触发器  时间格式time
         CronTrigger cronTrigger = new CronTrigger(name, SchedulerManager.JOBGROUP, time);  
         
         /*设置时间箱   
         cronTrigger.setStartTime(startTime); 
         cronTrigger.setEndTime(endTime);*/
         
         if(calendarName!=null){//设置作用的calender的名称
        	 cronTrigger.setCalendarName(calendarName); 
         }
         // Associate the trigger with the job in the scheduler       
         try {
			scheduler.scheduleJob(jobDetail, cronTrigger);//添加JOB到Scheduler
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
         return scheduler;
     }
     /**
      * 当Scheduler不为暂停状态时
      * <br>启动Scheduler
      * @param scheduler
      * @return
      */
     public static Scheduler startScheduler(Scheduler scheduler){
    	    try {       
    	         if (!scheduler.isInStandbyMode()) {       
    	              // pause the scheduler       
    	              scheduler.standby();       
    	         }             
    	         // and then restart it       
    	         scheduler.start();       
    	      
    	    } catch (SchedulerException ex) {       
    	         logger.error(ex);       
    	    } 
		return scheduler; 
     }
     
     
     
     /**
      * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
      * 
      * @param jobName
      * @author Mars zhang
      * @created 2014年10月29日 下午4:07:01
      */
     public static void removeJob(String jobName) {
         try {
             Scheduler sched = SchedulerManager.createScheduler(); 
             sched.pauseTrigger(jobName, SchedulerManager.JOBGROUP);// 停止触发器
             sched.unscheduleJob(jobName, SchedulerManager.JOBGROUP);// 移除触发器
             sched.deleteJob(jobName, SchedulerManager.JOBGROUP);// 删除任务
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException(e);
         }
     }
     
     
     
     /**
      * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
      * 
      * @param jobName
      * @param time
      * @author Mars zhang
      * @created 2014年10月29日 下午4:07:01
      */
     public static void modifyJobTime(String jobName, String time,Map<String ,Object> map) {
         try {
             Scheduler sched =  SchedulerManager.createScheduler();
             CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName,
            		 SchedulerManager.JOBGROUP);
             if (trigger == null) {
                 return;
             }
             String oldTime = trigger.getCronExpression();
             if (!oldTime.equalsIgnoreCase(time)) {
                 JobDetail jobDetail = sched.getJobDetail(jobName,
                		 SchedulerManager.JOBGROUP);
                 @SuppressWarnings("rawtypes")
                 Class objJobClass = jobDetail.getJobClass();
                 //String jobClass = objJobClass.getName();
                 removeJob(jobName);

                 schedulerAddJob(sched,jobName, objJobClass, time , null, map);
             }
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException(e);
         }
     }
     
     /**
      * 启动一个没有job的scheduler     
      */
     public static void startScheduler() {       
          Scheduler scheduler = null;       
      
          try {       
               // Get a Scheduler instance from the Factory       
               scheduler = StdSchedulerFactory.getDefaultScheduler();  //new StdSchedulerFactory() 

               // Start the scheduler       
               scheduler.start();       
               logger.info("Scheduler started at " + new Date());       
      
          } catch (SchedulerException ex) {       
               // deal with any exceptions       
               logger.error(ex);       
          }       
     }       
}   
