package com.agilepro.commons.models.scrum;

import java.util.Date;

import com.agilepro.commons.ActionConversationStatus;
import com.agilepro.commons.ScrumActionStatus;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ScrumActionItemConversationModel.
 * 
 * @author Pritam
 */
@Model(name = "ScrumActionItemConversation")
public class ScrumActionItemConversationModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The scrum action item id.
	 **/
	@Required
	private Long scrumActionItemId;

	/**
	 * The user id. *
	 */
	@Required
	private Long userId;

	/**
	 * The message.
	 */
	@Required
	private String message;

	/**
	 * The display name.
	 **/
	private String displayName;

	/**
	 * The time.
	 **/
	private String time;

	/**
	 * The display date.
	 **/
	private String displayDate;

	/**
	 * The provided by.
	 **/
	private String providedBy;

	/**
	 * The updated on.
	 **/
	private Date updatedOn;
	
	/** 
	 * Action status for displaying in which conversation the action status is closed or opened. 
	 **/
	private ActionConversationStatus actionConversationStatus;

	/**
	 * Scrum action status shows the status of the scrum action.
	 **/
	@Required
	private ScrumActionStatus scrumActionStatus;

	/**
	 * Instantiates a new scrum action item conversation model.
	 */
	public ScrumActionItemConversationModel()
	{
		super();
	}

	/**
	 * Instantiates a new scrum action item conversation model.
	 *
	 * @param scrumActionItemId
	 *            the scrum action item id
	 * @param userId
	 *            the user id
	 * @param message
	 *            the message
	 */
	public ScrumActionItemConversationModel(Long scrumActionItemId, Long userId, String message)
	{
		super();
		this.scrumActionItemId = scrumActionItemId;
		this.message = message;
		this.userId = userId;
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
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
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

	/**
	 * Gets the scrum action item id.
	 *
	 * @return the scrum action item id
	 */
	public Long getScrumActionItemId()
	{
		return scrumActionItemId;
	}

	/**
	 * Sets the scrum action item id.
	 *
	 * @param scrumActionItemId
	 *            the new scrum action item id
	 */
	public void setScrumActionItemId(Long scrumActionItemId)
	{
		this.scrumActionItemId = scrumActionItemId;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getDisplayDate()
	{
		return displayDate;
	}

	public void setDisplayDate(String displayDate)
	{
		this.displayDate = displayDate;
	}

	public String getProvidedBy()
	{
		return providedBy;
	}

	public void setProvidedBy(String providedBy)
	{
		this.providedBy = providedBy;
	}

	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public ScrumActionStatus getScrumActionStatus()
	{
		return scrumActionStatus;
	}

	public void setScrumActionStatus(ScrumActionStatus scrumActionStatus)
	{
		this.scrumActionStatus = scrumActionStatus;
	}

	public ActionConversationStatus getActionConversationStatus() {
		return actionConversationStatus;
	}

	public void setActionConversationStatus(ActionConversationStatus actionConversationStatus) {
		this.actionConversationStatus = actionConversationStatus;
	}
}
