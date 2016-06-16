package com.yukthi.automation.ui.steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yukthi.automation.AutomationContext;
import com.yukthi.automation.Executable;
import com.yukthi.automation.IExecutionLogger;
import com.yukthi.automation.IStep;

/**
 * Simulates the click event on the specified button.
 * @author akiran
 */
@Executable("closeSession")
public class CloseSessionStep implements IStep
{
	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(CloseSessionStep.class);

	@Override
	public void execute(AutomationContext context, IExecutionLogger exeLogger)
	{
		logger.trace("Closing current session");
		
		context.getWebDriver().close();
		context.resetDriver();
	}
}
