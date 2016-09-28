package com.agilepro.commons.models.customer;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ProjectReleaseModel.
 * 
 * @author Pritam
 */
@Model(name = "ProjectRelease")
public class ProjectReleaseModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The project id.
	 **/
	private Long projectId;

	/**
	 * The release id.
	 **/
	@Required
	private Long releaseId;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

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
	 * Gets the release id.
	 *
	 * @return the release id
	 */
	public Long getReleaseId()
	{
		return releaseId;
	}

	/**
	 * Sets the release id.
	 *
	 * @param releaseId
	 *            the new release id
	 */
	public void setReleaseId(Long releaseId)
	{
		this.releaseId = releaseId;
	}
}
