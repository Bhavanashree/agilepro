package com.agilepro.persistence.entity.notification;

import javax.persistence.Column;
import javax.persistence.Table;

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
	 * The cc.
	 **/
	@Column(name = "CC_MAIL")
	private String ccSend;

	/**
	 * The To.
	 **/
	@Column(name = "TO_SEND")
	private String toSend;

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
	 * @param subject
	 *            the new subject
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
	 * @param body
	 *            the new body
	 */
	public void setBody(String body)
	{
		this.body = body;
	}

	/**
	 * Gets the cc send.
	 *
	 * @return the cc send
	 */
	public String getCcSend()
	{
		return ccSend;
	}

	/**
	 * Sets the cc send.
	 *
	 * @param ccSend the new cc send
	 */
	public void setCcSend(String ccSend)
	{
		this.ccSend = ccSend;
	}

	/**
	 * Gets the to send.
	 *
	 * @return the to send
	 */
	public String getToSend()
	{
		return toSend;
	}

	/**
	 * Sets the to send.
	 *
	 * @param toSend the new to send
	 */
	public void setToSend(String toSend)
	{
		this.toSend = toSend;
	}
}
