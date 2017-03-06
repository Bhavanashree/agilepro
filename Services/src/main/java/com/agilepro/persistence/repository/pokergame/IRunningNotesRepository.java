package com.agilepro.persistence.repository.pokergame;

import java.util.List;

import com.agilepro.persistence.entity.pokergame.RunningNotesEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * IRunningNotesRepository for creating and queries related to running notes table.
 * 
 * @author Pritam.
 */
public interface IRunningNotesRepository extends IWebutilsRepository<RunningNotesEntity>
{
	public List<RunningNotesEntity> fetchRunningNotesByStory(@Condition(value = "story.id") Long storyId);
	
	public List<RunningNotesEntity> fetchRunningNotesByBug(@Condition(value = "bug.id") Long bugId);
}
