package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.customer.StoryReleaseModel;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class StoryReleaseEntity.
 * 
 * @author Pritam
 */
@Table(name = "STORY_RELEASE")
@UniqueConstraints({ @UniqueConstraint(name = "STORY_RELEASE_ID", fields = { "project", "story" }) })
public class StoryReleaseEntity extends WebutilsEntity
{
	@ManyToOne
	@PropertyMapping(type = StoryReleaseModel.class, from = "projectId", subproperty = "id")
	@Column(name = "PROJECT_ID", nullable = false)
	private ProjectEntity project;

	/**
	 * The story.
	 **/
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = StoryReleaseModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	public ProjectEntity getProject()
	{
		return project;
	}

	public void setProject(ProjectEntity project)
	{
		this.project = project;
	}

	/**
	 * Gets the story.
	 *
	 * @return the story
	 */
	public StoryEntity getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 *
	 * @param story
	 *            the new story
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
	}
}
