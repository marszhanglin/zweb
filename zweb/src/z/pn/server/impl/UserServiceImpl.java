/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pn.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import z.pn.dao.UserDao;
import z.pn.model.User;
import z.pn.server.UserNotFoundException;
import z.pn.server.UserService;

/**
 * 描述
 * @author Mars zhang
 * @created 2016年1月26日 下午8:39:23
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;
    
    
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月26日 下午8:39:23
     * @param userId
     * @return
     * @see z.pn.server.UserService#getUser(java.lang.String)
     */
    @Override
    public User getUser(String userId) {
        return userDao.getUser(new Long(userId));
    }

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月26日 下午8:39:23
     * @return
     * @see z.pn.server.UserService#getUsers()
     */
    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月26日 下午8:39:23
     * @param user
     * @return
     * @see z.pn.server.UserService#saveUser(z.pn.model.User)
     */
    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        return userDao.saveUser(user);
    }

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月26日 下午8:39:23
     * @param userId
     * @see z.pn.server.UserService#removeUser(java.lang.Long)
     */
    @Override
    public void removeUser(Long userId) {
        userDao.removeUser(userId);
    }

    /**
     * 描述  通过姓名获取用户
     * @author Mars zhang
     * @throws UserNotFoundException 
     * @created 2016年2月5日 下午4:44:10
     * @see z.pn.server.UserService#getUserByUsername()
     */
    @Override
    public User getUserByUsername(String name)     {
        return (User) userDao.getUserByUsername(name);
    }

}
