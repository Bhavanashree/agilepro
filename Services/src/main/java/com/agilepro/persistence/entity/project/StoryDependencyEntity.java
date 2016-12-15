package com.agilepro.persistence.entity.project;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@Column(name = "DEPENDENCY_STORY_ID")
	@ManyToOne
	@PropertyMapping(type = StoryDependencyModel.class, from = "dependencyStoryId", subproperty = "id")
	private StoryEntity dependencyStory;

	/**
	 * Story Dependency type the type of dependency.
	 */
	@Column(name = "DEPENEDENCY_TYPE")
	@DataTypeMapping(type = DataType.STRING)
	private StoryDependencyType storyDependencyType;

	/**
	 * StoryDependency intermediate table between story and dependency.
	 */
	@ManyToMany
	@JoinTable(name = "STORY_DEPENDENCY_MAP", joinColumns = { @JoinColumn(name = "STORY_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEPENDENCY_ID") })
	private List<StoryEntity> mainStory;

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
	 * Gets the main story.
	 * 
	 * @return the main story.
	 */
	public List<StoryEntity> getMainStory()
	{
		return mainStory;
	}

	/**
	 * Sets the main story.
	 * 
	 * @param mainStory
	 *            the new story.
	 */
	public void setMainStory(List<StoryEntity> mainStory)
	{
		this.mainStory = mainStory;
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
}
