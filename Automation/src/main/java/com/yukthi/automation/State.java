package com.yukthi.automation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents State of the current execution.
 * @author akiran
 */
public class State
{
	/**
	 * Pattern to find pattern parts in the url of the state.
	 */
	private static Pattern PATTERN_PART_PATTERN = Pattern.compile("\\{(.*)\\}");
	
	/**
	 * Name of the current state. Generally represents current page.
	 */
	private String name;
	
	/**
	 * Url pattern of the current state. Used to identify the current state based on this url
	 * pattern.
	 */
	private String urlPattern;
	
	/**
	 * Maps the steps from source state.
	 */
	private Map<String, Flow> stateToFlow = new HashMap<String, Flow>();
	
	/**
	 * Url pattern object representation of the url pattern.
	 */
	private Pattern urlPatternObject;

	/**
	 * Gets the name of the current state. Generally represents current page.
	 *
	 * @return the name of the current state
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the current state. Generally represents current page.
	 *
	 * @param name the new name of the current state
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the url pattern of the current state. Used to identify the current state based on this url pattern.
	 *
	 * @return the url pattern of the current state
	 */
	public String getUrlPattern()
	{
		return urlPattern;
	}

	/**
	 * Sets the url pattern of the current state. Used to identify the current state based on this url pattern.
	 *
	 * @param urlPattern the new url pattern of the current state
	 */
	public void setUrlPattern(String urlPattern)
	{
		this.urlPattern = urlPattern;
	}
	
	/**
	 * Adds steps to reach current state from the specified state.
	 * @param flow State from which current state can be reached with steps.
	 */
	public void addFlow(Flow flow)
	{
		String fromState = flow.getFromState();
		
		if(fromState == null || fromState.trim().length() == 0)
		{
			fromState = "";
		}
		
		this.stateToFlow.put(fromState, flow);
		flow.setParentState(this);
	}
	
	/**
	 * Gets steps to be followed from specified to current state.  
	 * @param state State from which current state to be reached
	 * @return Steps to be followed
	 */
	public Flow getFlow(String state)
	{
		return stateToFlow.get(state);
	}
	
	/**
	 * Converts underlying url pattern string into pattern object.
	 * @return Pattern version of the underlying url pattern
	 */
	public Pattern getUrlPatternObject()
	{
		if(urlPatternObject != null)
		{
			return urlPatternObject;
		}
		
		Matcher matcher = PATTERN_PART_PATTERN.matcher(urlPattern);
		StringBuilder builder = new StringBuilder();
		int lastEnd = 0;
		
		while(matcher.find())
		{
			builder.append( Pattern.quote( urlPattern.substring(lastEnd, matcher.start(1) - 1) ) );
			builder.append(matcher.group(1));
			
			lastEnd = matcher.end(1) + 1;
		}
		
		if(lastEnd < urlPattern.length())
		{
			builder.append(urlPattern.substring(lastEnd, urlPattern.length()));
		}
		
		urlPatternObject = Pattern.compile(builder.toString());
		return urlPatternObject;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("[");

		builder.append("Name: ").append(name);
		builder.append(",").append("Url Pattern: ").append(urlPattern);

		builder.append("]");
		return builder.toString();
	}
}
