package com.agilepro.commons.models.notification;

import java.util.List;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.ReadOnly;

/**
 * The Class MailTemplateModel.
 * 
 * @author Pritam
 */
@Model
public class MailTemplateModel
{
	/**
	 * The to.
	 **/
	@Required
	private List<String> recipient;

	/**
	 * The subject.
	 **/
	private String subject;

	/**
	 * The body.
	 **/
	private String body;

	/**
	 * The cc.
	 **/
	private String cc;

	/**
	 * Instantiates a new mail template model.
	 */
	public MailTemplateModel()
	{}

	/**
	 * Instantiates a new mail template model.
	 *
	 * @param recipient
	 *            the recipient
	 * @param subject
	 *            the subject
	 * @param body
	 *            the body
	 */
	public MailTemplateModel(List<String> recipient, String subject, String body)
	{
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * Gets the recipient.
	 *
	 * @return the recipient
	 */
	public List<String> getRecipient()
	{
		return recipient;
	}

	/**
	 * Sets the recipient.
	 *
	 * @param recipient
	 *            the new recipient
	 */
	public void setRecipient(List<String> recipient)
	{
		this.recipient = recipient;
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
	 * Gets the cc.
	 *
	 * @return the cc
	 */
	public String getCc()
	{
		return cc;
	}

	/**
	 * Sets the cc.
	 *
	 * @param cc the new cc
	 */
	public void setCc(String cc)
	{
		this.cc = cc;
	}
}
