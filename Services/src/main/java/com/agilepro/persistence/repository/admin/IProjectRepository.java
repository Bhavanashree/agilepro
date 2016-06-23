package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.ProjectsSearchQuery;
import com.agilepro.commons.models.customer.ProjectsSearchResult;
import com.agilepro.persistence.entity.admin.ProjectsEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IProjectRepository.
 * 
 * @author Pritam
 */
public interface IProjectRepository extends IWebutilsRepository<ProjectsEntity>
{
	/**
	 * Find projects.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "projectsSearch", queryModel = ProjectsSearchQuery.class)
	@OrderBy("name")
	public List<ProjectsSearchResult> findProjects(SearchQuery searchQuery);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
