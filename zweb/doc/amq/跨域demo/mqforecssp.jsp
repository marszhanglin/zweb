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
    This is /zweb/WebRoot/test/amq/mqforecssp.jsp <br>
    <script type="text/javascript">
  //activemq 监控传送过来的信息
	var amq = org.activemq.Amq;
    amq.init({
		uri: '<%=contextPath %>/amq',
		logging: false,
		timeout: 10,
		clientId:(new Date()).getTime().toString() 
    });
    //测试test主题
    var mytestHandler = function(message){ 
		exec_proxy(message.data,'test');
    } 
    try { amq.addListener("mytest", "topic://test", mytestHandler);} catch (e) {};
    
    //任务反馈planeventtaskresponse主题
    var myplaneventtaskresponseHandler = function(message){ 
		exec_proxy(message.data,'planeventtaskresponse');
    }
    try { amq.addListener("myplaneventtaskresponse", "topic://planeventtaskresponse", myplaneventtaskresponseHandler);} catch (e) {};
    
    
    var aa="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    for(var i=0;i<1000;i++){
    	aa=aa+i+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    }
    
    //在该页面加载一个代理iframe   当接收到消息时调用iframe的js
    function exec_proxy(msg,topic){  
        if(typeof(exec_obj)=='undefined'){  
            exec_obj = document.createElement('iframe');  
            exec_obj.name = 'tmp_frame';  
            exec_obj.src = 'http://localhost:8080/ecssp/test/amq_proxy.jsp?topic='+topic+'&time=' + Math.random()+"#"+msg;  
            exec_obj.style.display = 'none';  
            document.body.appendChild(exec_obj);  
        }else{  
            exec_obj.src = 'http://localhost:8080/ecssp/test/amq_proxy.jsp?topic='+topic+'&time=' + Math.random()+"#"+msg;  
        }  
      } 
    </script>
  </body>
</html>
