package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.persistence.entity.project.StoryNoteEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.DefaultCondition;
import com.yukthi.persistence.repository.annotations.MethodConditions;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.annotations.OrderByField;
import com.yukthi.persistence.repository.annotations.OrderByType;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IStoryNoteRepository.
 * 
 * @author Pritam
 */
public interface IStoryNoteRepository extends IWebutilsRepository<StoryNoteEntity>
{
	@RestrictBySpace
	@OrderBy(fields = { @OrderByField(name = "updatedOn", type = OrderByType.DESC) })
	@MethodConditions(
			conditions = @DefaultCondition(field = "storyNoteStatus", value = "PUBLISHED") 
		)
	public List<StoryNoteEntity> fetchLatestPublisedStoryNoteByStoryId(@Condition(value = "story.id") Long storyId);
	
	@RestrictBySpace
	@OrderBy(fields = { @OrderByField(name = "updatedOn", type = OrderByType.DESC) })
	public List<StoryNoteEntity> fetchAllStoryNoteByStoryId(@Condition(value = "story.id") Long storyId);
}
