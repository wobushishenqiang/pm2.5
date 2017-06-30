<%@ page language="java" import="java.util.*,java.sql.*" contentType="text/html;charset=gb2312"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
      
<!doctype html>
<html lang="en" class="fullscreen-bg">

<head>
	<title>注册|pm2.5监测与预测</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet" href="assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="assets/css/main.css">
	<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
	<link rel="stylesheet" href="assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700" rel="stylesheet">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
	<link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png">
</head>
        <script>  
            function addCheck(){  
            	var email=document.getElementById("email").value
                var username=document.getElementById("username").value;  
                var password=document.getElementById("password").value;  
                var password2=document.getElementById("passwordcheck").value; 
                var place=document.getElementById("place").value; 
                var checkEmail=/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/; 
                if(email==""){  
                	alert('邮箱不能为空！');
                    document.getElementById("email").focus();    
                    return false;  
                    }  
                if(!checkEmail.test(email)){
                	alert('请输入正确邮箱！');
                    document.getElementById("email").focus();    
                    return false;  
                }
               if(username==""){  
                    alert("用户名不能为空!");  
                    document.getElementById("username").focus();    
                    return false;  
                    }  
                if(password==""){  
                    alert("密码不能为空!");  
                     document.getElementById("password").focus();  
                     return false;  
                     }  
                if(password!=password2){
                	alert("请确认密码");
                	document.getElementByID("passwordcheck").focus();
                	return false;
                }
                return true;
            }  
        </script>  
<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<div class="vertical-align-wrap">
			<div class="vertical-align-middle">
				<div class="auth-box ">
					<div class="left">
						<div class="content">
							<div class="header">
								<div class="logo text-center"><img src="assets/img/logo-dark.png" alt="Klorofil Logo"></div>
								<p class="lead">Create a new account</p>
							</div>
							
							<form class="form-auth-small" action="CheckRegister.jsp" onsubmit="return addCheck();" >
								<div class="form-group">
									<label for="signin-email" class="control-label sr-only">Email</label>
									<input type="text" class="form-control" id="signin-email" value="" name="email" placeholder="email">
								</div>
								<div class="form-group">
									<label for="signin-username" class="control-label sr-only">UserName</label>
									<input type="text" class="form-control" id="signin-username" value="" name="username" placeholder="username">
								</div>
								
								<div class="form-group">
									<label for="signin-place" class="control-label sr-only">place</label>
									<select  class="form-control" id="signin-place"  name="place" >
									<option value="beijing">beijing
									<option value="nanjing">nanjing
									<option value="shanghai">shanghai
									<option value="tianjin">tianjin
									<option value="wuhan">wuhan
									<option value="shenzhen">shenzhen
									<option value="guangzhou">guangzhou
									<option value="chengdu">chengdu
									<option value="xian">xian
									<option value="hangzhou">hangzhou
									</select>
								</div>
								<div class="form-group">
									<label for="signin-password" class="control-label sr-only">Password</label>
									<input type="password" class="form-control" id="signin-password" value="" size=6 name="password" placeholder="password(only six number)">
								    <input type="password" class="form-control" id="signin-password" value="" size=6 name="passwordcheck" placeholder=" re-entering the password">
								</div>
								<div class="form-group">
								<input type="submit"  class="btn btn-primary btn-lg btn-block" id="Sign up" value="Sign Up" />
								</div>
							</form>
						</div>
					</div>
					<div class="right">
						<div class="overlay"></div>
						<div class="content text">
							<h1 class="heading">PM2.5 is bad for our health</h1>
							<p>Monitoring and Prevention</p>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- END WRAPPER -->
</body>

</html>