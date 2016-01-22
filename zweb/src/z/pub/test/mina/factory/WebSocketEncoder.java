/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.mina.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 描述
 * @author Mars zhang
 * @created 2016年1月22日 下午3:25:23
 */
public class WebSocketEncoder extends ProtocolEncoderAdapter{

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月22日 下午3:26:01
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws Exception
     * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
     */
    @Override
    public void encode(IoSession arg0, Object message, ProtocolEncoderOutput arg2) throws Exception {
        System.out.println("encode---"+message);
        arg2.write(message);
    }

}
