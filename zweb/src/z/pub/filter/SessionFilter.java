/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package z.pub.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import z.pub.staticvalue.DictKeys;

/**
 * Title: SessionFilter.java
 * @Description:用于检查用户是否登录了系统的过滤器
 * @author Mars zhang
 * @created 2014年10月5日 下午11:53:49
 */
public class SessionFilter implements Filter {
	private static Logger log = Logger.getLogger(SessionFilter.class);
    /** 需要排除（不拦截）的URL的正则表达式 */
    private Pattern excepUrlPattern;

    /** 检查不通过时，转发的URL */
    private String forwardUrl;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
    	log.info("=========SessionFilter.init");
        String excepUrlRegex = cfg.getInitParameter("excepUrlRegex");
        if (!StringUtils.isBlank(excepUrlRegex)) {
            excepUrlPattern = Pattern.compile(excepUrlRegex);
        }
        forwardUrl = cfg.getInitParameter("forwardUrl");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String servletPath = request.getServletPath();
        // 如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
        if (servletPath.equals(forwardUrl) || excepUrlPattern.matcher(servletPath).matches()) {
            chain.doFilter(req, res);
            return;
        }
       
        Object sessionObj = request.getSession().getAttribute(DictKeys.SESSION_INFO);
        // 如果Session为空，则跳转到指定页面
        if (sessionObj == null && !"/jf/attachment/upload".equals(servletPath)) {
            // 如果是ajax请求响应头会有，x-requested-with；
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                // 在响应头设置session状态
                response.setHeader("sessionstatus", "sessionOut");
                response.getWriter().print("sessionOut");
            } else {
                String contextPath = request.getContextPath();
                String redirect = servletPath + "?" + StringUtils.defaultString(request.getQueryString());
                response.sendRedirect(contextPath + StringUtils.defaultIfEmpty(forwardUrl, "/") + "?redirect="
                        + URLEncoder.encode(redirect, "UTF-8")); 
            }
        } else {
            String navdict = request.getParameter("navdict");
            if(navdict!=null&&"5".equals(navdict)){
                String contextPath = request.getContextPath();
                //String redirect = servletPath + "?" + StringUtils.defaultString(request.getQueryString());
                response.sendRedirect(contextPath + "/evecom/centerdis/index.html");
            }else{
                chain.doFilter(req, res);
            }
        }
    }

    @Override
    public void destroy() {
    }
}