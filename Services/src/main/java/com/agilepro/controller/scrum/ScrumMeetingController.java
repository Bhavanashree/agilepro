package com.agilepro.controller.scrum;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_SCRUM_MEETING;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.scrum.ScrumMeetingModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.scrum.ScrumMeetingService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ScrumMeetingController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_SCRUM_MEETING)
@RequestMapping("/scrumMeeting")
public class ScrumMeetingController extends BaseController
{
	/**
	 * The scrum meeting service.
	 **/
	@Autowired
	private ScrumMeetingService scrumMeetingService;

	/**
	 * Save scrum meeting.
	 *
	 * @param scrumMeetingModel
	 *            the scrum meeting model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BasicSaveResponse saveScrumMeeting(@RequestBody @Valid ScrumMeetingModel scrumMeetingModel)
	{
		return new BasicSaveResponse(scrumMeetingService.save(scrumMeetingModel).getId());
	}
}
