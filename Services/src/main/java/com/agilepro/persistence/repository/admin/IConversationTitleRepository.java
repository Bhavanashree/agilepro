package com.agilepro.persistence.repository.admin;

import java.util.List;
import com.agilepro.persistence.entity.project.ConversationTitleEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IConversationTitleRepository.
 * 
 * @author Pritam
 */
public interface IConversationTitleRepository extends IWebutilsRepository<ConversationTitleEntity>
{

	/**
	 * Fetch title by stroy id.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	@RestrictBySpace
	public List<ConversationTitleEntity> fetchTitleByStroyId(@Condition(value = "story.id") Long storyId);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
