package com.agilepro.commons.models.customer;

import java.util.List;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ProjectTeamModel.
 * 
 * @author Pritam
 */
@Model(name = "ProjectTeam")
public class ProjectTeamModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;
	
	/** 
	 * The name. 
	 **/
	@Required
	@MaxLen(15)
	@MinLen(5)
	private String name;
	
	/**
	 * Active project id.
	 **/
	@Required
	private Long projectId;
	
	/** 
	 * The version. 
	 **/
	@NonDisplayable
	private Integer version;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProjectId()
	{
		return projectId;
	}

	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
