package sendemail;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class sendEmail {
  public static void main(String[] args) throws ParseException{
  long daySpan = 24*60*60*1000;  
  // 规定的每天时间运行
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
  System.out.println(new Date(System.currentTimeMillis()));
  
  Date startTime = sdf.parse("2017/6/22 07:00:00"); 

  // 如果今天的已经过了 首次运行时间就改为明天
  //if(System.currentTimeMillis()>startTime.getTime())
   //startTime = new Date(startTime.getTime()+daySpan);
   
  Timer timer = new Timer();
  timer.schedule(new TimerTask(){
	  public void run(){
	       String messageText;
		   try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("加载Mysql Driver成功!");
		   }catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("加载Mysql Driver失败!");
		   }
		   try{
		   Connection connect=DriverManager.getConnection("jdbc:mysql://4.4.1.134/pm25?user=root&password=HHU123&useUnicode=true&characterEncoding=UTF-8&useSSL=true");		  
		   System.out.println("成功连接Mysql server!");
		   Statement B = connect.createStatement();
		   String conditionB="SELECT email FROM persons WHERE  place='beijing'";
		   ResultSet rs=B.executeQuery(conditionB);	
		   System.out.println("成功获取email!");
		   rs.last();
		   int rowNumber=rs.getRow();
		   String[] ReceiveB=new String[rowNumber];
		   rs.beforeFirst();
		   int j=0;
		   while(rs.next()){
			  System.out.println(rs.getString(1));
			  ReceiveB[j]=rs.getString(1);
			  j++;
		   }
		   System.out.println("成功赋值邮箱数组!");
		   Statement A = connect.createStatement();
		   String AB="SELECT AQI FROM pm25table WHERE  name='东四' ORDER BY id DESC LIMIT 0,1";
		   rs=A.executeQuery(AB);	
		   System.out.println("成功获取aqi!");
		   int AQI = 0;
		   while(rs.next()){
			   AQI=Integer.parseInt(rs.getString(1));
		   }	   		   
		   System.out.println("成功赋值");
		   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
		   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
		   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
		   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
		   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
		   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
		   String from="2337277290@qq.com";
	 		String subject="出行建议";
	        String password="ruomezdumiazdjbc";
	        String S =from;
	        int n =S.indexOf('@');
	        int m=S.length() ;
	        String server =S.substring(n+1,m);
	        //建立邮件会话
	 		Properties pro=new Properties();
	        pro.put("mail.smtp.host","smtp."+server);
	        pro.put("mail.smtp.auth","true");
	        pro.put("mail.smtp.port","587");
	        Session sess=Session.getInstance(pro);
	        sess.setDebug(true);
	        //新建一个消息对象
	        MimeMessage message=new MimeMessage(sess);
	        //设置发件人
	        InternetAddress from_mail=new InternetAddress(from);
	        message.setFrom(from_mail);
	        //设置收件人
	       InternetAddress[] send_mail = new InternetAddress[ReceiveB.length];
	       for (int i = 0; i < ReceiveB.length; i++) { 
	         System.out.println("发送到:" + ReceiveB[i]); 
	         send_mail[i] = new InternetAddress(ReceiveB[i]); 
	       } 
	       message.setRecipients(Message.RecipientType.TO ,send_mail);
	        //设置主题
	        message.setSubject(subject);
	        //设置内容
	        message.setText(messageText);
	        //设置发送时间
	        message.setSentDate(new Date());
	        //发送邮件
	        message.saveChanges();  //保存邮件信息
	        Transport transport =sess.getTransport("smtp");
	      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
			transport.connect("smtp.qq.com",from,password);
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	 		System.out.print("发送邮件成功");	 
	 		Statement N = connect.createStatement();
			   String conditionN="SELECT email FROM persons WHERE  place='nanjing'";
			   rs=N.executeQuery(conditionN);	
			   System.out.println("成功获取email!");
			   rs.last();
			   rowNumber=rs.getRow();
			   String[] ReceiveN=new String[rowNumber];
			   rs.beforeFirst();
			   j=0;
			   while(rs.next()){
				  System.out.println(rs.getString(1));
				  ReceiveN[j]=rs.getString(1);
				  j++;
			   }
			   System.out.println("成功赋值邮箱数组!");
			   String AN="SELECT AQI FROM pm25table WHERE  name='中华门' ORDER BY id DESC LIMIT 0,1";
			   rs=A.executeQuery(AN);	
			   System.out.println("成功获取aqi!");
			   while(rs.next()){
				   AQI=Integer.parseInt(rs.getString(1));
			   }	   		   
			   System.out.println("成功赋值");
			   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
			   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
			   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
			   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
			   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
			   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
		        //设置收件人
		       send_mail = new InternetAddress[ReceiveN.length];
		       for (int i = 0; i < ReceiveN.length; i++) { 
		         System.out.println("发送到:" + ReceiveN[i]); 
		         send_mail[i] = new InternetAddress(ReceiveN[i]); 
		       } 
		       message.setRecipients(Message.RecipientType.TO ,send_mail);
		        //设置主题
		        message.setSubject(subject);
		        //设置内容
		        message.setText(messageText);
		        //设置发送时间
		        message.setSentDate(new Date());
		        //发送邮件
		        message.saveChanges();  //保存邮件信息
		      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
				//  transport.connect("smtp.163.com",wode@163.com,"123456");
				transport.connect("smtp.qq.com",from,password);
		        transport.sendMessage(message,message.getAllRecipients());
		        transport.close();
		 		System.out.print("发送邮件成功");	 
		 		Statement G = connect.createStatement();
				   String conditionG="SELECT email FROM persons WHERE  place='guangzhou'";
				   rs=G.executeQuery(conditionG);	
				   System.out.println("成功获取email!");
				   rs.last();
				   rowNumber=rs.getRow();
				   String[] ReceiveG=new String[rowNumber];
				   rs.beforeFirst();
				   j=0;
				   while(rs.next()){
					  System.out.println(rs.getString(1));
					  ReceiveG[j]=rs.getString(1);
					  j++;
				   }
				   System.out.println("成功赋值邮箱数组!");
				   String AG="SELECT AQI FROM pm25table WHERE  name='市监测站' ORDER BY id DESC LIMIT 0,1";
				   rs=A.executeQuery(AG);	
				   System.out.println("成功获取aqi!");
				   while(rs.next()){
					   AQI=Integer.parseInt(rs.getString(1));
				   }	   		   
				   System.out.println("成功赋值");
				   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
				   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
				   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
				   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
				   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
				   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
			        //设置收件人
			       send_mail = new InternetAddress[ReceiveG.length];
			       for (int i = 0; i < ReceiveG.length; i++) { 
			         System.out.println("发送到:" + ReceiveG[i]); 
			         send_mail[i] = new InternetAddress(ReceiveG[i]); 
			       } 
			       message.setRecipients(Message.RecipientType.TO ,send_mail);
			        //设置主题
			        message.setSubject(subject);
			        //设置内容
			        message.setText(messageText);
			        //设置发送时间
			        message.setSentDate(new Date());
			        //发送邮件
			        message.saveChanges();  //保存邮件信息
			      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
					//  transport.connect("smtp.163.com",wode@163.com,"123456");
					transport.connect("smtp.qq.com",from,password);
			        transport.sendMessage(message,message.getAllRecipients());
			        transport.close();
			 		System.out.print("发送邮件成功");	
			 		Statement S1 = connect.createStatement();
					   String conditionS="SELECT email FROM persons WHERE  place='shanghai'";
					   rs=S1.executeQuery(conditionS);	
					   System.out.println("成功获取email!");
					   rs.last();
					   rowNumber=rs.getRow();
					   String[] ReceiveS=new String[rowNumber];
					   rs.beforeFirst();
					   j=0;
					   while(rs.next()){
						  System.out.println(rs.getString(1));
						  ReceiveS[j]=rs.getString(1);
						  j++;
					   }
					   System.out.println("成功赋值邮箱数组!");
					   String AS="SELECT AQI FROM pm25table WHERE  name='普陀' ORDER BY id DESC LIMIT 0,1";
					   rs=A.executeQuery(AS);	
					   System.out.println("成功获取aqi!");
					   while(rs.next()){
						   AQI=Integer.parseInt(rs.getString(1));
					   }	   		   
					   System.out.println("成功赋值");
					   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
					   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
					   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
					   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
					   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
					   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
				        //设置收件人
				       send_mail = new InternetAddress[ReceiveS.length];
				       for (int i = 0; i < ReceiveS.length; i++) { 
				         System.out.println("发送到:" + ReceiveS[i]); 
				         send_mail[i] = new InternetAddress(ReceiveS[i]); 
				       } 
				       message.setRecipients(Message.RecipientType.TO ,send_mail);
				        //设置主题
				        message.setSubject(subject);
				        //设置内容
				        message.setText(messageText);
				        //设置发送时间
				        message.setSentDate(new Date());
				        //发送邮件
				        message.saveChanges();  //保存邮件信息
				      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
						//  transport.connect("smtp.163.com",wode@163.com,"123456");
						transport.connect("smtp.qq.com",from,password);
				        transport.sendMessage(message,message.getAllRecipients());
				        transport.close();
				 		System.out.print("发送邮件成功");	
				 		/*Statement X = connect.createStatement();
						   String conditionX="SELECT email FROM persons WHERE  place='xian'";
						   rs=X.executeQuery(conditionN);	
						   System.out.println("成功获取email!");
						   rs.last();
						   rowNumber=rs.getRow();
						   String[] ReceiveX=new String[rowNumber];
						   rs.beforeFirst();
						   j=0;
						   while(rs.next()){
							  System.out.println(rs.getString(1));
							  ReceiveX[j]=rs.getString(1);
							  j++;
						   }
						   System.out.println("成功赋值邮箱数组!");
						   String AX="SELECT AQI FROM pm25table WHERE  name='经开区' ORDER BY id DESC LIMIT 0,1";
						   rs=A.executeQuery(AX);	
						   System.out.println("成功获取aqi!");
						   while(rs.next()){
							   AQI=Integer.parseInt(rs.getString(1));
						   }	   		   
						   System.out.println("成功赋值");
						   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
						   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
						   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
						   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
						   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
						   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
					        //设置收件人
					       send_mail = new InternetAddress[ReceiveN.length];
					       for (int i = 0; i < ReceiveX.length; i++) { 
					         System.out.println("发送到:" + ReceiveX[i]); 
					         send_mail[i] = new InternetAddress(ReceiveX[i]); 
					       } 
					       message.setRecipients(Message.RecipientType.TO ,send_mail);
					        //设置主题
					        message.setSubject(subject);
					        //设置内容
					        message.setText(messageText);
					        //设置发送时间
					        message.setSentDate(new Date());
					        //发送邮件
					        message.saveChanges();  //保存邮件信息
					      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
							//  transport.connect("smtp.163.com",wode@163.com,"123456");
							transport.connect("smtp.qq.com",from,password);
					        transport.sendMessage(message,message.getAllRecipients());
					        transport.close();
					 		System.out.print("发送邮件成功");	
					 		Statement W = connect.createStatement();
							   String conditionW="SELECT email FROM persons WHERE  place='wuhan'";
							   rs=W.executeQuery(conditionW);	
							   System.out.println("成功获取email!");
							   rs.last();
							   rowNumber=rs.getRow();
							   String[] ReceiveW=new String[rowNumber];
							   rs.beforeFirst();
							   j=0;
							   while(rs.next()){
								  System.out.println(rs.getString(1));
								  ReceiveW[j]=rs.getString(1);
								  j++;
							   }
							   System.out.println("成功赋值邮箱数组!");
							   String AW="SELECT AQI FROM pm25table WHERE  name='汉口花桥' ORDER BY id DESC LIMIT 0,1";
							   rs=A.executeQuery(AW);	
							   System.out.println("成功获取aqi!");
							   while(rs.next()){
								   AQI=Integer.parseInt(rs.getString(1));
							   }	   		   
							   System.out.println("成功赋值");
							   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
							   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
							   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
							   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
							   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
							   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
						        //设置收件人
						       send_mail = new InternetAddress[ReceiveW.length];
						       for (int i = 0; i < ReceiveW.length; i++) { 
						         System.out.println("发送到:" + ReceiveW[i]); 
						         send_mail[i] = new InternetAddress(ReceiveW[i]); 
						       } 
						       message.setRecipients(Message.RecipientType.TO ,send_mail);
						        //设置主题
						        message.setSubject(subject);
						        //设置内容
						        message.setText(messageText);
						        //设置发送时间
						        message.setSentDate(new Date());
						        //发送邮件
						        message.saveChanges();  //保存邮件信息
						      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
								//  transport.connect("smtp.163.com",wode@163.com,"123456");
								transport.connect("smtp.qq.com",from,password);
						        transport.sendMessage(message,message.getAllRecipients());
						        transport.close();
						 		System.out.print("发送邮件成功");	
						 		Statement T = connect.createStatement();
								   String conditionT="SELECT email FROM persons WHERE  place='tianjin'";
								   rs=T.executeQuery(conditionT);	
								   System.out.println("成功获取email!");
								   rs.last();
								   rowNumber=rs.getRow();
								   String[] ReceiveT=new String[rowNumber];
								   rs.beforeFirst();
								   j=0;
								   while(rs.next()){
									  System.out.println(rs.getString(1));
									  ReceiveT[j]=rs.getString(1);
									  j++;
								   }
								   System.out.println("成功赋值邮箱数组!");
								   String AT="SELECT AQI FROM pm25table WHERE  name='津沽路' ORDER BY id DESC LIMIT 0,1";
								   rs=A.executeQuery(AT);	
								   System.out.println("成功获取aqi!");
								   while(rs.next()){
									   AQI=Integer.parseInt(rs.getString(1));
								   }	   		   
								   System.out.println("成功赋值");
								   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
								   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
								   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
								   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
								   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
								   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
							        //设置收件人
							       send_mail = new InternetAddress[ReceiveT.length];
							       for (int i = 0; i < ReceiveT.length; i++) { 
							         System.out.println("发送到:" + ReceiveT[i]); 
							         send_mail[i] = new InternetAddress(ReceiveT[i]); 
							       } 
							       message.setRecipients(Message.RecipientType.TO ,send_mail);
							        //设置主题
							        message.setSubject(subject);
							        //设置内容
							        message.setText(messageText);
							        //设置发送时间
							        message.setSentDate(new Date());
							        //发送邮件
							        message.saveChanges();  //保存邮件信息
							      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
									//  transport.connect("smtp.163.com",wode@163.com,"123456");
									transport.connect("smtp.qq.com",from,password);
							        transport.sendMessage(message,message.getAllRecipients());
							        transport.close();
							 		System.out.print("发送邮件成功");	
							 		Statement Z = connect.createStatement();
									   String conditionZ="SELECT email FROM persons WHERE  place='shenzhen'";
									   rs=Z.executeQuery(conditionZ);	
									   System.out.println("成功获取email!");
									   rs.last();
									   rowNumber=rs.getRow();
									   String[] ReceiveZ=new String[rowNumber];
									   rs.beforeFirst();
									   j=0;
									   while(rs.next()){
										  System.out.println(rs.getString(1));
										  ReceiveZ[j]=rs.getString(1);
										  j++;
									   }
									   System.out.println("成功赋值邮箱数组!");
									   String AZ="SELECT AQI FROM pm25table WHERE  name='盐田' ORDER BY id DESC LIMIT 0,1";
									   rs=A.executeQuery(AZ);	
									   System.out.println("成功获取aqi!");
									   while(rs.next()){
										   AQI=Integer.parseInt(rs.getString(1));
									   }	   		   
									   System.out.println("成功赋值");
									   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
									   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
									   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
									   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
									   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
									   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
								        //设置收件人
								       send_mail = new InternetAddress[ReceiveW.length];
								       for (int i = 0; i < ReceiveZ.length; i++) { 
								         System.out.println("发送到:" + ReceiveZ[i]); 
								         send_mail[i] = new InternetAddress(ReceiveZ[i]); 
								       } 
								       message.setRecipients(Message.RecipientType.TO ,send_mail);
								        //设置主题
								        message.setSubject(subject);
								        //设置内容
								        message.setText(messageText);
								        //设置发送时间
								        message.setSentDate(new Date());
								        //发送邮件
								        message.saveChanges();  //保存邮件信息
								      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
										//  transport.connect("smtp.163.com",wode@163.com,"123456");
										transport.connect("smtp.qq.com",from,password);
								        transport.sendMessage(message,message.getAllRecipients());
								        transport.close();
								 		System.out.print("发送邮件成功");	
								 		Statement H = connect.createStatement();
										   String conditionH="SELECT email FROM persons WHERE  place='hangzhou'";
										   rs=H.executeQuery(conditionH);	
										   System.out.println("成功获取email!");
										   rs.last();
										   rowNumber=rs.getRow();
										   String[] ReceiveH=new String[rowNumber];
										   rs.beforeFirst();
										   j=0;
										   while(rs.next()){
											  System.out.println(rs.getString(1));
											  ReceiveH[j]=rs.getString(1);
											  j++;
										   }
										   System.out.println("成功赋值邮箱数组!");
										   String AH="SELECT AQI FROM pm25table WHERE  name='卧龙桥' ORDER BY id DESC LIMIT 0,1";
										   rs=A.executeQuery(AH);	
										   System.out.println("成功获取aqi!");
										   while(rs.next()){
											   AQI=Integer.parseInt(rs.getString(1));
										   }	   		   
										   System.out.println("成功赋值");
										   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
										   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
										   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
										   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
										   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
										   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
									        //设置收件人
									       send_mail = new InternetAddress[ReceiveH.length];
									       for (int i = 0; i < ReceiveW.length; i++) { 
									         System.out.println("发送到:" + ReceiveH[i]); 
									         send_mail[i] = new InternetAddress(ReceiveH[i]); 
									       } 
									       message.setRecipients(Message.RecipientType.TO ,send_mail);
									        //设置主题
									        message.setSubject(subject);
									        //设置内容
									        message.setText(messageText);
									        //设置发送时间
									        message.setSentDate(new Date());
									        //发送邮件
									        message.saveChanges();  //保存邮件信息
									      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
											//  transport.connect("smtp.163.com",wode@163.com,"123456");
											transport.connect("smtp.qq.com",from,password);
									        transport.sendMessage(message,message.getAllRecipients());
									        transport.close();
									 		System.out.print("发送邮件成功");	
									 		Statement C = connect.createStatement();
											   String conditionC="SELECT email FROM persons WHERE  place='chengdu'";
											   rs=C.executeQuery(conditionW);	
											   System.out.println("成功获取email!");
											   rs.last();
											   rowNumber=rs.getRow();
											   String[] ReceiveC=new String[rowNumber];
											   rs.beforeFirst();
											   j=0;
											   while(rs.next()){
												  System.out.println(rs.getString(1));
												  ReceiveC[j]=rs.getString(1);
												  j++;
											   }
											   System.out.println("成功赋值邮箱数组!");
											   String AC="SELECT AQI FROM pm25table WHERE  name='梁家巷' ORDER BY id DESC LIMIT 0,1";
											   rs=A.executeQuery(AC);	
											   System.out.println("成功获取aqi!");
											   while(rs.next()){
												   AQI=Integer.parseInt(rs.getString(1));
											   }	   		   
											   System.out.println("成功赋值");
											   if(AQI<50) messageText="空气质量优，各类人群可多参加户外活动。";
											   else if(AQI>=50&&AQI<100) messageText="空气质量良，除特别敏感人群外，不会对人体健康造成影响。";
											   else if(AQI>=100&&AQI<150) messageText="轻度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少长时间、高强度户外活动。";
											   else if(AQI>=150&&AQI<200) messageText="中度污染，不适合敏感人群，儿童、老人、呼吸系统疾病患者应减少户外活动、一般人群适量减少户外运动。";
											   else if(AQI>=200&&AQI<300) messageText="空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动";
											   else  messageText="严重污染,健康人群运动耐受力降低，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动";
										        //设置收件人
										       send_mail = new InternetAddress[ReceiveC.length];
										       for (int i = 0; i < ReceiveC.length; i++) { 
										         System.out.println("发送到:" + ReceiveC[i]); 
										         send_mail[i] = new InternetAddress(ReceiveW[i]); 
										       } 
										       message.setRecipients(Message.RecipientType.TO ,send_mail);
										        //设置主题
										        message.setSubject(subject);
										        //设置内容
										        message.setText(messageText);
										        //设置发送时间
										        message.setSentDate(new Date());
										        //发送邮件
										        message.saveChanges();  //保存邮件信息
										      //  transport.connect("smtp."+server,from,password);        //这一句很关键，相当于登陆管理员邮箱来发邮件
												//  transport.connect("smtp.163.com",wode@163.com,"123456");
												transport.connect("smtp.qq.com",from,password);
										        transport.sendMessage(message,message.getAllRecipients());
										        transport.close();
										 		System.out.print("发送邮件成功");	
	 		                                    connect.close();*/
	        } catch(Exception e){ System.out.println(e);}
		   }},startTime,daySpan);
 
  }
}
