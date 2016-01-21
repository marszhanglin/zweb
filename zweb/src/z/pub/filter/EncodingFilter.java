package z.pub.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Title: EncodingFilter.java
 * @Description: TODO(对HTTP请求进行编码)
 * @author Mars zhang
 * @date 2014-6-20 上午1:21:13
 * @version V1.0
 */
public class EncodingFilter implements Filter {

    /**
     * 默认编码
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * web.xml配的过滤器参数
     */
    public static final String ENCODING = "encoding";

    /**
     * 编码
     */
    private String encoding;

    /**
     * 对请求进行代�?
     * <br>该方法会在每次请求中调用
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getMethod().toUpperCase().equals("GET")) {
            req = new RequestWrapper((HttpServletRequest) req);
        } else {
            req.setCharacterEncoding(encoding);
        }
        res.setCharacterEncoding(encoding);
        chain.doFilter(req, res);
    }

    /**
     * do nothing
     * <br>
     */
    public void destroy() {
    }

    /**
     * 初始化编�?
     *  <br>只会在Web容器启动的时候自动调用而且只调用一次
     */
    public void init(FilterConfig config) throws ServletException {
        String encoding = config.getInitParameter(ENCODING);
        if (encoding == null) {
            this.encoding = DEFAULT_ENCODING;
        } else {
            this.encoding = encoding;
        }
    }
    /*<init-param>
    <param-name>encoding</param-name>
    <param-value>UTF-8</param-value>
  </init-param>*/
    /**
     * 方法处理映射
     */
    private static Map<String, Integer> methods = new HashMap<String, Integer>();
    static {
        methods.put("getParameter", 0);
        methods.put("getParameterValues", 1);
        methods.put("getParameterMap", 2);
    }

    /**
     * 描述 HTTP请求包装
     * @author Fandy Liu
     * @created 2014�?0�?�?下午6:15:25
     */
    class RequestWrapper extends HttpServletRequestWrapper {
        /**
         * 参数名与值映�?
         */
        private Map<String, String[]> map = null;

        /**
         * 资源路径
         */
        private String uri;
        /**
         * 访问路径
         */
        private StringBuffer url;

        /**
         * 对请求进行包�?
         * 
         * @param request
         */
        public RequestWrapper(HttpServletRequest request) {
            super(request);
            map = new HashMap<String, String[]>();
            @SuppressWarnings("unchecked")
            Map<String, String[]> m = request.getParameterMap();
            try {
                for (Entry<String, String[]> set : m.entrySet()) {
                    int k = 0;
                    for (String v : set.getValue()) {
                        set.getValue()[k++] = new String(v.getBytes("ISO8859-1"), encoding);
                    }
                    map.put(set.getKey(), set.getValue());
                }
                map = Collections.unmodifiableMap(map);
                uri = request.getRequestURI();
                url = request.getRequestURL();
            } catch (UnsupportedEncodingException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        @Override
        public StringBuffer getRequestURL() {
            return url;
        }

        @Override
        public String getRequestURI() {
            return uri;
        }

        @Override
        public String getParameter(String name) {
            String[] value = map.get(name);
            if (value != null) {
                switch (value.length) {
                    case 0:
                        return "";
                    case 1:
                        return value[0];
                    default:
                        StringBuilder rs = new StringBuilder();
                        for (String v : value) {
                            rs.append(',');
                            rs.append(v);
                        }
                        return rs.substring(1);
                }
            }
            return null;
        }

        @Override
        public String[] getParameterValues(String name) {
            return map.get(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return map;
        }
    }

}
