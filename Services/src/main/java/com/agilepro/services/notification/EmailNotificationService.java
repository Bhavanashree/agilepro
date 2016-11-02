package com.agilepro.services.notification;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.exception.NotificationException;
import com.agilepro.notification.INotificationProcessor;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.utils.exceptions.InvalidArgumentException;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.common.models.mails.EmailServerSettings;
import com.yukthi.webutils.mail.EmailService;
import com.yukthi.webutils.mail.IMailProcessor;
import com.yukthi.webutils.mail.ReceivedMailMessage;
import com.yukthi.webutils.mail.template.MailTemplateEntity;
import com.yukthi.webutils.mail.template.MailTemplateService;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserService;

/**
 * Mail service to send and receive mails.
 * @author akiran
 */
public class EmailNotificationService
{
	private static Logger logger = LogManager.getLogger(EmailNotificationService.class);
	
	/**
	 * Mail thread to read and process mails periodically.
	 * @author akiran
	 */
	private class ReadMailThread implements Runnable
	{
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
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
	 * The User service.
	 */
	@Autowired
	private CurrentUserService currentUserService;
	
	/**
	 * Mail template service to fetch mail templates.
	 */
	@Autowired
	private MailTemplateService mailTemplateService;
	
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
	 * Email service to send and receive mails.
	 */
	@Autowired
	private EmailService emailService;
	
	/**
	 * Customer service to fetch customers.
	 */
	@Autowired
	private CustomerService customerService;
	
	/**
	 * The User service.
	 **/
	@Autowired
	private UserService userService;
	
	/**
	 * Post construct method to start background read thread.
	 */
	@PostConstruct
	private void init()
	{
		new Thread(readMailThread, "Mail Processor").start();
	}

	/**
	 * Fetches settings for specified customer.
	 * @param customerId Customer for which settings needs to be fetched.
	 * @return Matching settings.
	 */
	private EmailServerSettings getSettings(long customerId)
	{
		return null;
	}

	/**
	 * Sends mail with specified template name and specified context. Uses the current user customer id.
	 * @param templateName Template name to be used.
	 * @param context Context for expressions parsing.
	 */
	public void sendMail(String templateName, Object context)
	{
		AgileProUserDetails curentUser = (AgileProUserDetails) currentUserService.getCurrentUserDetails();
		
		if(curentUser == null)
		{
			throw new InvalidStateException("No active user found on context.");
		}
		
		sendMail(curentUser.getCustomerId(), templateName, context);
	}

	/**
	 * This method tries to fetch mail template based on specified template name, with specified customer id. 
	 * If template customization is not found, fetches template from admin scope.
	 * 
	 * @param customerId Customer id which should be given preference.
	 * @param templateName Template name to be used.
	 * @param context Context for expressions parsing.
	 */
	public void sendMail(long customerId, String templateName, Object context)
	{
		MailTemplateEntity mailTemplate = mailTemplateService.fetchByOwner(templateName, CustomerEntity.class.getName(), customerId);
		
		if(mailTemplate == null)
		{
			mailTemplate = mailTemplateService.fetchByOwner(templateName, IMailTemplates.DEFAULT_OWNER_TYPE, 0L);
			
			if(mailTemplate == null)
			{
				throw new InvalidArgumentException("No mail template found with specified name - {}", templateName);
			}
		}
		
		EmailServerSettings emailServerSettings = getSettings(customerId);
		
		emailService.sendEmail(emailServerSettings, mailTemplate, context);
	}

	/**
	 * Registers specified notification processor.
	 * @param inotificationProcessor Processor to register.
	 */
	public void register(INotificationProcessor inotificationProcessor)
	{
		notificationProcessors.add(inotificationProcessor);
	}

	/**
	 * Invokes notification processors to process incoming mail.
	 * @param mail Mail to process.
	 * @return True if processed and mail to be deleted.
	 */
	private boolean notifyMail(ReceivedMailMessage mail)
	{
		boolean isProcessed = false;
		
		Long customerId = 1L;

		UserEntity userEntity = userService.fetchUserByUserName(customerId, mail.getFromMailId());

		if(userEntity == null)
		{
			throw new IllegalStateException("No user found with mail id: " + mail.getFromMailId());
		}

		for(INotificationProcessor processor : this.notificationProcessors)
		{
			if(processor.process(userEntity, mail))
			{
				logger.trace("Successfully processed mail with subject: ", mail.getSubject());
				return true;
			}
		}

		if(!isProcessed)
		{
			throw new NotificationException("Failed to proccess with mail: " + mail.getFromMailId() + ", title: " + mail.getSubject());
		}
		
		return false;
	}
	
	/**
	 * Reads all mails for all customers.
	 */
	private void readMails()
	{
		List<CustomerEntity> customers = customerService.fetchAllCustomers();
		
		for(CustomerEntity customer : customers)
		{
			readMails(customer.getId(), null);
		}
	}

	/**
	 * Reads mail for specified customer with specified settings.
	 * @param customerId Customer id for which mails needs to be fetched.
	 * @param emailServerSettings Server settings to fetch.
	 */
	private void readMails(long customerId, EmailServerSettings emailServerSettings)
	{
		emailService.readMails(emailServerSettings, new IMailProcessor()
		{
			@Override
			public boolean processAndDelete(ReceivedMailMessage mailMessage)
			{
				return notifyMail(mailMessage);
			}
		});
	}
}
