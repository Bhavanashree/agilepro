package com.agilepro.commons.models.scrum;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.agilepro.commons.ScrumActionStatus;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ScrumActionItemModel. Model class for creating action items for
 * sending mail.
 * 
 * @author Pritam
 */
@Model(name = "ScrumActionItem")
public class ScrumActionItemModel
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
	 * The scrum meeting id.
	 **/
	@Required
	private Long scrumMeetingId;

	/**
	 * The employee ids.
	 **/
	private Set<Long> employeeIds;

	/**
	 * The employee names.
	 **/
	@IgnoreField
	private Map<Long, String> employeeNames;

	/**
	 * The provided by.
	 **/
	private String providedBy;

	/**
	 * User id for many to one mapping.
	 **/
	@Required
	private Long userId;

	/**
	 * The message.
	 */
	@Required
	private String message;

	/**
	 * The updated on.
	 **/
	private Date updatedOn;

	/**
	 * The time.
	 **/
	private String time;

	/**
	 * The display date.
	 **/
	private String displayDate;

	/**
	 * Scrum action status shows the status of the scrum aciton.
	 **/
	private ScrumActionStatus scrumActionStatus;

	/**
	 * Instantiates a new scrum action item model.
	 */
	public ScrumActionItemModel()
	{
		super();
	}

	/**
	 * Instantiates a new scrum action item model.
	 *
	 * @param scrumMeetingId
	 *            the scrum meeting id
	 * @param employeeIds
	 *            the employee ids
	 * @param message
	 *            the message
	 * @param userId
	 *            the user id
	 */
	public ScrumActionItemModel(Long scrumMeetingId, Set<Long> employeeIds, String message, Long userId)
	{
		super();
		this.scrumMeetingId = scrumMeetingId;
		this.employeeIds = employeeIds;
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
	 * Gets the employee names.
	 *
	 * @return the employee names
	 */
	public Map<Long, String> getEmployeeNames()
	{
		return employeeNames;
	}

	/**
	 * Sets the employee names.
	 *
	 * @param employeeNames
	 *            the employee names
	 */
	public void setEmployeeNames(Map<Long, String> employeeNames)
	{
		this.employeeNames = employeeNames;
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

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * Gets the provided by.
	 *
	 * @return the provided by
	 */
	public String getProvidedBy()
	{
		return providedBy;
	}

	/**
	 * Sets the provided by.
	 *
	 * @param providedBy
	 *            the new provided by
	 */
	public void setProvidedBy(String providedBy)
	{
		this.providedBy = providedBy;
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
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time
	 *            the new time
	 */
	public void setTime(String time)
	{
		this.time = time;
	}

	/**
	 * Gets the display date.
	 *
	 * @return the display date
	 */
	public String getDisplayDate()
	{
		return displayDate;
	}

	/**
	 * Sets the display date.
	 *
	 * @param displayDate
	 *            the new display date
	 */
	public void setDisplayDate(String displayDate)
	{
		this.displayDate = displayDate;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}
}
