package com.agilepro.controller.projects;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.agilepro.commons.IStoryController;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.projects.StoryModel;
import com.agilepro.persistence.entity.projects.StoryEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.projects.StoryService;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class StoryController.
 */
@RestController
@ActionName(ACTION_PREFIX_STORY)
@RequestMapping("/story")
public class StoryController extends BaseController implements IStoryController
{

	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(StoryController.class);

	/**
	 * The story service.
	 **/
	@Autowired
	private StoryService storyService;

	/**
	 * Save Backlog.
	 *
	 * @param model
	 *            the model
	 * @return the Backlog save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid StoryModel model)
	{

		StoryEntity entity = storyService.saveStory(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read Backlog.
	 *
	 * @param id
	 *            the id
	 * @return the Backlog read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<StoryModel> read(@PathVariable(PARAM_ID) Long id)
	{
		StoryModel backLogModel = storyService.fetchFullModel(id, StoryModel.class);

		return new BasicReadResponse<StoryModel>(backLogModel);
	}

	/**
	 * Update Backlog.
	 *
	 * @param model
	 *            the model
	 * @return the Backlog update response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.STORY_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid StoryModel model)
	{
		logger.trace("Recieved request to update ", model.getId());
		if(model.getId() == null || model.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + model.getId());
		}

		storyService.updateStory(model);

		return new BaseResponse();
	}

	/**
	 * Delete Backlog.
	 *
	 * @param id
	 *            the id
	 * @return the Backlog delete response
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
	 * Delete all.
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
