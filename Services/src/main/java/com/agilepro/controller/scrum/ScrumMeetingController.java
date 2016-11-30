package com.agilepro.controller.scrum;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_SCRUM_MEETING;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_DATE_AND_PROJECT_ID;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.scrum.IScrumMeetingController;
import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.scrum.ScrumMeetingService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ScrumMeetingController. Controller class receiving the request and
 * sending back the response.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_SCRUM_MEETING)
@RequestMapping("/scrumMeeting")
public class ScrumMeetingController extends BaseController implements IScrumMeetingController
{
	/**
	 * The scrum meeting service.
	 **/
	@Autowired
	private ScrumMeetingService scrumMeetingService;

	/**
	 * Fetch meetings.
	 *
	 * @param date
	 *            the date
	 * @param projectId
	 *            the project id
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ_BY_DATE_AND_PROJECT_ID)
	@Authorization(roles = { UserRole.SCRUM_MEETING_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readByDateAndProjectId", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<ScrumMeetingModel> fetchMeetings(@RequestParam(value = "date", required = true) Date date, @RequestParam(value = "projectId", required = true) Long projectId)
	{
		return new BasicReadResponse<ScrumMeetingModel>(scrumMeetingService.fetchMeetingByDateAndProject(date, projectId));
	}
}
