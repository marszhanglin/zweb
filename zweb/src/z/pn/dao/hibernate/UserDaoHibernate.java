/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pn.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import z.pn.dao.UserDao;
import z.pn.model.User;

/**
 * 描述
 * 
 * @author Mars zhang
 * @created 2016年1月26日 下午7:37:38
 */
@Repository
public class UserDaoHibernate extends HibernateDaoSupport implements UserDao {

    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 描述
     * 
     * @author Mars zhang
     * @created 2016年1月26日 下午7:37:38
     * @param id
     * @return
     * @see z.pn.dao.UserDao#getUser(java.lang.Long)
     */
    @Override
    public User getUser(Long id) {
        return getHibernateTemplate().get(User.class, id);
    }

    /**
     * 描述
     * 
     * @author Mars zhang
     * @created 2016年1月26日 下午7:37:38
     * @param user
     * @return
     * @see z.pn.dao.UserDao#saveUser(z.pn.model.User)
     */
    @Override
    public User saveUser(User user) {
        getHibernateTemplate().saveOrUpdate(user);
        getHibernateTemplate().flush();
        return user;
    }

    /**
     * 描述
     * 
     * @author Mars zhang
     * @created 2016年1月26日 下午7:37:38
     * @param id
     * @see z.pn.dao.UserDao#removeUser(java.lang.Long)
     */
    @Override
    public void removeUser(Long id) {
        getHibernateTemplate().delete(getUser(id));
    }

    /**
     * 描述
     * 
     * @author Mars zhang
     * @created 2016年1月26日 下午7:37:38
     * @param id
     * @return
     * @see z.pn.dao.UserDao#exists(java.lang.Long)
     */
    @Override
    public boolean exists(Long id) {
        User user = getUser(id);
        return null != user;
    }

    /**
     * 描述
     * 
     * @author Mars zhang
     * @created 2016年1月26日 下午7:37:38
     * @return
     * @see z.pn.dao.UserDao#getUsers()
     */
    @Override
    public List<User> getUsers() {
        System.out.println(123213131);
        HibernateTemplate hibernateTemplate = getHibernateTemplate();
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) hibernateTemplate.find("from User u order by u.createdDate desc");
        return users;
    }

}
