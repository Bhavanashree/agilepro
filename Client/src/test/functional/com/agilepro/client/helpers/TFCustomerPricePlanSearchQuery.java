package com.agilepro.client.helpers;

import org.testng.annotations.Test;

import com.agilepro.commons.models.customer.priceplan.CustomerPricePlanSearchQuery;

/**
 * The Class TFCustomerPricePlanSearchQuery is responsible for testing the
 * search cases..
 */
public class TFCustomerPricePlanSearchQuery extends TFBase
{

	/**
	 * The search helper.
	 */
	// private SearchHelper searchHelper = new SearchHelper();

	/**
	 * Test customer price plan search results.
	 */
	@Test
	public void testSearchResults()
	{
		CustomerPricePlanSearchQuery query = new CustomerPricePlanSearchQuery("%a%");
		// List<CustomerPricePlanSearchResult> results =
		// searchHelper.executeSearchQuery(clientContext, "priceplanSearch",
		// query, 1, CustomerPricePlanSearchResult.class);
		// Assert.assertEquals(results.size(), 1);
		// to ensure bean conversion is good, check first bean
		// Assert.assertEquals(results.get(0).getName(), "abc");
	}
}
