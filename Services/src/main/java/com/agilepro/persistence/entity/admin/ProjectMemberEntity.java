package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.ProjectMemberRole;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ProjectMember.
 * 
 * @author Pritam
 */
@Table(name = "PROJECT_MEMBER")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "project", "employee" }) })
public class ProjectMemberEntity extends WebutilsEntity
{
	/**
	 * The projects entity.
	 **/
	@ManyToOne
	@Column(name = "PROJECT_ID", nullable = false)
	@PropertyMapping(type = ProjectMemberModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * The employee entity.
	 **/
	@ManyToOne
	@Column(name = "EMPLOYEE_ID", nullable = false)
	@PropertyMapping(type = ProjectMemberModel.class, from = "employeeId", subproperty = "id")
	private EmployeeEntity employee;
	
	/** 
	 * The project team. 
	 **/
	@ManyToOne
	@Column(name = "TEAM_ID")
	@PropertyMapping(type = ProjectMemberModel.class, from = "projectTeamId", subproperty = "id")
	private ProjectTeamEntity projectTeam;

	/**
	 * The user role.
	 **/
	@Column(name = "ROLE", length = 200)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private ProjectMemberRole projectMemberRole;

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public ProjectEntity getProject()
	{
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @param project
	 *            the new project
	 */
	public void setProject(ProjectEntity project)
	{
		this.project = project;
	}

	/**
	 * Gets the employee.
	 *
	 * @return the employee
	 */
	public EmployeeEntity getEmployee()
	{
		return employee;
	}

	/**
	 * Sets the employee.
	 *
	 * @param employee
	 *            the new employee
	 */
	public void setEmployee(EmployeeEntity employee)
	{
		this.employee = employee;
	}

	/**
	 * Gets the project member role.
	 *
	 * @return the project member role
	 */
	public ProjectMemberRole getProjectMemberRole()
	{
		return projectMemberRole;
	}

	/**
	 * Sets the project member role.
	 *
	 * @param projectMemberRole
	 *            the new project member role
	 */
	public void setProjectMemberRole(ProjectMemberRole projectMemberRole)
	{
		this.projectMemberRole = projectMemberRole;
	}
}
