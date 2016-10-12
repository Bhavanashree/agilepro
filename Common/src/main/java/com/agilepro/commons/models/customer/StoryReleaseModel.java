package com.agilepro.commons.models.customer;

import java.util.List;

import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class StoryReleaseModel.
 * 
 * @author Pritam
 */
@Model
public class StoryReleaseModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The release id.
	 **/
	@Required
	private Long releaseId;

	/**
	 * The story id.
	 **/
	private Long storyId;

	private List<Long> storyIds;

	public StoryReleaseModel()
	{
		super();
	}

	public StoryReleaseModel(Long storyId, Long releaseId)
	{
		super();
		this.storyId = storyId;
		this.releaseId = releaseId;
	}

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(Long releaseId) {
		this.releaseId = releaseId;
	}

	/**
	 * Gets the story id.
	 *
	 * @return the story id
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 *
	 * @param storyId
	 *            the new story id
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public List<Long> getStoryIds()
	{
		return storyIds;
	}

	public void setStoryIds(List<Long> storyIds)
	{
		this.storyIds = storyIds;
	}
}
