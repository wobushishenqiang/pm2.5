<%@ page contentType="text/html; charset=gb2312" language="java"  errorPage="" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import ="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����ʼ�</title>
</head>
<%
try{
 		request.setCharacterEncoding("gb2312");
 		String from="2337277290@qq.com";
 		String to=request.getParameter("to");
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
       InternetAddress to_mail=new InternetAddress(to);
       message.setRecipient(Message.RecipientType.TO ,to_mail);
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
 		out.print("�����ʼ��ɹ�");
 		}catch(Exception e){
  		out.print("�����ʼ�ʧ��,ԭ�������");
  		out.println(e.getMessage());
 }
%>
</html>