package com.agilepro.services.scrum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.scrum.ScrumActionItemModel;
import com.agilepro.commons.models.scrum.ScrumMeetingConversationModel;
import com.agilepro.controller.IAgileProConstants;
import com.agilepro.persistence.entity.scrum.ScrumMeetingConversationEntity;
import com.agilepro.persistence.repository.scrum.IScrumMeetingConversationRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.project.StoryService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
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

	/**
	 * The member service.
	 **/
	@Autowired
	private ProjectMemberService memberService;

	/**
	 * The scrum action item service.
	 **/
	@Autowired
	private ScrumActionItemService scrumActionItemService;

	/**
	 * Instantiates a new scrum meeting conversation service.
	 */
	public ScrumMeetingConversationService()
	{
		super(ScrumMeetingConversationEntity.class, IScrumMeetingConversationRepository.class);
	}

	/**
	 * Save scrum conversation.
	 *
	 * @param scrumMeetingConversationModel
	 *            the scrum meeting conversation model
	 * @return the scrum meeting conversation entity
	 */
	public ScrumMeetingConversationEntity saveScrumConversation(ScrumMeetingConversationModel scrumMeetingConversationModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			if(scrumMeetingConversationModel.getProjectMemberIds() != null)
			{
				Set<Long> projectMemberIds = scrumMeetingConversationModel.getProjectMemberIds();
				Set<Long> employeeIds = new HashSet<Long>(projectMemberIds.size());

				projectMemberIds.forEach(memberId -> employeeIds.add(memberService.fetch(memberId).getEmployee().getId()));

				scrumActionItemService.saveAndSendMail(new ScrumActionItemModel(scrumMeetingConversationModel.getScrumMeetingId(), employeeIds, scrumMeetingConversationModel.getMessage(), scrumMeetingConversationModel.getUserId()));
			}

			ScrumMeetingConversationEntity entity = super.save(scrumMeetingConversationModel);

			transaction.commit();

			return entity;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving model - {}", scrumMeetingConversationModel);
		}
	}
	
	/**
	 * Fetch scrum meeting conversation.
	 *
	 * @param scrumMeetingId
	 *            the scrum meeting id
	 * @return the map where key is always unique as number of messages, value
	 *         is list of conversation per user.
	 */
	public Map<Integer, List<ScrumMeetingConversationModel>> fetchScrumMeetingConversation(Long scrumMeetingId)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

		Long previousUserId = -1L;
		Integer numberOfConversations = 0;
		Map<Integer, List<ScrumMeetingConversationModel>> conversations = new HashMap<Integer, List<ScrumMeetingConversationModel>>();

		List<ScrumMeetingConversationEntity> scrumConversations = repository.fetchConversationByScrumMeeting(scrumMeetingId);

		List<ScrumMeetingConversationModel> scrumConversationModel = new ArrayList<ScrumMeetingConversationModel>();

		List<ScrumMeetingConversationModel> newUser = new ArrayList<ScrumMeetingConversationModel>();

		if(scrumConversations != null)
		{
			scrumConversations.forEach(entity -> scrumConversationModel.add(super.toModel(entity, ScrumMeetingConversationModel.class)));
		}

		for(ScrumMeetingConversationModel model : scrumConversationModel)
		{
			model.setDisplayDate(dateFormat.format(model.getUpdatedOn()));
			model.setTime(timeFormat.format(model.getUpdatedOn()));

			if(model.getStoryId() != null)
			{
				model.setDisplayStory(storyService.fetch(model.getStoryId()).getTitle());
			}

			if(!previousUserId.equals(model.getUserId()))
			{
				model.setDisplayName(userService.fetch(model.getUserId()).getDisplayName());

				if(newUser.size() > 0)
				{
					conversations.put(++numberOfConversations, newUser);
				}

				newUser = new ArrayList<ScrumMeetingConversationModel>();
			}
			else
			{
				model.setDisplayName(new String());
			}

			newUser.add(model);

			previousUserId = model.getUserId();
		}

		if(newUser.size() > 0)
		{
			conversations.put(++numberOfConversations, newUser);
		}

		return conversations;
	}
	
	@Override
	public String getUserSpace(ScrumMeetingConversationEntity entity, Object model)
	{
		if(model instanceof ScrumMeetingConversationModel)
		{
			ScrumMeetingConversationModel conversationModel = (ScrumMeetingConversationModel) model;
			
			if(conversationModel.getCustomerId() != null)
			{
				return IAgileProConstants.customerSpace(conversationModel.getCustomerId());
			}
		}
		
		return super.getUserSpace(entity, model);
	}
}
