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
	@Label(" open") 
	OPEN,
	
	/** 
	 * The submit.
	 **/
	@Label("submit")
	SUBMIT,
	
	/** 
	 * The reported.
	 **/
	@Label("reported") 
	REPORTED,	
	
	/**
	 *  The closed.
	 **/
	@Label("closed") 
	CLOSED
}
