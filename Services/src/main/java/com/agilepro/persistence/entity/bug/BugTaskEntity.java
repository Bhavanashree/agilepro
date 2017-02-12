package com.agilepro.persistence.entity.bug;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.TaskStatus;
import com.agilepro.commons.models.bug.BugTaskModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Bug task for saving the task related to bugs, bugs can be sub divided to
 * tasks.
 * 
 * @author Pritam.
 */
@ExtendableEntity(name = "bugTask")
@Table(name = "BUG_TASK")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_BUG_TASK_TITLE", fields = { "spaceIdentity", "bug", "title" }) })
public class BugTaskEntity extends WebutilsExtendableEntity
{
	/**
	 * The task title.
	 **/
	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;

	/**
	 * The task description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The estimation of time.
	 **/
	@Column(name = "ESTIMATE_TIME")
	private Integer estimateTime;

	/**
	 * The project id.
	 */
	@Column(name = "PROJECT_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = BugTaskModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity projectId;

	/**
	 * Bug id for many to one mapping.
	 */
	@Column(name = "BUG_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = BugTaskModel.class, from = "bugId", subproperty = "id")
	private BugEntity bug;

	/**
	 * The status.
	 **/
	@Column(name = "STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private TaskStatus status;

	/**
	 * Instantiates a new bug task entity.
	 */
	public BugTaskEntity()
	{}

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
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public ProjectEntity getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(ProjectEntity projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public TaskStatus getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(TaskStatus status)
	{
		this.status = status;
	}

	/**
	 * Gets the estimate time.
	 * 
	 * @return the estimate time.
	 */
	public Integer getEstimateTime()
	{
		return estimateTime;
	}

	/**
	 * Set the estimate time.
	 * 
	 * @param estimateTime
	 *            the new estimate time.
	 */
	public void setEstimateTime(Integer estimateTime)
	{
		this.estimateTime = estimateTime;
	}

	/**
	 * Gets the bug.
	 * 
	 * @return the bug.
	 */
	public BugEntity getBug()
	{
		return bug;
	}

	/**
	 * Set the bug.
	 * 
	 * @param bug
	 *            the new bug.
	 */
	public void setBug(BugEntity bug)
	{
		this.bug = bug;
	}
}
