package com.agilepro.commons.models.project;

import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class PrioritySearchQuery.
 */
@Model
public class PrioritySearchQuery
{

	/**
	 * The name.
	 **/
	@Condition(value = "name", op = Operator.LIKE, ignoreCase = true)
	private String name;

	/**
	 * value.
	 */
	@Condition(value = "value")
	private Integer value;

	/**
	 * The projectId.
	 **/
	@Condition(value = "projectId")
	private Long projectId;

	/**
	 * Instantiates a new priority search query.
	 */
	public PrioritySearchQuery()
	{}

	/**
	 * Instantiates a new priority search query.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param projectId
	 *            the project id
	 */
	public PrioritySearchQuery(String name, int value, Long projectId)
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
	 * @param value
	 *            the new value
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
