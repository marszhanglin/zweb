/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package z.pub.util.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:客户端相关信息，如，浏览器、操作系统等的相关信息
 * ClientInfo clientInfo = new ClientInfo(request.getHeader("user-agent")); clientInfo.getOSName()等
 * @author Fandy Liu
 * @created 2014年7月15日 下午3:44:22
 */
public class ClientInfo {
    /**
     * 描述：信息
     */
    private String info = "";

    /**
     * 描述：浏览器版本
     */
    private String explorerVer = "未知";

    /**
     * 描述：操作系统版本
     */
    private String OSVer = "未知";

    /**
     * 描述：构造函数
     * 
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:45:10
     * @param info
     */
    public ClientInfo(String info) {
        this.info = info;
    }

    /**
     * 描述:获取核心浏览器名称
     * 
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:48:40
     * @return
     */
    public String getExplorerName() {
        String str = "未知";
        Pattern pattern = Pattern.compile("");
        Matcher matcher;
        if (info.indexOf("MSIE") != -1 || info.indexOf("rv:11.0") != -1) {
            str = "MSIE"; // 微软IE
            pattern = Pattern.compile(str + "\\s([0-9.]+)");
        } else if (info.indexOf("Firefox") != -1) {
            str = "Firefox"; // 火狐
            pattern = Pattern.compile(str + "\\/([0-9.]+)");
        } else if (info.indexOf("Chrome") != -1) {
            str = "Chrome"; // Google
            pattern = Pattern.compile(str + "\\/([0-9.]+)");
        } else if (info.indexOf("Opera") != -1) {
            str = "Opera"; // Opera
            pattern = Pattern.compile("Version\\/([0-9.]+)");
        }
        matcher = pattern.matcher(info);
        if (matcher.find())
            explorerVer = matcher.group(1);
        return str;
    }

    /**
     * 描述:获取核心浏览器版本
     * 
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:45:27
     * @return
     */
    public String getExplorerVer() {
        return this.explorerVer;
    }

    /**
     * 描述：获取浏览器插件名称（例如：遨游、世界之窗等）
     * 
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:45:39
     * @return
     */
    public String getExplorerPlug() {
        String str = "无";
        if (info.indexOf("Maxthon") != -1)
            str = "Maxthon"; // 遨游
        return str;
    }

    /**
     * 描述：获取操作系统名称
     * 
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:45:51
     * @return
     */
    public String getOSName() {
        String str = "未知";
        Pattern pattern = Pattern.compile("");
        Matcher matcher;
        if (info.indexOf("Windows") != -1) {
            str = "Windows"; // Windows NT 6.1
            pattern = Pattern.compile(str + "\\s([a-zA-Z0-9]+\\s[0-9.]+)");
        }
        matcher = pattern.matcher(info);
        if (matcher.find())
            OSVer = matcher.group(1);
        return str;
    }

    /**
     * 描述：获取操作系统版本
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:46:10
     * @return
     */
    public String getOSVer() {
        return this.OSVer;
    }

    /**
     * 描述：获取请求IP地址
     * @author Fandy Liu
     * @created 2014年7月15日 下午3:46:20
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        ip = "0:0:0:0:0:0:0:1".equals(ip.trim()) ? "127.0.0.1" : ip;
        return ip;
    }
}