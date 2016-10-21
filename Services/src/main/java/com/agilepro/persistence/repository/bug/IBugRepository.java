package com.agilepro.persistence.repository.bug;

import java.util.List;

import com.agilepro.commons.models.bug.BugSearchQuery;
import com.agilepro.commons.models.bug.BugSearchResult;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IBugRepository.
 */
public interface IBugRepository extends IWebutilsRepository<BugEntity>
{

	/**
	 * Find bugs.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "bugSearch", queryModel = BugSearchQuery.class)
	@OrderBy("title")
	public List<BugSearchResult> findBugs(SearchQuery searchQuery);
}
