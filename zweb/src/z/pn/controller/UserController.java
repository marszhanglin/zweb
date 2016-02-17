/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import z.pn.server.UserService;

/**
 * 描述 springmvc控制层测试
 * 
 * @author Mars zhang
 * @created 2016年1月21日 上午11:19:43
 */
@Controller
public class UserController {

    @Autowired
    private UserService userServer;

    public void setUserServer(UserService userServer) {
        this.userServer = userServer;
    } 

    @RequestMapping(value = "pn/user/list")
    public String renderpnUserJsp(ModelMap modelMap) {
        List<z.pn.model.User> users = userServer.getUsers();
        modelMap.addAttribute("users",users);
        return "mobile/pn/users";
    }

}
