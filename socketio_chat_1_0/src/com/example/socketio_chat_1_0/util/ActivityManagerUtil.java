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
     * ����  if service worked
     * @author Mars zhang
     * @created 2016-4-10 ����5:03:06
     * @param className
     * @param tContext
     * @return
     */
    public static boolean isWorked(String className,Context tContext) {
        //ActivityManager��ϵͳ�����������ŵ�Activity�����ṩ�˽ӿ�
        ActivityManager myManager = (ActivityManager) tContext
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        //Ŀǰ�������е�Service
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
     * ����  ��activityManager����
     * @author Mars zhang
     * @created 2016-4-11 ����10:51:25
     * @param tContext
     */
    public static void activityManagertest(Context tContext){
        ActivityManager mActivityManager = (ActivityManager) tContext.getSystemService(Context.ACTIVITY_SERVICE) ;  
        
        //���ϵͳ���еĽ���
        List<ActivityManager.RunningAppProcessInfo> appList1 = mActivityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo running : appList1) {
            System.out.println(running.processName);
        }
        System.out.println("================");
        
        //��õ�ǰ�������е�service
        List<ActivityManager.RunningServiceInfo> appList2 = mActivityManager
                .getRunningServices(100);
        for (ActivityManager.RunningServiceInfo running : appList2) {
            System.out.println(running.service.getClassName());
        }
        
        System.out.println("================");
        
        //��õ�ǰ�������е�activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
                .getRunningTasks(1000);
        for (ActivityManager.RunningTaskInfo running : appList3) {
            System.out.println(running.baseActivity.getClassName());
        }
        System.out.println("================");
        
        //���������е�Ӧ��
        List<ActivityManager.RecentTaskInfo> appList4 = mActivityManager
                .getRecentTasks(100, 1);
        for (ActivityManager.RecentTaskInfo running : appList4) {
            System.out.println(running.origActivity.getClassName());
            }
    }
}
