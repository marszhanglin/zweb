<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'websocket.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	// 开始连接到服务器
    var pollingInterval;
    _ws = new WebSocket("ws://192.168.5.55:8088");
    //_ws = new window.MozWebSocket("ws://192.168.0.103:5002/WebIM5?uaid=200513807p8912-5a78ae8a-cabb-46ee-8d8a-85874bbc942c&re=0");
    _ws.onopen = function () {
        alert("onopen");

        _socketCreated = true;
        var args
        _ws.send("1234567890");
        _ws.send("33322233");
    };
    _ws.onmessage = function (event) {
        console.log("event.data=" + event.data);
                    
    };
    _ws.onclose = function () {
        alert("onclose");
        console.log("onclose");
    };
    _ws.onerror = function () {
        console.log("onerror");
    };
    function sendsocket() {
        _ws.send(document.getElementById("msg").value);
    }
	
	</script>

  </head>
  
  <body>
  <input type="text" id="msg" />
  
  <button onclick="sendsocket();" >websocket消息发送mina接收消息</button>
  <br>
    <br> 
    /zweb/src/z/pub/test/SpringTest.java<br>
    /zweb/src/z/pub/test/mina/MyHandler.java
  </body>
</html>
