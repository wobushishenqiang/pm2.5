<%@ page contentType="text/html; charset=gb2312"%>
<%@ page language="java" import="java.util.*,java.sql.*,java.net.*,java.lang.String" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
<head>
	<title>登录/注册</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
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
      <body>  
        <%      //接收用户名和密码    
				String user = request.getParameter("username");    
				String pwd = request.getParameter("password");  
      			session.setAttribute("username", user);
                try{
                	Class.forName("com.mysql.jdbc.Driver");//加载驱动   
                }catch(Exception e){
                	out.println("JDBC没有驱动好");
                }
                try{
                String url = "jdbc:mysql://4.4.1.134/pm25?user=root&password=HHU123&useUnicode=true&characterEncoding=gb2312"; 
                Connection conn = DriverManager.getConnection(url);//得到连接  
                PreparedStatement pStmt = conn.prepareStatement("select password from persons where username = '" + user + "'");  
                ResultSet rs = pStmt.executeQuery();  

                if(rs.next()){  
                   if(rs.getString(1).equals(pwd))
                   response.sendRedirect("index.jsp?username="+user);  
                }else{  
                     response.sendRedirect("Register.jsp?errNo");//密码不对返回到登陆    
                }  
         rs.close();  
         pStmt.close();  
         conn.close();  
                }catch(SQLException e){
                	out.print(e);
                }
         %>  
      </body>  
    </html>  