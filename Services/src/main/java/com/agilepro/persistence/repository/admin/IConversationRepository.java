package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.admin.ConversationEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IConversationRepository.
 * 
 * @author Pritam
 */
public interface IConversationRepository extends IWebutilsRepository<ConversationEntity>
{ 
	/*@SearchQueryMethod(name = "conversationSearch", queryModel = ConversationSearchQuery.class)
	@OrderBy("message")
	public List<ConversationSearchResult> findConversation(SearchQuery searchQuery);*/
	
	@RestrictBySpace
	public List<ConversationEntity> fetchConversationByStroyId(@Condition(value = "storyEntity.id")Long storyId);
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
}
