package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * The Story Status.
 */
public enum BacklogStatus
{
	/**
	 * Indicates story is assigned.
	 */
	@Label("Not started") 
	NOT_STARTED,	

	/**
	 * Indicates story is not assigned.
	 */
	@Label("In Progress") 
	IN_PROGRESS,

	/**
	 *  The completed. 
	 */
	@Label("Completed") 
	COMPLETED
}
