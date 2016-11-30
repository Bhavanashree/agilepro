package com.agilepro.persistence.entity.scrum;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agilepro.commons.ScrumActionStatus;
import com.agilepro.commons.models.scrum.ScrumActionItemModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * ScrumActionItemEntity this table will hold the value for action items.
 * Records are inserted when actions are provided to the employees.
 * 
 * @author Pritam
 */
@Table(name = "SCRUM_ACTION_ITEM")
public class ScrumActionItemEntity extends WebutilsEntity
{
	/**
	 * The scrum meeting object for mapping.
	 **/
	@OneToOne
	@PropertyMapping(type = ScrumActionItemModel.class, from = "scrumMeetingId", subproperty = "id")
	@Column(name = "SCRUM_MEETING_ID", nullable = false)
	private ScrumMeetingEntity scrumMeeting;

	/**
	 * The project member ids for action.
	 **/
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	@Column(name = "EMPLOYEES", length = 1000)
	private Set<Long> employeeIds;
	
	/** 
	 * The message. 
	 **/
	@Column(name = "MESSAGE")
	private String message;

	/**
	 * The user entity.
	 **/
	@Column(name = "USER_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ScrumActionItemModel.class, from = "userId", subproperty = "id")
	private UserEntity user;

	/**
	 * Scrum action status shows the status of the scrum aciton. By default status will be OPEN.
	 **/
	@Column(name = "ACTION_STATUS")
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private ScrumActionStatus scrumActionStatus = ScrumActionStatus.OPEN;

	/**
	 * Gets the scrum meeting.
	 *
	 * @return the scrum meeting
	 */
	public ScrumMeetingEntity getScrumMeeting()
	{
		return scrumMeeting;
	}

	/**
	 * Sets the scrum meeting.
	 *
	 * @param scrumMeeting
	 *            the new scrum meeting
	 */
	public void setScrumMeeting(ScrumMeetingEntity scrumMeeting)
	{
		this.scrumMeeting = scrumMeeting;
	}

	/**
	 * Gets the employee ids.
	 *
	 * @return the employee ids
	 */
	public Set<Long> getEmployeeIds()
	{
		return employeeIds;
	}

	/**
	 * Sets the employee ids.
	 *
	 * @param employeeIds
	 *            the new employee ids
	 */
	public void setEmployeeIds(Set<Long> employeeIds)
	{
		this.employeeIds = employeeIds;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserEntity getUser()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(UserEntity user)
	{
		this.user = user;
	}

	/**
	 * Gets the scrum action status.
	 *
	 * @return the scrum action status
	 */
	public ScrumActionStatus getScrumActionStatus()
	{
		return scrumActionStatus;
	}

	/**
	 * Sets the scrum action status.
	 *
	 * @param scrumActionStatus
	 *            the new scrum action status
	 */
	public void setScrumActionStatus(ScrumActionStatus scrumActionStatus)
	{
		this.scrumActionStatus = scrumActionStatus;
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
