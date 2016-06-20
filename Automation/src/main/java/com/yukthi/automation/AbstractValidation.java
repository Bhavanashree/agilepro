package com.yukthi.automation;

/**
 * Base class for validations.
 */
public abstract class AbstractValidation implements IValidation
{
	/**
	 * Failure message for the validation.
	 */
	private String failureMessage;

	/**
	 * Sets the failure message for the validation.
	 *
	 * @param failureMessage the new failure message for the validation
	 */
	public void setFailureMessage(String failureMessage)
	{
		this.failureMessage = failureMessage;
	}
	
	/* (non-Javadoc)
	 * @see com.yukthi.ui.automation.IValidation#getFailureMessage()
	 */
	@Override
	public String getFailureMessage()
	{
		return failureMessage;
	}
}
