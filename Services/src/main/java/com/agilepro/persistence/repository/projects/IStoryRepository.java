package com.agilepro.persistence.repository.projects;

import java.util.List;

import com.agilepro.commons.models.projects.StorySearchQuery;
import com.agilepro.commons.models.projects.StorySearchResult;
import com.agilepro.persistence.entity.projects.StoryEntity;
import com.agilepro.services.common.StorySearchCustomizer;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IBackLogRepository.
 */
public interface IStoryRepository extends IWebutilsRepository<StoryEntity>
{
	/**
	 * Find backlog.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "storysearch", queryModel = StorySearchQuery.class, customizer = StorySearchCustomizer.class)
	@OrderBy("title")
	public List<StorySearchResult> findBacklog(SearchQuery searchQuery);

	@LovQuery(name = "parentStoryId", valueField = "id", labelField = "title")
	@RestrictBySpace
	public List<ValueLabel> findParentStoryIdLov();

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
