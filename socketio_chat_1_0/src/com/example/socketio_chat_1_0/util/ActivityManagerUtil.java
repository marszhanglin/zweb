package com.example.socketio_chat_1_0.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ActivityManagerUtil {

    
    /**
     * 
     * 描述  if service worked
     * @author Mars zhang
     * @created 2016-4-10 下午5:03:06
     * @param className
     * @param tContext
     * @return
     */
    public static boolean isWorked(String className,Context tContext) {
        //ActivityManager与系统中所有运行着的Activity交互提供了接口
        ActivityManager myManager = (ActivityManager) tContext
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        //目前正在运行的Service
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(className)) {
                return true;
            }
        } 
        return false;
    }
    
    
    
    /**
     * 
     * 描述  对activityManager测试
     * @author Mars zhang
     * @created 2016-4-11 上午10:51:25
     * @param tContext
     */
    public static void activityManagertest(Context tContext){
        ActivityManager mActivityManager = (ActivityManager) tContext.getSystemService(Context.ACTIVITY_SERVICE) ;  
        
        //获得系统运行的进程
        List<ActivityManager.RunningAppProcessInfo> appList1 = mActivityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo running : appList1) {
            System.out.println(running.processName);
        }
        System.out.println("================");
        
        //获得当前正在运行的service
        List<ActivityManager.RunningServiceInfo> appList2 = mActivityManager
                .getRunningServices(100);
        for (ActivityManager.RunningServiceInfo running : appList2) {
            System.out.println(running.service.getClassName());
        }
        
        System.out.println("================");
        
        //获得当前正在运行的activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
                .getRunningTasks(1000);
        for (ActivityManager.RunningTaskInfo running : appList3) {
            System.out.println(running.baseActivity.getClassName());
        }
        System.out.println("================");
        
        //获得最近运行的应用
        List<ActivityManager.RecentTaskInfo> appList4 = mActivityManager
                .getRecentTasks(100, 1);
        for (ActivityManager.RecentTaskInfo running : appList4) {
            System.out.println(running.origActivity.getClassName());
            }
    }
}
