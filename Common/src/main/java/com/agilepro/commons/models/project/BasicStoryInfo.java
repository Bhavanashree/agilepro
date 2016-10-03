package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Field;

public class BasicStoryInfo
{
	@Field("story.id")
	private Long id;

	@Field("story.title")
	private String title;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
