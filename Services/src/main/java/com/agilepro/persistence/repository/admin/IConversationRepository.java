package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.admin.ConversationSearchQuery;
import com.agilepro.commons.models.admin.ConversationSearchResult;
import com.agilepro.persistence.entity.admin.ConversationEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IConversationRepository.
 */
public interface IConversationRepository extends IWebutilsRepository<ConversationEntity>
{ 
	
	/**
	 * Find conversation.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@SearchQueryMethod(name = "conversationSearch", queryModel = ConversationSearchQuery.class)
	@OrderBy("message")
	public List<ConversationSearchResult> findConversation(SearchQuery searchQuery);
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
}
