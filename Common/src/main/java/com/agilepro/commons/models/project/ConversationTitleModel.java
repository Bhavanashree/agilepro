package com.agilepro.commons.models.project;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ConversationTitleModel.
 * 
 * @author Pritam
 */
@Model
public class ConversationTitleModel
{
	/**
	 * The id.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * The version.
	 */
	@NonDisplayable
	private Integer version;
	
	/** 
	 * The story id. 
	 **/
	@Required
	@NonDisplayable
	private Long storyId;
	
	/** 
	 * The user id. 
	 **/
	@NonDisplayable
	private Long ownerId;

	/**
	 * The title.
	 **/
	@Required
	private String name;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public Long getStoryId()
	{
		return storyId;
	}

	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}

	public Long getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}
}
