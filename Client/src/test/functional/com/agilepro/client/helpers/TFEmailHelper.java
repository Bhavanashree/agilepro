package com.agilepro.client.helpers;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.agilepro.commons.models.admin.EmailModel;

/**
 * The TFEmail Helper class.
 * 
 * @author Pritam
 */
public class TFEmailHelper extends TFBase
{
	/**
	 * New Email helper object.
	 */
	private EmailHelper emailHelper = new EmailHelper(); 
	
	@Test
	public void testSendMail()
	{
		
		//Sending a mail
		/*List<String> to = new ArrayList<String>();
		to.add("pritamjosephjojo@gmail.com");
		*/
		EmailModel emailModel = new EmailModel("to", "subject", "message");
		
		Boolean flag = emailHelper.send(clientContext, emailModel);

		Assert.assertEquals(flag.booleanValue(), true);
	}
}
