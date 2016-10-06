package com.agilepro.controller.response;

import java.util.List;

import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * The Class ProjectMemberReadResponse.
 * 
 * @author Pritam
 */
@SuppressWarnings("rawtypes")
public class ProjectMemberReadResponse extends BasicReadResponse
{
	/**
	 * The manager.
	 **/
	private ProjectMemberModel manager;

	/**
	 * The admins.
	 **/
	private List<ProjectMemberModel> admins;

	/**
	 * The members.
	 **/
	private List<ProjectMemberModel> members;

	
	public ProjectMemberReadResponse(ProjectMemberModel manager, List<ProjectMemberModel> admins)
	{
		super();
		this.manager = manager;
		this.admins = admins;
	}
	
	public ProjectMemberReadResponse(List<ProjectMemberModel> members)
	{
		super();
		this.members = members;
	}

	/**
	 * Gets the manager.
	 *
	 * @return the manager
	 */
	public ProjectMemberModel getManager()
	{
		return manager;
	}

	/**
	 * Sets the manager.
	 *
	 * @param manager
	 *            the new manager
	 */
	public void setManager(ProjectMemberModel manager)
	{
		this.manager = manager;
	}

	/**
	 * Gets the admins.
	 *
	 * @return the admins
	 */
	public List<ProjectMemberModel> getAdmins()
	{
		return admins;
	}

	/**
	 * Sets the admins.
	 *
	 * @param admins
	 *            the new admins
	 */
	public void setAdmins(List<ProjectMemberModel> admins)
	{
		this.admins = admins;
	}

	/**
	 * Gets the members.
	 *
	 * @return the members
	 */
	public List<ProjectMemberModel> getMembers()
	{
		return members;
	}

	/**
	 * Sets the members.
	 *
	 * @param members
	 *            the new members
	 */
	public void setMembers(List<ProjectMemberModel> members)
	{
		this.members = members;
	}
}
