package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.persistence.entity.project.StoryDependencyEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * Story Dependency repository for th queries related to story dependency table. 
 * 
 * @author Pritam.
 */
public interface IStoryDependencyRepository extends IWebutilsRepository<StoryDependencyEntity>
{
	@Field("mainStory.id")
	public List<Long> fetchDependenciesIds(@Condition(value = "id") Long storyId);
}
