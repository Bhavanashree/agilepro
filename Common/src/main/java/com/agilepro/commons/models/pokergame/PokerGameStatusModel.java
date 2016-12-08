package com.agilepro.commons.models.pokergame;

import com.agilepro.commons.PokerGameStoryStatus;
import com.yukthi.persistence.repository.annotations.Field;

/**
 * PokerGameStatusModel signifies the status of the poker game with poker user
 * details also.
 * 
 * @author Pritam.
 */
public class PokerGameStatusModel
{
	@Field(value = "pokerGame.pokerGameStoryStatus")
	private PokerGameStoryStatus pokerGameStoryStatus;
	
	@Field(value = "projectMember.employee.name")
	private String employeeName;

	public PokerGameStoryStatus getPokerGameStoryStatus()
	{
		return pokerGameStoryStatus;
	}

	public void setPokerGameStoryStatus(PokerGameStoryStatus pokerGameStoryStatus)
	{
		this.pokerGameStoryStatus = pokerGameStoryStatus;
	}

	public String getEmployeeName()
	{
		return employeeName;
	}

	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	
}
