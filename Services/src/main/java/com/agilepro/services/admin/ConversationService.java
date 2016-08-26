package com.agilepro.services.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.admin.ConversationModel;
import com.agilepro.persistence.entity.admin.ConversationEntity;
import com.agilepro.persistence.repository.admin.IConversationRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class ConversationService.
 */
@Service
public class ConversationService extends BaseCrudService<ConversationEntity, IConversationRepository>
{
	/**
	 * The repository factory.
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;

	/** 
	 * The user service. 
	 **/
	@Autowired
	private UserService userService;
	
	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;
	
	/**
	 * The i conversation repository.
	 **/
	private IConversationRepository iconversationRepository;

	/**
	 * Instantiates a new conversation service.
	 */
	public ConversationService()
	{
		super(ConversationEntity.class, IConversationRepository.class);
	}

	@PostConstruct
	private void init()
	{
		iconversationRepository = repositoryFactory.getRepository(IConversationRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param conversationmodel the conversationmodel
	 * @return the conversation entity
	 */
	public ConversationEntity save(ConversationModel conversationmodel)
	{
		conversationmodel.setDate(new Date());
		
		return super.save(conversationmodel);
	}
	
	/**
	 * Fetch conversation.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public List<ConversationModel> fetchConversations(Long storyId)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		
		String previousDisplayName = new String(), displayName;
		Date conversationDate;
		
		List<ConversationEntity> conversationEntities = iconversationRepository.fetchConversationByStroyId(storyId);

		List<ConversationModel> conversationModels = new ArrayList<ConversationModel>();

		conversationEntities.forEach(entity -> conversationModels.add(super.toModel(entity, ConversationModel.class))); 
		
		for(ConversationModel model : conversationModels)
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
			
			if(currentUserService.getCurrentUserDetails().getUserId() == model.getUserId().longValue())
			{
				model.setDisplayLeft(true);
			}
			else
			{
				model.setDisplayRight(true);
			}
			
			model.setDisplayDate(dateFormat.format(conversationDate));
			model.setTime(timeFormat.format(conversationDate));
			
			previousDisplayName = displayName;
		}
		
		return conversationModels;
	}

	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		super.repository.deleteAll();
	}
}
