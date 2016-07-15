package com.agilepro.persistence.repository.projects;

import java.util.List;

import com.agilepro.commons.models.projects.SprintSearchQuery;
import com.agilepro.commons.models.projects.SprintSearchResult;
import com.agilepro.persistence.entity.projects.SprintEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ISprint.
 */
public interface ISprintRepository extends IWebutilsRepository<SprintEntity>
{
	@RestrictBySpace
	@SearchQueryMethod(name = "sprintSearch", queryModel = SprintSearchQuery.class)
	@OrderBy("name")
	public List<SprintSearchResult> findSprint(SearchQuery searchQuery);

	/**
	 * Delete All.
	 */
	public void deleteAll();
}
