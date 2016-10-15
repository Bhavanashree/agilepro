package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_NOTE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL_NOTE_BY_STORY_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE_OR_UPDATE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.project.StoryNoteModel;
import com.agilepro.controller.response.StoryNoteReadResponse;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.StoryNoteService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class StoryNoteController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_STORY_NOTE)
@RequestMapping("/storyNote")
public class StoryNoteController extends BaseController
{
	/**
	 * The story note service.
	 **/
	@Autowired
	private StoryNoteService storyNoteService;

	/**
	 * Save.
	 *
	 * @param storyNoteModel
	 *            the story note model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE_OR_UPDATE)
	@Authorization(roles = { UserRole.STORY_NOTE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid StoryNoteModel storyNoteModel)
	{
		return new BasicSaveResponse(storyNoteService.saveOrUpdate(storyNoteModel).getId());
	}
	
	@ActionName(ACTION_TYPE_READ_ALL_NOTE_BY_STORY_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_NOTE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAllNoteByStoryId", method = RequestMethod.GET)
	@ResponseBody
	public StoryNoteReadResponse fetchEmployees(@RequestParam(value = "storyId") Long storyId)
	{
		return storyNoteService.fetchAllNoteByStoryId(storyId); 
	}
}
