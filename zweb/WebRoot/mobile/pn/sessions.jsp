<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<%@ include file="../../common/jqueryMobileResource.jsp"%>
<%-- 黑色tab样式 --%>
<%@ include file="../../common/eveblack.jsp" %>   
</head>

<body>
	<div data-role="page" data-theme="a" id="aaa">
		<div data-role="header">
			  <a href="#" id="finish" data-icon="back">返回</a> 
			<h1 class="header-title">session</h1>
		</div>
		<div data-role="content">
			<ul data-role="none" class="index-list">
			<c:forEach items="${sessionList }" var="temp" varStatus="vst">
				<li>
					<a href="#" data-ajax="false">
						<div>
							<img src="./js/jquerymobile/images/ldgl.png" class="menu-img2"> 
							<label class="menu-title">${temp.username }</label> <br>
							<label class="menu-title">${temp.resource }</label> <br>
							<label class="menu-title">${temp.status }</label> <br>
							<label class="menu-title">
							<c:choose>
								<c:when test="${temp.presence eq 'Online'}">
									用户在线
								</c:when>
								<c:when test="${temp.presence eq 'Offline'}">
									用户不在线
								</c:when>
								<c:otherwise>
									用户离开
								</c:otherwise>
							</c:choose>
								<c:out value="${temp.presence}" /></label> <br>
						</div>
					</a>
					<span class="arrow-right"></span>
				</li>
			
			</c:forEach> 
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
	
	
</body>
 
</html>


<c:forEach var="sess" items="${sessionList}">
			<tr>
				<td><c:out value="${sess.username}" /></td>
				<td><c:out value="${sess.resource}" /></td>
				<td align="center"><c:out value="${sess.status}" /></td>
				<td>
					<c:choose>
					<c:when test="${sess.presence eq 'Online'}">
						<img src="images/user-online.png" />
					</c:when>
					<c:when test="${sess.presence eq 'Offline'}">
						<img src="images/user-offline.png" />
					</c:when>
					<c:otherwise>
						<img src="images/user-away.png" />
					</c:otherwise>
					</c:choose>
					<c:out value="${sess.presence}" />
				</td>
				<td><c:out value="${sess.clientIP}" /></td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sess.createdDate}" /></td>
			</tr>
		</c:forEach>
