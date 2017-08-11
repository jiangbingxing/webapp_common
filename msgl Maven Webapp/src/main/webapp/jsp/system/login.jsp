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
<title>登录</title>
<script src="/msgl/lib/jquery/jquery.min.js"></script>
<script src="/msgl/lib/bootstrap/md5.min.js"></script>  
<link href="/msgl/lib/bootstrap/bootstrap.css" rel="stylesheet">
<script src="/msgl/lib/bootstrap/bootstrap.min.js"></script>
<%@include file="../../../common/main.htm"%>
</head>
<body>
	<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
</head>
<STYLE TYPE="text/css">
body {
	background-image: URL(/msgl/images/system/login-in-background.jpg);
	background-position: center;
	background-repeat: no-repeat;
	background-attachment: fixed;
}

.logo-style {
	font-size: 18px;
	font-weight: blod;
	color: #0080c0;
}
.login-label{
	color: #0080c0;
}
</STYLE>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 div-container">
				<div>
					<img height="50" src="/msgl/images/system/logo.gif" />
					<p style="float:right">
						<strong class="logo-style">中国移动四川分公司</br>大数据平台体系合规系统
						</strong>
					</p>
					<hr />
					<div id="login-form" >
						<div class="form-group">
							<label for="account" class="login-label">账号：</label> <input type="text"
								class="form-control" id="user_name" name="user_name"
								placeholder="请输入您的账号">
							<lable class="error-hint" style="display:none; color:red">账号或密码错误</lable>
						</div>
						<div class="form-group">
							<label for="password" class="login-label">密码：</label> <input type="password"
								class="form-control" id="user_password" name="user_password"
								placeholder="请输入您的密码">
						</div>
						<br />
						<!-- <div class="form-group">
							<a href="#" style="float:right;font-size:12px">忘记密码>></a>
						</div> -->
						<br />
						<div class="form-group">
							<button type="submit" id="btn_submit" class="col-md-12 btn btn-primary">登录</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">


$('#btn_submit').click(function(){
	getLoginInfo();
});

function showErrorHint(){
	$(".error-hint").show();
}

function getLoginInfo(){
	var userName = $("#user_name").val();
	var userPassword = md5($("#user_password").val());
	$.post("system/loginvalidate.action",{user_name : userName,user_password : userPassword},
		function(data){
			if(data == "failed"){
				showErrorHint();
			} else if (data == "success"){
				window.location.href = "<%=basePath%>system/homepage.action";
			} else {
				alert("ERROR!");
			}
		}
	)
}



</script>


</html>