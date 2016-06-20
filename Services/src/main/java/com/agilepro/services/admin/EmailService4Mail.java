package com.agilepro.services.admin;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.admin.EmailModel;

/**
 * The Class EmailService4Mail.
 */
@Service
public class EmailService4Mail
{

	/**
	 * The user name.
	 */
	private String userName = "edwardshalu@gmail.com";

	/**
	 * The password.
	 */
	private String password = "8880821977";

	/**
	 * Send mail now.
	 *
	 * @param emailModel
	 *            the email model
	 */
	public void sendMailNow(EmailModel emailModel)
	{
		final String str = "true";
		Properties props = new Properties();
		props.put("mail.smtp.auth", str);
		props.put("mail.smtp.starttls.enable", str);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Session session = Session.getDefaultInstance(null);

		Session session = Session.getInstance(props, new javax.mail.Authenticator()
		{

			protected javax.mail.PasswordAuthentication getPasswordAuthentication()
			{

				return new javax.mail.PasswordAuthentication(userName, password);
			}
		});

		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("pritamjosephjojo@gmail.com"));
			message.setSubject("Test java mail");
			message.setText("Hey its working");

			Transport.send(message);
			System.out.println("done");
		} catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}

	/*
	 * public static void main(String[] args) { EmailService4Mail em = new
	 * EmailService4Mail(); em.sendMailNow(null); }
	 */
}
