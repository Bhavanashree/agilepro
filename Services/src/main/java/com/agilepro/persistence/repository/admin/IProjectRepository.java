package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.ProjectSearchQuery;
import com.agilepro.commons.models.customer.ProjectSearchResult;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * IProjectRepository for fetching project related data's with different queries.
 * 
 * @author Pritam
 */
public interface IProjectRepository extends IWebutilsRepository<ProjectEntity>
{
	/**
	 * Find projects from search button in Ui.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return all the project as list project search result.
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "projectSearch", queryModel = ProjectSearchQuery.class)
	@OrderBy("name")
	public List<ProjectSearchResult> findProjects(SearchQuery searchQuery);
 
	/**
	 * List of values to be displayed in the ui drop down.
	 *
	 * @return the list containing project details.
	 */
	@LovQuery(name = "projectsLov", valueField = "id", labelField = "name")
	@RestrictBySpace
	public List<ValueLabel> findProjectLov();

	/**
	 * Fetch project by space identity used in scrum meeting cron job.
	 *
	 * @param spaceIdentity
	 *            the space identity
	 * @return the matching result.
	 */
	public List<ProjectEntity> fetchProjectBySpaceIdentity(@Condition(value = "spaceIdentity") String spaceIdentity);

	/**
	 * Fetch all projects for the drop down in header.
	 *
	 * @return the list having all the projects.
	 */
	@RestrictBySpace
	public List<ProjectEntity> fetchAllProjects();
	
	/**
	 * Delete all.
	 */
	public void deleteAll();
}
