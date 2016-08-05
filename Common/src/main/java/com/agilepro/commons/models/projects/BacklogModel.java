package com.agilepro.commons.models.projects;

import com.agilepro.commons.BacklogStatus;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class StoryModel.
 * 
 * @author Bhavana.
 */

@ExtendableModel(name = "Backlog")
@Model
public class BacklogModel extends AbstractExtendableModel
{

	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The version.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The name.
	 **/
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	private String title;

	/**
	 * The description.
	 **/
	@MaxLen(200)
	@MultilineText
	private String description;

	/**
	 * The estimate.
	 **/
	private Integer estimate;

	/**
	 * The parent story id.
	 **/
	@LOV(name = "parentStoryId")
	private Long parentStoryId;

	/**
	 * The owner id.
	 **/
	@LOV(name = "projectMembers")
	private Long ownerId;

	/**
	 * The status.
	 **/
	private BacklogStatus status;

	/**
	 * The priority.
	 */
	@LOV(name = "priorityLov")
	private Long priority;

	/**
	 * The sprint id.
	 **/
	@NonDisplayable
	private Long sprint;

	/**
	 * Instantiates a new back log model.
	 */
	public BacklogModel()
	{}

	/**
	 * Instantiates a new story model.
	 *
	 * @param title
	 *            the title
	 * @param estimate
	 *            the estimate
	 * @param description
	 *            the description
	 * @param parentStoryId
	 *            the parent story id
	 * @param priority
	 *            the priority
	 */
	public BacklogModel(String title, Integer estimate, String description, Long parentStoryId, Long priority)
	{
		this.title = title;
		this.estimate = estimate;
		this.description = description;
		this.parentStoryId = parentStoryId;
		this.priority = priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
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
	 * Gets the estimate.
	 *
	 * @return the estimate
	 */
	public Integer getEstimate()
	{
		return estimate;
	}

	/**
	 * Sets the estimate.
	 *
	 * @param estimate
	 *            the new estimate
	 */
	public void setEstimate(Integer estimate)
	{
		this.estimate = estimate;
	}

	/**
	 * Gets the parent story id.
	 *
	 * @return the parent story id
	 */
	public Long getParentStoryId()
	{
		return parentStoryId;
	}

	/**
	 * Sets the parent story id.
	 *
	 * @param parentStoryId
	 *            the new parent story id
	 */
	public void setParentStoryId(Long parentStoryId)
	{
		this.parentStoryId = parentStoryId;
	}

	/**
	 * Gets the owner id.
	 *
	 * @return the owner id
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * Sets the owner id.
	 *
	 * @param ownerId
	 *            the new owner id
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public BacklogStatus getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(BacklogStatus status)
	{
		this.status = status;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Long getPriority()
	{
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	/**
	 * Gets the sprint.
	 *
	 * @return the sprint
	 */
	public Long getSprint()
	{
		return sprint;
	}

	/**
	 * Sets the sprint.
	 *
	 * @param sprint the new sprint
	 */
	public void setSprint(Long sprint)
	{
		this.sprint = sprint;
	}
}
