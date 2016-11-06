package com.agilepro.services.notification;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.common.models.mails.MailTemplateModel;

/**
 * Mail template model extension, which can load properties from property xml file.
 * @author akiran
 */
public class MailTemplateModelForLoad extends MailTemplateModel
{
	/**
	 * Owner entity type.
	 */
	private String ownerEntityType = IMailTemplates.DEFAULT_OWNER_TYPE;

	/**
	 * Loads the html content from specified template file.
	 * @param file File to load.
	 */
	public void setTemplateFile(String file)
	{
		try
		{
			InputStream is = MailTemplateModelForLoad.class.getResourceAsStream(file);
			String contentHtml = IOUtils.toString(is);
			
			super.setContentTemplate(contentHtml);
		}catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while loading file: {}", file);
		}
	}

	/**
	 * Gets the owner entity type.
	 *
	 * @return the owner entity type
	 */
	public String getOwnerEntityType()
	{
		return ownerEntityType;
	}

	/**
	 * Sets the owner entity type.
	 *
	 * @param ownerEntityType the new owner entity type
	 */
	public void setOwnerEntityType(String ownerEntityType)
	{
		this.ownerEntityType = ownerEntityType;
	}
}
