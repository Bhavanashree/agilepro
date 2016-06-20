package com.agilepro.client.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.agilepro.commons.models.admin.ConversationModel;
import com.agilepro.commons.models.admin.ConversationSearchQuery;

/**
 * The Class TFConversationSearchQuery.
 * 
 * @author akanksha
 */
public class TFConversationSearchQuery extends TFBase
{
	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(TFConversationHelper.class);
	/**
	 * The search helper.
	 */
	//private SearchHelper searchHelper = new SearchHelper();
	
	/**
	 * The conversation helper.
	 */
	private ConversationHelper conversationHelper = new ConversationHelper();
	
	/**
	 *  The message. 
	 *  */
	private String message = "akanksha";
	
	/**
	 *  The entitytype. 
	 *  */
	private String entitytype = "jain";
	
	/**
	 * Setup.
	 */
	@BeforeClass
	public void setup()
	{

		ConversationModel conversationModel = new ConversationModel(message, entitytype , 2L, null);

		long id = conversationHelper.save(clientContext, conversationModel);
		logger.debug("Saved conversation with id1 - {}", id);
		Assert.assertTrue(id > 0);
	}

	/**
	 * Test search results.
	 */
	@Test
	public void testSearchResults()
	{
		
		ConversationSearchQuery query = new ConversationSearchQuery(message, entitytype, 2L, null);

		//List<ConversationSearchResult> results = searchHelper.executeSearchQuery(clientContext, "conversationSearch", query, -1, ConversationSearchResult.class);
		// validate
		//Assert.assertEquals(results.size(), 1);
		// to ensure bean conversion is good, check first bean
		//Assert.assertEquals(results.get(0).getMessage(), message);
	}
	
	@AfterClass
	public void cleanUp()
	{
		conversationHelper.deleteAll(clientContext);
	}
}
