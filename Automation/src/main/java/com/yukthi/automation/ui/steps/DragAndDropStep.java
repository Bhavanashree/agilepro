package com.yukthi.automation.ui.steps;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.yukthi.automation.AutomationContext;
import com.yukthi.automation.Executable;
import com.yukthi.automation.IExecutionLogger;
import com.yukthi.automation.IStep;
import com.yukthi.automation.ui.common.AutomationUtils;
import com.yukthi.utils.exceptions.InvalidStateException;

/**
 * Drag and drop the web elements.
 * 
 * @author Pritam.
 */
@Executable("dragAndDrop")
public class DragAndDropStep implements IStep
{
	/**
	 * Source html element to be dragged.
	 */
	private String source;

	/**
	 * Destination html element area to drop.
	 */
	private String destination;

	@Override
	public void execute(AutomationContext context, IExecutionLogger logger)
	{
		WebElement sourceElement = AutomationUtils.findElement(context, null, source);
		WebElement destinationElement = AutomationUtils.findElement(context, null, destination);

		dragAndDrop(sourceElement, destinationElement);
	}

	/**
	 * Drag and drop web element.
	 * 
	 * @param sourceElement
	 *            the element to be dragged.
	 * @param destinationElement
	 *            area to be dropped.
	 */
	private void dragAndDrop(WebElement sourceElement, WebElement destinationElement)
	{
		try
		{
			if(!sourceElement.isDisplayed())
			{
				throw new InvalidStateException("Failed to find drag element - '{}'", source);
			}

			if(!destinationElement.isDisplayed())
			{
				throw new InvalidStateException("Failed to find drop area element - '{}'", destination);
			}

			Actions actions = new Actions(new FirefoxDriver());

			actions.dragAndDrop(sourceElement, destinationElement).build().perform();
		} catch(StaleElementReferenceException ex)
		{
			throw new InvalidStateException("Element with {} or {} is not attached to the page document", sourceElement, destinationElement);
		} catch(NoSuchElementException e)
		{
			throw new InvalidStateException("Element with {} or {} was not found in DOM ", sourceElement, destinationElement);
		} catch(Exception e)
		{
			throw new InvalidStateException("Error occurred while performing drag and drop operation ", e);
		}
	}

	/**
	 * Gets source html element to be dragged.
	 * 
	 * @return source html element to be dragged.
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * Sets the source html element to be dragged.
	 * 
	 * @param source
	 *            the source html element.
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * Gets the destination drop area html element.
	 * 
	 * @return the destination drop area html element.
	 */
	public String getDestination()
	{
		return destination;
	}

	/**
	 * Sets the destination drop area html element.
	 * 
	 * @param destination
	 *            the new destination drop area html element.
	 */
	public void setDestination(String destination)
	{
		this.destination = destination;
	}
}
