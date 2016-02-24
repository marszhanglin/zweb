///*
// * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
// * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *
// */
//package z.pub.util.springbean;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.stereotype.Component;
//
///**
// * 服务定位器
// * 
// * @author Yancey Qiu
// * @created 2014年11月1日 上午12:30:46
// * @version 2.0
// */ 
//@Component
//public class ServiceLocator implements BeanFactoryAware {
//
//     /**
//     * Bean工厂
//     * 
//     * @author Yancey Qiu
//     */ 
//    private static BeanFactory beanFactory = null;
//
//     /**
//     * ServiceLocator实例
//     * 
//     * @author Yancey Qiu
//     */ 
//    private static ServiceLocator instance = null;
//
//     /**
//     * 设置Bean工厂
//     * 
//     * @author Yancey Qiu
//     * @created 2014年11月1日 上午12:32:29
//     * @param factory
//     * @throws BeansException
//     */
//    @SuppressWarnings("static-access")
//    public void setBeanFactory(BeanFactory factory) throws BeansException {
//        this.beanFactory = factory;
//    }
//
//    /**
//     * 获取Bean工厂
//     * 
//     * @author Yancey Qiu
//     * @created 2014年11月1日 上午12:34:15
//     * @return
//     */
//    public BeanFactory getBeanFactory() {
//        return beanFactory;
//    }
//
//    /**
//     * 获取ServiceLocator实例
//     * 
//     * @author Yancey Qiu
//     * @created 2014年11月1日 上午12:34:38
//     * @return
//     */
//    public static ServiceLocator getInstance() {
//        if (instance == null)
//            instance = (ServiceLocator) beanFactory.getBean("serviceLocator");
//        return instance;
//    }
//
//    /**
//     * 根据提供的bean名称得到相应的服务类
//     * 
//     * @author Yancey Qiu
//     * @created 2014年11月1日 上午12:35:10
//     * @param servName
//     *            bean名称
//     * @return
//     */
//    public static Object getService(String servName) {
//        return beanFactory.getBean(servName);
//    }
//
//    /**
//     * 根据提供的bean名称得到对应于指定类型的服务类
//     * 
//     * @author Yancey Qiu
//     * @created 2014年11月1日 上午12:35:38
//     * @param servName
//     *            bean名称
//     * @param clazz
//     *            返回的bean类型,若类型不匹配,将抛出异常
//     * @return
//     */
//    @SuppressWarnings( { "rawtypes", "unchecked" })
//    public static Object getService(String servName, Class clazz) {
//        return beanFactory.getBean(servName, clazz);
//    }
//}
