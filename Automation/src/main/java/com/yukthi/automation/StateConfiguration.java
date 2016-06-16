package com.yukthi.automation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Maintains state configuration of the application.
 * @author akiran
 */
public class StateConfiguration
{
	/**
	 * Maintains name to state mapping.
	 */
	private Map<String, State> nameToState = new HashMap<String, State>();
	
	/**
	 * Application specific configuration.
	 */
	private Object app;
	
	/**
	 * Instantiates a new state configuration.
	 *
	 * @param appConfiguration the app configuration
	 */
	public StateConfiguration(Object appConfiguration)
	{
		this.app = appConfiguration;
	}
	
	/**
	 * Gets the application specific configuration.
	 *
	 * @return the application specific configuration
	 */
	public Object getApp()
	{
		return app;
	}
	
	/**
	 * Adds specified state to the current configuration. 
	 * @param state State to be added
	 */
	public void addState(State state)
	{
		nameToState.put(state.getName(), state);
	}
	
	/**
	 * Fetches the sate based on the specified name.
	 * @param name Name of the state to fetch
	 * @return Matching state
	 */
	public State getState(String name)
	{
		return nameToState.get(name);
	}
	
	/**
	 * Fetches all available states.
	 * @return All available states
	 */
	public Collection<State> getAllStates()
	{
		return nameToState.values();
	}
}
