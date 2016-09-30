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
public class ProjectReleaseReadResponse extends BasicReadResponse
{
	private List<BasicProjectInfo> basicProjectInfos;
	
	private List<ProjectModel> projectForRelease;

	public ProjectReleaseReadResponse(List<BasicProjectInfo> basicProjectInfos, List<ProjectModel> projectForRelease)
	{
		super();
		this.basicProjectInfos = basicProjectInfos;
		this.projectForRelease = projectForRelease;
	}

	public List<BasicProjectInfo> getBasicProjectInfos()
	{
		return basicProjectInfos;
	}

	public void setBasicProjectInfos(List<BasicProjectInfo> basicProjectInfos)
	{
		this.basicProjectInfos = basicProjectInfos;
	}

	public List<ProjectModel> getProjectForRelease()
	{
		return projectForRelease;
	}

	public void setProjectForRelease(List<ProjectModel> projectForRelease)
	{
		this.projectForRelease = projectForRelease;
	}
}
