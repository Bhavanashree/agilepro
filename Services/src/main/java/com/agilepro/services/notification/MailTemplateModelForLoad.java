package com.agilepro.services.notification;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;

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
	 * Loads the properties from specified property file template.
	 * @param file File to load.
	 */
	public void setTemplateFile(String file)
	{
		Properties properties = new Properties();
		
		try
		{
			InputStream is = MailTemplateModelForLoad.class.getResourceAsStream(file);
			properties.loadFromXML(is);
			
			is.close();
		}catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while loading file: {}", file);
		}
		
		for(Object key : properties.keySet())
		{
			try
			{
				BeanUtils.setProperty(this, (String) key, properties.getProperty( (String) key));
			}catch(Exception ex)
			{
				throw new InvalidStateException(ex, "An error occurred while setting property '{}' from file - {}", key, file);
			}
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
