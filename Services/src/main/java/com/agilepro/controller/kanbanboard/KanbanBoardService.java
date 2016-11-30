package com.agilepro.controller.kanbanboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agilepro.controller.response.KanbanBoardReadResponse;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.admin.ProjectTeamService;
import com.agilepro.services.project.SprintService;

/**
 * KanbanBoardService fetching sprint, team, users for kanban board page.
 * 
 * @author Pritam
 */
@Service
public class KanbanBoardService
{
	/**
	 * Sprint service.
	 */
	@Autowired
	private SprintService sprintService;

	/**
	 * Project team service.
	 **/
	@Autowired
	private ProjectTeamService projectTeamService;

	/**
	 * Project member service.
	 **/
	@Autowired
	private ProjectMemberService projectMemberService;

	public KanbanBoardReadResponse fetchSprintTeamUser(Long projectId)
	{
		return new KanbanBoardReadResponse(sprintService.fetchSprintDropDown(projectId), projectTeamService.fetchTeamsDropDown(projectId), projectMemberService.fetchMembersDropDown(projectId));
	}
}
