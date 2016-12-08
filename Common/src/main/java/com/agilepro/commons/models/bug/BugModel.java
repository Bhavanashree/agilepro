package com.agilepro.commons.models.bug;

import java.util.List;

import com.agilepro.commons.BugStatus;
import com.agilepro.commons.PriorityStatus;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;
import com.yukthi.webutils.common.annotations.ReadOnly;

/**
 * The Class BugModel.
 */
@ExtendableModel(name = "Bug")
@Model
public class BugModel extends AbstractExtendableModel
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
	private String title;

	/**
	 * The description.
	 **/
	@MultilineText
	private String description;

	/**
	 * The reported by.
	 **/

	@LOV(name = "projectMembers")
	private Long reportedBy;

	/**
	 * The bug status.
	 **/
	private BugStatus status;

	/**
	 * The owner.
	 **/
	@ReadOnly
	@LOV(name = "projectMembers")
	private Long owner;

	/**
	 * The priority status.
	 **/
	@ReadOnly
	private PriorityStatus priority;

	/**
	 * The project id.
	 **/
	@LOV(name = "projectsLov")
	private Long projectId;

	/**
	 * The sprint.
	 **/
	@LOV(name = "sprintLov")
	private Long sprintId;

	/**
	 * The story.
	 **/
	@LOV(name = "storiesLov")
	private Long storyId;

	/**
	 * The file.
	 **/
	private List<FileInfo> file;

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
	 * Gets the reported by.
	 *
	 * @return the reported by
	 */
	public Long getReportedBy()
	{
		return reportedBy;
	}

	/**
	 * Sets the reported by.
	 *
	 * @param reportedBy
	 *            the new reported by
	 */
	public void setReportedBy(Long reportedBy)
	{
		this.reportedBy = reportedBy;
	}

	public BugStatus getStatus()
	{
		return status;
	}

	public void setStatus(BugStatus status)
	{
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
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public Long getOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(Long owner)
	{
		this.owner = owner;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public PriorityStatus getPriority()
	{
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(PriorityStatus priority)
	{
		this.priority = priority;
	}

	/**
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the sprint id.
	 *
	 * @return the sprint id
	 */
	public Long getSprintId()
	{
		return sprintId;
	}

	/**
	 * Sets the sprint id.
	 *
	 * @param sprintId the new sprint id
	 */
	public void setSprintId(Long sprintId)
	{
		this.sprintId = sprintId;
	}

	/**
	 * Gets the story id.
	 *
	 * @return the story id
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 *
	 * @param storyId the new story id
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public List<FileInfo> getFile()
	{
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(List<FileInfo> file)
	{
		this.file = file;
	}
}
