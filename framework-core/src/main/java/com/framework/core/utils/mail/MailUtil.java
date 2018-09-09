package com.framework.core.utils.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.framework.core.utils.mail.bean.MailBean;
import com.framework.core.utils.mail.bean.MailConfigBean;
import com.framework.core.utils.mail.bean.MyAuthenticator;


public class MailUtil {
    public static void main(String[] args) throws MessagingException {
        //测试
        MailBean bean=new MailBean("3380309842@qq.com","标题","内容");
        MailConfigBean config=new MailConfigBean("820419196@qq.com","5288798lcj","smtp.qq.com");
        MailUtil.sendMail(bean,config);
    }
	public static void sendMail(MailBean bean,MailConfigBean config) throws MessagingException{
            
	       String userName = config.getUserName();  
	       String password = config.getPassword();  
	       String smtp=config.getSmtp();
	         
	        Properties props = new Properties();  
	        props.setProperty("mail.smtp.host", smtp);  
	        props.setProperty("mail.smtp.auth", "true");  
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.port", "465");
	        props.setProperty("mail.smtp.socketFactory.port", "465");
	        
	        Authenticator authenticator = new MyAuthenticator(userName, password);
	          
	        javax.mail.Session session = javax.mail.Session.getInstance(props,authenticator);  
	        session.setDebug(true);  
	          
            Address from = new InternetAddress(userName);  
            Address to = new InternetAddress(bean.getToMail());  
              
            MimeMessage msg = new MimeMessage(session);  
            msg.setFrom(from);  
            msg.setSubject(bean.getTitle());  
            msg.setSentDate(new Date());  
            msg.setContent(bean.getContent(), "text/html;charset=utf-8");  
            msg.setRecipient(RecipientType.TO, to);  
            /* 
            Transport transport = session.getTransport("smtp"); 
            transport.connect("smtp.1com", userName, password); 
            transport.sendMessage(msg,msg.getAllRecipients()); 
            transport.close(); 
            */  
            Transport.send(msg);  
	}
}
