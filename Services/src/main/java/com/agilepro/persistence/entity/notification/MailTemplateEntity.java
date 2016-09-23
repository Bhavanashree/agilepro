package com.agilepro.persistence.entity.notification;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.notification.MailTemplateModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class MailTemplateEntity.
 * 
 * @author Pritam
 */
@Table(name = "MAIL_TEMPLATE")
public class MailTemplateEntity extends WebutilsEntity
{
	/** 
	 * The user entity. 
	 **/
	@Column(name = "USER_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = MailTemplateModel.class, from = "userId", subproperty = "id")
	private UserEntity userEntity;
	
	/** 
	 * The subject. 
	 **/
	@Column(name = "SUBJECT", length = 200)
	private String subject;
	
	/** 
	 * The body. 
	 **/
	@Column(name = "BODY", length = 2000)
	private String body;

	/**
	 * Gets the user entity.
	 *
	 * @return the user entity
	 */
	public UserEntity getUserEntity()
	{
		return userEntity;
	}

	/**
	 * Sets the user entity.
	 *
	 * @param userEntity the new user entity
	 */
	public void setUserEntity(UserEntity userEntity)
	{
		this.userEntity = userEntity;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject()
	{
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	public void setBody(String body)
	{
		this.body = body;
	}
}
