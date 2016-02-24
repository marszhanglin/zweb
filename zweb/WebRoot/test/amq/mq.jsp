<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My MQ 'mq.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%String contextPath = request.getContextPath();%>
	<script type="text/javascript" src="<%=contextPath %>/test/amq/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/test/amq/amq_jquery_adapter.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/test/amq/amq.js"></script>
  </head>
  
  <body>
    This is my MQ page. <br>
    <script type="text/javascript">
  //activemq 监控传送过来的信息
	var amq = org.activemq.Amq;
    amq.init({
		uri: '<%=contextPath %>/amq',
		logging: false,
		timeout: 10,
		clientId:(new Date()).getTime().toString() 
    });
    var myHandler = function(message){
		alert(message.data);
    }
    amq.addListener("myname", "topic://test", myHandler);
    </script>
  </body>
</html>
