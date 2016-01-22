/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.mina.factory;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.util.Base64;

/**
 * 描述
 * 
 * @author Mars zhang
 * @created 2016年1月22日 下午2:36:57
 */
public class WebSocketDecoder extends CumulativeProtocolDecoder {

    private static final int SESSION_TAG_SHAKE_HANDS = 1;// 会话握手标识 用来标识是否握手
    private static final String WEBSOCKET_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    public static final byte FIN = 0x1; // 1000 0000
    public static final byte OPCODE = 0x0F;// 0000 1111
    public static final byte MASK = 0x1;// 1000 0000
    public static final byte PAYLOADLEN = 0x7F;// 0111 1111
    public static final byte HAS_EXTEND_DATA = 126;
    public static final byte HAS_EXTEND_DATA_CONTINUE = 127;

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        Object attribute = session.getAttribute(SESSION_TAG_SHAKE_HANDS);
        if (attribute == null) {
            // 握手
            byte[] bytes = new byte[in.limit()];
            in.get(bytes);
            String m = new String(bytes);
            // TODO 验证是否握手请求
            String secKey = getSecWebSocketKey(m);
            secKey += WEBSOCKET_GUID;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(secKey.getBytes("iso-8859-1"), 0, secKey.length());
            byte[] sha1Hash = md.digest();
            secKey = base64Encode(sha1Hash);
            String rtn = "HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: "
                    + secKey + "\r\n\r\n";
            byte[] rtnbytes = rtn.getBytes("utf-8");
            IoBuffer resp = IoBuffer.allocate(rtnbytes.length);
            resp.put(rtnbytes);
            resp.flip();
            session.write(resp);
            session.setAttribute(SESSION_TAG_SHAKE_HANDS, true);
            return false;
        }
        // 不够一个消息
        if (in.remaining() < 2) {
            // 消息头不完整
            return false;
        }
        in.mark();
        byte head1 = in.get();
        byte head2 = in.get();
        // int isend =head1>>7&FIN;
        int opcode = head1 & OPCODE;
        if (opcode == 8) {
            session.close(true);
            return false;
        }
        int ismask = head2 >> 7 & MASK;
        int length = 0;
        byte datalength = (byte) (head2 & PAYLOADLEN);
        if (datalength < HAS_EXTEND_DATA) {
            length = datalength;
        } else if (datalength == HAS_EXTEND_DATA) {
            if (in.remaining() < 2) {
                // 消息头不完整
                in.reset();
                return false;
            }
            byte[] extended = new byte[2];
            in.get(extended);
            int shift = 0;
            length = 0;
            for (int i = extended.length - 1; i >= 0; i--) {
                length = length + ((extended[i] & 0xFF) << shift);
                shift += 8;
            }
        } else if (datalength == HAS_EXTEND_DATA_CONTINUE) {
            if (in.remaining() < 2) {
                // 消息头不完整
                in.reset();
                return false;
            }
            byte[] extended = new byte[2];
            in.get(extended);
            int shift = 0;
            length = 0;
            for (int i = extended.length - 1; i >= 0; i--) {
                length = length + ((extended[i] & 0xFF) << shift);
                shift += 8;
            }
        }

        byte[] date = new byte[length];
        if (ismask == 1) {
            if (in.remaining() < 4 + length) {
                in.reset();
                return false;
            }
            // 利用掩码对org-data进行异或
            byte[] mask = new byte[4];
            in.get(mask);
            in.get(date);
            for (int i = 0; i < date.length; i++) {
                // 吧数据进行异或运算
                date[i] = (byte) (date[i] ^ mask[i % 4]);
            }
        } else {
            if (in.remaining() < length) {
                in.reset();
                return false;
            }
            in.get(date);
        }
        return true;
    }

    public static boolean checkHeader(IoSession session, IoBuffer in) {
        // Object attribute = session.getAttribute(SESSION_TAG_SHAKE_HANDS);

        return false;
    }

    public static String getSecWebSocketKey(String req) {
        Pattern p = Pattern.compile("^(Sec-WebSocket-Key:).+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher m = p.matcher(req);
        if (m.find()) {
            String foundstring = m.group();
            return foundstring.split(":")[1].trim();
        } else {
            return null;
        }
    }

    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

}
