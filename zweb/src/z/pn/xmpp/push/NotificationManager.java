/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package z.pn.xmpp.push;

import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmpp.packet.IQ;

import z.pn.model.Notification;
import z.pn.model.User;
import z.pn.server.NotificationService;
import z.pn.server.ServiceLocator;
import z.pn.server.UserNotFoundException;
import z.pn.server.UserService;
import z.pn.xmpp.session.ClientSession;
import z.pn.xmpp.session.SessionManager;

/**
 * This class is to manage sending the notifcations to the users.  
 * 描述  
 * @author Mars zhang
 * @created 2015年7月10日 上午9:46:22
 */
@Service
public class NotificationManager {

    private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

    private final Log log = LogFactory.getLog(getClass());
    private SessionManager sessionManager; 
    @Autowired
    private   ServiceLocator serviceLocator; 
    
    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }
    /**
     * Constructor.
     */
    public NotificationManager() { 
        sessionManager = SessionManager.getInstance();
    }


    /**
     * Broadcasts a newly created notification message to all connected users.
     * <br>向所有（在线）用户发送推送数据
     * <br>修改后:   是向所有用户发送数据   不在线的保存到notification中等待下次连接时发送
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendBroadcast(String apiKey, String title, String message,
            String uri) {
        log.debug("sendBroadcast()...");
      //创建数据包
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        //获取数据库中所有用户
        List<User> users=serviceLocator.getUserService().getUsers();
        //发送给所有在线或不在线用户
        /*for(User user:users){
            String userName=user.getName();
            send2OneUser(apiKey, userName, title, message, uri, notificationIQ);
        }*/
         //发送给所有在线用户
        for (ClientSession session : sessionManager.getSessions()) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
            }
        } 
    }

    /**
     * Sends a newly created notification message to the specific user.
     *  发送给指定用户
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */ 
    public void sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri) {
        log.debug("sendNotifcationToUser()...");
        //创建数据包
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        send2OneUser(apiKey, username, title, message, uri, notificationIQ);
    }
    
    /**
     * 
     * 描述  发送给指定用户  用户不在线就保存到数据库
     * @author Mars zhang
     * @created 2015年7月10日 上午9:39:50
     * @param apiKey
     * @param username
     * @param title
     * @param message
     * @param uri
     * @param notificationIQ
     */
    private void send2OneUser(String apiKey, String username, String title, String message, String uri,
            IQ notificationIQ) {
        //通过用户名获取用户会话
        ClientSession session = sessionManager.getSession(username);
        if (session != null) { 
            if (session.getPresence().isAvailable()) {//用户在线可以发送数据  就填充数据并发送
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
            }else{
            	saveNotification(username, apiKey, title, message, uri);
            }
        }else{//没有会话   可能不在线  也可能根本不存在该用户
        	User user=serviceLocator.getUserService().getUserByUsername(username);
            if(null!=user){//用户存在
            	saveNotification(username, apiKey, title, message, uri);
            }
        }
    }

    /**
     * 将不能发送成功的推送消息保存至数据库 
     * <br>等待下次链接时发送
     * @param username
     * @param apiKey
     * @param title
     * @param message
     * @param uri
     */
    private void saveNotification(String username,String apiKey,String  title,String  message,String  uri){
    	Notification notification=new Notification(apiKey, username, title, message, uri);
    	serviceLocator.getNotificationService().save(notification);
    }
    
    /**
     * Creates a new notification IQ and returns it.
     */
    private IQ createNotificationIQ(String apiKey, String title,
            String message, String uri) {
        Random random = new Random();
        String id = Integer.toHexString(random.nextInt());
        // String id = String.valueOf(System.currentTimeMillis());

        Element notification = DocumentHelper.createElement(QName.get(
                "notification", NOTIFICATION_NAMESPACE));
        notification.addElement("id").setText(id);
        notification.addElement("apiKey").setText(apiKey);
        notification.addElement("title").setText(title);
        notification.addElement("message").setText(message);
        notification.addElement("uri").setText(uri);

        IQ iq = new IQ();
        iq.setType(IQ.Type.set);
        iq.setChildElement(notification);

        return iq;
    }
}
