package com.agilepro.services.project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.ConversationMessageModel;
import com.agilepro.commons.models.project.ConversationTitleModel;
import com.agilepro.persistence.entity.project.ConversationMessageEntity;
import com.agilepro.persistence.repository.admin.IConversationMessageRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class ConversationMessageService.
 * 
 * @author Pritam
 */
@Service
public class ConversationMessageService extends BaseCrudService<ConversationMessageEntity, IConversationMessageRepository>
{
	/**
	 * The repository factory.
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The conversation title service.
	 **/
	@Autowired
	private ConversationTitleService conversationTitleService;

	/**
	 * The i conversation repository.
	 **/
	private IConversationMessageRepository iconversationMessageRepository;
	
	/** 
	 * The user service. 
	 **/
	@Autowired
	private UserService userService;

	/**
	 * Instantiates a new conversation service.
	 */
	public ConversationMessageService()
	{
		super(ConversationMessageEntity.class, IConversationMessageRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		iconversationMessageRepository = repositoryFactory.getRepository(IConversationMessageRepository.class);
	}

	/**
	 * Check conversation.
	 *
	 * @param conversationTitleId
	 *            the conversation title id
	 * @param userId
	 *            the user id
	 */
	private void checkConversation(Long conversationTitleId, Long userId)
	{
		List<ConversationMessageEntity> conversationMessageEntities = iconversationMessageRepository.fetchConversationMessageByTitleId(conversationTitleId);

		if(conversationMessageEntities.size() == 0)
		{
			ConversationTitleModel conversationTitleModel = conversationTitleService.fetchFullModel(conversationTitleId, ConversationTitleModel.class);

			conversationTitleModel.setOwnerId(userId);

			conversationTitleService.update(conversationTitleModel);
		}
	}

	/**
	 * Save.
	 *
	 * @param conversationMessagemodel
	 *            the conversation messagemodel
	 * @return the conversation message entity
	 */
	public ConversationMessageEntity save(ConversationMessageModel conversationMessagemodel)
	{
		checkConversation(conversationMessagemodel.getConversationTitleId(), conversationMessagemodel.getUserId());

		conversationMessagemodel.setDate(new Date());

		return super.save(conversationMessagemodel);
	}

	/**
	 * Fetch conversations.
	 *
	 * @param conversationTitleId
	 *            the conversation title id
	 * @return the list
	 */
	public List<ConversationMessageModel> fetchConversations(Long conversationTitleId)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

		String previousDisplayName = new String(), displayName = null;
		Date conversationDate;

		Long ownerId = conversationTitleService.fetchFullModel(conversationTitleId, ConversationTitleModel.class).getOwnerId();
		
		List<ConversationMessageEntity> conversationEntities = iconversationMessageRepository.fetchConversationMessageByTitleId(conversationTitleId);

		List<ConversationMessageModel> conversationModels = new ArrayList<ConversationMessageModel>();

		conversationEntities.forEach(entity -> conversationModels.add(super.toModel(entity, ConversationMessageModel.class)));

		for(ConversationMessageModel model : conversationModels)
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

			if(ownerId == model.getUserId())
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
