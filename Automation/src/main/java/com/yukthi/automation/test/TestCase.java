package com.yukthi.automation.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yukthi.automation.AutomationContext;
import com.yukthi.automation.IStep;
import com.yukthi.automation.IStepContainer;
import com.yukthi.automation.IValidation;
import com.yukthi.automation.IValidationContainer;
import com.yukthi.ccg.core.ValidateException;
import com.yukthi.ccg.core.Validateable;

/**
 * Test case with validations to be executed.
 */
public class TestCase implements IStepContainer, IValidationContainer, Validateable
{
	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(TestCase.class);

	/**
	 * Name of the test case.
	 */
	private String name;

	/**
	 * Description about test case.
	 */
	private String description;

	/**
	 * Steps for the test case.
	 */
	private List<IStep> steps = new ArrayList<>();

	/**
	 * Validations of test case.
	 */
	private List<IValidation> validations = new ArrayList<>();

	/**
	 * Gets the name of the test case.
	 *
	 * @return the name of the test case
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the test case.
	 *
	 * @param name
	 *            the new name of the test case
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description about test case.
	 *
	 * @return the description about test case
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description about test case.
	 *
	 * @param description
	 *            the new description about test case
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.ui.automation.IStepContainer#addStep(com.yukthi.ui.automation.
	 * IStep)
	 */
	@Override
	public void addStep(IStep step)
	{
		if(steps == null)
		{
			steps = new ArrayList<IStep>();
		}

		steps.add(step);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.ui.automation.IValidationContainer#addValidation(com.yukthi.ui
	 * .automation.IValidation)
	 */
	@Override
	public void addValidation(IValidation validation)
	{
		if(validations == null)
		{
			validations = new ArrayList<IValidation>();
		}

		validations.add(validation);
	}

	/**
	 * Execute.
	 *
	 * @param context
	 *            the context
	 * @return the test case result
	 */
	public TestCaseResult execute(AutomationContext context)
	{
		TestExecutionLogger exeLogger = new TestExecutionLogger();

		// execute the steps involved
		for(IStep step : steps)
		{
			exeLogger.debug("Executing step: {}", step);

			try
			{
				step.execute(context, exeLogger.getSubLogger());
			} catch(Exception ex)
			{
				exeLogger.error(ex, "An error occurred while executing step - " + step);

				return new TestCaseResult(this.name, TestStatus.ERRORED, exeLogger.toString(), "Step errored - " + step);
			}

			exeLogger.debug("Completed step: " + step);
		}

		// execute the validations
		for(IValidation validation : validations)
		{
			exeLogger.debug("Executing validation: {}", validation);

			try
			{
				if(!validation.validate(context, exeLogger.getSubLogger()))
				{
					exeLogger.error("Validation failed - " + validation);

					return new TestCaseResult(this.name, TestStatus.FAILED, exeLogger.toString(), validation.getFailureMessage());
				}
			} catch(Exception ex)
			{
				exeLogger.error(ex, "An error occurred while executing validation - " + validation);

				return new TestCaseResult(this.name, TestStatus.ERRORED, exeLogger.toString(), "Validation errored - " + validation);
			}

			exeLogger.debug("Completed validation: " + validation);
		}

		return new TestCaseResult(this.name, TestStatus.SUCCESSUFUL, exeLogger.toString(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.ccg.core.Validateable#validate()
	 */
	@Override
	public void validate() throws ValidateException
	{
		if(StringUtils.isEmpty(name))
		{
			throw new ValidateException("No name is provided for test case.");
		}

		if(StringUtils.isEmpty(description))
		{
			throw new ValidateException("No description is provided for test case - " + name);
		}

		if(CollectionUtils.isEmpty(validations))
		{
			throw new ValidateException("No validations provided for test case - " + name);
		}
	}
}
