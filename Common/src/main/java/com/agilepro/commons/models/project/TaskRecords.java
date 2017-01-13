package com.agilepro.commons.models.project;

import com.agilepro.commons.TaskStatus;

/**
 * Task records holds the changes done for a task from ui.
 * 
 * @author Pritam.
 */
public class TaskRecords
{
	/**
	 * Changed actual time.
	 */
	private Integer actualTime;
	
	/**
	 * Changed task status.
	 */
	private TaskStatus taskStatus;

	/**
	 * Get the actual time.
	 * 
	 * @return the actual time.
	 */
	public Integer getActualTime()
	{
		return actualTime;
	}

	/**
	 * Set the actual time.
	 * 
	 * @param actualTime the new actual time.
	 */
	public void setActualTime(Integer actualTime)
	{
		this.actualTime = actualTime;
	}

	/**
	 * Get the task status.
	 * 
	 * @return the task status.
	 */
	public TaskStatus getTaskStatus()
	{
		return taskStatus;
	}

	/**
	 * Set the task status.
	 * 
	 * @param taskStatus the new task status.
	 */
	public void setTaskStatus(TaskStatus taskStatus)
	{
		this.taskStatus = taskStatus;
	}
}
