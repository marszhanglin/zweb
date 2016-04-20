package com.example.socketio_chat_1_0.server;

import android.content.Context;

import com.example.socketio_chat_1_0.util.ActivityManagerUtil;
/**I
 * 
 * ÃèÊö  
 * @author Mars zhang
 * @created 2016-4-10 ÏÂÎç5:02:57
 */
public class ServiceUtil {
    
    
    /**
     * 
     * ÃèÊö  if service worked
     * @author Mars zhang
     * @created 2016-4-10 ÏÂÎç5:03:06
     * @param className
     * @param tContext
     * @return
     */
    public static boolean isWorked(String className,Context tContext) {
         return ActivityManagerUtil.isWorked(className, tContext);
    } 
    
    
}
