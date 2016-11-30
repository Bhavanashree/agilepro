package com.agilepro.commons.controllers.notification;

import java.util.List;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.models.notification.NotificationSetting;

/**
 * NotificationSettingsModel for wrapping list of notification settings for controller.
 */
@Model
public class NotificationSettingsModel
{
	/** 
	 * The notification settings. 
	 **/
	@IgnoreField
	private List<NotificationSetting> notificationSettings;

	public List<NotificationSetting> getNotificationSettings()
	{
		return notificationSettings;
	}

	public void setNotificationSettings(List<NotificationSetting> notificationSettings)
	{
		this.notificationSettings = notificationSettings;
	}
}
