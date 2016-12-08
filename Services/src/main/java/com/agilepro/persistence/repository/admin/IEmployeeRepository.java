package com.agilepro.persistence.repository.admin;

import java.util.List;
import com.agilepro.commons.models.admin.EmployeeSearchQuery;
import com.agilepro.commons.models.admin.EmployeeSearchResult;
import com.agilepro.persistence.entity.admin.DesignationEntity;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * EmployeeRepository class.
 * 
 * @author bhavana
 *
 */
public interface IEmployeeRepository extends IWebutilsRepository<EmployeeEntity>
{
	/**
	 * employee Search.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "employeeSearch", queryModel = EmployeeSearchQuery.class)
	@OrderBy("name")
	public List<EmployeeSearchResult> findEmployee(SearchQuery searchQuery);

	/**
	 * Fetch with no space.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the employee entity
	 */
	public EmployeeEntity fetchWithNoSpace(@Condition(value = "id") Long employeeId);

	/**
	 * Fetch employees.
	 *
	 * @param employeeName
	 *            the employee name
	 * @return the list
	 */
	@RestrictBySpace
	public List<EmployeeEntity> fetchEmployees(@Condition(value = "name", op = Operator.LIKE, ignoreCase = true) String employeeName);

	/**
	 * Finds the designation for specified employee id.
	 * 
	 * @param empId
	 *            Employee for designation to be fetched
	 * @return Matching designation
	 */
	@Field("designations.id")
	public List<Long> fetchDesignationIds(@Condition("id") long empId);

	/**
	 * Find employee lov.
	 *
	 * @return the list
	 */
	@LovQuery(name = "employeeLov", valueField = "id", labelField = "name")
	public List<ValueLabel> findEmployeeLov();

	/**
	 * Find project members.
	 *
	 * @return the list
	 */
	@LovQuery(name = "projectMembers", valueField = "id", labelField = "name")
	public List<ValueLabel> findProjectMembers();

	/**
	 * Delete All.
	 */
	public void deleteAll();
}
