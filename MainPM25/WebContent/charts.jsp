<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="gb2312"%>
<%@ page language="java" import="java.sql.Statement,java.sql.*,java.util.ArrayList,java.util.List,java.lang.String"%>
<html>
<head>
	<title>历史数据|PM2.5监测与预测</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
	<link rel="stylesheet" href="assets/vendor/chartist/css/chartist-custom.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="assets/css/main.css">
	<link rel="stylesheet" href="assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700" rel="stylesheet">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
	<link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png">
</head>

<body>
<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="brand">
				<a href="index.html"><img src="assets/img/logo-dark.png" alt=" " class="img-responsive logo"></a>
			</div>
			<div class="container-fluid">
				<div class="navbar-btn">
				</div>
				<form class="navbar-form navbar-left">
					<div class="input-group">
					</div>
				</form>
				<div class="navbar-btn navbar-btn-right">
					<a class="btn btn-success update-pro" href="#" title="Upgrade to Pro" target="_blank"><i class="fa fa-rocket"></i> <span>UPGRADE TO PRO</span></a>
				</div>
				<div id="navbar-menu">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="typography.jsp"><i class="lnr lnr-question-circle"></i> <span>Help</span></a>
						</li>
						<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="assets/img/user.png" class="img-circle" alt="Avatar"><span><%if(session.getAttribute("username")==null){%>登录<%}else {out.print(session.getAttribute("username"));%></span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
							<ul class="dropdown-menu">
								<li><a href="#"><i class="lnr lnr-user"></i> <span>My Profile</span></a></li>
								<li><a href="#"><i class="lnr lnr-envelope"></i> <span>Message</span></a></li>
								<li><a href="#"><i class="lnr lnr-cog"></i> <span>Settings</span></a></li>
							</ul><%} %>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-scroll">
				<nav>
					<ul class="nav">
						<li><a href="index.jsp" class=""><i class="lnr lnr-home"></i> <span>实时天气</span></a></li>
						<li><a href="charts.jsp" class="active"><i class="lnr lnr-chart-bars"></i> <span>历史数据</span></a></li>
						<li><a href="tables.jsp" class=""><i class="lnr lnr-dice"></i> <span>预测数据</span></a></li>
						<li>
							<a href="#subPages" data-toggle="collapse" class="collapsed"><i class="lnr lnr-file-empty"></i> <span>登录/注册</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
							<div id="subPages" class="collapse ">
								<ul class="nav">
									<li><a href="Login.jsp" class="">登录</a></li>
									<li><a href="Register.jsp" class="">注册</a></li>
								</ul>
							</div>
						</li>
						
						<li><a href="typography.jsp" class=""><i class="lnr lnr-text-format"></i> <span>网站说明</span></a></li>
					</ul>
				</nav>
			</div>
		</div>
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<h2  class="text-primary">pm2.5监测</h2>
					<div class="row">
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">北京</h3>
								</div>
								<div class="panel-body">
									<div id="demo-line-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">南京</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">广州</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar1-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">上海</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar2-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">西安</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar3-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">武汉</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar4-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">天津</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar5-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">深圳</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar6-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">杭州</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar7-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">成都</h3>
								</div>
								<div class="panel-body">
									<div id="demo-bar8-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<footer>

		</footer>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/vendor/chartist/js/chartist.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>	
	<script charset=utf-8>	
	$(function() {        
		var options;   
		       <%
		       int[] arrB=new int[10];
		       String[] timeB=new String[10];
		       int[] arrN=new int[10];
		       String[] timeN=new String[10];
		       int[] arrG=new int[10];
		       String[] timeG=new String[10];
		       int[] arrS=new int[10];
		       String[] timeS=new String[10];
		       int[] arrX=new int[10];
		       String[] timeX=new String[10];
		       int[] arrW=new int[10];
		       String[] timeW=new String[10];
		       int[] arrT=new int[10];
		       String[] timeT=new String[10];
		       int[] arrZ=new int[10];
		       String[] timeZ=new String[10];
		       int[] arrH=new int[10];
		       String[] timeH=new String[10];
		       int[] arrC=new int[10];
		       String[] timeC=new String[10];
		       try{
			   Class.forName("org.gjt.mm.mysql.Driver");
			   System.out.println("加载Mysql Driver成功!");
			   }
			   catch(Exception e)
			   {
			   System.out.println("加载Mysql Driver失败!");
			   e.printStackTrace();
			   }
		       String subString;	  
			   System.out.println("开始连接Mysql server!<br>");
			   Connection connect=DriverManager.getConnection("jdbc:mysql://4.4.1.134/pm25?user=root&password=HHU123&useUnicode=true&characterEncoding=gb2312");		  
			   System.out.println("成功连接Mysql server!");
			     Statement B = connect.createStatement();
			     ResultSet rs=B.executeQuery("select time,PM25VALUE from pm25table where name='东四' ORDER BY id DESC LIMIT 0,10");

			     System.out.print("读取数据如下：");
			     int j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeB[j]=rs.getString(1).substring(6,16);
			    		 arrB[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeB[j]=rs.getString(1).substring(6,16);
					       arrB[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
			       timeB[j]=rs.getString(1).substring(10,16);
			       arrB[j]=Integer.parseInt(rs.getString(2));
			       j++;
			     }%>
			     var dataB = {
						    labels: ['<%=timeB[9]%>', '<%=timeB[8]%>', '<%=timeB[7]%>', '<%=timeB[6]%>', '<%=timeB[5]%>', '<%=timeB[4]%>', '<%=timeB[3]%>', '<%=timeB[2]%>', '<%=timeB[1]%>','<%=timeB[0]%>'],
					     	series:[
							[<%=arrB[9]%>, <%=arrB[8]%>, <%=arrB[7]%>,<%=arrB[6]%>, <%=arrB[5]%>, <%=arrB[4]%>,<%=arrB[3]%>,<%=arrB[2]%>, <%=arrB[1]%>, <%=arrB[0]%>]]};
			     <%
			     Statement N = connect.createStatement();
			     rs=N.executeQuery("select time,PM25VALUE from pm25table where name='中华门' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    		 boolean isNum = rs.getString(2).matches("[0-9]+");
				    	 if(!isNum){
				    		 timeN[j]=rs.getString(1).substring(6,16);
				    		 arrN[j]=0;
				    		 continue;
				    	 }
				    	 if(j==0){
					       timeN[j]=rs.getString(1).substring(6,16);
					       arrN[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;					       
			    	 }
				       timeN[j]=rs.getString(1).substring(10,16);

				       arrN[j]=Integer.parseInt(rs.getString(2));
				       j++;
				 }

                %>			   	            
	            var dataN = {
	    			    labels: ['<%=timeN[9]%>', '<%=timeN[8]%>', '<%=timeN[7]%>', '<%=timeN[6]%>', '<%=timeN[5]%>', '<%=timeN[4]%>', '<%=timeN[3]%>', '<%=timeN[2]%>', '<%=timeN[1]%>','<%=timeN[0]%>'],
	    		     	series:[
	    				[<%=arrN[9]%>, <%=arrN[8]%>, <%=arrN[7]%>,<%=arrN[6]%>, <%=arrN[5]%>, <%=arrN[4]%>,<%=arrN[3]%>,<%=arrN[2]%>, <%=arrN[1]%>, <%=arrN[0]%>]]};
			     <%
	             Statement G = connect.createStatement();
			     rs=G.executeQuery("select time,PM25VALUE from pm25table where name='市监测站' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeG[j]=rs.getString(1).substring(6,16);
			    		 arrG[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeG[j]=rs.getString(1).substring(6,16);
					       arrG[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeG[j]=rs.getString(1).substring(10,16);
				       arrG[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }
                %>
	            var dataG = {
	    			    labels: ['<%=timeG[9]%>', '<%=timeG[8]%>', '<%=timeG[7]%>', '<%=timeG[6]%>', '<%=timeG[5]%>', '<%=timeG[4]%>', '<%=timeG[3]%>', '<%=timeG[2]%>', '<%=timeG[1]%>','<%=timeG[0]%>'],
	    		     	series:[
	    				[<%=arrG[9]%>, <%=arrG[8]%>, <%=arrG[7]%>,<%=arrG[6]%>, <%=arrG[5]%>, <%=arrG[4]%>,<%=arrG[3]%>,<%=arrG[2]%>, <%=arrG[1]%>, <%=arrG[0]%>]]};
	            <%
	             Statement S = connect.createStatement();
			     rs=S.executeQuery("select time,PM25VALUE from pm25table where name='普陀' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeS[j]=rs.getString(1).substring(6,16);
			    		 arrS[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeS[j]=rs.getString(1).substring(6,16);
					       arrS[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeS[j]=rs.getString(1).substring(10,16);
				       arrS[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }
	            
	            %>
	            var dataS = {
	    			    labels: ['<%=timeS[9]%>', '<%=timeS[8]%>', '<%=timeS[7]%>', '<%=timeS[6]%>', '<%=timeS[5]%>', '<%=timeS[4]%>', '<%=timeS[3]%>', '<%=timeS[2]%>', '<%=timeS[1]%>','<%=timeS[0]%>'],
	    		     	series:[
	    				[<%=arrS[9]%>, <%=arrS[8]%>, <%=arrS[7]%>,<%=arrS[6]%>, <%=arrS[5]%>, <%=arrS[4]%>,<%=arrS[3]%>,<%=arrS[2]%>, <%=arrS[1]%>, <%=arrS[0]%>]]};
	            <%
	            Statement X = connect.createStatement();
			     rs=X.executeQuery("select time,PM25VALUE from pm25table where name='经开区' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeX[j]=rs.getString(1).substring(6,16);
			    		 arrX[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeX[j]=rs.getString(1).substring(6,16);
					       arrX[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeX[j]=rs.getString(1).substring(10,16);
				       arrX[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }%>
				     var dataX = {
			    			    labels: ['<%=timeX[9]%>', '<%=timeX[8]%>', '<%=timeX[7]%>', '<%=timeX[6]%>', '<%=timeX[5]%>', '<%=timeX[4]%>', '<%=timeX[3]%>', '<%=timeX[2]%>', '<%=timeX[1]%>','<%=timeX[0]%>'],
			    		     	series:[
			    				[<%=arrX[9]%>, <%=arrX[8]%>, <%=arrX[7]%>,<%=arrX[6]%>, <%=arrX[5]%>, <%=arrX[4]%>,<%=arrX[3]%>,<%=arrX[2]%>, <%=arrX[1]%>, <%=arrX[0]%>]]}; 
				     <%
			     Statement W = connect.createStatement();
			     rs=W.executeQuery("select time,PM25VALUE from pm25table where name='汉口花桥' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeW[j]=rs.getString(1).substring(6,16);
			    		 arrW[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeW[j]=rs.getString(1).substring(6,16);
					       arrW[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeW[j]=rs.getString(1).substring(10,16);
				       arrW[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }%>
				     var dataW = {
			    			    labels: ['<%=timeW[9]%>', '<%=timeW[8]%>', '<%=timeW[7]%>', '<%=timeW[6]%>', '<%=timeW[5]%>', '<%=timeW[4]%>', '<%=timeW[3]%>', '<%=timeW[2]%>', '<%=timeW[1]%>','<%=timeW[0]%>'],
			    		     	series:[
			    				[<%=arrW[9]%>, <%=arrW[8]%>, <%=arrW[7]%>,<%=arrW[6]%>, <%=arrW[5]%>, <%=arrW[4]%>,<%=arrW[3]%>,<%=arrW[2]%>, <%=arrW[1]%>, <%=arrW[0]%>]]};    
				     <%
			     Statement T = connect.createStatement();
			     rs=T.executeQuery("select time,PM25VALUE from pm25table where name='津沽路' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){

			    		 boolean isNum = rs.getString(2).matches("[0-9]+");
				    	 if(!isNum){
				    		 timeT[j]=rs.getString(1).substring(6,16);
				    		 arrT[j]=0;
				    		 continue;
				    	 }
				    	 if(j==9){
					       timeT[j]=rs.getString(1).substring(6,16);
					       arrT[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeT[j]=rs.getString(1).substring(10,16);
				       arrT[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }%>
				     var dataT = {
			    			    labels: ['<%=timeT[9]%>', '<%=timeT[8]%>', '<%=timeT[7]%>', '<%=timeT[6]%>', '<%=timeT[5]%>', '<%=timeT[4]%>', '<%=timeT[3]%>', '<%=timeT[2]%>', '<%=timeT[1]%>','<%=timeT[0]%>'],
			    		     	series:[
			    				[<%=arrT[9]%>, <%=arrT[8]%>, <%=arrT[7]%>,<%=arrT[6]%>, <%=arrT[5]%>, <%=arrT[4]%>,<%=arrT[3]%>,<%=arrT[2]%>, <%=arrT[1]%>, <%=arrT[0]%>]]};   
				     <%
			     Statement Z = connect.createStatement();
			     rs=Z.executeQuery("select time,PM25VALUE from pm25table where name='盐田' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeZ[j]=rs.getString(1).substring(6,16);
			    		 arrZ[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeZ[j]=rs.getString(1).substring(6,16);
					       arrZ[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
			    	 if(rs.getString(2)=="_"){
			    		 timeZ[j]=rs.getString(1).substring(10,16);
			    		 arrZ[j]=0;
			    	 }
				       timeZ[j]=rs.getString(1).substring(10,16);
				       arrZ[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }%>
				     var dataZ = {
			    			    labels: ['<%=timeZ[9]%>', '<%=timeZ[8]%>', '<%=timeZ[7]%>', '<%=timeZ[6]%>', '<%=timeZ[5]%>', '<%=timeZ[4]%>', '<%=timeZ[3]%>', '<%=timeZ[2]%>', '<%=timeZ[1]%>','<%=timeZ[0]%>'],
			    		     	series:[
			    				[<%=arrZ[9]%>, <%=arrZ[8]%>, <%=arrZ[7]%>,<%=arrZ[6]%>, <%=arrZ[5]%>, <%=arrZ[4]%>,<%=arrZ[3]%>,<%=arrZ[2]%>, <%=arrZ[1]%>, <%=arrZ[0]%>]]};   
				     <%
			     Statement H = connect.createStatement();
			     rs=H.executeQuery("select time,PM25VALUE from pm25table where name='卧龙桥' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeH[j]=rs.getString(1).substring(6,16);
			    		 arrH[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeH[j]=rs.getString(1).substring(6,16);
					       arrH[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeH[j]=rs.getString(1).substring(10,16);
				       arrH[j]=Integer.parseInt(rs.getString(2));
				       j++;
				     }%>
				     var dataH = {
			    			    labels: ['<%=timeH[9]%>', '<%=timeH[8]%>', '<%=timeH[7]%>', '<%=timeH[6]%>', '<%=timeH[5]%>', '<%=timeH[4]%>', '<%=timeH[3]%>', '<%=timeH[2]%>', '<%=timeH[1]%>','<%=timeH[0]%>'],
			    		     	series:[
			    				[<%=arrH[9]%>, <%=arrH[8]%>, <%=arrH[7]%>,<%=arrH[6]%>, <%=arrH[5]%>, <%=arrH[4]%>,<%=arrH[3]%>,<%=arrH[2]%>, <%=arrH[1]%>, <%=arrH[0]%>]]};   
				     <%
			     Statement C = connect.createStatement();
			     rs=C.executeQuery("select time,PM25VALUE from pm25table where name='梁家巷' ORDER BY id DESC LIMIT 0,10");
			     j=0;
			     while(rs.next()){
			    	 boolean isNum = rs.getString(2).matches("[0-9]+");
			    	 if(!isNum){
			    		 timeC[j]=rs.getString(1).substring(6,16);
			    		 arrC[j]=0;
			    		 continue;
			    	 }
			    	 if(j==0){
					       timeC[j]=rs.getString(1).substring(6,16);
					       arrC[j]=Integer.parseInt(rs.getString(2));
					       j++;
					       continue;
			    	 }
				       timeC[j]=rs.getString(1).substring(10,16);
				       arrC[j]=Integer.parseInt(rs.getString(2));
				       j++;
				       }
			     connect.close();
	            %>	  
	            var dataC = {
	    			    labels: ['<%=timeC[9]%>', '<%=timeC[8]%>', '<%=timeC[7]%>', '<%=timeC[6]%>', '<%=timeC[5]%>', '<%=timeC[4]%>', '<%=timeC[3]%>', '<%=timeC[2]%>', '<%=timeC[1]%>','<%=timeC[0]%>'],
	    		     	series:[
	    				[<%=arrC[9]%>, <%=arrC[8]%>, <%=arrC[7]%>,<%=arrC[6]%>, <%=arrC[5]%>, <%=arrC[4]%>,<%=arrC[3]%>,<%=arrC[2]%>, <%=arrC[1]%>, <%=arrC[0]%>]]};
		options = {
			height: "300px",
			showPoint: true,
			axisX: {
				showGrid: false
			},
			lineSmooth: true,
		};
		new Chartist.Line('#demo-line-chart', dataB, options);
		options = {
			height: "300px",
			axisX: {
				showGrid: false
			},
		};

		new Chartist.Line('#demo-bar-chart', dataN, options);
		options = {
			height: "270px",
			showArea: false,
			showLine: true,
			showPoint: true,
			axisX: {
				showGrid: false
			},
			lineSmooth: true,
		};
		new Chartist.Line('#demo-bar1-chart', dataG, options);
		options = {
				height: "270px",
				axisX: {
					showGrid: false
				},
			};
		    new Chartist.Line('#demo-bar2-chart', dataX, options);
			new Chartist.Line('#demo-bar3-chart', dataX, options);
			new Chartist.Line('#demo-bar4-chart', dataW, options);
			new Chartist.Line('#demo-bar5-chart', dataT, options);
			new Chartist.Line('#demo-bar6-chart', dataZ, options);
			new Chartist.Line('#demo-bar7-chart', dataH, options);
			new Chartist.Line('#demo-bar8-chart', dataC, options);


	});
	</script>
</body>
</html>