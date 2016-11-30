package com.agilepro.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.notification.INotificationProcessor;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.scrum.ScrumMeetingConversationService;
import com.agilepro.services.scrum.ScrumMeetingService;
import com.yukthi.webutils.mail.MailProcessingException;
import com.yukthi.webutils.mail.ReceivedMailMessage;
import com.yukthi.webutils.repository.UserEntity;

/**
 * The Class ScrumMeetingUpdateProcessor, processor for reading the reply of
 * scrum meeting.
 * 
 * @author Pritam
 */
@Component
public class ScrumMeetingUpdateProcessor implements INotificationProcessor
{
	/**
	 * The Constant SCRUM_UPDATE_NAME.
	 **/
	private static final String SCRUM_UPDATE_NAME = "scrumUpdate";

	/**
	 * The Constant SCRUM_MEETING_ID.
	 **/
	private static final String SCRUM_MEETING_ID = "scrumMeetingId";

	/**
	 * The scrum meeting conversation service.
	 **/
	@Autowired
	private ScrumMeetingConversationService scrumMeetingConversationService;
	
	/** 
	 * The Project member service. 
	 **/
	@Autowired
	private ProjectMemberService projectMemberService;
	
	@Autowired
	private ScrumMeetingService scrumMeetingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agilepro.notification.INotificationProcessor#process(com.yukthi.
	 * webutils.repository.UserEntity,
	 * com.yukthi.webutils.mail.ReceivedMailMessage)
	 */
	@Override
	public boolean process(UserEntity userEntity, ReceivedMailMessage mailMessage) throws MailProcessingException, EmailProcessingException
	{
		Long scrumMeetingId = -1L;
		Long customerId = -1L;
		String scrumMailReply = mailMessage.getTextByName(SCRUM_UPDATE_NAME);
		String scrumMeetingIdStr = mailMessage.getTextByName(SCRUM_MEETING_ID);
		String customerIdStr = mailMessage.getTextByName(SCRUM_MEETING_ID);
		
		if(scrumMailReply == null)
		{
			throw new EmailProcessingException("This mail doesn't belong to scrum meeting");
		}
		
		if(scrumMeetingIdStr == null)
		{
			throw new EmailProcessingException("Scrum meeting mail has been corrupted");
		}
		
		try
		{
			scrumMeetingId = Long.valueOf(scrumMeetingIdStr);
			customerId = Long.valueOf(customerIdStr);
		}catch(NumberFormatException ex)
		{
			throw new EmailProcessingException("An error occured while processing scrum update mail reply", ex);
		}

		Long projectId = scrumMeetingService.fetchProjectIdByScrumMeeting(scrumMeetingId);
		
		if(!projectMemberService.isProjectMember(projectId, userEntity.getBaseEntityId()))
		{
			throw new EmailProcessingException("{} is not a member of scrum meeting", userEntity.getDisplayName());
		}
		
		scrumMeetingConversationService.saveScrumConversation(new ScrumMeetingConversationModel(scrumMeetingId, userEntity.getId(), scrumMailReply, customerId));
		return true;
	}
}
