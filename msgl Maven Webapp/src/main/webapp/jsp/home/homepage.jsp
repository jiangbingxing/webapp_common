<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!-- <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script> -->
<script src="/msgl/lib/jquery/jquery.min.js"></script>
<script src="/msgl/lib/layui/layui.js"></script>
<script src="/msgl/js/larry.js"></script>
<script src="/msgl/js/index.js"></script>
<script src="/msgl/js/navtab.js"></script>
<link rel="stylesheet" href="/msgl/lib/layui/css/layui.css">
<link rel="stylesheet" href="/msgl/css/common.css">
<link rel="stylesheet" href="/msgl/css/global.css">
<link rel="stylesheet" href="/msgl/css/admin.css">
<title>大数据平台体系合规系统</title>
</head>
<script type="text/javascript">
	/*需要隐藏左侧菜单栏的模块ID*/
	var hideLeftModule = [100,5255757555];
	function containsHideModule(moduleId){
		for(var i = 0; i < hideLeftModule.length; i++){
			if(hideLeftModule[i] == moduleId)
				return true;
		}
		return false;
	}
	
	layui.use('element', function(){
		  var element = layui.element();
		  element.on('tab(demo)', function(data){
			  var showModuleId = $("#larry-tab .layui-this").attr("lay-id");
			  console.log($(".layui-this"))
			  console.log(showModuleId)
			  if(containsHideModule(showModuleId)){
				  hideLeftMenu();
			  } else {
				  showLeftMenu()
			  }
		  });
	});
	
	function hideLeftMenu(){
	/* 	$('#larry-side').removeClass("left-menu-width-show")
		$('#larry-side').addClass("left-menu-width-hide") */
		$('#larry-body').removeClass("center-width-show")
		$('#larry-body').addClass("center-width-hide")
		$('.layui-footer').removeClass("center-width-show")
		$('.layui-footer').addClass("center-width-hide")
		$('#larry-side').hide();
	}

	function showLeftMenu(){
/* 		$('#larry-side').removeClass("left-menu-width-hide")
		$('#larry-side').addClass("left-menu-width-show") */
		$('#larry-body').removeClass("center-width-hide")
		$('#larry-body').addClass("center-width-show")
		$('.layui-footer').removeClass("center-width-hide")
		$('.layui-footer').addClass("center-width-show")
		$('#larry-side').show();
	}
	
	/*初始化后加载*/
	$(function() {
		setContentHeight();
		$.post("system/privilege.action", function(data) {
			fill(data);
			defaultClick()
		})
		$("#larry-nav-side").click();
	})
	/*默认点击项目*/
	function defaultClick() {
		var defaultTop = $("a[module-id='1']")
		var defaultLeft = $("li[module-id='100']")
		defaultTop.click();
		hideLeftMenu()
	}

	function fill(str) {
		var data = JSON.parse(str);
		var topNav = $("#top-nav");
		var leftNav = $("#left-nav");
		for (var i = 0; i < data.length; i++) {
			var module = data[i];
			var parent = module.parent;

			var parentId = parent.id
			var parentTitle = parent.name

			var topLiHtml = $("<li class='layui-nav-item'></li>")
			var topAHtml = $("<a module-id='" + parentId + "' href='javascript:void(0)' onclick='topClick(this)'>" + parentTitle + "</a>")

			topLiHtml.append(topAHtml)

			topNav.append(topLiHtml)

			var children = module.children;
			for (var j = 0; j < children.length; j++) {
				var child = children[j];
				var childId = child.id;
				var childTitle = child.name;
				var childParentId = child.parentId;
				var childUrl = child.url;
				var leftLiHtml = $("<li module-id='" + childId + "' class='layui-nav-item parent_id_" + childParentId + "'></li>")
				var leftAHtml = $("<a href='javascript:void(0)' data-url=" + childUrl + ">")
				var leftSpanHtml = $("<span>" + childTitle + "</span>")

				leftAHtml.append(leftSpanHtml)
				leftLiHtml.append(leftAHtml)

				leftNav.append(leftLiHtml)
			}

		}

	}

	function topClick(aEle) {
		$("#top-nav").find("li").removeClass("layui-this")
		var top = $(aEle);
		top.parent().addClass("layui-this")

		var parentId = top.attr("module-id")
		$("#left-nav").find("li").hide();
		$("#left-nav").find("li.parent_id_" + parentId).show();
		
		$("#larry-nav-side").click();
		//点击上侧父模块后，默认点击左侧第一个模块
		$("#left-nav").find("li.parent_id_" + parentId).first().click();
	}
	
	function setContentHeight(){
		var totalHeight = $(window).height();
		var topHeight = $("div.layui-main").height();
		var buttomHeight = $("div.layui-footer").height();
		var tableHeight = $("ul.layui-tab-title").height();
    	$('#larry-tab .layui-tab-content').height(totalHeight - topHeight - buttomHeight - tableHeight)
    }
</script>
<body style="width:100%;height:100%">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header header header-demo">
			<div class="layui-main">
				<div class="admin-logo-box">
					<a class="logo"> <img src="/msgl/images/system/bigdata.gif">
					</a>
				</div>
				<div class="layui-larry-menu">
					<!-- 顶部菜单选项#开始-->
					<ul class="layui-nav clearfix" id="top-nav"></ul>
					<!-- 顶部菜单选项#结束-->
				</div>
			</div>
			<!-- 顶部菜单右侧用户信息#开始-->
			<ul class="layui-nav larry-header-item">
				<li class="layui-nav-item" id="refresh_iframe"><a
					href="javascript:;"><i style="font-size: 30px; color: #FF5722;"
						class="layui-icon">&#x1002;</i></a></li>
				<li class="layui-nav-item"><a href="system/logout.action"> 退出 </a></li>
			</ul>
			<!-- 顶部菜单右侧用户信息#结束-->
		</div>
		<!-- 顶部菜单#结束 -->

		<!-- 左侧导航#开始 -->
		<div class="layui-side layui-side-bg layui-larry-side" id="larry-side">
			<div class="layui-side-scroll" id="larry-nav-side" lay-filter="side">
				<!-- 左侧菜单 -->
				<ul class="layui-nav layui-nav-tree" id="left-nav"></ul>
			</div>
		</div>
		<!-- 左侧导航#结束 -->

		<!-- 主体内容#开始 -->
		<div class="layui-body" id="larry-body" style="bottom: 0;border-left:solid 2px #1AA094;">
			<div class="layui-tab layui-tab-card larry-tab-box" id="larry-tab"
				lay-filter="demo" lay-allowclose="true">
				<ul class="layui-tab-title">
					<li lay-id="100" class="layui-this"><em>图表</em><i
						class="layui-icon layui-unselect layui-tab-close"></i></li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<iframe class="larry-iframe" data-id='0' src="report/initchartpage.action"></iframe>
					</div>
				</div>
			</div>
		</div>
		<!-- 主体内容#结束 -->

		<!-- 底部区域#开始 -->
		<div class="layui-footer layui-larry-foot"></div>
		<!-- 底部区域#结束 -->
	</div>
</body>

</html>
