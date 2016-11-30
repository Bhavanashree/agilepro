package com.agilepro.persistence.repository.project;

import java.util.Date;
import java.util.List;

import com.agilepro.commons.models.sprint.SprintDropDown;
import com.agilepro.commons.models.sprint.SprintSearchQuery;
import com.agilepro.commons.models.sprint.SprintSearchResult;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.annotations.SearchResult;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
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
	@SearchQueryMethod(name = "sprintSearch", queryModel = SprintSearchQuery.class)
	@OrderBy("name")
	public List<SprintSearchResult> findSprint(SearchQuery searchQuery);
	
	@LovQuery(name = "sprintLov", valueField = "id", labelField = "name")
	public List<ValueLabel> findSprintsLov();

	@RestrictBySpace
	public List<SprintEntity> fetchAllSprint(@Condition(value = "name", op = Operator.LIKE, ignoreCase = true) String sprintName);
	
	@RestrictBySpace
	@SearchResult
	@OrderBy("startDate")
	public List<SprintDropDown> fetchSprintByProjId(@Condition(value = "project.id") Long projectId,
									@Condition(value = "endDate", op = Operator.GE) Date date);

	/**
	 * Delete All.
	 */
	public void deleteAll();
}