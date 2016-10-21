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
	private BugStatus bugStatus;

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
	private Long project;

	/**
	 * The sprint.
	 **/
	@LOV(name = "sprintLov")
	private Long sprint;

	/**
	 * The story.
	 **/
	@LOV(name = "storiesLov")
	private Long story;

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
	 * Gets the project.
	 *
	 * @return the project
	 */
	public Long getProject()
	{
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @param project
	 *            the new project
	 */
	public void setProject(Long project)
	{
		this.project = project;
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
	 * @param sprint
	 *            the new sprint
	 */
	public void setSprint(Long sprint)
	{
		this.sprint = sprint;
	}

	/**
	 * Gets the story.
	 *
	 * @return the story
	 */
	public Long getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 *
	 * @param story
	 *            the new story
	 */
	public void setStory(Long story)
	{
		this.story = story;
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
}
