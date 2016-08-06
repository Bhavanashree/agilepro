package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.ProjectSearchQuery;
import com.agilepro.commons.models.customer.ProjectSearchResult;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IProjectRepository.
 * 
 * @author Pritam
 */
public interface IProjectRepository extends IWebutilsRepository<ProjectEntity>
{
	/**
	 * Find projects.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "projectSearch", queryModel = ProjectSearchQuery.class)
	@OrderBy("name")
	public List<ProjectSearchResult> findProjects(SearchQuery searchQuery);

	/**
	 * Find project lov.
	 *
	 * @return the list
	 */
	@LovQuery(name = "projectsLov", valueField = "id", labelField = "name")
	@RestrictBySpace
	public List<ValueLabel> findProjectLov();

	/**
	 * Fetch projects.
	 *
	 * @return the list
	 */
	@RestrictBySpace
	public List<ProjectEntity> fetchProjects();

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
