package com.agilepro.commons.models.project;

import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.Required;

@Model
public class StoryAttachmentModel 
{
	/**
	 * The id.
	 */
	@NonDisplayable
	private Long id;
	
	@Required
	@MinLen(5)
	@MaxLen(15)
	private String title;
	
	private FileInfo file;
	
	@MultilineText
	private String description;
	
	/**
	 * The version.
	 */
	@NonDisplayable
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
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

	public FileInfo getFile() {
		return file;
	}

	public void setFile(FileInfo file) {
		this.file = file;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
