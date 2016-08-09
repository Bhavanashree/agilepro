package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.models.project.BacklogSearchQuery;
import com.agilepro.commons.models.project.BacklogSearchResult;
import com.agilepro.persistence.entity.project.BacklogEntity;
import com.agilepro.services.common.BacklogSearchCustomizer;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IBackLogRepository for story table.
 */
public interface IBacklogRepository extends IWebutilsRepository<BacklogEntity>
{
	/**
	 * Find Story.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "backlogSearch", queryModel = BacklogSearchQuery.class, customizer = BacklogSearchCustomizer.class)
	@OrderBy("title")
	public List<BacklogSearchResult> findBacklog(SearchQuery searchQuery);

	@LovQuery(name = "parentStoryId", valueField = "id", labelField = "title")
	@RestrictBySpace
	public List<ValueLabel> findParentStoryIdLov();

	@RestrictBySpace
	public List<BacklogEntity> fetchAllStory(@Condition(value = "title") String storyTitle);
	
	@RestrictBySpace
	public List<BacklogEntity> fetchStoryBySprintId(@Condition(value = "sprint.id") Long sprintId);
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
}
