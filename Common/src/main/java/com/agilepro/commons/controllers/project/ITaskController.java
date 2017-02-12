package com.agilepro.commons.controllers.project;

import java.util.List;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.commons.models.project.StoryTaskModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface ITaskController.
 * 
 * @author Pritam.
 */
@RemoteService
public interface ITaskController
{
	/**
	 * Save new task model.
	 * 
	 * @param model for save.
	 * @return basic save response on success save.
	 */
	public BasicSaveResponse save(StoryTaskModel model);

	/**
	 * Read task record for the given id.
	 * 
	 * @param id for which task is to be fetched.
	 * @return basic read response wrapped with matching record.
	 */
	public BasicReadResponse<StoryTaskModel> read(Long id);

	/**
	 * Fetch tasks for a given story id.
	 * 
	 * @param storyId provided story id for which tasks are to be fetched.
	 * @return matching records.
	 */
	public BasicReadResponse<List<StoryTaskModel>> fetchTask(Long storyId);
	
	/**
	 * Update task.
	 * 
	 * @param model new model for update.
	 * @return base response on success update.
	 */
	public BaseResponse update(StoryTaskModel model);
	
	/**
	 * Update task changes.
	 * 
	 * @param taskChanges group of changes to be updated. 
	 * @return base response on success update.
	 */
	public BaseResponse updateTaskChanges(TaskChangesModel taskChanges);
	
	/**
	 * Delete the task.
	 * 
	 * @param id provided id for delete. 
	 * @return base response for delete.
	 */
	public BaseResponse delete(Long id);

	/**
	 * Delete all the records of task table.
	 * 
	 * @return base response.
	 */
	public BaseResponse deleteAll();
}
