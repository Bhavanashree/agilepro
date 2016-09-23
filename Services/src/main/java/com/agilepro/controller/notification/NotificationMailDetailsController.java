package com.agilepro.controller.notification;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_MAIL_DETAILS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.NotificationMailDetails;
import com.agilepro.services.admin.CustomerService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.security.UserDetails;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class NotificationMailDetailsController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_MAIL_DETAILS)
@RequestMapping("/mailDetails")
public class NotificationMailDetailsController extends BaseController
{
	/**
	 * The customer service.
	 **/
	@Autowired
	private CustomerService customerService;

	/**
	 * The current user service.
	 **/
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

	/**
	 * Save.
	 *
	 * @param notificationMailDetails
	 *            the notification mail details
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid NotificationMailDetails notificationMailDetails)
	{
		UserDetails userDetails = currentUserService.getCurrentUserDetails();

		UserEntity userEntity = userService.fetch(userDetails.getUserId());

		CustomerModel customerModel = customerService.fetchFullModel(userEntity.getBaseEntityId(), CustomerModel.class);

		customerModel.setNotificationMailDetails(notificationMailDetails);
		
		customerService.update(customerModel);

		return new BasicSaveResponse();
	}
}
