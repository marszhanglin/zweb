/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import z.pn.dao.UserDao;

/**
 * 描述 web.xml配置
 * <servlet> 
       <servlet-name>mytest</servlet-name> 
       <servlet-class> 
          z.webservice.service.MyTest
       </servlet-class> 
    </servlet> 
       
    <servlet-mapping> 
       <servlet-name>mytest</servlet-name> 
       <url-pattern>/mytest</url-pattern> 
    </servlet-mapping>
 * @author Mars zhang
 * @created 2016年2月26日 上午9:44:10
 */
@WebService
public class MyTest {
    /** dao */
    @Autowired
    private UserDao userDao; 
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @WebMethod
    public String sayHello(){
        return "hello webservice,呵呵"+userDao.getUsers().get(0).getId();
    }

}
