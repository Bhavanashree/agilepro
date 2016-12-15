package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * Story dependency type signifies the type of dependency.
 * 
 * @author Pritam.
 */
public enum StoryDependencyType
{
	/**
	 * Starts with.
	 */
	@Label("Starts With") STARTS_WITH,

	/**
	 * Ends with.
	 */
	@Label("Ends With") ENDS_WITH
}
