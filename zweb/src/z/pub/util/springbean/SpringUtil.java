/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.util.springbean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 描述
 * @author Mars zhang
 * @created 2016年2月10日 下午12:18:53
 */
public class SpringUtil implements ApplicationContextAware {
   private static  ApplicationContext applicationContext;  
    /**
     * 描述
     * @author Mars zhang
     * @created 2016年2月10日 下午12:18:54
     * @param arg0
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }
    
    
    /**
     * 
     * 描述
     * @author Mars zhang
     * @created 2016年2月10日 下午12:25:55
     * @param beanid
     * @return
     */
    public static Object getbean(String beanid){
        return applicationContext.getBean(beanid);
    }
    
    /**
     * 
     * 描述
     * @author Mars zhang
     * @created 2016年2月10日 下午1:01:23
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return SpringUtil.applicationContext;
    }
}
