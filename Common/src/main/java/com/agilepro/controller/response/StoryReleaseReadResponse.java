package com.agilepro.controller.response;

import java.util.List;

import com.agilepro.commons.models.project.BasicStoryInfo;
import com.agilepro.commons.models.project.StoryModel;

public class StoryReleaseReadResponse 
{
	private List<BasicStoryInfo> basicStoryInfos;
	
	private List<StoryModel> storiesForRelease;

	
	public StoryReleaseReadResponse(List<BasicStoryInfo> basicStoryInfos, List<StoryModel> storiesForRelease) {
		super();
		this.basicStoryInfos = basicStoryInfos;
		this.storiesForRelease = storiesForRelease;
	}

	public List<BasicStoryInfo> getBasicStoryInfos() {
		return basicStoryInfos;
	}

	public void setBasicStoryInfos(List<BasicStoryInfo> basicStoryInfos) {
		this.basicStoryInfos = basicStoryInfos;
	}

	public List<StoryModel> getStoriesForRelease() {
		return storiesForRelease;
	}

	public void setStoriesForRelease(List<StoryModel> storiesForRelease) {
		this.storiesForRelease = storiesForRelease;
	}
	
	
}
