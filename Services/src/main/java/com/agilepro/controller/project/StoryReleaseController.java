package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_REALSE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_BY_STORY_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL_STORY_RELEASE_BY_RELEASE_AND_PROJECT;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_BY_PROJECT_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.StoryReleaseModel;
import com.agilepro.controller.response.StoryReleaseReadResponse;
import com.agilepro.services.admin.StoryReleaseService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class StoryReleaseController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_STORY_REALSE)
@RequestMapping("/storyRelease")
public class StoryReleaseController
{
	/**
	 * The story release service.
	 **/
	@Autowired
	private StoryReleaseService storyReleaseService;

	/**
	 * Save.
	 *
	 * @param storyReleaseModel
	 *            the story release model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.STORY_RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid StoryReleaseModel storyReleaseModel)
	{
		return new BasicSaveResponse(storyReleaseService.saveStoryRelease(storyReleaseModel).getId());
	}

	@ActionName(ACTION_TYPE_READ_ALL_STORY_RELEASE_BY_RELEASE_AND_PROJECT)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_RELEASE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAllStoryReleaseByReleaseAndProject", method = RequestMethod.GET)
	@ResponseBody
	public StoryReleaseReadResponse fetchStoryRelease(@RequestParam(value = "releaseId", required = true) Long releaseId, @RequestParam(value = "projectId", required = true) Long projectId)
	{
		return storyReleaseService.fetchAllStoryReleaseByReleaseAndProject(releaseId, projectId);
	}
	
	/**
	 * Delete by project.
	 *
	 * @param storyReleaseModel the story release model
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_BY_PROJECT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteByProjectId", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteByProject(@RequestBody @Valid StoryReleaseModel storyReleaseModel)
	{
		storyReleaseService.deleteByProjectId(storyReleaseModel);
		
		return new BaseResponse();
	}
	
	/**
	 * Delete.
	 *
	 * @param storyReleaseModel
	 *            the story release model
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_BY_STORY_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.STORY_RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteByStoryId", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse delete(@RequestBody @Valid StoryReleaseModel storyReleaseModel)
	{
		storyReleaseService.deleteByStoryId(storyReleaseModel);

		return new BaseResponse();
	}
}
