package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.ProjectTeamModel;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * ProjectTeam where a project will have teams with different name and members.
 * Create Read Update Delete.
 * 
 * @author Pritam
 */
@Table(name = "PROJECT_TEAM")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "project", "name"}) })
public class ProjectTeamEntity extends WebutilsEntity
{
	/** 
	 * The name. 
	 **/
	@Column(name = "NAME", length = 30)
	private String name;
	
	/** 
	 * The project. 
	 **/
	@ManyToOne
	@Column(name = "PROJECT_ID", nullable = false)
	@PropertyMapping(type = ProjectTeamModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

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
	 * @param project the new project
	 */
	public void setProject(ProjectEntity project)
	{
		this.project = project;
	}
}
