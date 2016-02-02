/**
 * 初始化页面容器效果
 */

var $_artWin = {};

// 界面加载完成后调用方法
$(function() {
	// 设置允许浮动层拖动
	$("#zhyyPanel").draggable({
		handle : ".zhyy-title"
	});

	// 设置综合应用上下缩放
	$("#arrowBtn").bind("click", function() {
		if ($(this).attr("src") == rootPath + "/images/gis/ExpandIco1.png") {
			$(this).attr("src", rootPath + "/images/gis/ExpandIco.png");
		} else {
			$(this).attr("src", rootPath + "/images/gis/ExpandIco1.png");
		}
		$(".zhyy-con").slideToggle({
			duration : 800,
			easing : 'easeOutBounce'
		});
	});

	// 绑定右边tab页鼠标移动事件
	$('.header ul li').mouseover(function() {
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
	});

	// 绑定左边菜单栏单击事件
	$("#map-leftbar #rightArrow").click(
			function() {
				if ($(this).hasClass("arrow-hide")) {
					$(this).attr("class", "arrow-show").attr("title", "显示工具条")
							.parent().animate({
								left : "-66px"
							}, 500);
				} else {
					$(this).attr("class", "arrow-hide").attr("title", "隐藏工具条")
							.parent().animate({
								left : "0"
							}, 500);
				}
			});

	// 左边菜单上下页事件
	$(".leftbar-main>li:gt(7)").hide();// 初始化，前面10条数据显示，其他的数据隐藏。
	var leftbar_total = $(".leftbar-main").children().length;// 总数据
	var leftbar_cPage = 8;// 每页显示的数据
	var leftbar_cNum = 1;// 当前页数
	var leftbar_page = Math.ceil(leftbar_total / leftbar_cPage);// 总页数

	// 下一页
	$(".scroll-down").click(function() {
		if (leftbar_cNum == leftbar_page) {
			return false;// 如果大于总页数就禁用下一页
		} else {
			++leftbar_cNum;
			$.each($(".leftbar-main>li"), function(index, item) {
				var start = leftbar_cPage * (leftbar_cNum - 1);// 起始范围
				var end = leftbar_cPage * leftbar_cNum;// 结束范围
				if (index >= start && index < end) {// 如果索引值是在start和end之间的元素就显示，否则就隐
					$(this).show();
				} else {
					$(this).hide();
				}
			});
		}
	});
	// 上一页方法
	$(".scroll-up").click(function() {
		if (leftbar_cNum == 1) {
			return false;
		} else {
			--leftbar_cNum;
			$.each($(".leftbar-main>li"), function(index, item) {
				var start = leftbar_cPage * (leftbar_cNum - 1);// 起始范围
				var end = leftbar_cPage * leftbar_cNum;// 结束范围
				if (index >= start && index < end) {// 如果索引值是在start和end之间的元素就显示，否则就隐藏
					$(this).show();
				} else {
					$(this).hide();
				}
			});
		}
	});

	// 绑定右边tabs鼠标悬浮事件
	$('.header ul li').mouseover(function() {
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
	});
	
});

// 右边列表移动tab显示对应内容
function nTabs(thisObj, Num) {
	if (thisObj.className == "active")
		return;
	var tabObj = thisObj.parentNode.id;
	var tabList = document.getElementById(tabObj).getElementsByTagName("li");
	for (i = 0; i < tabList.length; i++) {
		if (i == Num) {
			thisObj.className = "active";
			document.getElementById(tabObj + "_Content" + i).style.display = "block";
		} else {
			tabList[i].className = "normal";
			document.getElementById(tabObj + "_Content" + i).style.display = "none";
		}
	}
}

// 显示隐藏右边列表区域
function showHideDiv() {
	$(".tab_con").fadeToggle(1);
	if ($("#showHideDiv").html() == "隐藏") {
		$("#showHide").attr("src", rootPath + "/images/gis/show.png");
		$("#showHideDiv").html("显示");
	} else {
		$("#showHide").attr("src", rootPath + "/images/gis/hide.png");
		$("#showHideDiv").html("隐藏");
	}
}

// ajax方法 url:访问路径， data：查询条件， dataType:返回数据类型，handler:执行成功后调用的回调函数
function $_ajax(url, data, dataType, handler) {
	$.ajax({
		url : url,
		data : data,
		dataType : dataType,
		success : handler,
		error : function() {
			//alertArtMsg("系统超时或查询错误！");
			//window.top.location.href = rootPath + "/jsp/tyjg/login/loginx.jsp";
		},
		timeout : 20000
	});
}

//打开窗体
function openArtWindow(winId, title, url, width, height, left, top, closeHandler, isLock) {
	//如果该窗体已经打开过，阻止该窗体继续打开
	if($_artWin[winId] != null) {
		return;
	}
	//窗体配置对象
	var config = {
		title: title,
		width: width,
		height: height,
		left: left,
		top: top,
		isLock: isLock == null ? false : isLock,
		close: function () {
			if($_artWin[$_artWin[winId].config.id + "_childrenWin"] != null) {
				closeAtrWin($_artWin[winId].config.id + "_childrenWin");
			}
			$_artWin[winId] = null;
			delete $_artWin[winId];
			if(closeHandler != null) {
				closeHandler();
			}
		}
	};
	//打开窗体
	$_artWin[winId] = $.dialog.open(url, config);
}

//打开子窗体
function openChildrenAtrWindow(pwid, title, url, width, height, closeHandler, isLock) {
	var pwidth = $_artWin[pwid].config.width;
	var left = 0;
	if(typeof($_artWin[pwid].config.left) == "string") {
		left = document.body.clientWidth/2 + pwidth/2 ;
	} else {
		left = $_artWin[pwid].config.left + pwidth + 10;
	}
	var top = $_artWin[pwid].config.top;
	var id = $_artWin[pwid].config.id + "_childrenWin";
	openArtWindow(id, title, url, width, height, left, top, closeHandler, isLock)
}

//关闭窗体对象
function closeAtrWin(winId) {
	if($_artWin[winId] == null) {
		return;
	}
	$_artWin[winId].close();
	$_artWin[winId] = null;
	delete $_artWin[winId];
}

//关闭所有窗口
function closeAllArtWin() {
	for(winId in $_artWin) {
		closeAtrWin(winId);
	}
}

//弹出提示信息窗口
function alertArtMsg(msg) {
	$.dialog.alert(msg);
}

//为容器添加HTML
function addHtml(id, html) {
	$("#"+id).html("");
	$("#"+id).html(html);
}