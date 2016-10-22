package com.agilepro.services.scrum;

import com.agilepro.persistence.entity.scrum.ScrumMeetingEntity;
import com.agilepro.persistence.repository.scrum.IScrumMeetingRepository;
import com.yukthi.webutils.services.BaseCrudService;

public class ScrumMeetingService extends BaseCrudService<ScrumMeetingEntity, IScrumMeetingRepository> 
{
	public ScrumMeetingService()
	{
		super(ScrumMeetingEntity.class, IScrumMeetingRepository.class);
	}
}
