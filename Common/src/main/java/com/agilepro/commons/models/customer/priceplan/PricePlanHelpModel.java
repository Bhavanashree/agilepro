package com.agilepro.commons.models.customer.priceplan;

import java.util.List;

/**
 * The Class PricePlanHelpModel.
 */
public class PricePlanHelpModel
{

	/**
	 * The runtime variables of price plan.
	 */
	private List<RuntimeVariables> runtimeVariables;

	/**
	 * The functions.
	 */
	private List<Functions> functions;

	/**
	 * Instantiates a new price plan help model.
	 */
	public PricePlanHelpModel()
	{
	}

	/**
	 * Instantiates a new price plan help model.
	 *
	 * @param runtimeVariables
	 *            the runtime variables
	 * @param functions
	 *            the functions
	 */
	public PricePlanHelpModel(List<RuntimeVariables> runtimeVariables, List<Functions> functions)
	{
		super();
		this.runtimeVariables = runtimeVariables;
		this.functions = functions;
	}

	/**
	 * Gets the runtime variables of price plan.
	 *
	 * @return the runtime variables of price plan
	 */
	public List<RuntimeVariables> getRuntimeVariables()
	{
		return runtimeVariables;
	}

	/**
	 * Sets the runtime variables of price plan.
	 *
	 * @param runtimeVariables
	 *            the new runtime variables of price plan
	 */
	public void setRuntimeVariables(List<RuntimeVariables> runtimeVariables)
	{
		this.runtimeVariables = runtimeVariables;
	}

	/**
	 * Gets the The functions.
	 *
	 * @return the The functions
	 */
	public List<Functions> getFunctions()
	{
		return functions;
	}

	/**
	 * Sets the The functions.
	 *
	 * @param functions
	 *            the new The functions
	 */
	public void setFunctions(List<Functions> functions)
	{
		this.functions = functions;
	}
}
