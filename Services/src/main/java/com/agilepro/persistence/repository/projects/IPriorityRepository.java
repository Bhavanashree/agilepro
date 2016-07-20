package com.agilepro.persistence.repository.projects;

import java.util.List;

import com.agilepro.commons.models.projects.PrioritySearchQuery;
import com.agilepro.commons.models.projects.PrioritySearchResult;
import com.agilepro.persistence.entity.projects.PriorityEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IPriorityRepository.
 */
public interface IPriorityRepository extends IWebutilsRepository<PriorityEntity>
{
	
	/**
	 * Find priority.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "prioritySearch", queryModel = PrioritySearchQuery.class)
	@OrderBy("name")
	public List<PrioritySearchResult> findPriority(SearchQuery searchQuery);

	/**
	 * Find priority lov.
	 *
	 * @return the list
	 */
	@LovQuery(name = "priorityLov", valueField = "id", labelField = "name")
	public List<ValueLabel> findPriorityLov();

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
