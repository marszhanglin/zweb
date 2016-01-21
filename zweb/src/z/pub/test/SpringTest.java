package z.pub.test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Scheduler;

import z.pub.quartz.job.ScanDirectoryJob;
import z.pub.quartz.scheduler.SchedulerManager;



public class SpringTest {
	/*private  NativeJdbcServer nativeJdbcServer;
	@Autowired
	public void setNativeJdbcServer(NativeJdbcServer nativeJdbcServer) {
		this.nativeJdbcServer = nativeJdbcServer;
	}*/

	private  SpringTest() throws ParseException, ClassNotFoundException {
		System.out.println("SpringTest.SpringTest--容器启动bean的构造函数也启动");
		SpringTest.springWhenBeanRun();//
		//nativeJdbcServer.doSomething();
	}
	
	
	/**
	 * 启动侦测文件夹文件大小JOB
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public static void springWhenBeanRun() throws ParseException, ClassNotFoundException {
		System.out.println("SpringTest.springWhenBeanRun--启动侦测文件夹文件大小JOB"); 
		System.out.println("SpringTest.springWhenBeanRun--问题为什么无法将日志输出到文件中, 而在main方法可以"); 
		Map map=new HashMap<String, Object>();
   	 	map.put("DIR_MAP_KEY", "D:\\myeclipse\\gsecdsworkspace\\zweb"); 
   	 	Scheduler  scheduler= SchedulerManager.createScheduler();
   	 	SchedulerManager.schedulerAddJob(scheduler, ScanDirectoryJob.JOBNAME,
   			 Class.forName("z.pub.quartz.job.ScanDirectoryJob"),
   			 "3 * * * * ? ",null, map);
   	 	SchedulerManager.startScheduler(scheduler);
	}
	
	
	
}
