package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * The Enum BugStatus.
 */
public enum BugStatus
{
	
	/**
	 *  The open.
	 */
	@Label("Open") 
	OPEN,
	
	/** 
	 * The submit.
	 **/
	@Label("Submit")
	SUBMIT,
	
	/** 
	 * The reported.
	 **/
	@Label("Reported") 
	REPORTED,	
	
	/**
	 *  The closed.
	 **/
	@Label("Closed") 
	CLOSED
}
