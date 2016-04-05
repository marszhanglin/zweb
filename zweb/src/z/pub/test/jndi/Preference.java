/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.jndi;

import java.io.Serializable;

import org.junit.Test;

/**
 * 描述 JNDI LDAP ApacheDS 用户信息
 * 
 * @author Mars zhang
 * @created 2016年3月4日 下午2:33:17
 */
public class Preference implements Serializable {

    String styleSheetURL = "1231张林";
    
    public void setStyles(String styleSheetURL){
        this.styleSheetURL = styleSheetURL;
    }
}
