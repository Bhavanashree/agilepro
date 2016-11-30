package com.agilepro.services.scrum;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

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
	 * The I scrum meeting repository.
	 **/
	private IScrumMeetingRepository iscrumMeetingRepository;

	/**
	 * Instantiates a new scrum meeting service.
	 */
	public ScrumMeetingService()
	{
		super(ScrumMeetingEntity.class, IScrumMeetingRepository.class);
	}

	@PostConstruct
	private void init()
	{
		iscrumMeetingRepository = repositoryFactory.getRepository(IScrumMeetingRepository.class);
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
		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		String s = simpleDateFormat.format(date);*/
		
		ScrumMeetingEntity scrumMeeting = iscrumMeetingRepository.fetchMeetingByDate(date, projectId);

		return super.toModel(scrumMeeting, ScrumMeetingModel.class);
	}
	
	public Long fetchProjectIdByScrumMeeting(Long projectId)
	{
		return iscrumMeetingRepository.fetchProjectId(projectId);
	}
}
