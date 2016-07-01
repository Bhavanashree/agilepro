package com.agilepro.persistence.repository.projects;

import java.util.List;

import com.agilepro.commons.models.projects.BacklogSearchQuery;
import com.agilepro.commons.models.projects.BacklogSearchResult;
import com.agilepro.persistence.entity.projects.BackLogEntity;
import com.agilepro.services.common.BacklogSearchCustomizer;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IBackLogRepository.
 */
public interface IBackLogRepository extends IWebutilsRepository<BackLogEntity>
{
	/**
	 * Find backlog.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "backlogsearch", queryModel = BacklogSearchQuery.class, customizer = BacklogSearchCustomizer.class)
	@OrderBy("title")
	public List<BacklogSearchResult> findBacklog(SearchQuery searchQuery);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
