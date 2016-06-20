package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.PaymentSearchQuery;
import com.agilepro.commons.models.customer.PaymentSearchResult;
import com.agilepro.persistence.entity.admin.CustomerPaymentEntity;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IPaymentRepository.
 */
public interface IPaymentRepository extends IWebutilsRepository<CustomerPaymentEntity>
{
	/**
	 * payment Search .
	 * 
	 * @param searchQuery
	 *            payment searchQuery
	 * @return searcgResults
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "paymentSearch", queryModel = PaymentSearchQuery.class)
	public List<PaymentSearchResult> findPayment(SearchQuery searchQuery);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}