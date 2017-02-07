package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.StoryStatus;
import com.agilepro.commons.models.project.BacklogStoryModel;
import com.agilepro.commons.models.project.BackLogPriorityModel;
import com.agilepro.commons.models.project.StoryAndTaskResult;
import com.agilepro.commons.models.project.StorySearchQuery;
import com.agilepro.commons.models.project.StorySearchResult;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.agilepro.services.common.StorySearchCustomizer;
import com.yukthi.persistence.repository.annotations.AggregateFunction;
import com.yukthi.persistence.repository.annotations.AggregateFunctionType;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.DefaultCondition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.persistence.repository.annotations.MethodConditions;
import com.yukthi.persistence.repository.annotations.NullCheck;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.annotations.OrderByField;
import com.yukthi.persistence.repository.annotations.OrderByType;
import com.yukthi.persistence.repository.annotations.SearchResult;
import com.yukthi.persistence.repository.annotations.UpdateFunction;
import com.yukthi.persistence.repository.annotations.UpdateOperator;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IBackLogRepository for story table.
 */
public interface IStoryRepository extends IWebutilsRepository<StoryEntity>
{
	/**
	 * Find Story.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "storySearch", queryModel = StorySearchQuery.class, customizer = StorySearchCustomizer.class)
	@OrderBy("title")
	public List<StorySearchResult> findStories(SearchQuery searchQuery);
	
	@LovQuery(name = "storiesLov", valueField = "id", labelField = "title")
	public List<ValueLabel> findStoriesLov();

	@RestrictBySpace
	@SearchQueryMethod(name = "storyTaskSearch", queryModel = StorySearchQuery.class)
	public List<StoryAndTaskResult> findByStories(SearchQuery searchQuery);
	
	@LovQuery(name = "parentStory", valueField = "id", labelField = "title")
	@RestrictBySpace
	public List<ValueLabel> findParentStoryIdLov();

	@RestrictBySpace
	public List<StoryEntity> fetchAllStory(@Condition(value = "title") String storyTitle);
	
	@RestrictBySpace
	public List<StoryEntity> fetchStoryBySprintId(@Condition(value = "sprint.id") Long sprintId);
	
	@RestrictBySpace
	public List<StoryEntity> fetchStoryByProjIdAndSprint(@Condition(value = "project.id") Long projectId, 
									@Condition(value = "sprint.id") Long sprintId);

	@RestrictBySpace
	@MethodConditions(
		nullChecks = @NullCheck(field = "sprint.id"),
		conditions = @DefaultCondition(field = "isManagementStory", value = "false") 
	)
	public List<StoryEntity> fetchBacklogsForKanban(@Condition(value = "project.id") Long projectId);
	
	@SearchResult
	@RestrictBySpace
	@OrderBy(fields = { @OrderByField(name = "priority", type = OrderByType.ASC) })
	@MethodConditions(
		nullChecks = @NullCheck(field = "sprint.id"),
		conditions = @DefaultCondition(field = "isManagementStory", value = "false") 
	)
	public List<BacklogStoryModel> fetchBacklogsForDrag(@Condition(value = "project.id") Long projectId);
	
	@RestrictBySpace
	@SearchResult
	@MethodConditions(
		nullChecks = @NullCheck(field = "sprint.id")
	)
	public List<BacklogStoryModel> fetchBacklogs(@Condition(value = "project.id") Long projectId);

	@RestrictBySpace
	public List<StoryEntity> fetchChilds(@Condition(value = "parentStory.id") Long parentStoryId);
	
	@RestrictBySpace
	@AggregateFunction
	public int storyHasChilds(@Condition(value = "parentStory.id") Long parentStoryId);

	@RestrictBySpace
	public List<StoryEntity> fetchStoriesByProject(@Condition(value = "project.id") Long projectId);
	
	/**
	 * Fetch stories for the given project id in priority order for poker game. 
	 *  
	 * @param pojectId provided project id for which stories are to be fetched.
	 * @return matching records.
	 */
	@RestrictBySpace
	@OrderBy("priority")
	public List<StoryEntity> fetchStoriesByProjectOrderByPriority(@Condition(value = "project.id") Long pojectId);
	
	@RestrictBySpace
	@SearchResult
	@OrderBy("priority")
	@MethodConditions(
			nullChecks = @NullCheck(field = "sprint.id")
		)
	public List<BackLogPriorityModel> fetchStoriesWherePriorityGreaterThan(@Condition(value = "project.id") Long pojectId, 
			@Condition(value = "priority", op = Operator.GE) Integer priority);
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
	
	/**
	 * Fetches maximum order number under specified project and parent story. Parent story is optional.
	 * @param projectId Project to search
	 * @return Max order currently configured
	 */
	@RestrictBySpace
	@AggregateFunction(type = AggregateFunctionType.MAX, field = "priority")
	public int getMaxOrder(@Condition(value = "project.id") Long projectId);

	/**
	 * Fetches minimum order number under specified project and parent story. Parent story is optional.
	 * @param projectId Project to search
	 * @return Max order currently configured
	 */
	@RestrictBySpace
	@AggregateFunction(type = AggregateFunctionType.MIN, field = "priority")
	public int getMinOrder(@Condition(value = "project.id") Long projectId);
	
	/**
	 * Fetches the order of specified story.
	 * @param storyId Story to fetch
	 * @return Specified story order
	 */
	@RestrictBySpace
	@Field("priority")
	public int fetchOrderOfStory(@Condition(value = "id") Long storyId);
	
	/**
	 * Moves the stories back from specified order under specified project and parent story. In other words increases the order of matching stories
	 * by 1.
	 * @param projectId Parent project
	 * @param fromOrder From order where stories needs to move down
	 * @return Number of stories affected
	 */
	@RestrictBySpace
	@UpdateFunction
	@OrderBy(fields = { @OrderByField(name = "priority", type = OrderByType.DESC) })
	public int moveStoriesDown(@Condition(value = "project.id") Long projectId, 
		@Condition(value = "priority", op = Operator.GE) int fromOrder, 
		@Field(value = "priority", updateOp = UpdateOperator.ADD) int incrementValue);
	
	/**
	 * Update priority for the given id. 
	 * 
	 * @param id new priority is to be set for the provided id.
	 * @param newPriority priority of the story where this story is dropped.
	 * @return 1 on successful update of priority.
	 */
	public int updatePriority(@Condition(value = "id") Long id, @Field(value = "priority") Integer newPriority);
	
	public int updateStatus(@Condition(value = "id") Long id, @Field(value = "status") StoryStatus status);
	
	public int updateSprint(@Condition(value = "id") Long id, @Field(value = "sprint") SprintEntity sprint);
	
	public int updateManagement(@Condition(value = "id") Long id, @Field(value = "isManagementStory") Boolean isManagementStory);
}
