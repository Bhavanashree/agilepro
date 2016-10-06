package com.agilepro.controller.response;

import java.util.List;

import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.commons.models.project.BasicProjectInfo;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Class ProjectReleaseResponse.
 * 
 * @author Pritam
 */
@SuppressWarnings("rawtypes")
public class ProjectReleaseReadResponse extends BasicReadResponse
{
	/**
	 * The basic project infos.
	 **/
	private List<BasicProjectInfo> basicProjectInfos;

	/**
	 * The project for release.
	 **/
	private List<ProjectModel> projectForRelease;

	/**
	 * Instantiates a new project release read response.
	 *
	 * @param basicProjectInfos
	 *            the basic project infos
	 * @param projectForRelease
	 *            the project for release
	 */
	public ProjectReleaseReadResponse(List<BasicProjectInfo> basicProjectInfos, List<ProjectModel> projectForRelease)
	{
		super();
		this.basicProjectInfos = basicProjectInfos;
		this.projectForRelease = projectForRelease;
	}

	/**
	 * Gets the basic project infos.
	 *
	 * @return the basic project infos
	 */
	public List<BasicProjectInfo> getBasicProjectInfos()
	{
		return basicProjectInfos;
	}

	/**
	 * Sets the basic project infos.
	 *
	 * @param basicProjectInfos
	 *            the new basic project infos
	 */
	public void setBasicProjectInfos(List<BasicProjectInfo> basicProjectInfos)
	{
		this.basicProjectInfos = basicProjectInfos;
	}

	/**
	 * Gets the project for release.
	 *
	 * @return the project for release
	 */
	public List<ProjectModel> getProjectForRelease()
	{
		return projectForRelease;
	}

	/**
	 * Sets the project for release.
	 *
	 * @param projectForRelease
	 *            the new project for release
	 */
	public void setProjectForRelease(List<ProjectModel> projectForRelease)
	{
		this.projectForRelease = projectForRelease;
	}
}
