package com.agilepro.services.scrum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * Fetch all meetings.
	 *
	 * @param date
	 *            the date
	 * @return the list
	 */
	public List<ScrumMeetingModel> fetchAllMeetings(Date date)
	{
		List<ScrumMeetingEntity> scrumMeetingEntities = iscrumMeetingRepository.fetchMeetingsByDate(date);

		List<ScrumMeetingModel> scrumMeetingModels = new ArrayList<ScrumMeetingModel>();

		if(scrumMeetingEntities != null)
		{
			scrumMeetingEntities.forEach(entity -> scrumMeetingModels.add(super.toModel(entity, ScrumMeetingModel.class)));
		}

		return scrumMeetingModels;
	}
}
