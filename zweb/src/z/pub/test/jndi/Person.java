/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.jndi;

/**
 * 描述
 * @author Mars zhang
 * @created 2016年3月4日 下午7:41:35
 */
public class Person {
    private String name;
    private String address;   
    private String password;
    
    private MessagingPreferences p;
    
    public Preference getP() {
        return p;
    }
    public void setP(MessagingPreferences p) {
        this.p = p;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
