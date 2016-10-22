package com.agilepro.commons.models.bug;

import java.util.List;

import com.agilepro.commons.BugStatus;
import com.agilepro.commons.PriorityStatus;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

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
	private String name;

	/**
	 * The description.
	 **/
	private String description;

	/**
	 * The reported by.
	 **/

	@LOV(name = "projectMembers")
	private Long reportedBy;

	/**
	 * The bug status.
	 **/
	private BugStatus bugStatus;

	/**
	 * The owner.
	 **/
	@LOV(name = "projectMembers")
	private Long ownerId;

	/**
	 * The story.
	 **/
	@LOV(name = "storiesLov")
	private Long storyId;

	/**
	 * The sprint.
	 **/
	@LOV(name = "sprintLov")
	private Long sprintId;

	/**
	 * The comments.
	 **/
	private Long commentsId;

	/**
	 * The project id.
	 **/
	@LOV(name = "projectsLov")
	private Long projectId;
	/**
	 * The priority status.
	 **/
	private PriorityStatus priorityStatus;

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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name)
	{
		this.name = name;
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

	/**
	 * Gets the bug status.
	 *
	 * @return the bug status
	 */
	public BugStatus getBugStatus()
	{
		return bugStatus;
	}

	/**
	 * Sets the bug status.
	 *
	 * @param bugStatus
	 *            the new bug status
	 */
	public void setBugStatus(BugStatus bugStatus)
	{
		this.bugStatus = bugStatus;
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
	 * @param storyId
	 *            the new story id
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
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
	 * @param sprintId
	 *            the new sprint id
	 */
	public void setSprintId(Long sprintId)
	{
		this.sprintId = sprintId;
	}

	/**
	 * Gets the comments id.
	 *
	 * @return the comments id
	 */
	public Long getCommentsId()
	{
		return commentsId;
	}

	/**
	 * Sets the comments id.
	 *
	 * @param commentsId
	 *            the new comments id
	 */
	public void setCommentsId(Long commentsId)
	{
		this.commentsId = commentsId;
	}

	/**
	 * Gets the priority status.
	 *
	 * @return the priority status
	 */
	public PriorityStatus getPriorityStatus()
	{
		return priorityStatus;
	}

	/**
	 * Sets the priority status.
	 *
	 * @param priorityStatus
	 *            the new priority status
	 */
	public void setPriorityStatus(PriorityStatus priorityStatus)
	{
		this.priorityStatus = priorityStatus;
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
	 * @param file
	 *            the new file
	 */
	public void setFile(List<FileInfo> file)
	{
		this.file = file;
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
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
}
