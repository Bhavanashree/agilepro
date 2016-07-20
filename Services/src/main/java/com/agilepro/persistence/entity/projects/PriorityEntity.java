package com.agilepro.persistence.entity.projects;

import javax.persistence.Column;
import javax.persistence.Table;

import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class PriorityEntity.
 */
@ExtendableEntity(name = "Priority")
@Table(name = "PRIORITY")
public class PriorityEntity extends WebutilsExtendableEntity
{
	/**
	 * The name.
	 */
	@Column(name = "NAME", length = 50)
	private String name;

	/**
	 * The value.
	 */
	@Column(name = "VALUE")
	private Integer value;

	/**
	 * The project id.
	 */
	@Column(name = "PROJECT_ID")
	private Long projectId;

	/**
	 * Instantiates a new priority entity.
	 */
	public PriorityEntity()
	{}

	/**
	 * Instantiates a new priority entity.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param projectId
	 *            the project id
	 */
	public PriorityEntity(String name, int value, Long projectId)
	{
		this.name = name;
		this.value = value;
		this.projectId = projectId;
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
	 * @param value the new value
	 */
	public void setValue(Integer value)
	{
		this.value = value;
	}

	/**
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
}
