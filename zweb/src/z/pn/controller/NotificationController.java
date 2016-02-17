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
package z.pn.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import z.pn.util.Config;
import z.pn.xmpp.push.NotificationManager;

/**
 * A controller class to process the notification related requests.  
 * 描述 推送消息的 action
 * @author Mars zhang
 * @created 2015年7月10日 上午8:50:16
 */
@Controller
public class NotificationController {
    
    //推送消息管理对象
    private NotificationManager notificationManager; 
    @Autowired
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }
    
    
    @RequestMapping(value="pn/notification/form")
    public ModelAndView form(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // mav.addObject("list", null);
        mav.setViewName("mobile/pn/form");
        return mav;
    }
    /**
     * 获取浏览器请求 发送推送
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="pn/notification/send")
    public ModelAndView send(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	//是否发生给所有人
        String broadcast = ServletRequestUtils.getStringParameter(request,
                "broadcast", "Y");
        //接收者名称
        String username = ServletRequestUtils.getStringParameter(request,
                "username");
        String title = ServletRequestUtils.getStringParameter(request, "title");
        String message = ServletRequestUtils.getStringParameter(request,
                "message");
        String uri = ServletRequestUtils.getStringParameter(request, "uri");

        String apiKey = Config.getString("apiKey", ""); 

        if (broadcast.equalsIgnoreCase("Y")) {
            notificationManager.sendBroadcast(apiKey, title, message, uri);
        } else {//发送给指定用户
            notificationManager.sendNotifcationToUser(apiKey, username, title,
                    message, uri);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("mobile/pn/form");
        return mav;
    }

}
