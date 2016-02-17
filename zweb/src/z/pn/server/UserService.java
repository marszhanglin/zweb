/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pn.server;

import java.util.List;

import z.pn.model.User;

/**
 * 描述
 * @author Mars zhang
 * @created 2016年1月26日 下午8:33:02
 */
public interface UserService {
    
    public User getUser(String userId);

    public List<User> getUsers();
    
    public User saveUser(User user);
    
    public void removeUser(Long userId);

    public User getUserByUsername(String name)    ;
}
