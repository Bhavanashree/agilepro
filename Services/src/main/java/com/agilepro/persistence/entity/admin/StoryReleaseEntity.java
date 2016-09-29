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
	private ReleaseEntity release;

	/**
	 * The story.
	 **/
	@Column(name = "STORY_ID")
	@ManyToOne
	@PropertyMapping(type = StoryReleaseModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * Gets the release.
	 *
	 * @return the release
	 */
	public ReleaseEntity getRelease()
	{
		return release;
	}

	/**
	 * Sets the release.
	 *
	 * @param release the new release
	 */
	public void setRelease(ReleaseEntity release)
	{
		this.release = release;
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
