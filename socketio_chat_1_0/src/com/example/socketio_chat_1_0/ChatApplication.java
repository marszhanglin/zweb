package com.example.socketio_chat_1_0;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

import android.app.Application;

public class ChatApplication extends Application {
    /** MemberVariables */
    public static ChatApplication instence;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
    
    @Override
    public void onCreate() { 
        super.onCreate();
        instence = this;

    }
}
