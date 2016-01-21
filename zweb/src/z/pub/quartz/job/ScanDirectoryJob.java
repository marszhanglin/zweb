package z.pub.quartz.job;
import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import z.pub.quartz.scheduler.SchedulerManager;
import z.pub.util.FileExtensionFileFilter;
      
/**     
 * <p>     
 * A simple Quartz job that, once configured, will scan a     
 * directory and print out details about the files found     
 * in the directory.     
 * </p>     
 * Subdirectories will filtered out by the use of a     
 * <code>{@link FileExtensionFileFilter}</code>.     
 *     
 * @author Chuck Cavaness     
 * @see java.io.FileFilter     
 */
/**
 * 定时器 扫描文件夹job
 * <br>每执行一次就创建job一次  
 * <br>所以全局性的东西不能放在全局变量中  
 * <br>要存在JobDataMap中保存（jobDetail.setJobDataMap(dataMap);）   ??然并卵   下次执行时还是不能获取到值
 * @author EVECOM-PC
 *	
 */
public class ScanDirectoryJob implements Job {       
	 public static Log logger = LogFactory.getLog(ScanDirectoryJob.class);       
     public static String JOBNAME="SCANDIRECTORYJOB";
     /**执行次数*/
     public int indexTime = 0;
     
     
     /**
      * 0-9每1分钟扫描一次
      * 10-19每2分钟扫描一次
      * 20-29每4分钟扫描一次
      * 30-  每8分钟扫描一次
      */
     public void execute(JobExecutionContext context)       
               throws JobExecutionException {       
      
          // Every job has its own job detail       
          JobDetail jobDetail = context.getJobDetail();       
      
          // The name is defined in the job definition       
          String jobName = jobDetail.getName();       
      
          // Log the time the job started       
          logger.info(jobName + " fired at " + new Date());       
      
          // The directory to scan is stored in the job map       
          JobDataMap dataMap = jobDetail.getJobDataMap();       
                    String dirName = dataMap.getString("DIR_MAP_KEY");       
      
          // Validate the required input       
          if (dirName == null) {       
               throw new JobExecutionException( "Directory not configured" );       
          }       
      
          // Make sure the directory exists       
          File dir = new File(dirName);       
          if (!dir.exists()) {       
           throw new JobExecutionException( "Invalid Dir "+ dirName);       
          }       
      
          // Use FileFilter to get only XML files       
          FileFilter filter = new FileExtensionFileFilter(".log");       
      
          File[] files = dir.listFiles(filter);       
      
          if (files == null || files.length <= 0) {       
               logger.info("No XML files found in " + dir);   
               // Return since there were no files       
               return;       
          }        
          // The number of XML files       
          int size = files.length;        
          // Iterate through the files found       
          for (int i = 0; i < size; i++) {       
      
               File file = files[i];       
      
               // Log something interesting about each file.       
               File aFile = file.getAbsoluteFile();       
               long fileSize = file.length();       
               String msg = aFile + " - Size: " + fileSize;       
               logger.info(msg);       
          }
          Object indexTimeTemp=dataMap.get("indexTime"); 
          dataMap.remove("indexTime");
          if(null!=indexTimeTemp){
        	  indexTime=(Integer.parseInt(indexTimeTemp+"" ));
        	  indexTime++;
              logger.info("执行次数："+indexTime); 
        	  dataMap.put("indexTime", indexTime);
          }else{
        	  indexTime++;
              //logger.info("执行次数："+indexTime);
              dataMap.put("indexTime", indexTime);
          }
          jobDetail.setJobDataMap(dataMap); 
          if(indexTime==10){// 
        	  SchedulerManager.modifyJobTime(ScanDirectoryJob.JOBNAME, "3 0/2 * * * ?", dataMap);
        	  logger.info("第十次执行重置job-----每两分钟执行一次" + new Date()); 
          }else if(indexTime==20){ 
        	  SchedulerManager.modifyJobTime(ScanDirectoryJob.JOBNAME, "3 0/4 * * * ?", dataMap);
        	  logger.info("第二十次执行重置job-----每四分钟执行一次" + new Date()); 
          }else if(indexTime==30){
        	  SchedulerManager.modifyJobTime(ScanDirectoryJob.JOBNAME, "3 0/8 * * * ?", dataMap);
        	  logger.info("第三十次执行重置job-----每八分钟执行一次" + new Date()); 
          }
     }       
} 