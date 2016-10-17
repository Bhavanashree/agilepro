package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.customer.StoryReleaseModel;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.persistence.annotations.DeleteWithParent;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

import freemarker.core._RegexBuiltins.replace_reBI;

/**
 * The Class StoryReleaseEntity.
 * 
 * @author Pritam
 */
@Table(name = "STORY_RELEASE")
@UniqueConstraints({ @UniqueConstraint(name = "STORY_RELEASE_ID", fields = { "release", "story" }) })
public class StoryReleaseEntity extends WebutilsEntity
{
	@DeleteWithParent
	@ManyToOne
	@Column(name = "RELEASE_ID", nullable = false)
	@PropertyMapping(type = StoryReleaseModel.class, from = "releaseId", subproperty = "id")
	private ReleaseEntity release;

	/**
	 * The story.
	 **/
	@Column(name = "STORY_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = StoryReleaseModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	public ReleaseEntity getRelease() {
		return release;
	}

	public void setRelease(ReleaseEntity release) {
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
