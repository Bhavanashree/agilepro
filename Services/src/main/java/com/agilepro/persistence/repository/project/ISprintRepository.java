package com.agilepro.persistence.repository.project;

import java.util.Date;
import java.util.List;

import com.agilepro.commons.models.project.SprintSearchQuery;
import com.agilepro.commons.models.project.SprintSearchResult;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.services.common.StorySearchCustomizer;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
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
	@OrderBy("startDate")
	public List<SprintEntity> fetchSprintByProjId(@Condition(value = "project.id") Long projectId,
									@Condition(value = "endDate", op = Operator.GE) Date date);

	/**
	 * Delete All.
	 */
	public void deleteAll();
}