package com.agilepro.commons.models.admin;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class TodoModel is responsible for organizing the tasks.
 */
@Model(name = "TodoModel")
public class TodoModel
{

	/**
	 * The id of todo.
	 */
	@NonDisplayable
	private long id;

	/**
	 * The version.
	 */
	@NonDisplayable
	private Integer version;

	/**
	 * Title of Todo.
	 */
	@NotEmpty
	@MaxLen(100)
	private String title;

	/**
	 * Description of Todo.
	 */
	@MaxLen(1000)
	private String description;

	/**
	 * Owner of Todo.
	 */
	private String owner;

	/**
	 * start time of Todo.
	 */
	private double startTime;

	/**
	 * end time of Todo.
	 */
	private double endTime;

	/**
	 * Instantiates a new todo model.
	 */
	public TodoModel()
	{}

	/**
	 * Instantiates a new todo model.
	 *
	 * @param title
	 *            the title
	 * @param description
	 *            the description
	 * @param owner
	 *            the owner
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 */
	public TodoModel(String title, String description, String owner, double startTime, double endTime)
	{
		super();
		this.title = title;
		this.description = description;
		this.owner = owner;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id)
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
	 * Gets the title of Todo.
	 *
	 * @return the title of Todo
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title of Todo.
	 *
	 * @param title
	 *            the new title of Todo
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the description of Todo.
	 *
	 * @return the description of Todo
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description of Todo.
	 *
	 * @param description
	 *            the new description of Todo
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the owner of Todo.
	 *
	 * @return the owner of Todo
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner of Todo.
	 *
	 * @param owner
	 *            the new owner of Todo
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * Gets the start time of Todo.
	 *
	 * @return the start time of Todo
	 */
	public double getStartTime()
	{
		return startTime;
	}

	/**
	 * Sets the start time of Todo.
	 *
	 * @param startTime
	 *            the new start time of Todo
	 */
	public void setStartTime(double startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * Gets the end time of Todo.
	 *
	 * @return the end time of Todo
	 */
	public double getEndTime()
	{
		return endTime;
	}

	/**
	 * Sets the end time of Todo.
	 *
	 * @param endTime
	 *            the new end time of Todo
	 */
	public void setEndTime(double endTime)
	{
		this.endTime = endTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof TodoModel))
		{
			return false;
		}

		TodoModel model = (TodoModel) obj;
		return this.id == model.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int) id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.getTitle().trim();
	}
}
