package com.agilepro.controller.response;

import java.util.List;
import com.agilepro.commons.models.sprint.SprintDropDown;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.ValueLabel;

/**
 * KanbanBoardReadResponse for wrapping sprints, teams, members and send it to
 * ui.
 * 
 * @author Pritam
 */
@SuppressWarnings("rawtypes")
public class KanbanBoardReadResponse extends BasicReadResponse
{
	/**
	 * Sprint for the drop down.
	 **/
	private List<SprintDropDown> sprints;

	/**
	 * Team  for the drop down.
	 */
	private List<ValueLabel> teams;

	/**
	 * Employee for drop down.
	 */
	private  List<ValueLabel> employees;
	
	public KanbanBoardReadResponse(List<SprintDropDown> sprints, List<ValueLabel> teams, List<ValueLabel> employees)
	{
		super();
		this.sprints = sprints;
		this.teams = teams;
		this.employees = employees;
	}

	public List<SprintDropDown> getSprints()
	{
		return sprints;
	}

	public void setSprints(List<SprintDropDown> sprints)
	{
		this.sprints = sprints;
	}

	public List<ValueLabel> getTeams()
	{
		return teams;
	}

	public void setTeams(List<ValueLabel> teams)
	{
		this.teams = teams;
	}

	public List<ValueLabel> getEmployees()
	{
		return employees;
	}

	public void setEmployees(List<ValueLabel> employees)
	{
		this.employees = employees;
	}
}
