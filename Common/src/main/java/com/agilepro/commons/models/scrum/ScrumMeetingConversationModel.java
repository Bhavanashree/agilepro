package com.agilepro.commons.models.scrum;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ScrumMeetingConversationModel.
 * 
 * @author Pritam
 */
@Model(name = "ScrumMeetingConversation")
public class ScrumMeetingConversationModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The scrum meeting id.
	 **/
	@Required
	private Long scrumMeetingId;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

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
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the scrum meeting id.
	 *
	 * @return the scrum meeting id
	 */
	public Long getScrumMeetingId()
	{
		return scrumMeetingId;
	}

	/**
	 * Sets the scrum meeting id.
	 *
	 * @param scrumMeetingId
	 *            the new scrum meeting id
	 */
	public void setScrumMeetingId(Long scrumMeetingId)
	{
		this.scrumMeetingId = scrumMeetingId;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}
}
