package com.itechart.contactcatalog.logic;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.ServiceException;
import com.itechart.contactcatalog.property.MailResourceManager;

public class MailSender {
private static Logger logger = LoggerFactory.getLogger(MailSender.class);
	
	public static void sendMessage(String topic, String text, String to) throws ServiceException{
		try {
			logger.debug("Start of sendMessage method");
			String smtpHost = MailResourceManager.getProperty("mail.smtp.host");
			int smtpPort = Integer.parseInt(MailResourceManager.getProperty("mail.smtp.port"));               
			String username = MailResourceManager.getProperty("mail.user.name");
			String password = MailResourceManager.getProperty("mail.user.password");
			MailService mailService = new MailService();
			Session session = mailService.createSession(smtpHost, smtpPort, username, password);
			MimeMessage message;
			message = mailService.createMimeMessage(session, topic, username, to, Message.RecipientType.TO);
			mailService.addText(message, text,"utf-8","html");
			mailService.sendMimeMessage(message);
		} catch (MessagingException | IOException e) {
			logger.error("Exception in changeContact: {} ", e);
			throw new ServiceException(e);
		} 
	}
	
}
