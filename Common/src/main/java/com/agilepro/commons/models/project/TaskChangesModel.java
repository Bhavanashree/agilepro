package com.agilepro.commons.models.project;

import java.util.Map;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;

/**
 * Task changes, holds the changes from ui.
 * 
 * @author Pritam.
 */
@Model
public class TaskChangesModel
{
	/**
	 * Task changes map where key is the id and value is changes made for tasks.
	 */
	@IgnoreField
	private Map<Long, TaskRecords> taskChanges;

	/**
	 * Get task changes.
	 * 
	 * @return the task changes.
	 */
	public Map<Long, TaskRecords> getTaskChanges()
	{
		return taskChanges;
	}

	/**
	 * Set the task changes.
	 * 
	 * @param taskChanges the new task changes.
	 */
	public void setTaskChanges(Map<Long, TaskRecords> taskChanges)
	{
		this.taskChanges = taskChanges;
	}
}
