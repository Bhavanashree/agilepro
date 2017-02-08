package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_AND_BUG;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_FETCH_BACKLOGS_BY_PROJECT_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_STORY_SPRINT_BUG_SPRINT;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE_STORY_SPRINT_BUG_SPRINT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.project.StoryAndBugInBacklogModel;
import com.agilepro.commons.models.project.StoryAndBugInSprintModel;
import com.agilepro.commons.models.project.StoryAndBugSprintUpdateModel;
import com.agilepro.services.bug.BugService;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.project.StoryService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;

/**
 * Story and Bug controller to receive the request for updating story or bug sprint or both.
 * 
 * @author Pritam.
 */
@RestController
@ActionName(ACTION_PREFIX_STORY_AND_BUG)
@RequestMapping(value = "/storyAndBug")
public class StoryAndBugController
{
	/**
	 * The story service.
	 **/
	@Autowired
	private StoryService storyService;
	
	/**
	 * Bug Service.
	 */
	@Autowired
	private BugService bugService;
	
	@ActionName(ACTION_TYPE_FETCH_BACKLOGS_BY_PROJECT_ID)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.BACKLOG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/fetchBacklogsByProjectId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<StoryAndBugInBacklogModel> fetchBacklogForDrag(@RequestParam(value = "projectId") Long projectId)
	{
		StoryAndBugInBacklogModel storyAndBugInBacklogModel = new StoryAndBugInBacklogModel(storyService.fetchBacklogsStory(projectId), bugService.fetchBacklogBugs(projectId));
		
		return new BasicReadResponse<StoryAndBugInBacklogModel>(storyAndBugInBacklogModel);
	}
	
	@ActionName(ACTION_TYPE_READ_STORY_SPRINT_BUG_SPRINT)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.BACKLOG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readStoriesAndBugBySprint", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<StoryAndBugInSprintModel> fetchStoryBySprintId(@RequestParam(value = "sprintId", required = true) Long sprintId)
	{
		StoryAndBugInSprintModel storyAndBugInSprintModel = new StoryAndBugInSprintModel(storyService.fetchStoryBySprintId(sprintId), bugService.fetchBugBySprint(sprintId));
		
		return new BasicReadResponse<StoryAndBugInSprintModel>(storyAndBugInSprintModel);
	}
	
	@ActionName(ACTION_TYPE_UPDATE_STORY_SPRINT_BUG_SPRINT)
	@RequestMapping(value = "/updateStorySprintBugSprint", method = RequestMethod.POST)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.BACKLOG_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BaseResponse updateStorySprint(@RequestBody StoryAndBugSprintUpdateModel storySprintUpdateModel)
	{
		Long sprintId = storySprintUpdateModel.getSprintId();
		
		storyService.updateStorySprint(storySprintUpdateModel.getMultipleStoryIds(), sprintId);
		
		bugService.updateBugSprint(storySprintUpdateModel.getMultipleBugIds(), sprintId);
		
		return new BaseResponse();
	}
}
