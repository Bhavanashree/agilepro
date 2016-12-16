package com.agilepro.persistence.entity.project;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.StoryDependencyType;
import com.agilepro.commons.models.project.StoryDependencyModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * StoryDepenedency table will hold the record dependency with story.
 * 
 * @author Pritam.
 */
@Table(name = "STORY_DEPENDENCY")
public class StoryDependencyEntity extends WebutilsEntity
{
	/**
	 * Dependency story id.
	 */
	@Column(name = "MAIN_STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = StoryDependencyModel.class, from = "mainStoryId", subproperty = "id")
	private StoryEntity mainStory;
	
	/**
	 * Dependency story id.
	 */
	@Column(name = "DEPENDENCY_STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = StoryDependencyModel.class, from = "dependencyStoryId", subproperty = "id")
	private StoryEntity dependencyStory;

	/**
	 * Story Dependency type the type of dependency.
	 */
	@Column(name = "DEPENEDENCY_TYPE", nullable = false)
	@DataTypeMapping(type = DataType.STRING)
	private StoryDependencyType storyDependencyType;

	/**
	 * Gets the dependency story.
	 * 
	 * @return the dependency story.
	 */
	public StoryEntity getDependencyStory()
	{
		return dependencyStory;
	}

	/**
	 * Sets the dependency story.
	 * 
	 * @param dependencyStory
	 *            the new dependency story.
	 */
	public void setDependencyStory(StoryEntity dependencyStory)
	{
		this.dependencyStory = dependencyStory;
	}
	
	/**
	 * Gets the dependency type.
	 * 
	 * @return the dependency type.
	 */
	public StoryDependencyType getStoryDependencyType()
	{
		return storyDependencyType;
	}

	/**
	 * Sets the dependency type.
	 * 
	 * @param storyDependencyType
	 *            the new dependency type.
	 */
	public void setStoryDependencyType(StoryDependencyType storyDependencyType)
	{
		this.storyDependencyType = storyDependencyType;
	}
	
	public StoryEntity getMainStory()
	{
		return mainStory;
	}

	public void setMainStory(StoryEntity mainStory)
	{
		this.mainStory = mainStory;
	}

}
