package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.project.ConversationMessageEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IConversationMessageRepository.
 * 
 * @author Pritam
 */
public interface IConversationMessageRepository extends IWebutilsRepository<ConversationMessageEntity>
{ 
	/*@SearchQueryMethod(name = "conversationSearch", queryModel = ConversationSearchQuery.class)
	@OrderBy("message")
	public List<ConversationSearchResult> findConversation(SearchQuery searchQuery);*/
	
	/**
	 * Fetch conversation by stroy id.
	 *
	 * @param conversationTitleId the conversation title id
	 * @return the list
	 */
	@RestrictBySpace
	public List<ConversationMessageEntity> fetchConversationMessageByTitleId(@Condition(value = "conversationTitleEntity.id")Long conversationTitleId);
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
}
