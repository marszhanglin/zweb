/*package z.pub.test.mina.factory;

 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 
package z.pub.test.mina;

import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Random;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

*//**
 * 描述
 * 
 * @author Mars zhang
 * @created 2016年1月22日 下午2:50:38
 *//*
public class WebSocketENcoder2 extends ProtocolEncoderAdapter {
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

    public void setByteOrder(ByteOrder byteOrder) {

        this.byteOrder = byteOrder;

    }

    private CharsetEncoder charsetEncoder = Charset.forName("utf-8")

    .newEncoder();

    public void setCharsetEncoder(CharsetEncoder charsetEncoder) {

        this.charsetEncoder = charsetEncoder;

    }

    private int defaultPageSize = 65536;

    public void setDefaultPageSize(int defaultPageSize) {

        this.defaultPageSize = defaultPageSize;

    }

    // 返回的数据默认不必进行掩码运算。

    private boolean isMasking = false;

    public void setIsMasking(boolean masking) {

        this.isMasking = masking;

    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput encoderOutput)
            throws CharacterCodingException {
        IoBuffer buff = IoBuffer.allocate(1024).setAutoExpand(true);
        WSSessionState status = getSessionState(session);
        switch (status) {
            case Handshake:
                try {
                    buff.putString((String) message, charsetEncoder).flip();
                    encoderOutput.write(buff);
                } catch (CharacterCodingException e) {
                    session.close(true);

                }
                session.setAttribute(WSSessionState.ATTRIBUTE_KEY, WSSessionState.Connected);
                break;
            case Connected:
                if (!session.isConnected() || message == null)

                    return;
                byte dataType = 1;
                // 将数据统一转换成byte数组进行处理。
                byte[] data = null;
                if (message instanceof String)
                    data = ((String) message).getBytes(charsetEncoder.charset());
                else {
                    data = (byte[]) message;
                    dataType = 2;
                }
                // 生成掩码。
                byte[] mask = new byte[4];
                Random random = new Random();
                // 用掩码处理数据。
                for (int i = 0, limit = data.length; i < limit; i++)
                    data[i] = (byte) (data[i] ^ mask[i % 4]);
                *//**
                 * 以分片的方式向客户端推送数据。
                 *//*
                int pageSize = this.defaultPageSize;
                // 分页大小
                int remainLength = data.length;
                // 剩余数据长度
                int realLength = 0;
                // 数据帧中“负载数据”的实际长度
                int dataIndex = 0;
                for (boolean isFirstFrame = true, isFinalFrame = false; !isFinalFrame; buff.clear(), isFirstFrame = false) {
                    int headerLeng = 2;
                    int payload = 0;
                    if (remainLength > 0 && remainLength <= 125) {
                        payload = remainLength;
                    } else if (remainLength > 125 && remainLength <= 65536) {
                        payload = 126;
                    } else {
                        payload = 127;
                    }
                    switch (payload) {
                        case 126:
                            headerLeng += 2;
                            break;
                        case 127:
                            headerLeng += 8;
                            break;
                        default:
                            headerLeng += 0;
                            break;
                    }
                    headerLeng += isMasking ? 4 : 0;
                    // 计算当前帧中剩余的可用于保存“负载数据”的字节长度。
                    realLength = (pageSize - headerLeng) >= remainLength ? remainLength : (pageSize - headerLeng);
                    // 判断当前帧是否为最后一帧。
                    isFinalFrame = (remainLength - (pageSize - headerLeng)) < 0;
                    // 生成第一个字节
                    byte fstByte = (byte) (isFinalFrame ? 0x80 : 0x0);
                    // 若当前帧为第一帧，则还需保存数据类型信息。
                    fstByte += isFirstFrame ? dataType : 0;
                    buff.put(fstByte);
                    // 生成第二个字节。判断是否需要掩码，若需要掩码，则置标志位的值为1.
                    byte sndByte = (byte) (isMasking ? 0x80 : 0);
                    // 保存payload信息。
                    sndByte += payload;
                    buff.put(sndByte);
                    switch (payload) {
                        case 126:
                            buff.putUnsignedShort(realLength);
                            break;
                        case 127:
                            buff.putLong(realLength);
                            break;
                        default:
                            break;
                    }
                    if (isMasking) {
                        random.nextBytes(mask);
                        buff.put(mask);
                    }
                    for (int loopCount = dataIndex + realLength, i = 0; dataIndex < loopCount; dataIndex++, i++)
                        buff.put((byte) (data[i] ^ mask[i % 4]));
                    buff.flip();
                    encoderOutput.write(buff);
                    remainLength -= (pageSize - headerLeng);
                }
                break;
            default:
                session.close(true);
                break;
        }
    }

}*/