package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_TASK;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_STORY_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE_TASK_CHANGES;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.project.ITaskController;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.commons.models.project.StoryTaskModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.StoryTaskService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class TaskController.
 */
@RestController
@ActionName(ACTION_PREFIX_STORY_TASK)
@RequestMapping("/storyTask")
public class StoryTaskController extends BaseController implements ITaskController
{
	/**
	 * The task service.
	 **/
	@Autowired
	private StoryTaskService taskService;

	/**
	 * Receive request for saving a new task.
	 * 
	 * @param model
	 *            new task model for save.
	 * @return basic save response.
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid StoryTaskModel model)
	{
		return new BasicSaveResponse(taskService.save(model).getId());
	}

	/**
	 * Receive request to read the task for the given id.
	 * 
	 * @param id
	 *            for which task record is to be fetched.
	 * @return matching record.
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<StoryTaskModel> read(@PathVariable(PARAM_ID) Long id)
	{
		return new BasicReadResponse<StoryTaskModel>(taskService.fetchFullModel(id, StoryTaskModel.class));
	}

	/**
	 * Receive request for reading tasks for the given story id.
	 * 
	 * @param storyId
	 *            provided story id for which tasks are to be fetched.
	 * @return matching records.
	 */
	@Override
	@ActionName(ACTION_TYPE_READ_BY_STORY_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readByStoryId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<StoryTaskModel>> fetchTask(@RequestParam(value = "storyId", required = true) Long storyId)
	{
		return new BasicReadResponse<List<StoryTaskModel>>(taskService.fetchTaskByStory(storyId));
	}

	/**
	 * Receive request for updating the task.
	 * 
	 * @param model
	 *            updated task model.
	 * @return base response.
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid StoryTaskModel model)
	{
		taskService.update(model);
		return new BaseResponse();
	}

	/**
	 * Receive request for updating group of task changes.
	 * 
	 * @param taskChanges.
	 * @return base response.
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE_TASK_CHANGES)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.TASK_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/updateTaskChanges", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateTaskChanges(@RequestBody @Valid TaskChangesModel taskChanges)
	{
		taskService.updateTaskChanges(taskChanges);
		return new BaseResponse();
	}

	/**
	 * Receive request for deleting the task.
	 * 
	 * @param id
	 *            for which matching record will be deleted from task.
	 * @return base response.
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TASK_DELETE, UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		taskService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Receive request for deleting all the records from task.
	 * 
	 * @return base response.
	 */
	@Override
	@Authorization(roles = { UserRole.TEST_DELETE_ALL, UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		taskService.deleteAll();
		return new BaseResponse();
	}
}