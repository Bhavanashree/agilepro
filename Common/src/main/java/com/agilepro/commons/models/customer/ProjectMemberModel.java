package com.agilepro.commons.models.customer;

import com.agilepro.commons.UserRole;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class ProjectMemberModel.
 * 
 * @author Pritam
 */
@Model(name = "ProjectMember")
public class ProjectMemberModel
{
	private Long projectId;
	
	private Long employeeId;
	
	private UserRole userRole;

	public Long getProjectId()
	{
		return projectId;
	}

	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	public Long getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
	}

	public UserRole getUserRole()
	{
		return userRole;
	}

	public void setUserRole(UserRole userRole)
	{
		this.userRole = userRole;
	}
}
