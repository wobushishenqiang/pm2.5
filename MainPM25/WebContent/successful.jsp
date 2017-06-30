<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
    <%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
    %>  
      
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
    <html>  
      <head>  
        <base href="<%=basePath%>">  
        <title>登录成功</title>  
        <meta http-equiv="pragma" content="no-cache">  
        <meta http-equiv="cache-control" content="no-cache">  
        <meta http-equiv="expires" content="0">      
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
        <meta http-equiv="description" content="This is my page">  
        <!-- 
        <link rel="stylesheet" type="text/css" href="styles.css"> 
        -->  
      </head>  
      <body>  
        <center>  
        <%  
         String name = request.getParameter("username");  
         out.println("登录成功！");  
        %><br>  
        <form action="index.html">
			<div class="input-group">
			<input type="password" class="form-control" placeholder="返回">
			<span class="input-group-btn"><button type="submit" class="btn btn-primary"><i class="fa fa-arrow-right"></i></button></span>
			</div>
		</form>
        <a href="Register.jsp">重新登陆</a>  
        </center>  
      </body>  
    </html>  