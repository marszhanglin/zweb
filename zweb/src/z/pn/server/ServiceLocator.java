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
package z.pn.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import z.pn.xmpp.XmppServer;

/**
 * 
 * 描述  加载spring的server层
 * @author Mars zhang
 * @created 2016年2月9日 上午10:07:45
 */
@Service
public class ServiceLocator {

    public static String USER_SERVICE = "userService";
    public static String NOTIFICATION_SERVICE = "notificationService"; 
    
    private  UserService userService;
   
    private   NotificationService notificationService;
    
    @Autowired
    public void setUserService(UserService userService) {
         this.userService = userService;
    }
    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    } 
    /**
     * Generic method to obtain a service object for a given name. 
     * 
     * @param name the service bean name
     * @return
     */
    /*public static Object getService(String name) {
        return XmppServer.getInstance().getBean(name);
    }*/

    /**
     * Obtains the user service.
     * 
     * @return the user service
     */
    /**
     * 获取User的服务层
     * @return
     */
    public   UserService getUserService() { 
//        return (UserService) XmppServer.getInstance().getBean(USER_SERVICE);
//        return (UserService) XmppServer.getInstance().getUserService();
        XmppServer.getInstance();
        return userService;
    }
    /**
     * 获取Notification的服务层
     * @return
     */
    public   NotificationService getNotificationService(){
//        return (NotificationService) XmppServer.getInstance().getBean(NOTIFICATION_SERVICE);
//    	return (NotificationService) XmppServer.getInstance().getNotificationService();
        XmppServer.getInstance();
        return notificationService; 
    }

}
