package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.models.project.SprintSearchQuery;
import com.agilepro.commons.models.project.SprintSearchResult;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.agilepro.services.common.StorySearchCustomizer;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ISprint for Sprint Entity table.
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
	
	@RestrictBySpace
	public List<SprintEntity> fetchsprintByProjId(@Condition(value = "sprintProjectId.id") Long projectId);

	/**
	 * Delete All.
	 */
	public void deleteAll();
}