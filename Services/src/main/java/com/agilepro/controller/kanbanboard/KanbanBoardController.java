package com.agilepro.controller.kanbanboard;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_KANBAN_BOARD;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_SPRINT_TEAM_USER_BY_PROJECT_ID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.agilepro.commons.UserRole;
import com.agilepro.controller.response.KanbanBoardReadResponse;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;

import com.yukthi.webutils.controllers.BaseController;

/**
 * KanbanBoardController for receiving the request and returning back the sprint, team, members.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_KANBAN_BOARD)
@RequestMapping("/kanbanBoard")
public class KanbanBoardController extends BaseController
{
	/** 
	 * kanban board service. 
	 **/
	@Autowired
	private KanbanBoardService kanbanBoardService;
	
	@ActionName(ACTION_TYPE_READ_SPRINT_TEAM_USER_BY_PROJECT_ID)
	@RequestMapping(value = "/readSprintTeamUserByProjectId", method = RequestMethod.GET)
	//@Authorization(roles = { UserRole.KANBAN_BOARD_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public KanbanBoardReadResponse fetchSprintTeamUser(@RequestParam(value = "projectId") Long projectId)
	{
		return kanbanBoardService.fetchSprintTeamUser(projectId);
	}
}
