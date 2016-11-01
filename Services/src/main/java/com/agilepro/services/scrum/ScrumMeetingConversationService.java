package com.agilepro.services.scrum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.commons.models.scrum.ScrumMeetingConversationReader;
import com.agilepro.controller.response.ScrumMeetingConversationReadResponse;
import com.agilepro.persistence.entity.scrum.ScrumMeetingConversationEntity;
import com.agilepro.persistence.repository.scrum.IScrumMeetingConversationRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.project.StoryService;
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

	/**
	 * The story service.
	 **/
	@Autowired
	private StoryService storyService;

	@Autowired
	private ProjectMemberService memberService;
	
	/**
	 * The iscrum meeting conversation repository.
	 **/
	private IScrumMeetingConversationRepository iscrumMeetingConversationRepository;

	/**
	 * The repository factory.
	 **/
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

	
	public Map<Integer, List<ScrumMeetingConversationModel>> fetchScrumMeetingConversation(Long scrumMeetingId)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

		Long previousUserId = -1L;
		Integer numberOfConversations = 0;
		Map<Integer, List<ScrumMeetingConversationModel>> conversations = new HashMap<Integer, List<ScrumMeetingConversationModel>>();
		
		Map<Long, String> projectMembers = null;
		
		List<ScrumMeetingConversationEntity> scrumConversations = iscrumMeetingConversationRepository.fetchConversationByScrumMeeting(scrumMeetingId);

		List<ScrumMeetingConversationModel> scrumConversationModel = new ArrayList<ScrumMeetingConversationModel>();

		List<ScrumMeetingConversationReader> scrumMeetingConversationReaders = new ArrayList<ScrumMeetingConversationReader>();
		
		List<ScrumMeetingConversationModel> newUser = new ArrayList<ScrumMeetingConversationModel>();
		
		if(scrumConversations != null)
		{
			scrumConversations.forEach(entity -> scrumConversationModel.add(super.toModel(entity, ScrumMeetingConversationModel.class)));
		}

		for(ScrumMeetingConversationModel model : scrumConversationModel)
		{
			model.setDisplayDate(dateFormat.format(model.getDate()));
			model.setTime(timeFormat.format(model.getDate()));

			if(model.getProjectMemberIds() != null)
			{
				projectMembers = new HashMap<Long, String>();
				
				for(Long memberId : model.getProjectMemberIds())
				{
					projectMembers.put(memberId, memberService.fetch(memberId).getEmployee().getName());
				}
				
				model.setProjectMembers(projectMembers);
			}
			
			if(model.getStoryId() != null)
			{
				model.setDisplayStory(storyService.fetch(model.getStoryId()).getTitle());
			}

			if(!previousUserId.equals(model.getUserId()))
			{
				model.setDisplayName(userService.fetch(model.getUserId()).getDisplayName());
				
				if(newUser.size() > 0)
				{
					conversations.put(++ numberOfConversations, newUser);
				}
				
				newUser = new ArrayList<ScrumMeetingConversationModel>();
			}else
			{
				model.setDisplayName(new String());
			}
			
			newUser.add(model);
			
			previousUserId = model.getUserId();
		}
		
		if(newUser.size() > 0)
		{
			conversations.put(++ numberOfConversations, newUser);
		}
		
		return conversations;
	}
}
