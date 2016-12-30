package com.yukthi.automation.ui.steps;

import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;
import com.yukthi.automation.AutomationContext;
import com.yukthi.automation.Executable;
import com.yukthi.automation.IExecutionLogger;
import com.yukthi.automation.IStep;
import com.yukthi.automation.ui.common.AutomationUtils;
import com.yukthi.ccg.xml.DynamicBean;
import com.yukthi.utils.exceptions.InvalidStateException;

/**
 * Fill form with action fills the form and then goes for the provided action.
 * 
 * @author Pritam.
 */
@Executable("fillFormWithAction")
public class FillFormWithActionStep implements IStep
{
	/**
	 * Html locator of the form or container (like DIV) enclosing the input
	 * elements.
	 */
	String locator;

	/**
	 * Data to be filled. All the fields matching with the property names of
	 * specified bean will be searched and populated with corresponding data.
	 */
	Object data;

	/**
	 * PressEnterAtEnd if true then for the provided action or else ignore.
	 */
	Boolean pressEnterAtEnd;

	/**
	 * Fills the form using dynamic bean.
	 * 
	 * @param context
	 *            Automation context
	 * @param exeLogger
	 *            logger
	 */
	private void fillWithDynamicBean(AutomationContext context, IExecutionLogger exeLogger)
	{
		DynamicBean dynamicBean = (DynamicBean) data;
		Map<String, Object> properties = dynamicBean.getProperties();
		Object value = null;

		for(String name : properties.keySet())
		{
			value = properties.get(name);

			// ignore java core properties like - getClass()
			if(properties.get(name) == null)
			{
				continue;
			}

			if(!AutomationUtils.populateField(context, null, name, "" + value))
			{
				throw new InvalidStateException("pritam error");
			}
		}
	}

	private void pressEnter(AutomationContext context, IExecutionLogger exeLogger)
	{
		WebElement webElement = AutomationUtils.findElement(context, null, locator);
		webElement.sendKeys(Keys.ENTER);
		
		Selenium s = (Selenium) webElement;
		s.keyPressNative("13");
	}
	
	@Override
	public void execute(AutomationContext context, IExecutionLogger logger)
	{
		if(data instanceof DynamicBean)
		{
			fillWithDynamicBean(context, logger);
		}
		
		if((pressEnterAtEnd != null) && (pressEnterAtEnd))
		{
			pressEnter(context, logger);
		}
	}

	/**
	 * Gets the html locator of the form or container (like DIV) enclosing the
	 * input elements.
	 *
	 * @return the html locator of the form or container (like DIV) enclosing
	 *         the input elements
	 */
	public String getLocator()
	{
		return locator;
	}

	/**
	 * Sets the html locator of the form or container (like DIV) enclosing the
	 * input elements.
	 *
	 * @param locator
	 *            the new html locator of the form or container (like DIV)
	 *            enclosing the input elements
	 */
	public void setLocator(String locator)
	{
		this.locator = locator;
	}

	/**
	 * Gets the data to be filled. All the fields matching with the property
	 * names of specified bean will be searched and populated with corresponding
	 * data.
	 *
	 * @return the data to be filled
	 */
	public Object getData()
	{
		return data;
	}

	/**
	 * Sets the data to be filled. All the fields matching with the property
	 * names of specified bean will be searched and populated with corresponding
	 * data.
	 *
	 * @param data
	 *            the new data to be filled
	 */
	public void setData(Object data)
	{
		this.data = data;
	}

	public Boolean getPressEnterAtEnd()
	{
		return pressEnterAtEnd;
	}

	public void setPressEnterAtEnd(Boolean pressEnterAtEnd)
	{
		this.pressEnterAtEnd = pressEnterAtEnd;
	}
}
