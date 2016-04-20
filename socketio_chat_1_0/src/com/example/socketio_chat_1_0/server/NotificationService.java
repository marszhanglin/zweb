package com.example.socketio_chat_1_0.server;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.socketio_chat_1_0.ChatApplication;
import com.example.socketio_chat_1_0.MainActivity;
import com.example.socketio_chat_1_0.R;
/**
 * 
 * 描述 
 * @author Mars zhang
 * @created 2016-4-10 下午5:25:31
 */
public class NotificationService extends Service {
    
    public static String SERVICE_NAME="com.example.socketio_chat_1_0.server.NotificationService";
    private Socket mSocket;
    
    /**
     * 
     * 描述  startService 方式执行service跑不了onBind   去看看生命周期
     * @author Mars zhang
     * @created 2016-4-10 下午5:57:54
     * @param intent
     * @return
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
        
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong("c", 1023L);
        editor.commit();
        
        return null;
    }
    
    
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong("b", 333333L);
        editor.commit(); 
        connectToNode();
    } 
    
    
    
    private void connectToNode(){
        ChatApplication app = ChatApplication.instence;
        mSocket = app.getSocket(); 
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(final Object... args) { 
                SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
                Editor editor = sp.edit();
                editor.putString("ERROR","CONNECT");
                editor.commit();
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(final Object... args) { 
                SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
                Editor editor = sp.edit();
                editor.putString("ERROR","ERROR");
                editor.commit();
            }
        } );
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT,new Emitter.Listener() {
            @Override
            public void call(final Object... args) { 
                SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
                Editor editor = sp.edit();
                editor.putString("TIMEOUT","TIMEOUT");
                editor.commit();
            }
        } );
        mSocket.on("message",new Emitter.Listener() {
            @Override
            public void call(final Object... args) { 
                JSONObject data = (JSONObject) args[0];
                SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
                Editor editor = sp.edit();
                String preMessage=sp.getString("message", "");
                editor.putString("message",preMessage+data.toString());
                editor.commit();
                notifyDefaultNtf(data.toString());
            }
        } ); 
        mSocket.connect();
    }
    
    
    private void notifyNtf(String msg ){
        
        NotificationManager ntfManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        SharedPreferences sp = getApplicationContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
        Editor editor = sp.edit(); 
        editor.putString("d","111");
        editor.commit();
        
        Notification myNotify=new Notification();
        //myNotify.icon=R.drawable.ic_launcher;
        myNotify.tickerText="你有新消息，请注意查收";
        myNotify.when=System.currentTimeMillis();
        myNotify.flags=Notification.FLAG_NO_CLEAR;//不能清除
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.ntf_tv);
        rv.setTextViewText(R.id.ntf_tv_id, msg);
        editor.putString("e","222");
        editor.commit();
        PendingIntent contentPendingIntent =PendingIntent.getActivity(this, 1, new Intent(Intent.ACTION_MAIN), PendingIntent.FLAG_CANCEL_CURRENT);
        myNotify.contentIntent=contentPendingIntent;
        editor.putString("f",contentPendingIntent.toString());
        editor.commit();
        ntfManager.notify(1, myNotify);
        editor.putString("g","222");
        editor.commit();
    }
    
    
    private void notifyDefaultNtf(String msg ){
        
        NotificationManager ntfManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  
                new Intent(this, MainActivity.class), 0);  
        // 下面需兼容Android 2.x版本是的处理方式  
        // Notification notify1 = new Notification(R.drawable.message,  
        // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());  
        Notification notify1 = new Notification();  
        notify1.icon = R.drawable.ic_launcher;  
        notify1.tickerText = "TickerText:您有新短消息，请注意查收！";  
        notify1.when = System.currentTimeMillis();  
        notify1.setLatestEventInfo(this, "Notification Title",  
                msg, pendingIntent);  
        notify1.number = 1;  
        notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。  
        // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示  
        ntfManager.notify(1, notify1);  
    }
}
