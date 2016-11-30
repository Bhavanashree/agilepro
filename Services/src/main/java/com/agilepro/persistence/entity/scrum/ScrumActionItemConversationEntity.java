package com.agilepro.persistence.entity.scrum;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.ActionConversationStatus;
import com.agilepro.commons.models.scrum.ScrumActionItemConversationModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * ScrumActionItemConversationEntity holds the scrum action conversation.
 * Records are inserted from the action conversation message.
 * 
 * @author Pritam
 */
@Table(name = "SCRUM_ACTION_ITEM_CONVERSATION")
public class ScrumActionItemConversationEntity extends WebutilsEntity
{
	/**
	 * The scrum action item.
	 **/
	@ManyToOne
	@PropertyMapping(type = ScrumActionItemConversationModel.class, from = "scrumActionItemId", subproperty = "id")
	@Column(name = "SCRUM_ACTION_ITEM_ID", nullable = false)
	private ScrumActionItemEntity scrumActionItem;

	/**
	 * The user entity.
	 **/
	@Column(name = "USER_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ScrumActionItemConversationModel.class, from = "userId", subproperty = "id")
	private UserEntity user;

	/**
	 * The provided by.
	 **/
	private String providedBy;

	/**
	 * Message for scrum action conversation.
	 */
	@Column(name = "NAME")
	private String message;

	/**
	 * Action status for showing which message is closed or re opened.
	 **/
	@Column(name = "ACTION_STATUS")
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private ActionConversationStatus actionConversationStatus;

	/**
	 * Gets the scrum action item.
	 *
	 * @return the scrum action item
	 */
	public ScrumActionItemEntity getScrumActionItem()
	{
		return scrumActionItem;
	}

	/**
	 * Sets the scrum action item.
	 *
	 * @param scrumActionItem
	 *            the new scrum action item
	 */
	public void setScrumActionItem(ScrumActionItemEntity scrumActionItem)
	{
		this.scrumActionItem = scrumActionItem;
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
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	public UserEntity getUser()
	{
		return user;
	}

	public void setUser(UserEntity user)
	{
		this.user = user;
	}

	public String getProvidedBy()
	{
		return providedBy;
	}

	public void setProvidedBy(String providedBy)
	{
		this.providedBy = providedBy;
	}

	public ActionConversationStatus getActionConversationStatus() {
		return actionConversationStatus;
	}

	public void setActionConversationStatus(ActionConversationStatus actionConversationStatus) {
		this.actionConversationStatus = actionConversationStatus;
	}
}
