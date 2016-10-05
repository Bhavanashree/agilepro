package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

public enum PriorityStatus
{

	@Label("blockers") 
	BLOCKERS,
	
	@Label("major")
	MAJOR,
	
	@Label(" minor") 
	MINOR,
	
	@Label("abnormal")
	ABNORMAL,
	
}
