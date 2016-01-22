/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.mina.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 描述
 * @author Mars zhang
 * @created 2016年1月22日 下午3:17:55
 */
public class MyWebSocketFactory implements ProtocolCodecFactory {

    private ProtocolEncoder encoder = null;

    private ProtocolDecoder decoder = null;
    
    
    
    
    public MyWebSocketFactory(ProtocolEncoder encoder, ProtocolDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月22日 下午3:17:55
     * @param arg0
     * @return
     * @throws Exception
     * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache.mina.core.session.IoSession)
     */
    @Override
    public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub
        return this.decoder;
    }

    /**
     * 描述
     * @author Mars zhang
     * @created 2016年1月22日 下午3:17:55
     * @param arg0
     * @return
     * @throws Exception
     * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache.mina.core.session.IoSession)
     */
    @Override
    public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub
        return this.encoder;
    }

}
