package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Field;

/**
 * The Class BasicStoryInfo.
 * 
 * @author Pritam
 */
public class BasicStoryInfo
{
	/**
	 * The id.
	 **/
	@Field("story.id")
	private Long id;

	/**
	 * The title.
	 **/
	@Field("story.title")
	private String title;

	/**
	 * The project id.
	 **/
	@Field("story.project.id")
	private Long projectId;

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
