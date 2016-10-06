package com.agilepro.controller.response;

import java.util.List;

import com.agilepro.commons.models.project.BasicStoryInfo;
import com.agilepro.commons.models.project.StoryModel;

/**
 * The Class StoryReleaseReadResponse.
 * 
 * @author Pritam
 */
public class StoryReleaseReadResponse
{
	/**
	 * The basic story infos.
	 **/
	private List<BasicStoryInfo> basicStoryInfos;

	/**
	 * The stories for release.
	 **/
	private List<StoryModel> storiesForRelease;

	/**
	 * Instantiates a new story release read response.
	 *
	 * @param basicStoryInfos
	 *            the basic story infos
	 * @param storiesForRelease
	 *            the stories for release
	 */
	public StoryReleaseReadResponse(List<BasicStoryInfo> basicStoryInfos, List<StoryModel> storiesForRelease)
	{
		super();
		this.basicStoryInfos = basicStoryInfos;
		this.storiesForRelease = storiesForRelease;
	}

	/**
	 * Gets the basic story infos.
	 *
	 * @return the basic story infos
	 */
	public List<BasicStoryInfo> getBasicStoryInfos()
	{
		return basicStoryInfos;
	}

	/**
	 * Sets the basic story infos.
	 *
	 * @param basicStoryInfos
	 *            the new basic story infos
	 */
	public void setBasicStoryInfos(List<BasicStoryInfo> basicStoryInfos)
	{
		this.basicStoryInfos = basicStoryInfos;
	}

	/**
	 * Gets the stories for release.
	 *
	 * @return the stories for release
	 */
	public List<StoryModel> getStoriesForRelease()
	{
		return storiesForRelease;
	}

	/**
	 * Sets the stories for release.
	 *
	 * @param storiesForRelease
	 *            the new stories for release
	 */
	public void setStoriesForRelease(List<StoryModel> storiesForRelease)
	{
		this.storiesForRelease = storiesForRelease;
	}
}
