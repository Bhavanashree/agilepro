package com.agilepro.services.notification;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_NOTIFICATION_SETTINGS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.controllers.notification.NotificationSettingsModel;
import com.agilepro.controller.AgileProUserDetails;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.common.models.notification.NotificationModel;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.notification.NotificationService;
import com.yukthi.webutils.services.CurrentUserService;

/**
 * The Class NotificationSettingsController.
 */
@RestController
@ActionName(ACTION_PREFIX_NOTIFICATION_SETTINGS)
@RequestMapping("/notificationSettings")
public class NotificationSettingsController extends BaseController
{
	/**
	 * The User service.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The notifications service.
	 **/
	@Autowired
	private NotificationService notificationsService;

	/**
	 * Fetch notifications.
	 *
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@ResponseBody
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public BasicReadResponse<List<NotificationModel>> fetchNotifications()
	{
		AgileProUserDetails agileModel = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		return new BasicReadResponse<List<NotificationModel>>(notificationsService.getUserPreferences(agileModel.getUserId()));
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid NotificationSettingsModel model)
	{
		AgileProUserDetails agileModel = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		notificationsService.setUserPrefereneces(agileModel.getUserId(), model.getNotificationSettings());

		return new BasicSaveResponse();
	}
}
