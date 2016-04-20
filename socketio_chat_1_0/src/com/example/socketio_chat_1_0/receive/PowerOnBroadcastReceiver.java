/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.example.socketio_chat_1_0.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.socketio_chat_1_0.server.NotificationService;
/**
 * 
 * √Ë ˆPowerOnBroadcastReceiver
 * @author Mars zhang
 * @created 2014-11-5 …œŒÁ10:27:20
 */
public class PowerOnBroadcastReceiver extends BroadcastReceiver { 

    @Override
    public void onReceive(Context context, Intent intent) {  
        SharedPreferences sp = context.getSharedPreferences("TEST", context.MODE_PRIVATE);
            Editor editor = sp.edit();
            editor.putLong("a", 22L);
            editor.commit(); 
            context.startService(new Intent(context, NotificationService.class));
    } 
    
    
    
 
}
