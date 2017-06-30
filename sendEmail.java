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
  // �涨��ÿ��ʱ������
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
  System.out.println(new Date(System.currentTimeMillis()));
  
  Date startTime = sdf.parse("2017/6/22 07:00:00"); 

  // ���������Ѿ����� �״�����ʱ��͸�Ϊ����
  //if(System.currentTimeMillis()>startTime.getTime())
   //startTime = new Date(startTime.getTime()+daySpan);
   
  Timer timer = new Timer();
  timer.schedule(new TimerTask(){
	  public void run(){
	       String messageText;
		   try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("����Mysql Driver�ɹ�!");
		   }catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("����Mysql Driverʧ��!");
		   }
		   try{
		   Connection connect=DriverManager.getConnection("jdbc:mysql://4.4.1.134/pm25?user=root&password=HHU123&useUnicode=true&characterEncoding=UTF-8&useSSL=true");		  
		   System.out.println("�ɹ�����Mysql server!");
		   Statement B = connect.createStatement();
		   String conditionB="SELECT email FROM persons WHERE  place='beijing'";
		   ResultSet rs=B.executeQuery(conditionB);	
		   System.out.println("�ɹ���ȡemail!");
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
		   System.out.println("�ɹ���ֵ��������!");
		   Statement A = connect.createStatement();
		   String AB="SELECT AQI FROM pm25table WHERE  name='����' ORDER BY id DESC LIMIT 0,1";
		   rs=A.executeQuery(AB);	
		   System.out.println("�ɹ���ȡaqi!");
		   int AQI = 0;
		   while(rs.next()){
			   AQI=Integer.parseInt(rs.getString(1));
		   }	   		   
		   System.out.println("�ɹ���ֵ");
		   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
		   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
		   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
		   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
		   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
		   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
		   String from="2337277290@qq.com";
	 		String subject="���н���";
	        String password="ruomezdumiazdjbc";
	        String S =from;
	        int n =S.indexOf('@');
	        int m=S.length() ;
	        String server =S.substring(n+1,m);
	        //�����ʼ��Ự
	 		Properties pro=new Properties();
	        pro.put("mail.smtp.host","smtp."+server);
	        pro.put("mail.smtp.auth","true");
	        pro.put("mail.smtp.port","587");
	        Session sess=Session.getInstance(pro);
	        sess.setDebug(true);
	        //�½�һ����Ϣ����
	        MimeMessage message=new MimeMessage(sess);
	        //���÷�����
	        InternetAddress from_mail=new InternetAddress(from);
	        message.setFrom(from_mail);
	        //�����ռ���
	       InternetAddress[] send_mail = new InternetAddress[ReceiveB.length];
	       for (int i = 0; i < ReceiveB.length; i++) { 
	         System.out.println("���͵�:" + ReceiveB[i]); 
	         send_mail[i] = new InternetAddress(ReceiveB[i]); 
	       } 
	       message.setRecipients(Message.RecipientType.TO ,send_mail);
	        //��������
	        message.setSubject(subject);
	        //��������
	        message.setText(messageText);
	        //���÷���ʱ��
	        message.setSentDate(new Date());
	        //�����ʼ�
	        message.saveChanges();  //�����ʼ���Ϣ
	        Transport transport =sess.getTransport("smtp");
	      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
			transport.connect("smtp.qq.com",from,password);
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	 		System.out.print("�����ʼ��ɹ�");	 
	 		Statement N = connect.createStatement();
			   String conditionN="SELECT email FROM persons WHERE  place='nanjing'";
			   rs=N.executeQuery(conditionN);	
			   System.out.println("�ɹ���ȡemail!");
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
			   System.out.println("�ɹ���ֵ��������!");
			   String AN="SELECT AQI FROM pm25table WHERE  name='�л���' ORDER BY id DESC LIMIT 0,1";
			   rs=A.executeQuery(AN);	
			   System.out.println("�ɹ���ȡaqi!");
			   while(rs.next()){
				   AQI=Integer.parseInt(rs.getString(1));
			   }	   		   
			   System.out.println("�ɹ���ֵ");
			   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
			   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
			   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
			   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
			   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
			   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
		        //�����ռ���
		       send_mail = new InternetAddress[ReceiveN.length];
		       for (int i = 0; i < ReceiveN.length; i++) { 
		         System.out.println("���͵�:" + ReceiveN[i]); 
		         send_mail[i] = new InternetAddress(ReceiveN[i]); 
		       } 
		       message.setRecipients(Message.RecipientType.TO ,send_mail);
		        //��������
		        message.setSubject(subject);
		        //��������
		        message.setText(messageText);
		        //���÷���ʱ��
		        message.setSentDate(new Date());
		        //�����ʼ�
		        message.saveChanges();  //�����ʼ���Ϣ
		      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
				//  transport.connect("smtp.163.com",wode@163.com,"123456");
				transport.connect("smtp.qq.com",from,password);
		        transport.sendMessage(message,message.getAllRecipients());
		        transport.close();
		 		System.out.print("�����ʼ��ɹ�");	 
		 		Statement G = connect.createStatement();
				   String conditionG="SELECT email FROM persons WHERE  place='guangzhou'";
				   rs=G.executeQuery(conditionG);	
				   System.out.println("�ɹ���ȡemail!");
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
				   System.out.println("�ɹ���ֵ��������!");
				   String AG="SELECT AQI FROM pm25table WHERE  name='�м��վ' ORDER BY id DESC LIMIT 0,1";
				   rs=A.executeQuery(AG);	
				   System.out.println("�ɹ���ȡaqi!");
				   while(rs.next()){
					   AQI=Integer.parseInt(rs.getString(1));
				   }	   		   
				   System.out.println("�ɹ���ֵ");
				   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
				   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
				   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
				   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
				   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
				   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
			        //�����ռ���
			       send_mail = new InternetAddress[ReceiveG.length];
			       for (int i = 0; i < ReceiveG.length; i++) { 
			         System.out.println("���͵�:" + ReceiveG[i]); 
			         send_mail[i] = new InternetAddress(ReceiveG[i]); 
			       } 
			       message.setRecipients(Message.RecipientType.TO ,send_mail);
			        //��������
			        message.setSubject(subject);
			        //��������
			        message.setText(messageText);
			        //���÷���ʱ��
			        message.setSentDate(new Date());
			        //�����ʼ�
			        message.saveChanges();  //�����ʼ���Ϣ
			      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
					//  transport.connect("smtp.163.com",wode@163.com,"123456");
					transport.connect("smtp.qq.com",from,password);
			        transport.sendMessage(message,message.getAllRecipients());
			        transport.close();
			 		System.out.print("�����ʼ��ɹ�");	
			 		Statement S1 = connect.createStatement();
					   String conditionS="SELECT email FROM persons WHERE  place='shanghai'";
					   rs=S1.executeQuery(conditionS);	
					   System.out.println("�ɹ���ȡemail!");
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
					   System.out.println("�ɹ���ֵ��������!");
					   String AS="SELECT AQI FROM pm25table WHERE  name='����' ORDER BY id DESC LIMIT 0,1";
					   rs=A.executeQuery(AS);	
					   System.out.println("�ɹ���ȡaqi!");
					   while(rs.next()){
						   AQI=Integer.parseInt(rs.getString(1));
					   }	   		   
					   System.out.println("�ɹ���ֵ");
					   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
					   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
					   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
					   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
					   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
					   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
				        //�����ռ���
				       send_mail = new InternetAddress[ReceiveS.length];
				       for (int i = 0; i < ReceiveS.length; i++) { 
				         System.out.println("���͵�:" + ReceiveS[i]); 
				         send_mail[i] = new InternetAddress(ReceiveS[i]); 
				       } 
				       message.setRecipients(Message.RecipientType.TO ,send_mail);
				        //��������
				        message.setSubject(subject);
				        //��������
				        message.setText(messageText);
				        //���÷���ʱ��
				        message.setSentDate(new Date());
				        //�����ʼ�
				        message.saveChanges();  //�����ʼ���Ϣ
				      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
						//  transport.connect("smtp.163.com",wode@163.com,"123456");
						transport.connect("smtp.qq.com",from,password);
				        transport.sendMessage(message,message.getAllRecipients());
				        transport.close();
				 		System.out.print("�����ʼ��ɹ�");	
				 		/*Statement X = connect.createStatement();
						   String conditionX="SELECT email FROM persons WHERE  place='xian'";
						   rs=X.executeQuery(conditionN);	
						   System.out.println("�ɹ���ȡemail!");
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
						   System.out.println("�ɹ���ֵ��������!");
						   String AX="SELECT AQI FROM pm25table WHERE  name='������' ORDER BY id DESC LIMIT 0,1";
						   rs=A.executeQuery(AX);	
						   System.out.println("�ɹ���ȡaqi!");
						   while(rs.next()){
							   AQI=Integer.parseInt(rs.getString(1));
						   }	   		   
						   System.out.println("�ɹ���ֵ");
						   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
						   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
						   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
						   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
						   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
						   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
					        //�����ռ���
					       send_mail = new InternetAddress[ReceiveN.length];
					       for (int i = 0; i < ReceiveX.length; i++) { 
					         System.out.println("���͵�:" + ReceiveX[i]); 
					         send_mail[i] = new InternetAddress(ReceiveX[i]); 
					       } 
					       message.setRecipients(Message.RecipientType.TO ,send_mail);
					        //��������
					        message.setSubject(subject);
					        //��������
					        message.setText(messageText);
					        //���÷���ʱ��
					        message.setSentDate(new Date());
					        //�����ʼ�
					        message.saveChanges();  //�����ʼ���Ϣ
					      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
							//  transport.connect("smtp.163.com",wode@163.com,"123456");
							transport.connect("smtp.qq.com",from,password);
					        transport.sendMessage(message,message.getAllRecipients());
					        transport.close();
					 		System.out.print("�����ʼ��ɹ�");	
					 		Statement W = connect.createStatement();
							   String conditionW="SELECT email FROM persons WHERE  place='wuhan'";
							   rs=W.executeQuery(conditionW);	
							   System.out.println("�ɹ���ȡemail!");
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
							   System.out.println("�ɹ���ֵ��������!");
							   String AW="SELECT AQI FROM pm25table WHERE  name='���ڻ���' ORDER BY id DESC LIMIT 0,1";
							   rs=A.executeQuery(AW);	
							   System.out.println("�ɹ���ȡaqi!");
							   while(rs.next()){
								   AQI=Integer.parseInt(rs.getString(1));
							   }	   		   
							   System.out.println("�ɹ���ֵ");
							   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
							   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
							   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
							   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
							   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
							   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
						        //�����ռ���
						       send_mail = new InternetAddress[ReceiveW.length];
						       for (int i = 0; i < ReceiveW.length; i++) { 
						         System.out.println("���͵�:" + ReceiveW[i]); 
						         send_mail[i] = new InternetAddress(ReceiveW[i]); 
						       } 
						       message.setRecipients(Message.RecipientType.TO ,send_mail);
						        //��������
						        message.setSubject(subject);
						        //��������
						        message.setText(messageText);
						        //���÷���ʱ��
						        message.setSentDate(new Date());
						        //�����ʼ�
						        message.saveChanges();  //�����ʼ���Ϣ
						      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
								//  transport.connect("smtp.163.com",wode@163.com,"123456");
								transport.connect("smtp.qq.com",from,password);
						        transport.sendMessage(message,message.getAllRecipients());
						        transport.close();
						 		System.out.print("�����ʼ��ɹ�");	
						 		Statement T = connect.createStatement();
								   String conditionT="SELECT email FROM persons WHERE  place='tianjin'";
								   rs=T.executeQuery(conditionT);	
								   System.out.println("�ɹ���ȡemail!");
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
								   System.out.println("�ɹ���ֵ��������!");
								   String AT="SELECT AQI FROM pm25table WHERE  name='���·' ORDER BY id DESC LIMIT 0,1";
								   rs=A.executeQuery(AT);	
								   System.out.println("�ɹ���ȡaqi!");
								   while(rs.next()){
									   AQI=Integer.parseInt(rs.getString(1));
								   }	   		   
								   System.out.println("�ɹ���ֵ");
								   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
								   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
								   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
								   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
								   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
								   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
							        //�����ռ���
							       send_mail = new InternetAddress[ReceiveT.length];
							       for (int i = 0; i < ReceiveT.length; i++) { 
							         System.out.println("���͵�:" + ReceiveT[i]); 
							         send_mail[i] = new InternetAddress(ReceiveT[i]); 
							       } 
							       message.setRecipients(Message.RecipientType.TO ,send_mail);
							        //��������
							        message.setSubject(subject);
							        //��������
							        message.setText(messageText);
							        //���÷���ʱ��
							        message.setSentDate(new Date());
							        //�����ʼ�
							        message.saveChanges();  //�����ʼ���Ϣ
							      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
									//  transport.connect("smtp.163.com",wode@163.com,"123456");
									transport.connect("smtp.qq.com",from,password);
							        transport.sendMessage(message,message.getAllRecipients());
							        transport.close();
							 		System.out.print("�����ʼ��ɹ�");	
							 		Statement Z = connect.createStatement();
									   String conditionZ="SELECT email FROM persons WHERE  place='shenzhen'";
									   rs=Z.executeQuery(conditionZ);	
									   System.out.println("�ɹ���ȡemail!");
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
									   System.out.println("�ɹ���ֵ��������!");
									   String AZ="SELECT AQI FROM pm25table WHERE  name='����' ORDER BY id DESC LIMIT 0,1";
									   rs=A.executeQuery(AZ);	
									   System.out.println("�ɹ���ȡaqi!");
									   while(rs.next()){
										   AQI=Integer.parseInt(rs.getString(1));
									   }	   		   
									   System.out.println("�ɹ���ֵ");
									   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
									   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
									   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
									   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
									   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
									   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
								        //�����ռ���
								       send_mail = new InternetAddress[ReceiveW.length];
								       for (int i = 0; i < ReceiveZ.length; i++) { 
								         System.out.println("���͵�:" + ReceiveZ[i]); 
								         send_mail[i] = new InternetAddress(ReceiveZ[i]); 
								       } 
								       message.setRecipients(Message.RecipientType.TO ,send_mail);
								        //��������
								        message.setSubject(subject);
								        //��������
								        message.setText(messageText);
								        //���÷���ʱ��
								        message.setSentDate(new Date());
								        //�����ʼ�
								        message.saveChanges();  //�����ʼ���Ϣ
								      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
										//  transport.connect("smtp.163.com",wode@163.com,"123456");
										transport.connect("smtp.qq.com",from,password);
								        transport.sendMessage(message,message.getAllRecipients());
								        transport.close();
								 		System.out.print("�����ʼ��ɹ�");	
								 		Statement H = connect.createStatement();
										   String conditionH="SELECT email FROM persons WHERE  place='hangzhou'";
										   rs=H.executeQuery(conditionH);	
										   System.out.println("�ɹ���ȡemail!");
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
										   System.out.println("�ɹ���ֵ��������!");
										   String AH="SELECT AQI FROM pm25table WHERE  name='������' ORDER BY id DESC LIMIT 0,1";
										   rs=A.executeQuery(AH);	
										   System.out.println("�ɹ���ȡaqi!");
										   while(rs.next()){
											   AQI=Integer.parseInt(rs.getString(1));
										   }	   		   
										   System.out.println("�ɹ���ֵ");
										   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
										   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
										   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
										   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
										   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
										   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
									        //�����ռ���
									       send_mail = new InternetAddress[ReceiveH.length];
									       for (int i = 0; i < ReceiveW.length; i++) { 
									         System.out.println("���͵�:" + ReceiveH[i]); 
									         send_mail[i] = new InternetAddress(ReceiveH[i]); 
									       } 
									       message.setRecipients(Message.RecipientType.TO ,send_mail);
									        //��������
									        message.setSubject(subject);
									        //��������
									        message.setText(messageText);
									        //���÷���ʱ��
									        message.setSentDate(new Date());
									        //�����ʼ�
									        message.saveChanges();  //�����ʼ���Ϣ
									      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
											//  transport.connect("smtp.163.com",wode@163.com,"123456");
											transport.connect("smtp.qq.com",from,password);
									        transport.sendMessage(message,message.getAllRecipients());
									        transport.close();
									 		System.out.print("�����ʼ��ɹ�");	
									 		Statement C = connect.createStatement();
											   String conditionC="SELECT email FROM persons WHERE  place='chengdu'";
											   rs=C.executeQuery(conditionW);	
											   System.out.println("�ɹ���ȡemail!");
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
											   System.out.println("�ɹ���ֵ��������!");
											   String AC="SELECT AQI FROM pm25table WHERE  name='������' ORDER BY id DESC LIMIT 0,1";
											   rs=A.executeQuery(AC);	
											   System.out.println("�ɹ���ȡaqi!");
											   while(rs.next()){
												   AQI=Integer.parseInt(rs.getString(1));
											   }	   		   
											   System.out.println("�ɹ���ֵ");
											   if(AQI<50) messageText="���������ţ�������Ⱥ�ɶ�μӻ�����";
											   else if(AQI>=50&&AQI<100) messageText="���������������ر�������Ⱥ�⣬��������彡�����Ӱ�졣";
											   else if(AQI>=100&&AQI<150) messageText="�����Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٳ�ʱ�䡢��ǿ�Ȼ�����";
											   else if(AQI>=150&&AQI<200) messageText="�ж���Ⱦ�����ʺ�������Ⱥ����ͯ�����ˡ�����ϵͳ��������Ӧ���ٻ�����һ����Ⱥ�������ٻ����˶���";
											   else if(AQI>=200&&AQI<300) messageText="��������״�������ض���Ⱦ����ʱ�����ಡ�ͷβ�����֢״�����Ӿ磬�˶����������ͣ�������Ⱥ�ձ����֢״�������ͯ�������˺����ಡ���β�����Ӧͣ�������ڣ�ֹͣ�����˶���һ����Ⱥ���ٻ����˶�";
											   else  messageText="������Ⱦ,������Ⱥ�˶����������ͣ������ͯ�������˺Ͳ���Ӧ���������ڣ������������ģ�һ����ȺӦ���⻧��";
										        //�����ռ���
										       send_mail = new InternetAddress[ReceiveC.length];
										       for (int i = 0; i < ReceiveC.length; i++) { 
										         System.out.println("���͵�:" + ReceiveC[i]); 
										         send_mail[i] = new InternetAddress(ReceiveW[i]); 
										       } 
										       message.setRecipients(Message.RecipientType.TO ,send_mail);
										        //��������
										        message.setSubject(subject);
										        //��������
										        message.setText(messageText);
										        //���÷���ʱ��
										        message.setSentDate(new Date());
										        //�����ʼ�
										        message.saveChanges();  //�����ʼ���Ϣ
										      //  transport.connect("smtp."+server,from,password);        //��һ��ܹؼ����൱�ڵ�½����Ա���������ʼ�
												//  transport.connect("smtp.163.com",wode@163.com,"123456");
												transport.connect("smtp.qq.com",from,password);
										        transport.sendMessage(message,message.getAllRecipients());
										        transport.close();
										 		System.out.print("�����ʼ��ɹ�");	
	 		                                    connect.close();*/
	        } catch(Exception e){ System.out.println(e);}
		   }},startTime,daySpan);
 
  }
}
