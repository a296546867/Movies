package utli;

import hadesky.domain.Users;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends Thread{

	private Users c;

	public SendMail(Users c) {
		this.c = c;
	}
	//���ͼ����ʼ�����
	public void run() {
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");//ָ���ʼ����͵�Э�飬�����ǹ淶�涨��
			props.setProperty("mail.host", "smtp.163.com");//ָ�������������ĵ�ַ�������ǹ淶�涨��
//		props.setProperty("mail.debug", "true");//�ʼ����͵ĵ���ģʽ�������ǹ淶�涨��
			props.setProperty("mail.smtp.auth", "true");//������������������֤������������JavaMailʵ���й�
			Session session = Session.getInstance(props);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("m18565381698@163.com"));
			message.setRecipients(Message.RecipientType.TO, c.getEmail());
			message.setSubject("hello");
			
//			message.setContent("尊敬的用户："+c.getUsername()+"<br/>您好，请点击以完成注册，<br/><a href='http://localhost:8080/Movies/User/ClientController?op=active&code="+c.getCode()+"'>点击验证</a><br/>", "text/html;charset=UTF-8");
			message.setContent("尊敬的用户："+c.getUsername(), "text/html;charset=UTF-8");

			message.saveChanges();
			
			Transport ts = session.getTransport();
			ts.connect("m18565381698", "163com1991");
			ts.sendMessage(message, message.getAllRecipients());
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
