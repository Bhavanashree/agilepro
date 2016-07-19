package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * The Story Status.
 */
public enum StoryStatus
{

	/**
	 * Indicates story is not assigned.
	 */
	@Label("Not assigned") 
	NOT_ASSIGNED,

	/**
	 * Indicates story is assigned.
	 */
	@Label("Assigned") 
	ASSIGNED
}
