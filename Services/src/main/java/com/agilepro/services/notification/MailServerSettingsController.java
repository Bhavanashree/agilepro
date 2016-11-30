package com.agilepro.services.notification;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_MAIL_DETAILS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.yukthi.webutils.common.IWebUtilsActionConstants.ACTION_TYPE_FETCH;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.services.admin.CustomerService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.mails.EmailServerSettings;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.services.CurrentUserService;

/**
 * The Class NotificationMailDetailsController.
 * 
 * @author Bhavana
 */
@RestController
@ActionName(ACTION_PREFIX_MAIL_DETAILS)
@RequestMapping("/mailDetails")
public class MailServerSettingsController extends BaseController
{
	/**
	 * The customer service.
	 **/
	@Autowired
	private CustomerService customerService;

	/**
	 * The email service.
	 **/
	@Autowired
	private EmailNotificationService emailService;

	/**
	 * The User service.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * Save.
	 *
	 * @param mailServerDetails
	 *            the notification mail details
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BaseResponse save(@RequestBody @Valid EmailServerSettings mailServerDetails)
	{
		AgileProUserDetails userDetails = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		customerService.updateMailServerSetting(mailServerDetails, userDetails.getCustomerId());

		return new BaseResponse();
	}

	/**
	 * Fetch server settings.
	 *
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_FETCH)
	@ResponseBody
	@RequestMapping(value = "/fetch", method = RequestMethod.GET)
	public BasicReadResponse<EmailServerSettings> fetchServerSettings()
	{
		AgileProUserDetails userDetailss = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		return new BasicReadResponse<EmailServerSettings>(emailService.getSettings(userDetailss.getCustomerId()));
	}
}
