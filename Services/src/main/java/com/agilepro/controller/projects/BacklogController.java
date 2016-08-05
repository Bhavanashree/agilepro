package com.agilepro.controller.projects;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BACKLOG;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_SPRINT;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.agilepro.commons.IBacklogController;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.projects.BacklogModel;
import com.agilepro.persistence.entity.projects.BacklogEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.projects.BacklogService;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class StoryController is responsible for receiving the requests from
 * Client. Once received , it directs the request to the service class
 * (StoryService). It also takes care for sending the response back to the
 * client received from service class.
 */
@RestController
@ActionName(ACTION_PREFIX_BACKLOG)
@RequestMapping("/backlog")
public class BacklogController extends BaseController implements IBacklogController
{

	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(BacklogController.class);

	/**
	 * The story service.
	 **/
	@Autowired
	private BacklogService storyService;

	/**
	 * Save the StoryModel.
	 *
	 * @param model
	 *            StoryModel
	 * @return the StoryModel save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid BacklogModel model)
	{

		BacklogEntity entity = storyService.saveStory(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read the Story.
	 *
	 * @param id
	 *            id of StoryModel
	 * 
	 * @return the StoryModel read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<BacklogModel> read(@PathVariable(PARAM_ID) Long id)
	{
		BacklogModel storyModel = storyService.fetchFullModel(id, BacklogModel.class);

		return new BasicReadResponse<BacklogModel>(storyModel);
	}

	/**
	 * Read the list of stories.
	 *
	 * @param title
	 *            title of StoryModel
	 * 
	 * @return the StoryModel read response
	 */
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<BacklogModel>> fetchAllStory(@RequestParam(value = "storyTitle", required = false) String storyTitle)
	{
		return new BasicReadResponse<List<BacklogModel>>(storyService.fetchAllStory(storyTitle));
	}

	@Override
	@ActionName(ACTION_TYPE_READ_SPRINT)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readSprints", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<BacklogModel>> fetchStoryBySprintId(@RequestParam(value = "sprintId", required = true) Long sprintId)
	{

		return new BasicReadResponse<List<BacklogModel>>(storyService.fetchStoryBySprintId(sprintId));
	}

	/**
	 * Update the stories.
	 *
	 * @param model
	 *            id of StoryModel
	 * 
	 * @return the StoryModel response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid BacklogModel model)
	{
		logger.trace("Recieved request to update ", model.getId());
		if(model.getId() == null || model.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + model.getId());
		}

		storyService.updateStories(model);

		return new BaseResponse();
	}

	/**
	 * Delete the story.
	 *
	 * @param id
	 *            the id of story
	 * 
	 * @return the story save response
	 */

	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_DELETE, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		storyService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all story.
	 *
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.STORY_DELETE_ALL, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		storyService.deleteAll();
		return new BaseResponse();
	}
}
