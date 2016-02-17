/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package z.pn.xmpp.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.dom4j.io.XMPPPacketReader;
import org.jivesoftware.openfire.net.MXParser;
import org.jivesoftware.openfire.nio.XMLLightweightParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import z.pn.xmpp.XmppServer;

/**
 * This class is to create new sessions, destroy sessions and deliver
 * received XML stanzas to the <b>StanzaHandler</b>.
 * @author Mars zhang
 * @created 2016年2月9日 上午10:13:59
 */
public class XmppIoHandler implements IoHandler {

//    private static final Log log = LogFactory.getLog(XmppIoHandler.class);

    public static final String XML_PARSER = "XML_PARSER";

    private static final String CONNECTION = "CONNECTION";

    private static final String STANZA_HANDLER = "STANZA_HANDLER";

    private String serverName;

    private static Map<Integer, XMPPPacketReader> parsers = new ConcurrentHashMap<Integer, XMPPPacketReader>();

    private static XmlPullParserFactory factory = null;

    static {
        try {
            factory = XmlPullParserFactory.newInstance(
                    MXParser.class.getName(), null);
            factory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            System.out.printf("Error creating a parser factory", e);
        }
    }

    /**
     * Constructor. Set the server name from server instance. 
     */
    protected XmppIoHandler() {
        serverName = XmppServer.getInstance().getServerName();
    }

    /**
     * Invoked from an I/O processor thread when a new connection has been created.
     */
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("sessionCreated()...");
    }

    /**
     * Invoked when a connection has been opened.
     * 连接开始  创建xmlparser工具   及     Connection管理ioSession 及  StanzaHandler
     * 并放入ioSession中属性中方便获取
     */ 
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("sessionOpened()...");
        System.out.println("remoteAddress=" + session.getRemoteAddress());
        //  创建xmlparser工具
        XMLLightweightParser parser = new XMLLightweightParser("UTF-8");
        //  创建Connection管理ioSession 
        Connection connection = new Connection(session); 
        //  创建StanzaHandler
        StanzaHandler stanzaHandler = new StanzaHandler(serverName,connection);
        session.setAttribute(XML_PARSER, parser); 
        session.setAttribute(CONNECTION, connection);
        session.setAttribute(STANZA_HANDLER, stanzaHandler); 
    }

    /**
     * Invoked when a connection is closed.
     * 获取自定义连接对象 并 关闭连接
     */
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("sessionClosed()...");
        //获取自定义连接对象
        Connection connection = (Connection) session.getAttribute(CONNECTION);
        //关闭连接
        connection.close();
    }

    /**
     * Invoked with the related IdleStatus when a connection becomes idle.
     * 太久没有收到心跳连接的就关闭该连接   多久看mina-config.xlm
     */
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        System.out.println("sessionIdle()...");
        //获取连接对象
        Connection connection = (Connection) session.getAttribute(CONNECTION);
//        if (log.isDebugEnabled()) {
            System.out.println("Closing connection that has been idle: " + connection);
//        }
        //关闭连接
        connection.close();
    }

    /**
     * Invoked when any exception is thrown.
     */
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        System.out.println("exceptionCaught()...");
        System.out.print(cause);
    }

    /**
     * Invoked when a message is received.
     */
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        System.out.println("messageReceived()...");
        System.out.println("RCVD: " + message);

        // Get the stanza handler
        StanzaHandler handler = (StanzaHandler) session
                .getAttribute(STANZA_HANDLER);

        // Get the XMPP packet parser
        int hashCode = Thread.currentThread().hashCode();
        XMPPPacketReader parser = parsers.get(hashCode);
        if (parser == null) {
            parser = new XMPPPacketReader();
            parser.setXPPFactory(factory);
            parsers.put(hashCode, parser);
        }

        // The stanza handler processes the message
        try {
            handler.process((String) message, parser); //解析 message数据（为xml格式的字符串数据）
        } catch (Exception e) {
            System.out.printf(
                    "Closing connection due to error while processing message: "
                            + message, e);
            Connection connection = (Connection) session
                    .getAttribute(CONNECTION);
            connection.close();
        }
    }

    /**
     * Invoked when a message written by IoSession.write(Object) is sent out.
     * 发送消息  IoSession在其他地方调了ioSession.write(Object)
     */
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("messageSent()...");
    }

}