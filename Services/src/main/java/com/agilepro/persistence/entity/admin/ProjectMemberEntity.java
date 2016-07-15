package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.UserRole;
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
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "projectsEntity", "employeeEntity" }) })
public class ProjectMemberEntity extends WebutilsEntity
{
	/** 
	 * The projects entity. 
	 **/
	@ManyToOne
	@Column(name = "PROJECT_ID")
	@PropertyMapping(type = ProjectMemberModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity projectsEntity;
	
	/** 
	 * The employee entity. 
	 **/
	@ManyToOne
	@Column(name = "EMPLOYEE_ID")
	@PropertyMapping(type = ProjectMemberModel.class, from = "employeeId", subproperty = "id")
	private EmployeeEntity employeeEntity;
	
	/** 
	 * The user role. 
	 **/
	@Column(name = "ROLE", length = 200)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private UserRole userRole;

	public ProjectEntity getProjectsEntity()
	{
		return projectsEntity;
	}

	public void setProjectsEntity(ProjectEntity projectsEntity)
	{
		this.projectsEntity = projectsEntity;
	}

	public EmployeeEntity getEmployeeEntity()
	{
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity)
	{
		this.employeeEntity = employeeEntity;
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
