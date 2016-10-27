package com.agilepro.persistence.repository.scrum;

import java.util.List;

import com.agilepro.persistence.entity.scrum.ScrumMeetingConversationEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IScrumMeetingConversationRepository.
 * 
 * @author Pritam
 */
public interface IScrumMeetingConversationRepository extends IWebutilsRepository<ScrumMeetingConversationEntity>
{
	@RestrictBySpace
	public List<ScrumMeetingConversationEntity> fetchConversationByScrumMeeting(@Condition(value = "scrumMeeting.id") Long scrumMeetingId);
}
