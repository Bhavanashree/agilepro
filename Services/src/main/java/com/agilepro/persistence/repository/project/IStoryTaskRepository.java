package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.TaskStatus;
import com.agilepro.persistence.entity.project.StoryTaskEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.persistence.repository.annotations.UpdateFunction;
import com.yukthi.persistence.repository.annotations.UpdateOperator;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ITaskRepository.
 */
public interface IStoryTaskRepository extends IWebutilsRepository<StoryTaskEntity>
{
	@RestrictBySpace
	public List<StoryTaskEntity> fetchAllStories(@Condition(value = "story.id") Long storyId);

	@RestrictBySpace
	public List<StoryTaskEntity> fetchByStoryId(@Condition(value = "story.id") Long storyId);
	
	@RestrictBySpace
	public StoryTaskEntity fetchVersionById(Integer versionId);
	
	@UpdateFunction
	public boolean addExtraTime(@Condition("id") Long id, @Field(value = "actualTimeTaken", updateOp = UpdateOperator.ADD) Integer timeTaken);

	@RestrictBySpace
	public int updateTaskStatus(@Condition("id") Long id, @Field(value = "status") TaskStatus status);

	@RestrictBySpace
	public int updateTaskStatusByStory(@Condition("story.id") Long storyId, @Field(value = "status") TaskStatus status);
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
}
