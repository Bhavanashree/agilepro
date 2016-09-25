package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.project.StoryAttachmentEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IStoryAttachmentRepository.
 * 
 * @author Pritam
 */
public interface IStoryAttachmentRepository extends IWebutilsRepository<StoryAttachmentEntity>
{
	/**
	 * Fetch attachment by story id.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public List<StoryAttachmentEntity> fetchAttachmentByStoryId(@Condition(value = "storyEntity.id") Long storyId);
}
