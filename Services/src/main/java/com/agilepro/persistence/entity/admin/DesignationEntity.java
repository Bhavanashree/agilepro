package com.agilepro.persistence.entity.admin;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.agilepro.commons.UserRole;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class Designation Entity.
 * 
 * @author bhavana
 */
@ExtendableEntity(name = "Designation")
@Table(name = "DESIGNATION")
public class DesignationEntity extends WebutilsExtendableEntity
{
	/**
	 * setting error message for duplicate designation.
	 */
	private static final String ERROR_MESSAGE_DUPLICATE_DESIGNATION = "Specified  designation already  exists:";

	/**
	 * The Name of the Designation.
	 **/
	@UniqueConstraint(name = "NAME", message = ERROR_MESSAGE_DUPLICATE_DESIGNATION)
	@Column(name = "NAME", length = 50)
	private String name;

	/**
	 * Description described depending the roles they are going to handle.
	 **/
	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	/**
	 * Roles that are assigned.
	 **/
	@Column(name = "ROLES", length = 1000)
	@DataTypeMapping(type = DataType.BLOB, converterType = JsonConverter.class)
	private List<UserRole> roles;

	/**
	 * The project level.
	 **/
	@Column(name = "PROJEECT_LEVEL")
	private Boolean projectLevel;

	/**
	 * Instantiates a new designation entity.
	 */
	public DesignationEntity()
	{}

	/**
	 * Instantiates a new designation entity.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param role
	 *            the role
	 */
	public DesignationEntity(String name, String description, List<UserRole> role)
	{
		this.name = name;
		this.description = description;
		this.roles = role;
	}

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
	 * @param name
	 *            the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
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
