package com.agilepro.persistence.entity.notification;

import javax.persistence.Column;

import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class NotificationMailDetailEntity.
 * 
 * @author Pritam
 */
public class NotificationMailDetailEntity extends WebutilsEntity
{ 
	@Column(name = "EMAIL", length = 50, nullable = false)
	private String email;
}
