package sendEmail;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Date;
public class sendEmail {
  public static void main(String[] args){
		   try {
			Class.forName("org.gjt.mm.mysql.Driver");
		   }catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		   }
		   try{
		   Connection connect=DriverManager.getConnection("jdbc:mysql://4.4.1.134/pm25?user=root&password=HHU123&useUnicode=true&characterEncoding=UTF-8");		  
		   Statement B = connect.createStatement();
		   String condition="SELECT email FROM persons WHERE  place='beijing'";
		   ResultSet rs=B.executeQuery(condition);	
		   rs.last();
		   int rowNumber=rs.getRow();
		   String[] ReceiveB=new String[rowNumber];
		   rs.beforeFirst();
		   int j=0;
		   while(rs.next()){
			  ReceiveB[j]=rs.getString(1);
			  j++;
		   }
	 	   String from="2337277290@qq.com";
	 		String subject="���н���";
	 		String messageText="���н���";
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
			//  transport.connect("smtp.163.com",wode@163.com,"123456");
			transport.connect("smtp.qq.com",from,password);
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	 		System.out.print("�����ʼ��ɹ�");	 		
  } catch(Exception e){}
  }
}
