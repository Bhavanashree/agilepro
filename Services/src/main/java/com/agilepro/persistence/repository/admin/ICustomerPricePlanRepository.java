package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanSearchQuery;
import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanSearchResult;
import com.agilepro.persistence.entity.admin.CustomerPricePlanEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IPricePlanRepository.
 * 
 * @author Neha
 */
public interface ICustomerPricePlanRepository extends IWebutilsRepository<CustomerPricePlanEntity>
{
	/**
	 * Find customer price plan.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "priceplanSearch", queryModel = CustomerPricePlanSearchQuery.class)
	@OrderBy("name")
	public List<CustomerPricePlanSearchResult> findCustomerPricePlan(SearchQuery searchQuery);

	/**
	 * Find price plan lov.
	 *
	 * @return the list
	 */
	@RestrictBySpace
	@LovQuery(name = "pricePlanLov", valueField = "id", labelField = "name")
	public List<ValueLabel> findPricePlanLov();

	/**
	 * Delete all.
	 */
	public void deleteAll();
}