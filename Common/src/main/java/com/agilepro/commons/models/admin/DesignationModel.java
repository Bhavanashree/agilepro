package com.agilepro.commons.models.admin;

import java.util.List;

import com.agilepro.commons.UserRole;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class DesignationModel.
 * 
 * @author bhavana
 */
@ExtendableModel(name = "Designation")
@Model
public class DesignationModel extends AbstractExtendableModel
{

	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The Name of the Designation.
	 **/
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	@Required
	private String name;

	/**
	 * Description described depending the roles they are going to handle.
	 **/
	@MultilineText
	@MaxLen(1000)
	private String description;

	/**
	 * Roles that are assigned.
	 **/
	@LOV(name = "externalRoleList")
	private List<UserRole> roles;

	/**
	 * The project level.
	 **/
	private Boolean projectLevel;

	/**
	 * version.
	 */

	@NonDisplayable
	private Integer version;

	/**
	 * Instantiates a new designation model.
	 */
	public DesignationModel()
	{}

	/**
	 * Instantiates a new designation model.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param roles
	 *            the roles
	 * 
	 */
	public DesignationModel(long id, String name, String description, List<UserRole> roles)
	{
		this.name = name;
		this.description = description;
		this.roles = roles;
	}

	/**
	 * Gets the Name of the Designation.
	 *
	 * @return the Name of the Designation
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the Name of the Designation.
	 *
	 * @param name
	 *            the new Name of the Designation
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description described depending the roles they are going to
	 * handle.
	 *
	 * @return the description described depending the roles they are going to
	 *         handle
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description described depending the roles they are going to
	 * handle.
	 *
	 * @param description
	 *            the new description described depending the roles they are
	 *            going to handle
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<UserRole> getRoles()
	{
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<UserRole> roles)
	{
		this.roles = roles;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.IEntity#setVersion(java.lang.Integer)
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * Gets the project level.
	 *
	 * @return the project level
	 */
	public Boolean getProjectLevel()
	{
		return projectLevel;
	}

	/**
	 * Sets the project level.
	 *
	 * @param projectLevel
	 *            the new project level
	 */
	public void setProjectLevel(Boolean projectLevel)
	{
		this.projectLevel = projectLevel;
	}
}
