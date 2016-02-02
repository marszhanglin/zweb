/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import z.pn.server.UserServer;

/**
 * 描述 springmvc控制层测试
 * 
 * @author Mars zhang
 * @created 2016年1月21日 上午11:19:43
 */
@Controller
public class SpringControllerTest {

    @Autowired
    private UserServer userServer;

    public void setUserServer(UserServer userServer) {
        this.userServer = userServer;
    }

    @RequestMapping(value = "spring/test")
    public String abc() {
        System.out.println("--------------------------------------------");
        return "bb12312";
    }

    @RequestMapping(value = "spring/test/param")
    public String abcd(@RequestParam(value = "username") String name) {
        System.out.println("-------------------" + name + "-------------------------");
        User user = new User();
        user.setAge(26);
        user.setName(name);
        user.setId(RandomUtils.nextInt());
        return "bb12312";
    }

    /**
     * 
     * 描述 <br>
     * json: http://localhost:8081/zweb/spring/test/param/aaabbbb1231.json<br>
     * xml: http://localhost:8081/zweb/spring/test/param/aaabbbb1231.xml<br>
     * jsp: http://localhost:8081/zweb/spring/test/param/aaabbbb1231<br>
     * 
     * @author Mars zhang
     * @created 2016年1月21日 下午2:53:24
     * @param name
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "spring/test/param/{username}")
    public String abcde(@PathVariable(value = "username") String name, ModelMap modelMap) {
        System.out.println("-------------------" + name + "-------------------------");
        User user = new User();
        user.setAge(26);
        user.setName(name);
        user.setId(RandomUtils.nextInt());
        User user2 = new User();
        user2.setAge(26);
        user2.setName(name + "2");
        user2.setId(RandomUtils.nextInt());
        modelMap.addAttribute("user", user);
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);
        modelMap.addAttribute(users);
        modelMap.addAttribute("abc");
        return "mobile/user";// 如果是json与xml这里可以返回空null;
    }

    @RequestMapping(value = "spring/test/websocket")
    public String renderWebsocketJsp() {
        return "websocket";
    }

    @RequestMapping(value = "spring/pnuser/getallusers")
    public String renderpnUserJsp(ModelMap modelMap) {
        List<z.pn.model.User> users = userServer.getUsers();
        modelMap.addAttribute("users",users);
        return "pnuser";
    }

}
