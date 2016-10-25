package com.agilepro.services.scrum;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.scrum.ScrumMeetingConversationEntity;
import com.agilepro.persistence.repository.scrum.IScrumMeetingConversationRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ScrumMeetingConversationService.
 * 
 * @author Pritam
 */
@Service
public class ScrumMeetingConversationService extends BaseCrudService<ScrumMeetingConversationEntity, IScrumMeetingConversationRepository>
{
	/**
	 * Instantiates a new scrum meeting conversation service.
	 */
	public ScrumMeetingConversationService()
	{
		super(ScrumMeetingConversationEntity.class, IScrumMeetingConversationRepository.class);
	}
}
