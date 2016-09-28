package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Field;

public class BasicProjectInfo
{
	
	@Field("project.id")
	private Long id;
	
	@Field("project.name")
	private String name;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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
