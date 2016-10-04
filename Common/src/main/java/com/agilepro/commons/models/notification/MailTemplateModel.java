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
	 *  The to admin. 
	 **/
	private Boolean toAdmin;

	/** 
	 * The to project manager.
	 */
	private Boolean toProjectManager;

	/**
	 *  The cc admin.
	 **/
	private Boolean ccAdmin;

	/** 
	 * The cc project manager. 
	 **/
	private Boolean ccProjectManager;

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
	 * Gets the to admin.
	 *
	 * @return the to admin
	 */
	public Boolean getToAdmin()
	{
		return toAdmin;
	}

	/**
	 * Sets the to admin.
	 *
	 * @param toAdmin the new to admin
	 */
	public void setToAdmin(Boolean toAdmin)
	{
		this.toAdmin = toAdmin;
	}

	/**
	 * Gets the to project manager.
	 *
	 * @return the to project manager
	 */
	public Boolean getToProjectManager()
	{
		return toProjectManager;
	}

	/**
	 * Sets the to project manager.
	 *
	 * @param toProjectManager the new to project manager
	 */
	public void setToProjectManager(Boolean toProjectManager)
	{
		this.toProjectManager = toProjectManager;
	}

	/**
	 * Gets the cc admin.
	 *
	 * @return the cc admin
	 */
	public Boolean getCcAdmin()
	{
		return ccAdmin;
	}

	/**
	 * Sets the cc admin.
	 *
	 * @param ccAdmin the new cc admin
	 */
	public void setCcAdmin(Boolean ccAdmin)
	{
		this.ccAdmin = ccAdmin;
	}

	/**
	 * Gets the cc project manager.
	 *
	 * @return the cc project manager
	 */
	public Boolean getCcProjectManager()
	{
		return ccProjectManager;
	}

	/**
	 * Sets the cc project manager.
	 *
	 * @param ccProjectManager the new cc project manager
	 */
	public void setCcProjectManager(Boolean ccProjectManager)
	{
		this.ccProjectManager = ccProjectManager;
	}
}
