package com.agilepro.notification;

import java.util.Map;

import com.yukthi.webutils.repository.UserEntity;

/**
 * The Interface INotificationProcessor.
 * 
 * @author Pritam
 */
public interface INotificationProcessor
{
	/**
	 * Process.
	 *
	 * @param userEntity the user entity
	 * @param title the title
	 * @param body the body
	 * @param attributes the attributes
	 * @return true, if successful
	 */
	public boolean process(UserEntity userEntity, String title, String body, Map<String, String> attributes);
}
