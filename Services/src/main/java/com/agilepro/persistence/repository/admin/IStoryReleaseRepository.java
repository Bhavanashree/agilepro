package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.project.BasicStoryInfo;
import com.agilepro.persistence.entity.admin.StoryReleaseEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.SearchResult;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IStoryReleaseRepository.
 * 
 * @author Pritam
 */
public interface IStoryReleaseRepository extends IWebutilsRepository<StoryReleaseEntity>
{
	@RestrictBySpace
	@SearchResult
	public List<BasicStoryInfo> fetchStorysByReleaseAndProject(@Condition(value = "release.id") Long releaseId, @Condition(value = "story.project.id") Long projectId);
	
	@RestrictBySpace
	public boolean deleteByProjectId(@Condition(value = "release.id") Long releaseId, @Condition(value = "story.project.id") Long projectId);
	
	@RestrictBySpace
	public boolean deleteByStoryId(@Condition(value = "story.id") Long storyId);
}
