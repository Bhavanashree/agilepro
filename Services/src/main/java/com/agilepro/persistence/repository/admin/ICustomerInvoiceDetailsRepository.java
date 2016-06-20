package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.CustomerInvoiceDetailsSearchQuery;
import com.agilepro.commons.models.customer.CustomerInvoiceDetailsSearchResult;
import com.agilepro.commons.models.customer.priceplan.InvoiceDetails;
import com.agilepro.persistence.entity.admin.CustomerInvoiceDetailsEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.SearchResult;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * Customer invoice repository.
 */
public interface ICustomerInvoiceDetailsRepository extends IWebutilsRepository<CustomerInvoiceDetailsEntity>
{
	/**
	 * Find customer invoice details.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "customerInvoiceDetailsSearch", queryModel = CustomerInvoiceDetailsSearchQuery.class)
	public List<CustomerInvoiceDetailsSearchResult> findInvoice(SearchQuery searchQuery);

	/**
	 * Fetches invoice details of specified invoice id.
	 * 
	 * @param id
	 *            Invoice id to be fetched
	 * @return Invoice details
	 */
	@SearchResult
	public InvoiceDetails fetchInvoiceDetails(@Condition("id") Long id);

	/**
	 * Deletes all invoices.
	 */
	public void deleteAll();
}
