package com.agilepro.persistence.entity.projects;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.StoryStatus;
import com.agilepro.commons.models.projects.StoryModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Maintains the Stories created by Teams.
 * 
 * @author Bhavana.
 */
@ExtendableEntity(name = "Story")
@Table(name = "STORY")
public class StoryEntity extends WebutilsExtendableEntity
{
	/**
	 * The Story title.
	 **/
	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;

	/**
	 * The story description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The estimation of time.
	 **/
	@Column(name = "ESTIMATE")
	private Integer estimate;

	/**
	 * The parent story id.
	 **/
	@Column(name = "PARENT_STORY_ID")
	private Long parentStoryId;

	/**
	 * The owner.
	 **/
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "ownerId", subproperty = "id")
	@Column(name = "STORY_OWNER_ID")
	private EmployeeEntity owner;

	/**
	 * The status.
	 **/
	@Column(name = "STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private StoryStatus status;

	/** 
	 * The priority id.
	 */
	@Column(name = "STORY_PRIORITY_ID")
	@ManyToOne
	@PropertyMapping(type = StoryModel.class, from = "priority", subproperty = "id")
	private PriorityEntity priorityId;
	
	/**
	 * Instantiates a new back log entity.
	 */
	public StoryEntity()
	{
	}

	/**
	 * Instantiates a new back log entity.
	 *
	 * @param title
	 *            the title
	 * @param estimate
	 *            the estimate
	 * @param description
	 *            the description
	 * @param status
	 *            the status
	 */
	public StoryEntity(String title, Integer estimate, String description, StoryStatus status)
	{
		this.title = title;
		this.description = description;
		this.estimate = estimate;
		this.status = status;
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
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public EmployeeEntity getOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(EmployeeEntity owner)
	{
		this.owner = owner;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public StoryStatus getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(StoryStatus status)
	{
		this.status = status;
	}

	/**
	 * Gets the priority id.
	 *
	 * @return the priority id
	 */
	public PriorityEntity getPriorityId()
	{
		return priorityId;
	}

	/**
	 * Sets the priority id.
	 *
	 * @param priorityId the new priority id
	 */
	public void setPriorityId(PriorityEntity priorityId)
	{
		this.priorityId = priorityId;
	}
}
