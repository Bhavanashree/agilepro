package com.agilepro.commons.models.customer;

import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

@Model(name = "AlreadyReleased")
public class AlreadyReleasedModel 
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	private Long projectId;
	
	private Long storyId;
	
	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getStoryId() {
		return storyId;
	}

	public void setStoryId(Long storyId) {
		this.storyId = storyId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
