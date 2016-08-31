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
public interface IConversationTitleRepository  extends IWebutilsRepository<ConversationTitleEntity>
{
	@RestrictBySpace
	public List<ConversationTitleEntity> fetchTitleByStroyId(@Condition(value = "storyEntity.id")Long storyId);
}
