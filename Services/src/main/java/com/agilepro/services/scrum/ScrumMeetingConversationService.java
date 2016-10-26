package com.agilepro.services.scrum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.persistence.entity.scrum.ScrumMeetingConversationEntity;
import com.agilepro.persistence.repository.scrum.IScrumMeetingConversationRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class ScrumMeetingConversationService.
 * 
 * @author Pritam
 */
@Service
public class ScrumMeetingConversationService extends BaseCrudService<ScrumMeetingConversationEntity, IScrumMeetingConversationRepository>
{
	/** 
	 * The user service. 
	 **/
	@Autowired
	private UserService userService;
	
	private IScrumMeetingConversationRepository iscrumMeetingConversationRepository;
	
	@Autowired
	private RepositoryFactory repositoryFactory;
	
	/**
	 * Instantiates a new scrum meeting conversation service.
	 */
	public ScrumMeetingConversationService()
	{
		super(ScrumMeetingConversationEntity.class, IScrumMeetingConversationRepository.class);
	}
	
	@PostConstruct
	private void init()
	{
		iscrumMeetingConversationRepository = repositoryFactory.getRepository(IScrumMeetingConversationRepository.class);
	}
	
	public List<ScrumMeetingConversationModel> fetchScrumMeeting(Long scrumMeetingId, Date date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

		String previousDisplayName = new String(), displayName = null;
		Date conversationDate;
		
		List<ScrumMeetingConversationEntity> scrumConversations = iscrumMeetingConversationRepository.fetchConversationByProjectAndDate(scrumMeetingId, date);
		
		List<ScrumMeetingConversationModel> scrumConversationModel = new ArrayList<ScrumMeetingConversationModel>();
		
		if(scrumConversations != null)
		{
			scrumConversations.forEach(entity -> scrumConversationModel.add(super.toModel(entity, ScrumMeetingConversationModel.class)));
		}
		
		for(ScrumMeetingConversationModel model : scrumConversationModel)
		{
			conversationDate = model.getDate();
			displayName = userService.fetch(model.getUserId()).getDisplayName();
			
			if(previousDisplayName.equals(displayName))
			{
				model.setDisplayName(new String());
			}
			else
			{
				model.setDisplayName(displayName);
			}
			
			model.setDisplayDate(dateFormat.format(conversationDate));
			model.setTime(timeFormat.format(conversationDate));

			previousDisplayName = displayName;

		}
		
		return scrumConversationModel;
	}
	
}
