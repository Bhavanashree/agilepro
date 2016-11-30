package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * ActionConversationStatus for showing which conversation is closed and re opened.
 * 
 *@author Pritam
 */
public enum ActionConversationStatus 
{
	/**
	 * Closed status to show in which message the action is closed. 
	 */
	@Label(value = "Closed") CLOSED,
	
	/**
	 * Re open to show in which message the action is re opened.
	 */
	@Label(value = "ReOpen") RE_OPEN
}
