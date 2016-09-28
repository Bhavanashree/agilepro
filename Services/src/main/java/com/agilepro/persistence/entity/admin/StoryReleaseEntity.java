package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.ProjectReleaseModel;
import com.agilepro.commons.models.customer.StoryReleaseModel;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class StoryReleaseEntity.
 * 
 * @author Pritam
 */
@Table(name = "STORY_RELEASE")
public class StoryReleaseEntity extends WebutilsEntity
{
	/**
	 * The release entity.
	 **/
	@ManyToOne
	@PropertyMapping(type = ProjectReleaseModel.class, from = "releaseId", subproperty = "id")
	@Column(name = "RELEASE_ID", nullable = false)
	private ReleaseEntity releaseEntity;

	/**
	 * The story.
	 **/
	@Column(name = "STORY_ID")
	@ManyToOne
	@PropertyMapping(type = StoryReleaseModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * Gets the release entity.
	 *
	 * @return the release entity
	 */
	public ReleaseEntity getReleaseEntity()
	{
		return releaseEntity;
	}

	/**
	 * Sets the release entity.
	 *
	 * @param releaseEntity
	 *            the new release entity
	 */
	public void setReleaseEntity(ReleaseEntity releaseEntity)
	{
		this.releaseEntity = releaseEntity;
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
