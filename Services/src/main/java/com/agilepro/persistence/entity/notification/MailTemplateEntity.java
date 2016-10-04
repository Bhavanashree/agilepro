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
	 * The To.
	 **/
	@Column(name = "TO_ADMIN")
	private Boolean toAdmin;

	/**
	 * The to project manager.
	 **/
	@Column(name = "TO_PROJECT_MANAGER")
	private Boolean toProjectManager;

	/**
	 * The cc admin.
	 **/
	@Column(name = "CC_ADMIN")
	private Boolean ccAdmin;

	/**
	 * The cc project manager.
	 **/
	@Column(name = "CC_PROJECT_MANAGER")
	private Boolean ccProjectManager;

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
	 * @param toAdmin
	 *            the new to admin
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
	 * @param toProjectManager
	 *            the new to project manager
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
	 * @param ccAdmin
	 *            the new cc admin
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
	 * @param ccProjectManager
	 *            the new cc project manager
	 */
	public void setCcProjectManager(Boolean ccProjectManager)
	{
		this.ccProjectManager = ccProjectManager;
	}
}
