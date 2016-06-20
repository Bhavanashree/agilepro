package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.admin.DesignationSearchQuery;
import com.agilepro.commons.models.admin.DesignationSearchResult;
import com.agilepro.persistence.entity.admin.DesignationEntity;
import com.yukthi.persistence.repository.annotations.DefaultCondition;
import com.yukthi.persistence.repository.annotations.MethodConditions;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * Designation Repository class.
 * 
 * @author bhavana
 *
 */
public interface IDesignationRepository extends IWebutilsRepository<DesignationEntity>
{

	/**
	 * Designation Search.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return searchQuery
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "designationSearch", queryModel = DesignationSearchQuery.class)
	@OrderBy("name")
	public List<DesignationSearchResult> findDesignation(SearchQuery searchQuery);

	/**
	 * Find designation lov.
	 *
	 * @return the list
	 */
	@LovQuery(name = "designationList", valueField = "id", labelField = "name")
	public List<ValueLabel> findDesignationLov();

	/**
	 * Fetch project designations.
	 *
	 * @return the list
	 */
	@MethodConditions(conditions = { @DefaultCondition(field = "projectLevel", value = "true") })
	@LovQuery(name = "designationLovProjectLevel", valueField = "id", labelField = "name")
	public List<ValueLabel> fetchProjectDesignations();

	/**
	 * fetch the roles.
	 *
	 * @return the list
	 */
	public List<DesignationEntity> fetchExternalRoles();

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
