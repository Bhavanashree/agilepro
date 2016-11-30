package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * ScrumActionStatus signifies the status of the scrum action.
 * 
 * @author Pritam
 */
public enum ScrumActionStatus
{
	/**
	 * Open status for sending the scrum action conversation.
	 **/
	@Label(value = "Open") OPEN,

	/**
	 * Close status closes the scrum action.
	 **/
	@Label(value = "Close") CLOSE,

	/**
	 * Re open status re opens the closed scrum action.
	 **/
	@Label(value = "ReOpen") RE_OPEN
}
