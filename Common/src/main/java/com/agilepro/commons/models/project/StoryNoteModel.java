package com.agilepro.commons.models.project;

import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

@Model(name = "StoryNote")
public class StoryNoteModel 
{
	@NonDisplayable
	private Long id;
	
	@NonDisplayable
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
