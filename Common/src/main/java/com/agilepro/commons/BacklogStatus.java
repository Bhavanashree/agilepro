package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * The Story Status.
 */
public enum BacklogStatus
{
	/**
	 * Indicates story is not started.
	 */
	@Label("Not started") 
	NOT_STARTED,	

	/**
	 * Indicates story is in progress.
	 */
	@Label("In Progress") 
	IN_PROGRESS,

	/**
	 *  The completed. 
	 */
	@Label("Completed") 
	COMPLETED
}
