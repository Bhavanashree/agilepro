package com.agilepro.persistence.repository.projects;

import java.util.List;

import com.agilepro.commons.models.projects.SprintSearchQuery;
import com.agilepro.commons.models.projects.SprintSearchResult;
import com.agilepro.persistence.entity.projects.SprintEntity;
import com.agilepro.services.common.StorySearchCustomizer;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
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

	/**
	 * Find sprint.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "sprintSearch", queryModel = SprintSearchQuery.class, customizer = StorySearchCustomizer.class)
	@OrderBy("name")
	public List<SprintSearchResult> findSprint(SearchQuery searchQuery);

	@RestrictBySpace
	public List<SprintEntity> fetchAllSprint(@Condition(value = "name", op = Operator.LIKE, ignoreCase = true) String sprintName);

	/**
	 * Delete All.
	 */
	public void deleteAll();
}
