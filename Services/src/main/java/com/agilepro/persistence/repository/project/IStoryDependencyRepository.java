package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.StoryDependencyType;
import com.agilepro.commons.models.project.StoryDependencyModel;
import com.agilepro.persistence.entity.project.StoryDependencyEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.annotations.RequestParam;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * Story Dependency repository for th queries related to story dependency table. 
 * 
 * @author Pritam.
 */
public interface IStoryDependencyRepository extends IWebutilsRepository<StoryDependencyEntity>
{
	/**
	 * Fetch dependency id's for the main story.
	 * 
	 * @param mainStoryId the main story id for which dependency is added.
	 * @return matching records.
	 */
	@RestrictBySpace
	public List<StoryDependencyEntity> fetchDependenciesIds(@Condition(value = "mainStory.id") Long mainStoryId);
	
	/**
	 * Update the dependency type for the matching record.
	 * 
	 * @param id for which dependency type is to be updated.
	 * @param storyDependencyType the new dependency type.
	 * @return true on success update or else false.
	 */
	@RestrictBySpace
	public boolean updateDependencyType(@Condition(value = "id") Long id, 
										@Field(value = "storyDependencyType") StoryDependencyType storyDependencyType);
}
