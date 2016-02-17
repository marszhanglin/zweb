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
package z.pn.xmpp.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.jivesoftware.openfire.nio.XMLLightweightParser;

import z.pn.xmpp.net.XmppIoHandler;

/**
 * 
 * Decoder class that parses ByteBuffers and generates XML stanzas.
 * <br>解码器  字节转对象
 * @author Mars zhang
 * @created 2016年2月9日 上午10:30:09
 */
public class XmppDecoder extends CumulativeProtocolDecoder {

    // private final Log log = LogFactory.getLog(XmppDecoder.class);

    @Override
    public boolean doDecode(IoSession session, IoBuffer in,
            ProtocolDecoderOutput out) throws Exception {
        // log.debug("doDecode(...)...");

        XMLLightweightParser parser = (XMLLightweightParser) session
                .getAttribute(XmppIoHandler.XML_PARSER);  //从session中获取XMLLightweightParser实例  
                                                          //什么时候设置进去的？ z.pn.xmpp.net.XmppIoHandler.sessionOpened(IoSession session)
        parser.read(in);

        if (parser.areThereMsgs()) {
            for (String stanza : parser.getMsgs()) {
                out.write(stanza); //传给下一个解码器去解码
            }
        }
        return !in.hasRemaining();//缓冲区中有元素时返回 false  //hasRemaining()缓冲区中有元素时返回 true
    }

}
