package com.agilepro.commons.models.project;

import java.util.List;

import com.agilepro.commons.StoryDependencyType;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * Story Dependency model for dependency.
 * 
 * @author Pritam.
 */
@Model(name = "StoryDependency")
public class StoryDependencyModel
{
	/**
	 * Primary key id.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * Dependency story id for mapping.
	 */
	@Required
	private Long dependencyStoryId;

	/**
	 * Story dependency type.
	 */
	@Required
	private StoryDependencyType storyDependencyType;

	/**
	 * Main story id.
	 */
	@Required
	private Long mainStoryId;
	
	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * Gets the dependency story id.
	 * 
	 * @return the dependency story id.
	 */
	public Long getDependencyStoryId()
	{
		return dependencyStoryId;
	}

	/**
	 * Sets the dependency story id.
	 * 
	 * @param dependencyStoryId
	 *            the new dependency story id.
	 */
	public void setDependencyStoryId(Long dependencyStoryId)
	{
		this.dependencyStoryId = dependencyStoryId;
	}

	/**
	 * Gets the story dependency type.
	 * 
	 * @return the story dependency type.
	 */
	public StoryDependencyType getStoryDependencyType()
	{
		return storyDependencyType;
	}

	/**
	 * Sets the story dependency type.
	 * 
	 * @param storyDependencyType
	 *            the new dependency type.
	 */
	public void setStoryDependencyType(StoryDependencyType storyDependencyType)
	{
		this.storyDependencyType = storyDependencyType;
	}

	public Long getMainStoryId()
	{
		return mainStoryId;
	}

	public void setMainStoryId(Long mainStoryId)
	{
		this.mainStoryId = mainStoryId;
	}

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
}
