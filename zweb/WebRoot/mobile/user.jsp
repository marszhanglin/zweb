<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("ctxs", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'user.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<meta name="viewport" content="width=device-width" />
<meta name="viewport" content="initial-scale=1.0,user-scalable=no" />
<%@ include file="../common/jqueryMobileResource.jsp"%>
<%-- 黑色tab样式 --%>
<%@ include file="../common/eveblack.jsp" %>   
</head>

<body>
	<div data-role="page" data-theme="a" id="aaa">
		<div data-role="header">
			  <a href="#" id="finish" data-icon="back">返回</a> 
			<h1 class="header-title">应急资源</h1>
		</div>
		<div data-role="content">
			<ul data-role="none" class="index-list">
				<li>
					<a href="${ctx}jfs/mobile/androidIndex/jqmobileBlist" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/ldgl.png" class="menu-img2"> 
							<label class="menu-title">楼栋管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
				<!-- 静态页面跳转 -->
					<a href="#fwgl"  data-transition="slide">
						<div>
							<img src="./js/jquerymobile/images/fwgl.png" class="menu-img2"> 
							<label class="menu-title">房屋管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="mobile/orginfoController/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/zzgl.png" class="menu-img2"> 
							<label class="menu-title">社会组织管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="#rkgl" data-transition="slide">
						<div>
							<img src="./js/jquerymobile/images/rkgl.png" class="menu-img2"> 
							<label class="menu-title">人口管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="mobile/placeController/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/csgl.png" class="menu-img2"> 
							<label class="menu-title">场所管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
			</ul>
		</div>
	</div> 
	<script type="text/javascript"> 
		$('#finish').live('tap',function(event, ui){
			if(confirm("是否要退出该模块?")){
				window.androidbase.finish("关闭");
			} 
			}); 
	</script>
	
	
	
	
	<div data-role="page" data-theme="a" id="base">
		<div data-role="header">
			<a href="#base" data-icon="back" data-transition="slide" data-direction="reverse">返回</a>
			<h1 class="header-title">房屋管理</h1>
		</div>
		<div data-role="content">
			<ul data-role="none" class="index-list">
				<li>
					<a href="mobile/house/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/house.png" class="menu-img1"> 
							<label class="menu-title">实有房屋管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="mobile/rentalController/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/house.png" class="menu-img3"> 
							<label class="menu-title">出租房管理</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
			</ul>
		</div> 
	</div>
	
		<script type="text/javascript">
		$('#fwgl').live('swiperight',function(event, ui){
			　$.mobile.changePage( $("#base"),"slide");
			});  
	</script>
	
	<div data-role="page" data-theme="a" id="rkgl">
		<div data-role="header">
			<a href="#base" data-icon="back" data-transition="slide" data-direction="reverse">返回</a>
			<h1 class="header-title">人口管理</h1>
		</div>
		<div data-role="content">
			<ul data-role="none" class="index-list">
				<li>
					<a href="mobile/person/personController/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/people.png" class="menu-img1"> 
							<label class="menu-title">常住人口</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="mobile/person/inflowController/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/people.png" class="menu-img2"> 
							<label class="menu-title">流动人口</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="mobile/person/unregister/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/people.png" class="menu-img3"> 
							<label class="menu-title">未落户人口</label> 
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
				<li>
					<a href="mobile/person/outBoundController/list" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/rkgl.png" class="menu-img4"> 
							<label class="menu-title">境外人员</label> 
						</div>s
					</a>
					<span class="arrow-right"></span>
				</li>
			</ul>
		</div>
	</div>
		<script type="text/javascript">
		$('#rkgl').live('swiperight',function(event, ui){
			　$.mobile.changePage( $("#base"),"slide");
			});  
		$('#rkgl').live('swipeleft',function(event, ui){
			　window.androidbase.finish("关闭");
			});
		$('body').live('taphold',function(event, ui){
			window.androidbase.show("log");
			}); 
	</script>
	
	
</body>

<%-- <body>
	${user.id }
	<br> ${user.name }
	<br> ${user.age }
	<br>
	<%=basePath%>user.jsp
	<br>
</body> --%>
</html>
