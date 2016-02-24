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
	<%String contextPath = request.getContextPath();
	  String topic= request.getParameter("topic");
	  request.setAttribute("topic",topic);
	%>
	<script type="text/javascript" src="<%=contextPath %>/jslib/jquery/jquery-1.9.1.min.js"></script>
	<!-- <script type="text/javascript" src="<%=contextPath %>/jslib/amq/amq_jquery_adapter.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/jslib/amq/amq.js"></script> -->
	
  </head>
  
  <body> 
    <script type="text/javascript"> 
    	var topic = '<%= topic %>'
		alert(topic); 
		
		var msg ="";
		if(window.location.hash&&window.location.hash.length>0){
			msg=window.location.hash.substring(1,window.location.hash.length);
		}
		switch (topic) {
		case 'test':
			parent.parent.testhandler(msg);
			break;
		case 'planeventtaskresponse':
			parent.parent.planeventtaskresponsehandler(msg);
			break;
		}
	</script> 
  </body>
</html>
