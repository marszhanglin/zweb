/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pn.dao;

import java.util.List;

import z.pn.model.User;

/**
 * 描述  getHibernateTemplate()
 * @author Mars zhang
 * @created 2016年1月26日 下午7:35:29
 */
public interface UserDao {

    public User getUser(Long id);

    public User saveUser(User user);

    public void removeUser(Long id);

    public boolean exists(Long id);
    
    public List<User> getUsers();
}
