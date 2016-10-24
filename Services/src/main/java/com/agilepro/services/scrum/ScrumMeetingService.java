package com.agilepro.services.scrum;

import org.springframework.stereotype.Service;
import com.agilepro.persistence.entity.scrum.ScrumMeetingEntity;
import com.agilepro.persistence.repository.scrum.IScrumMeetingRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ScrumMeetingService.
 * 
 * @author Pritam
 */
@Service
public class ScrumMeetingService extends BaseCrudService<ScrumMeetingEntity, IScrumMeetingRepository>
{
	/**
	 * Instantiates a new scrum meeting service.
	 */
	public ScrumMeetingService()
	{
		super(ScrumMeetingEntity.class, IScrumMeetingRepository.class);
	}
}
