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
	<%String contextPath = request.getContextPath();%>
	<script type="text/javascript" src="<%=contextPath %>/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/js/amq_jquery_adapter.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/js/amq.js"></script>
  </head>
  
  <body>
    This is /zweb/WebRoot/test/amq/mqforecssp.jsp <br>
    <script type="text/javascript">
	//获取iframe的参数 
	var hashobj;
    if(window.location.hash&&window.location.hash.length>0){
		var hash=window.location.hash.substring(1,window.location.hash.length); 
		hashobj = eval('(' + hash + ')');  
	} 
  //activemq 监控传送过来的信息
	var amq = org.activemq.Amq;
    amq.init({
		uri: '<%=contextPath %>/amq',
		logging: false,
		timeout: parseInt(hashobj.second),
		clientId:(new Date()).getTime().toString() 
    }); 
	var msgqueues = [];
	for(var i=0;i<hashobj.topics.length;i++){
    	var mytopic = hashobj.topics[i];
    	var bb = function (mytopic) {
    		amq.addListener(mytopic.topic, "topic://"+mytopic.topic, function(message){ 
				msgqueues.push(message);
				console.log(message.data);
				notify(); 
        	},{ selector:mytopic.selector });
    	}
    	bb(mytopic);
    }
	//是否正在运行
	var isrunning = false;
	//是否正在加载iframe
	var isiframeloadsuccess=true;
	//通知
	function notify(){
		if(isrunning)return;
		isrunning = true;
		for(;msgqueues[0]&&isiframeloadsuccess;){  
				isiframeloadsuccess=false;
				exec_proxy(msgqueues[0].data,mytopic.topic,hashobj.proxyurl); 
		} 
		isrunning=false;
		
	}
    //在该页面加载一个代理iframe   当接收到消息时调用iframe的js
	var exec_obj;
    function exec_proxy(msg,topic,proxyurl){  
        if(typeof(exec_obj)=='undefined'){  
            exec_obj = document.createElement('iframe');  
            exec_obj.name = 'tmp_frame';  
            exec_obj.src = proxyurl +'?topic='+topic+'&time=' + Math.random()+"#"+msg;  
            exec_obj.style.display = 'none';   
			
			//iframe 加载完成
			if (exec_obj.attachEvent){
					exec_obj.attachEvent("onload", function(){  
						msgqueues.splice(0,1); 
						isiframeloadsuccess=true;
						notify();
					});
				} else {
					exec_obj.onload = function(){ 
						msgqueues.splice(0,1); 
						isiframeloadsuccess=true;
						notify();
					};
				}
			
			
            document.body.appendChild(exec_obj);  
        }else{  
            exec_obj.src = proxyurl +'?topic='+topic+'&time=' + Math.random()+"#"+msg; 
        }  
      } 
	  
	   
    </script>
  </body>
</html>
