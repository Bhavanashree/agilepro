package com.agilepro.commons.models.project;

import com.agilepro.commons.StoryDependencyType;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;

/**
 * Story Dependency model for dependency.
 * 
 * @author Pritam.
 */
@Model(name = "StoryDependency")
public class StoryDependencyModel
{
	@Required
	private Long dependencyStoryId;

	@Required
	private StoryDependencyType storyDependencyType;

	public Long getDependencyStoryId()
	{
		return dependencyStoryId;
	}

	public void setDependencyStoryId(Long dependencyStoryId)
	{
		this.dependencyStoryId = dependencyStoryId;
	}

	public StoryDependencyType getStoryDependencyType()
	{
		return storyDependencyType;
	}

	public void setStoryDependencyType(StoryDependencyType storyDependencyType)
	{
		this.storyDependencyType = storyDependencyType;
	}
}
