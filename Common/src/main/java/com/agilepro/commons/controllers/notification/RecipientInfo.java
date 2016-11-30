package com.agilepro.commons.controllers.notification;

import java.util.List;

import com.agilepro.commons.ListOfTeams;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class Receipt.
 */

public class RecipientInfo
{

	/**
	 * The admin.
	 **/
	private Boolean admin;

	/**
	 * The project manager.
	 **/
	private Boolean projectManager;

	/**
	 * The teams.
	 **/
	@NonDisplayable
	private List<Long> teamIds;
	

	public Boolean getAdmin()
	{
		return admin;
	}

	public void setAdmin(Boolean admin)
	{
		this.admin = admin;
	}

	public Boolean getProjectManager()
	{
		return projectManager;
	}

	public void setProjectManager(Boolean projectManager)
	{
		this.projectManager = projectManager;
	}

	public List<Long> getTeamIds()
	{
		return teamIds;
	}

	public void setTeamIds(List<Long> teamIds)
	{
		this.teamIds = teamIds;
	}
}
