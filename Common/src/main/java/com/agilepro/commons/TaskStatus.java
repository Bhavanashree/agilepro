package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

public enum TaskStatus {

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
