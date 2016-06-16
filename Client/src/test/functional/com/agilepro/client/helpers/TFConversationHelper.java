package com.agilepro.client.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.agilepro.commons.models.admin.ConversationModel;

/**
 * The Class TFConversationHelper.
 * 
 * @author akanksha
 */
public class TFConversationHelper extends TFBase
{

	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(TFConversationHelper.class);

	/**
	 * The conversation helper.
	 */
	private ConversationHelper conversationHelper = new ConversationHelper();

	/**
	 * Test save.
	 */
	@Test
	public void testSave()
	{

		ConversationModel conversationModel = new ConversationModel("akanksha", "jain", 2L, null);

		long id = conversationHelper.save(clientContext, conversationModel);
		logger.debug("Saved conversation with id1 - {}", id);
		Assert.assertTrue(id > 0);
	}

	/**
	 * Test read.
	 */
	@Test
	public void testRead()
	{
		String st = "akanksha1";
		// save entity
		ConversationModel model = new ConversationModel(st, "jain1", 2L, null);

		long id = conversationHelper.save(clientContext, model);
		logger.debug("Saved conversation with id2 - {}", id);
		Assert.assertTrue(id > 0);

		// read entity
		ConversationModel sn = conversationHelper.read(clientContext, id);

		// validate
		Assert.assertEquals(st, sn.getMessage());
	}

	/**
	 * Test update.
	 */
	@Test
	public void testUpdate()
	{

		String message = "something";
		// update entity
		ConversationModel model = new ConversationModel("akanksha2", "jain2", 2L, null);
		long id = conversationHelper.save(clientContext, model);
		logger.debug("Saved conversation with id - {}", id);

		// validate
		Assert.assertTrue(id > 0);

		model = new ConversationModel("akanksha3", "jain3", 2L, null);
		model.setMessage(message);
		model.setId(id);
		model.setVersion(1);

		conversationHelper.update(clientContext, model);

		ConversationModel userAdminModel = conversationHelper.read(clientContext, id);

		// validate
		Assert.assertEquals(message, userAdminModel.getMessage());
	}

	/**
	 * Test delete.
	 */
	@Test
	public void testDelete()
	{
		// delete entity
		ConversationModel model = new ConversationModel("akanksha4", "jain4", 2L, null);

		long id = conversationHelper.save(clientContext, model);
		logger.debug("Saved  conversation with id - {}", id);

		// validate
		Assert.assertTrue(id > 0);

		conversationHelper.delete(clientContext, id);
	}

	/**
	 * Cleanup.
	 */
	@AfterClass
	public void cleanup()
	{
		conversationHelper.deleteAll(clientContext);
	}
}
