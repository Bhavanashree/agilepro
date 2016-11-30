package com.agilepro.persistence.repository.scrum;

import java.util.List;

import com.agilepro.persistence.entity.scrum.ScrumActionItemConversationEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IScrumActionItemConversationRepository.
 * 
 * @author Pritam
 */
public interface IScrumActionItemConversationRepository extends IWebutilsRepository<ScrumActionItemConversationEntity>
{
	public List<ScrumActionItemConversationEntity> fetchConversationByActionId(@Condition(value = "scrumActionItem.id") Long scrumActionItemId);
}
