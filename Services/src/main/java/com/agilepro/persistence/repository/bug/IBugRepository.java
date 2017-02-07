package com.agilepro.persistence.repository.bug;

import java.util.List;

import com.agilepro.commons.models.bug.BacklogBugModel;
import com.agilepro.commons.models.bug.BugSearchQuery;
import com.agilepro.commons.models.bug.BugSearchResult;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.yukthi.persistence.repository.annotations.AggregateFunction;
import com.yukthi.persistence.repository.annotations.AggregateFunctionType;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.persistence.repository.annotations.MethodConditions;
import com.yukthi.persistence.repository.annotations.NullCheck;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.annotations.SearchResult;
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
	
	@RestrictBySpace
	public List<BugEntity> fetchBugsBySprintId(@Condition(value = "project.id") Long projectId, @Condition(value = "targetSprint.id") Long sprintId);
	
	@SearchResult
	@RestrictBySpace
	@MethodConditions(
		nullChecks = @NullCheck(field = "targetSprint.id")
	)
	public List<BacklogBugModel> fetchBacklogBugs(@Condition(value = "project.id") Long projectId);
	
	/**
	 * Fetches maximum order number under specified project.
	 * @param projectId Project to search
	 * @return Max order currently configured
	 */
	@RestrictBySpace
	@AggregateFunction(type = AggregateFunctionType.MAX, field = "priority")
	public int getMaxOrder(@Condition(value = "project.id") Long projectId);
	
	public int updateSprint(@Condition(value = "id") Long id, @Field(value = "targetSprint") SprintEntity sprint);
}
