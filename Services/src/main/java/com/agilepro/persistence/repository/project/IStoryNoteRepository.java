package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.persistence.entity.project.StoryNoteEntity;
import com.yukthi.persistence.repository.annotations.Condition;
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
	public List<StoryNoteEntity> fetchAllPublishedNoteByStoryId(@Condition(value = "story.id") Long storyId, @Condition(value = "published") Boolean published);
}
