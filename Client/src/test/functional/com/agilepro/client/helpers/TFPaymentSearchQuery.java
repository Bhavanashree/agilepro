package com.agilepro.client.helpers;

import org.testng.annotations.Test;

import com.agilepro.commons.models.customer.PaymentSearchQuery;

/**
 * paymentSearchQuery.
 * 
 * @author bhavana
 *
 */

public class TFPaymentSearchQuery extends TFBase
{
	/**
	 * The search helper.
	 */
	// private SearchHelper searchHelper = new SearchHelper();

	@Test
	public void testPaymentSearchQuery()
	{

		PaymentSearchQuery query = new PaymentSearchQuery();
		// List<PaymentSearchResult> results =
		// searchHelper.executeSearchQuery(clientContext, "paymentSearch",
		// query, -1, PaymentSearchResult.class);

		// Assert.assertEquals(results.size(), 1);
	}
}
