package com.yukthi.automation;

import java.util.ArrayList;
import java.util.List;

import com.yukthi.automation.ui.steps.ExecuteFlowStep;

/**
 * Represents the steps to be taken to reach target state (enclosing state) from
 * "fromState".
 * 
 * @author akiran
 */
public class Flow implements IStepContainer
{
	/**
	 * Steps to be execute to reach target state from {@link #fromState}.
	 */
	private List<IStep> steps = new ArrayList<>();

	/**
	 * State from which enclosed steps needs to be executed.
	 */
	private String fromState;

	/**
	 * State which contains this flow.
	 */
	private State parentState;

	/**
	 * Sets parent state of the flow.
	 * 
	 * @param parentState
	 *            Parent state
	 */
	void setParentState(State parentState)
	{
		this.parentState = parentState;
	}

	/**
	 * Gets the state which contains this flow.
	 *
	 * @return the state which contains this flow
	 */
	public State getParentState()
	{
		return parentState;
	}

	/**
	 * Gets the state from which enclosed steps needs to be executed.
	 *
	 * @return the state from which enclosed steps needs to be executed
	 */
	public String getFromState()
	{
		return fromState;
	}

	/**
	 * Sets the state from which enclosed steps needs to be executed.
	 *
	 * @param fromState
	 *            the new state from which enclosed steps needs to be executed
	 */
	public void setFromState(String fromState)
	{
		this.fromState = fromState;
	}

	/* (non-Javadoc)
	 * @see com.yukthi.ui.automation.IStepContainer#addStep(com.yukthi.ui.automation.IStep)
	 */
	@Override
	public void addStep(IStep step)
	{
		this.steps.add(step);
		
		if(step instanceof ExecuteFlowStep)
		{
			((ExecuteFlowStep) step).setParentFlow(this);
		}
	}

	/**
	 * Fetches steps of this flow.
	 * @return Steps of this flow.
	 */
	public List<IStep> getSteps()
	{
		return steps;
	}
}
