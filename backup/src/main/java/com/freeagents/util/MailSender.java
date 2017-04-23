package com.freeagents.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	
	final String username = "freeagentseu@gmail.com";
	final String password = "freeagentseupass1357";
    String receiverEmailID = null;
	String emailSubject = null;
	String emailBody = null;
	
	public MailSender(String receiverEmailID, String emailSubject, String emailBody){
		this.receiverEmailID = receiverEmailID;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		try {
			Properties props = System.getProperties();
	        props.setProperty("mail.transport.protocol", "smtp");
	        props.setProperty("mail.host", "smtp.gmail.com");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "465");
	        props.put("mail.debug", "true");
	        props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.socketFactory.class",
	                "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "false");
	 
	        Session emailSession = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication("freeagentseu@gmail.com","freeagentseupass1357");
	                }
	        });
	        
	        emailSession.setDebug(true);
	        Message message = new MimeMessage(emailSession);
	        message.setFrom(new InternetAddress("freeagentseu@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailID));
	        message.setSubject(emailSubject);
	        message.setText(emailBody);
	 
	        Transport transport = emailSession.getTransport("smtps");
	        transport.connect("smtp.gmail.com", username, password);
	        transport.sendMessage(message, message.getAllRecipients());
	        
	        System.out.println("Successfully sent email");
		}
		catch (MessagingException e) {
			System.out.println("Unable to send email");
		}
		
	}
	
}
