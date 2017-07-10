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
<title>发送邮件</title>
</head>
<%
try{
 		request.setCharacterEncoding("gb2312");
 		String from="2337277290@qq.com";
 		String to=request.getParameter("to");
 		String subject="出行建议";
 		String messageText="出行建议";
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
       InternetAddress to_mail=new InternetAddress(to);
       message.setRecipient(Message.RecipientType.TO ,to_mail);
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
		//  transport.connect("smtp.163.com",wode@163.com,"123456");
		transport.connect("smtp.qq.com",from,password);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
 		out.print("发送邮件成功");
 		}catch(Exception e){
  		out.print("发送邮件失败,原因可能是");
  		out.println(e.getMessage());
 }
%>
</html>