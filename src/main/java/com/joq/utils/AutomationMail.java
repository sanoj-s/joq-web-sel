package com.joq.utils;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.joq.base.AutomationEngine;
import com.joq.exception.AutomationException;
import com.joq.keywords.DataHandler;

public class AutomationMail extends AutomationEngine {

	DataHandler propertyObj = new DataHandler();

	/**
	 * Send the execution report to the recipients list mentioned in the
	 * email_config file
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @throws Exception
	 * 
	 */
	public void sendMailReport() throws Exception {
		String mailHost = "", subject = "", message = "", maillist = "";

		mailHost = propertyObj.getProperty(AutomationConstants.EMAIL_CONFIG, "mailHost");
		subject = propertyObj.getProperty(AutomationConstants.EMAIL_CONFIG, "subject");
		message = propertyObj.getProperty(AutomationConstants.EMAIL_CONFIG, "message");
		maillist = propertyObj.getProperty(AutomationConstants.EMAIL_CONFIG, "maillist");

		Properties properties = new Properties();
		if (mailHost.equalsIgnoreCase("gmail")) {
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
		}
		if (mailHost.equalsIgnoreCase("outlook")) {
			properties.put("mail.smtp.host", "outlook.office365.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		}
		String userName = propertyObj.getProperty(AutomationConstants.EMAIL_CONFIG, "username");
		String password = propertyObj.getProperty(AutomationConstants.EMAIL_CONFIG, "password");

		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);
		DateFormat dff = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss z");

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(userName));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(maillist));
			msg.setSubject(subject + " – " + dff.format(new Date()).toString());
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds only the latest Report file in attachments
			MimeBodyPart attachPart = new MimeBodyPart();
			attachPart.attachFile(
					lastFileModified(System.getProperty("user.dir") + AutomationConstants.EMAIL_REPORT_FOLDER));
			multipart.addBodyPart(attachPart);

			// sets the multi-part as e-mail’s content
			msg.setContent(multipart);
			// sends the e-mail
			Transport.send(msg);
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}

	/**
	 * Get the last modified File
	 * 
	 * @author sanojs
	 * @since 20-04-2021
	 * @param filePath
	 * @throws AutomationException
	 */
	public File lastFileModified(String filePath) throws AutomationException {
		try {
			File fl = new File(filePath);
			File[] files = fl.listFiles(new FileFilter() {

				public boolean accept(File file) {
					return file.isFile();
				}
			});
			long lastMod = Long.MIN_VALUE;
			File choice = null;
			for (File file : files) {
				if (file.lastModified() > lastMod) {
					choice = file;
					lastMod = file.lastModified();
				}
			}
			return choice;
		} catch (Exception e) {
			throw new AutomationException(getExceptionMessage(), e);
		}
	}
}
