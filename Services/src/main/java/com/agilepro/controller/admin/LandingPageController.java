package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_LPAGE;
import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PASSWORD_RESET;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.LandingPageModel;
import com.agilepro.controller.IAgileProConstants;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.services.admin.CustomerService;
import com.agilepro.services.notification.EmailNotificationService;
import com.agilepro.services.notification.IMailTemplates;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.WebutilsConfiguration;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.NoAuthentication;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.mail.IMailCustomizer;
import com.yukthi.webutils.mail.MailMessage;
import com.yukthi.webutils.services.UserService;

/**
 * Controller for landing page.
 * 
 * @author akiran
 */
@ActionName(ACTION_PREFIX_LPAGE)
@RestController
@RequestMapping("/landing-page")
public class LandingPageController extends BaseController
{
	/**
	 * Mail context for reset password template.
	 * @author akiran
	 */
	public static class ResetPasswordContext implements IMailCustomizer
	{
		/**
		 * Mail id for which password being reset.
		 */
		private String mailId;
		
		/**
		 * New password to set.
		 */
		private String newPassword;
		
		/**
		 * Instantiates a new reset password context.
		 *
		 * @param mailId the mail id
		 * @param password the password
		 */
		public ResetPasswordContext(String mailId, String password)
		{
			this.mailId = mailId;
			this.newPassword = password;
		}
		
		/**
		 * Gets the new password to set.
		 *
		 * @return the new password to set
		 */
		public String getNewPassword()
		{
			return newPassword;
		}

		/* (non-Javadoc)
		 * @see com.yukthi.webutils.mail.IMailCustomizer#customize(com.yukthi.webutils.mail.MailMessage)
		 */
		@Override
		public void customize(MailMessage mailMessage)
		{
			mailMessage.setToList(Arrays.asList(mailId));
		}
	}
	
	/** 
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(LandingPageController.class);

	/**
	 * Special characters used in password generation.
	 */
	private static char[] SPECIAL_CHAR = { '!', '@', '$', '%', '^', '&', '*' };

	/**
	 * Random object to generate password.
	 */
	private static Random random = new Random();

	/**
	 * Service to fetch user details and reseting password.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Service to sent emails.
	 */
	@Autowired
	private EmailNotificationService emailNotificationService;

	/**
	 * Service to build mails from templates.
	 */
	//@Autowired
	//private EmailTemplateService emailTemplateService;

	/**
	 * Used to fetch app configuration.
	 */
	@Autowired
	private WebutilsConfiguration configuration;

	/**
	 * Service to fetch customer details.
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * Expected url pattern to extract customer name.
	 */
	private Pattern urlPattern;

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		String pattern = configuration.getAppConfigurations().get(IAgileProConstants.CONFIG_URL_PATTERN);
		this.urlPattern = Pattern.compile(pattern);
	}

	/**
	 * Gets the landing page configuration.
	 *
	 * @param landingPageUrl
	 *            the landing page url
	 * @return the landing page configuration
	 */
	@RequestMapping(value = "/customization", method = RequestMethod.GET)
	@ResponseBody
	@NoAuthentication
	public LandingPageModel getLandingPageConfiguration(@RequestParam("loginUrl") String landingPageUrl)
	{
		LandingPageModel landingPageModel = new LandingPageModel();

		// find the customer details
		Matcher matcher = urlPattern.matcher(landingPageUrl);

		if(matcher.matches())
		{
			String subdomain = matcher.group(1);
			CustomerEntity customerEntity = customerService.findCustomerWithSubdomain(subdomain);

			if(customerEntity == null)
			{
				throw new InvalidRequestParameterException("No customer found with subdomain - {}", subdomain);
			}

			landingPageModel.setCustomerId(customerEntity.getId());
		}

		return landingPageModel;
	}

	/**
	 * Generate password.
	 *
	 * @return the string
	 */
	private String generatePassword()
	{
		final int size = 5;
		final int var1 = 26;
		final int var2 = 10;
		char paswdChars[] = new char[size];

		paswdChars[0] = (char) ('a' + random.nextInt(var1));
		paswdChars[1] = (char) ('A' + random.nextInt(var1));
		paswdChars[2] = SPECIAL_CHAR[random.nextInt(SPECIAL_CHAR.length)];
		paswdChars[2 + 1] = (char) ('0' + random.nextInt(var2));
		paswdChars[2 + 2] = (char) ('a' + random.nextInt(var1));

		return new String(paswdChars);
	}

	/**
	 * Helps in reseting the password of an user.
	 *  Reset password.
	 * @param mailId
	 *            the mail id
	 * @param customerId
	 *            the customer id
	 * @return the base response
	 */
	@ActionName(ACTION_PREFIX_PASSWORD_RESET)
	@RequestMapping(value = "/passwordReset", method = RequestMethod.GET)
	@ResponseBody
	@NoAuthentication
	public BaseResponse resetPassword(@RequestParam("mailId") String mailId, @RequestParam("customerId") long customerId)
	{
		logger.debug("Reset password is invoked for [Customer: {}, Mail id: {}]", customerId, mailId);

		// generate new password
		String newPwd = generatePassword();
		boolean updateRes = false;

		// validate if provided email id is right
		if(customerId >= 0)
		{
			logger.debug("Updating password for user under customer with mail id - {}", mailId);
			updateRes = userService.updatePassword(IAgileProConstants.customerSpace(customerId), mailId, newPwd);
		}
		else
		{
			logger.debug("Updating password for admin user with mail id - {}", mailId);
			updateRes = userService.updatePassword(IAgileProConstants.ADMIN_USER_SPACE, mailId, newPwd);
		}

		if(!updateRes)
		{
			throw new InvalidRequestParameterException("Invalid email id specified. Please check your mail id- {}", mailId);
		}

		logger.debug("Sending reset password to mail id - {}", mailId);

		// send mail with new password
		emailNotificationService.sendMail(IMailTemplates.RESET_PASSWORD, new ResetPasswordContext(mailId, newPwd));
		
		// return success message
		return new BaseResponse();
	}
}
