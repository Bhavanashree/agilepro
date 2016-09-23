package com.agilepro.services.notification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.notification.MailTemplateModel;
import com.agilepro.exception.NotificationException;
import com.agilepro.notification.INotificationProcessor;
import com.agilepro.notification.INotificationService;
import com.agilepro.persistence.entity.notification.MailTemplateEntity;
import com.agilepro.persistence.repository.notification.IMailTemplateRepository;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class MailNotificationService.
 * 
 * @author Pritam
 */
@Service
public class MailNotificationService extends BaseCrudService<MailTemplateEntity, IMailTemplateRepository> implements INotificationService
{
	/**
	 * The Class ReadMailThread.
	 * 
	 * @author Pritam
	 */
	private class ReadMailThread implements Runnable
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run()
		{
			while(true)
			{
				try
				{
					readMails();
					Thread.sleep(mailThreadSleepTime);
				} catch(InterruptedException ex)
				{
					throw new IllegalStateException("Mail read thread was interrupted", ex);
				}
			}
		}
	}

	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(MailNotificationService.class);

	/**
	 * The User service.
	 **/
	@Autowired
	private UserService userService;

	/**
	 * The mail smtp auth.
	 **/
	@Value("${agilepro.mail.mailSmtpAuth}")
	private String mailSmtpAuth;

	/**
	 * The mail smtp starttls enable.
	 **/
	@Value("${agilepro.mail.mailSmtpStarttlsEnable}")
	private String mailSmtpStarttlsEnable;

	/**
	 * The mail smtp host.
	 */
	@Value("${agilepro.mail.mailSmtpHost}")
	private String mailSmtpHost;

	/**
	 * The mail smtp port.
	 **/
	@Value("${agilepro.mail.mailSmtpPort}")
	private String mailSmtpPort;

	/**
	 * The read mail id.
	 **/
	@Value("${agilepro.mail.mailId}")
	private String mailId;

	/**
	 * The password.
	 **/
	@Value("${agilepro.mail.password}")
	private String password;

	/**
	 * The mail thread sleep time.
	 */
	@Value("${agilepro.mail.readThreadSleepTime}")
	private long mailThreadSleepTime;

	/**
	 * The read mail thread.
	 **/
	private ReadMailThread readMailThread = new ReadMailThread();

	/**
	 * The notification processors.
	 **/
	private List<INotificationProcessor> notificationProcessors = new LinkedList<>();

	/**
	 * The properties.
	 **/
	private Properties properties = new Properties();

	/**
	 * Instantiates a new mail notification service.
	 */
	public MailNotificationService()
	{
		super(MailTemplateEntity.class, IMailTemplateRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		properties.put("mail.smtp.auth", mailSmtpAuth);
		properties.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
		properties.put("mail.smtp.host", mailSmtpHost);
		properties.put("mail.smtp.port", mailSmtpPort);

		Thread thread = new Thread(readMailThread, "Read Mail Thread");
		//thread.start();

		logger.trace(thread.getName(), " has started");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.agilepro.notification.INotificationService#send(com.agilepro.commons.
	 * models.notification.MailTemplateModel)
	 */
	@Override
	public void send(MailTemplateModel mailTemplateModel)
	{
		Session session = Session.getInstance(properties, new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(mailId, password);
			}
		});

		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailId));

			List<String> recipient = mailTemplateModel.getRecipient();

			if(recipient.size() > 1)
			{
				InternetAddress[] recipientAddress = new InternetAddress[recipient.size()];

				for(int i = 0; i < recipientAddress.length; i++)
				{
					recipientAddress[i] = new InternetAddress(recipient.get(i));
				}

				message.setRecipients(Message.RecipientType.TO, recipientAddress);
			}
			else
			{
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.get(0)));
			}

			message.setSubject(mailTemplateModel.getSubject());
			message.setText(mailTemplateModel.getBody());

			Transport.send(message);
		} catch(MessagingException e)
		{
			throw new IllegalStateException("Exception occured while sending the mail", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.agilepro.notification.INotificationService#register(com.agilepro.
	 * notification.INotificationProcessor)
	 */
	@Override
	public void register(INotificationProcessor inotificationProcessor)
	{
		notificationProcessors.add(inotificationProcessor);
	}

	/**
	 * Notify mail.
	 *
	 * @param fromMailId
	 *            the from mail id
	 * @param title
	 *            the title
	 * @param body
	 *            the body
	 * @param attributes
	 *            the attributes
	 */
	private void notifyMail(String fromMailId, String title, String body, Map<String, String> attributes)
	{
		logger.trace("Notify mail with title: ", title);

		boolean isProcessed = false;
		
		Long customerId = 1L;

		UserEntity userEntity = userService.fetchUserByUserName(customerId, fromMailId);

		if(userEntity == null)
		{
			throw new IllegalStateException("No user found with mail id: " + fromMailId);
		}

		for(INotificationProcessor processor : this.notificationProcessors)
		{
			if(processor.process(userEntity, title, body, attributes))
			{
				logger.trace("Successfully processed mail with title: ", title);
				isProcessed = true;
				break;
			}
		}

		if(!isProcessed)
		{
			throw new NotificationException("Failed to proccess with mail: " + fromMailId + ", title: " + title);
		}
	}

	/**
	 * Read mails to read the mails and update the conversation.
	 */
	private void readMails()
	{
		try
		{
			Session session = Session.getDefaultInstance(properties);

			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", mailId.trim(), password.trim());

			Folder inboxFolder = store.getFolder("INBOX");
			inboxFolder.open(Folder.READ_WRITE);

			Message[] messages = inboxFolder.getMessages();

			for(int i = 0; i < messages.length; i++)
			{
				Message message = messages[i];
				Multipart multipart = (Multipart) message.getContent();
				String subject = message.getSubject();

				String nameMailId = message.getFrom()[0].toString(); 
				String frmMailId = nameMailId.substring(nameMailId.indexOf("<") + 1 , nameMailId.indexOf(">")).trim();

				try
				{
					notifyMail(frmMailId, subject, multipart.getBodyPart(0).getContent().toString(), new HashMap());
				} catch(NotificationException ex)
				{
					// TODO: If exception is thrown return mail indicating the
					// problem
					// should we add error message in subject to mail
					send(new MailTemplateModel(Arrays.asList(frmMailId), subject, ex.getMessage()));
				}

				message.setFlag(Flags.Flag.DELETED, true);
			}

			inboxFolder.close(false);
			store.close();
		} catch(Exception e)
		{
			throw new IllegalStateException("Exception occured while reading the mail ", e);
		}
	}
}
