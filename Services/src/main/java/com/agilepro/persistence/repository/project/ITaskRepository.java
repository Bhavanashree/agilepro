package com.agilepro.persistence.repository.project;

import java.util.List;

import com.agilepro.commons.models.customer.TagSearchQuery;
import com.agilepro.commons.models.project.TaskSearchQuery;
import com.agilepro.commons.models.project.TaskSearchResult;
import com.agilepro.persistence.entity.project.TaskEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ITaskRepository.
 */
public interface ITaskRepository extends IWebutilsRepository<TaskEntity>
{
	
	/**
	 * Find task.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "taskSearch", queryModel = TaskSearchQuery.class)
	@OrderBy("title")
	public List<TaskSearchResult> findTask(SearchQuery searchQuery);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
