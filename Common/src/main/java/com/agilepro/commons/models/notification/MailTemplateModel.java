package com.agilepro.commons.models.notification;

import java.util.List;

import com.yukthi.webutils.common.annotations.Model;

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
	 * Instantiates a new mail template model.
	 */
	public MailTemplateModel()
	{}

	/**
	 * Instantiates a new mail template model.
	 *
	 * @param recipient the recipient
	 * @param subject the subject
	 * @param body the body
	 */
	public MailTemplateModel(List<String> recipient, String subject, String body)
	{
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}

	public List<String> getRecipient()
	{
		return recipient;
	}

	public void setRecipient(List<String> recipient)
	{
		this.recipient = recipient;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}
}
