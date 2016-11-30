package com.agilepro.services.notification;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.controller.IAgileProConstants;
import com.agilepro.notification.INotificationProcessor;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.utils.exceptions.InvalidArgumentException;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.common.models.mails.EmailServerSettings;
import com.yukthi.webutils.mail.EmailService;
import com.yukthi.webutils.mail.IMailProcessingContext;
import com.yukthi.webutils.mail.IMailProcessor;
import com.yukthi.webutils.mail.MailProcessingException;
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
@Service
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
				} catch(Exception ex)
				{
					logger.error("An error occurred while reading mails", ex);
				}

				try
				{
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
	 * Application context to fetch registered notification processors.
	 */
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * Post construct method to start background read thread.
	 */
	@PostConstruct
	private void init()
	{
		new Thread(readMailThread, "Mail Processor").start();
		
		//register processors from application context
		Map<String, INotificationProcessor> processors = applicationContext.getBeansOfType(INotificationProcessor.class);
		
		for(INotificationProcessor processor : processors.values())
		{
			register(processor);
		}
	}

	/**
	 * Fetches settings for specified customer.
	 * @param customerId Customer for which settings needs to be fetched.
	 * @return Matching settings.
	 */
	public EmailServerSettings getSettings(long customerId)
	{
		//TODO: modify this code to fetch mail settings directly instead of fetching full entity
		// 	And also take care of caching.
		CustomerEntity customerEntity = customerService.fetchByNoSpace(customerId);
		return customerEntity.getEmailServerSettings();
	}
	
	/**
	 * Fetches the mail server settings for current user.
	 * @return Current user's customer mail settings.
	 */
	public EmailServerSettings getSettings()
	{
		AgileProUserDetails userDetails = (AgileProUserDetails) currentUserService.getCurrentUserDetails();
		return getSettings(userDetails.getCustomerId());
	}

	/**
	 * Fetches mail template with specified name. If customization is present for specified customer
	 * the same will be fetched, if not default mail template from admin will be fetched.
	 * @param customerId Customer id for whom mail template needs to be fetched.
	 * @param templateName Template to be fetched.
	 * @return Matching mail template entity.
	 */
	public MailTemplateEntity getMailTemplate(long customerId, String templateName)
	{
		//fetch customized mail template for customer
		MailTemplateEntity mailTemplate = mailTemplateService.fetchByOwner(templateName, CustomerEntity.class.getName(), customerId);
		
		if(mailTemplate == null)
		{
			//if not found, try to get default one
			mailTemplate = mailTemplateService.fetchByOwner(templateName, IMailTemplates.DEFAULT_OWNER_TYPE, 0L);
			
			//if defalt one is also not found
			if(mailTemplate == null)
			{
				throw new InvalidArgumentException("No mail template found with specified name - {}", templateName);
			}
		}
		
		return mailTemplate;
	}
	
	/**
	 * Fetches the specified mail template for current user.
	 * @param templateName Mail template name to be fetched.
	 * @return Matching mail template
	 */
	public MailTemplateEntity getMailTemplate(String templateName)
	{
		AgileProUserDetails userDetails = (AgileProUserDetails) currentUserService.getCurrentUserDetails();
		return getMailTemplate(userDetails.getCustomerId(), templateName);
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
		MailTemplateEntity mailTemplate = getMailTemplate(customerId, templateName);
		
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
	 * @param context Processing context that can be used to reply mails.
	 * @param customerId Customer under which this mail is received
	 * @param customerName Name of the customer
	 * @param mail Mail to process.
	 * @return True if processed and mail to be deleted.
	 */
	private boolean processReceivedMail(IMailProcessingContext context, long customerId, String customerName, ReceivedMailMessage mail)
	{
		UserEntity userEntity = userService.getUser(mail.getFromMailId(), IAgileProConstants.customerSpace(customerId));

		if(userEntity == null)
		{
			logger.debug("While processing mail with subject '{}', no user found with mail id '{}' under customer - {} [{}]. Sending a reply mail indicating no user found.", 
					mail.getSubject(), mail.getFromMailId(), customerName, customerId);
			
			MailProcessingErrorContext mailProcessingErrorContext = new MailProcessingErrorContext(mail.getFromMailId(), customerName, MailProcessingErrorContext.ERR_CODE_NO_USER, null, null);
			MailTemplateEntity mailTemplate = getMailTemplate(customerId, IMailTemplates.MAIL_PROCESSING_ERROR); 
			
			try
			{
				context.replyToAll(mailTemplate, mailProcessingErrorContext);
			}catch(Exception ex)
			{
				throw new InvalidStateException(ex, "An error occurred while sending no-user-found processing error mail for mail with subject - " + mail.getSubject());
			}
			
			return true;
		}
		
		if(!mail.hasContent())
		{
			MailProcessingErrorContext mailProcessingErrorContext = new MailProcessingErrorContext(mail.getFromMailId(), customerName, 
					MailProcessingErrorContext.ERR_CODE_NON_HTML, null, null);
			MailTemplateEntity mailTemplate = getMailTemplate(customerId, IMailTemplates.MAIL_PROCESSING_ERROR); 
			
			try
			{
				context.replyToAll(mailTemplate, mailProcessingErrorContext);
			}catch(Exception ex)
			{
				logger.error("An error occurred while sending non-html-content processing error mail for mail with subject - " + mail.getSubject(), ex);
				//throw new InvalidStateException(ex, "An error occurred while sending non-html-content processing error mail for mail with subject - " + mail.getSubject());
			}
			
			return true;
		}
		
		String errorMssg = null, errorCode = null;

		for(INotificationProcessor processor : this.notificationProcessors)
		{
			try
			{
				if(processor.process(userEntity, mail))
				{
					logger.trace("Successfully processed mail with subject: {}", mail.getSubject());
					return true;
				}
			}catch(Exception ex)
			{
				logger.error("An error occurred while processing mail with subject - {}", mail.getSubject(), ex);
				
				if(ex instanceof EmailProcessingException)
				{
					errorMssg = ex.getMessage();
					errorCode = MailProcessingErrorContext.ERR_CODE_PROCESSING_ERRORED;
				}
				else
				{
					errorCode = MailProcessingErrorContext.ERR_CODE_SERVER_ERROR;
				}
				
				break;
			}
		}
		
		MailProcessingErrorContext mailProcessingErrorContext = null;

		if(errorCode != null)
		{
			logger.debug("An error occurred while processing mail with subject '{}'. Sending a reply mail indicating process failed.", 
					mail.getSubject(), mail.getFromMailId(), customerName, customerId);
	
			mailProcessingErrorContext = new MailProcessingErrorContext(mail.getFromMailId(), customerName, 
					errorCode, errorMssg, userEntity.getDisplayName());
		}
		else
		{
			logger.debug("Failed to process mail with subject '{}'. Sending a reply mail indicating process failed.", 
					mail.getSubject(), mail.getFromMailId(), customerName, customerId);
	
			mailProcessingErrorContext = new MailProcessingErrorContext(mail.getFromMailId(), customerName, 
					MailProcessingErrorContext.ERR_CODE_PROCESSING_FAILED, null, userEntity.getDisplayName());
		}
		
		try
		{
			MailTemplateEntity mailTemplate = getMailTemplate(customerId, IMailTemplates.MAIL_PROCESSING_ERROR);
			
			context.replyToAll(mailTemplate, mailProcessingErrorContext);
			return true;
		}catch(MailProcessingException ex)
		{
			logger.error("An error occurred while replying to mail whose processing failed", ex);
			return false;
		}
	}
	
	/**
	 * Reads all mails for all customers.
	 */
	private void readMails()
	{
		List<CustomerEntity> customers = customerService.fetchAllCustomers();
		
		for(CustomerEntity customer : customers)
		{
			readMails(customer.getId(), customer.getName(), customer.getEmailServerSettings());
		}
	}

	/**
	 * Reads mail for specified customer with specified settings.
	 * @param customerId Customer id for which mails needs to be fetched.
	 * @param customerName Name of the customer
	 * @param emailServerSettings Server settings to fetch.
	 */
	private void readMails(long customerId, String customerName, EmailServerSettings emailServerSettings)
	{
		emailService.readMails(emailServerSettings, new IMailProcessor()
		{
			@Override
			public void process(IMailProcessingContext context, ReceivedMailMessage mailMessage)
			{
				if( processReceivedMail(context, customerId, customerName, mailMessage) )
				{
					//if processing is done delete the mail.
					context.delete();
				}
			}
		});
	}
}
