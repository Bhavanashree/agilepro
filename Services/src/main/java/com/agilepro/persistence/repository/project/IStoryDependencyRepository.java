package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.models.project.StoryDependencyModel;
import com.agilepro.persistence.entity.project.StoryDependencyEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * Story Dependency repository for th queries related to story dependency table. 
 * 
 * @author Pritam.
 */
public interface IStoryDependencyRepository extends IWebutilsRepository<StoryDependencyEntity>
{
	public List<StoryDependencyEntity> fetchDependenciesIds(@Condition(value = "mainStory.id") Long mainStoryId);
}
