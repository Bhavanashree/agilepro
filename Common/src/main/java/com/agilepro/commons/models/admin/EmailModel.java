package com.agilepro.commons.models.admin;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class EmailModel.
 * 
 * @author Pritam.
 */
@Model
public class EmailModel
{
	/**
	 * Email id.
	 */
	@NonDisplayable
	private Long id;
	
	/**
	 * To whom we want to send email.
	 */
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_EMAIL, message = "Invalid mail id specified")
	@MinLen(5)
	@MaxLen(50)
	@NotEmpty
	private String to;
	
	/**
	 * Subject of the message.
	 */
	private String subject;

	/**
	 * Message.
	 */
	private String message;
	
	/**
	 * Instantiates a new email model.
	 */
	public EmailModel()
	{}
	
	/**
	 * Instantiates a new email model.
	 *
	 * @param to the to
	 * @param subject the subject
	 * @param message the message
	 */
	public EmailModel(String to, String subject, String message)
	{
		this.to = to;
		this.subject = subject;
		this.message = message;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String getTo()
	{
		return to;
	}
	
	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public void setTo(String to)
	{
		this.to = to;
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
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
