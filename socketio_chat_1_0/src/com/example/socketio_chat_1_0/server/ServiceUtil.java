package com.example.socketio_chat_1_0.server;

import android.content.Context;

import com.example.socketio_chat_1_0.util.ActivityManagerUtil;
/**I
 * 
 * ����  
 * @author Mars zhang
 * @created 2016-4-10 ����5:02:57
 */
public class ServiceUtil {
    
    
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
         return ActivityManagerUtil.isWorked(className, tContext);
    } 
    
    
}
