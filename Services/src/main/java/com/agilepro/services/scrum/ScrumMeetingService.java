package com.agilepro.services.scrum;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.scrum.ScrumMeetingModel;
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

	/**
	 * Fetch meeting by date and project.
	 *
	 * @param date
	 *            the date
	 * @param projectId
	 *            the project id
	 * @return the scrum meeting model
	 */
	public ScrumMeetingModel fetchMeetingByDateAndProject(Date date, Long projectId)
	{
		ScrumMeetingEntity scrumMeeting = repository.fetchMeetingByDate(date, projectId);

		return super.toModel(scrumMeeting, ScrumMeetingModel.class);
	}
	
	/**
	 * Fetch project id for the provided scrum meeting id.
	 * 
	 * @param scrumMeetingId provided scrum meeting id.
	 * @return matching id.
	 */
	public Long fetchProjectIdByScrumMeeting(Long scrumMeetingId)
	{
		return repository.fetchProjectId(scrumMeetingId);
	}
}
