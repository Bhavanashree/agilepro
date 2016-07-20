package com.agilepro.commons.models.projects;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class PriorityModel.
 */
@ExtendableModel(name = "Priority")
@Model
public class PriorityModel extends AbstractExtendableModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The version.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The name.
	 **/
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	private String name;

	/**
	 * The value.
	 **/
	private Integer value;

	/**
	 * The parent story id.
	 **/
	private Long project;

	/**
	 * Instantiates a new priority model.
	 */
	public PriorityModel()
	{}

	/**
	 * Instantiates a new priority model.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param project
	 *            the project
	 */
	public PriorityModel(String name, int value, Long project)
	{
		this.name = name;
		this.value = value;
		this.project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
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

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Integer getValue()
	{
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Integer value)
	{
		this.value = value;
	}

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public Long getProject()
	{
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @param project
	 *            the new project
	 */
	public void setProject(Long project)
	{
		this.project = project;
	}
}
