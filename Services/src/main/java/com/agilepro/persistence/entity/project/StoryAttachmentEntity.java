package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.Table;
import com.yukthi.webutils.repository.WebutilsEntity;


@Table(name = "STORY_ATTACHMENT")
public class StoryAttachmentEntity extends WebutilsEntity 
{
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
