/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 描述   mina 处理回调handler<br>
 * 只要（都是）继承IoHandlerAdapter<br>
 * 重写方法
 * @author Mars zhang
 * @created 2016年1月21日 下午7:39:43
 */
public class MyWebSocketHandler extends IoHandlerAdapter {
    
    public static Log logger = LogFactory.getLog(MyWebSocketHandler.class); 
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(session, cause);
        logger.info("exceptionCaught");
    }
    
    //接收消息回调
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("messageReceived"+message.getClass().getName());
        logger.info("messageReceived");
    }
    
    //发送消息回调
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("messageSent"+message);
        logger.info("messageSent");
    }
    
    //session关闭回调
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("sessionClosed");
        logger.info("sessionClosed");
    }
    
    //session创建回调
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("sessionCreated");
        logger.info("sessionCreated");
    }
    
    //session空闲时
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception { 
        System.out.println("sessionIdle");
        logger.info("sessionIdle");
    }
    
    //session
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("sessionOpened");
        logger.info("sessionOpened");
    }
    
}
