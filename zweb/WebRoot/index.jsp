<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    welcome-file-----index.jsp 
   <br> 
   <a href="<%=basePath%>spring/test/websocket">websocket-mina</a>
   <a href="http://192.168.5.55:9001">node socket.io</a>
   <a href="<%=basePath%>node/public/index.html">node socket.io 跨域解决</a>
   <a href="<%=basePath%>pn/user/list">pn/user/list</a>
   <a href="<%=basePath%>pn/session/list">pn/session/list</a>
   <a href="<%=basePath%>pn/notification/form">pn/notification/form</a>
   <br> 
    详见：web.xml
  </body>
</html>
