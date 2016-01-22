package z.pub.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.quartz.Scheduler;

import z.pub.quartz.job.ScanDirectoryJob;
import z.pub.quartz.scheduler.SchedulerManager;
import z.pub.test.mina.MyWebSocketHandler;
import z.pub.test.mina.factory.MyWebSocketFactory;
import z.pub.test.mina.factory.WebSocketDecoder;
import z.pub.test.mina.factory.WebSocketEncoder;

/**
 * 
 * 描述
 * 
 * @author Mars zhang
 * @created 2016年1月21日 下午7:34:25
 */
public class SpringTest {
    /*
     * private NativeJdbcServer nativeJdbcServer;
     * 
     * @Autowired public void setNativeJdbcServer(NativeJdbcServer
     * nativeJdbcServer) { this.nativeJdbcServer = nativeJdbcServer; }
     */

    private SpringTest() throws ParseException, ClassNotFoundException {
        System.out.println("SpringTest.SpringTest--容器启动bean的构造函数也启动");
        // SpringTest.springWhenBeanRun();//
        // nativeJdbcServer.doSomething();

        startMinaSocketService();
    }

    /**
     * 定时器<br>
     * 启动侦测文件夹文件大小JOB
     * 
     * @throws ParseException
     * @throws ClassNotFoundException
     */
    public static void springWhenBeanRun() throws ParseException, ClassNotFoundException {
        System.out.println("SpringTest.springWhenBeanRun--启动侦测文件夹文件大小JOB");
        System.out.println("SpringTest.springWhenBeanRun--问题为什么无法将日志输出到文件中, 而在main方法可以");
        Map map = new HashMap<String, Object>();
        map.put("DIR_MAP_KEY", "D:\\myeclipse\\gsecdsworkspace\\zweb");
        Scheduler scheduler = SchedulerManager.createScheduler();
        SchedulerManager.schedulerAddJob(scheduler, ScanDirectoryJob.JOBNAME,
                Class.forName("z.pub.quartz.job.ScanDirectoryJob"), "3 * * * * ? ", null, map);
        SchedulerManager.startScheduler(scheduler);
    }

    /**
     * 
     * 描述 mina服务器端
     * 
     * @author Mars zhang
     * @created 2016年1月21日 下午7:33:48
     */
    public static void startMinaSocketService() {
        // 1、初始化
        NioSocketAcceptor nioSocketAcceptor = new NioSocketAcceptor();
        // 2、处理回调
        nioSocketAcceptor.setHandler(new MyWebSocketHandler());
        // 3、消息数据转换(对象转字节) 要自定义的写 这里用mina内置的一个
        // nioSocketAcceptor.getFilterChain().addLast("codec", new
        // ProtocolCodecFilter(new TextLineCodecFactory()));
        nioSocketAcceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new MyWebSocketFactory(new WebSocketEncoder(), new WebSocketDecoder())));
        // 4、地址
        try {
            nioSocketAcceptor.bind(new InetSocketAddress(8088));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
