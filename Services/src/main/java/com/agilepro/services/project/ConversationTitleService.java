package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.ConversationTitleModel;
import com.agilepro.persistence.entity.project.ConversationTitleEntity;
import com.agilepro.persistence.repository.admin.IConversationTitleRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ConversationTitleService.
 * 
 * @author Pritam
 */
@Service
public class ConversationTitleService extends BaseCrudService<ConversationTitleEntity, IConversationTitleRepository>
{
	/** 
	 * The repository factory. 
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory; 
	
	/** 
	 * The iconversation title repository. 
	 **/
	private IConversationTitleRepository iconversationTitleRepository;
	
	/**
	 * Instantiates a new conversation title service.
	 */
	public ConversationTitleService()
	{
		super(ConversationTitleEntity.class, IConversationTitleRepository.class);
	}
	
	/**
	 * Inits the iconversationTitleRepository.
	 */
	@PostConstruct
	public void init()
	{
		iconversationTitleRepository = repositoryFactory.getRepository(IConversationTitleRepository.class);
	}
	
	/**
	 * Fetch conversation titles.
	 *
	 * @param storyId the story id
	 * @return the list
	 */
	public List<ConversationTitleModel> fetchConversationTitles(Long storyId)
	{
		List<ConversationTitleEntity> conversationTitleEntities = iconversationTitleRepository.fetchTitleByStroyId(storyId);
		
		List<ConversationTitleModel> conversationTitleModels = new ArrayList<ConversationTitleModel>();
		
		conversationTitleEntities.forEach(entity -> conversationTitleModels.add(super.toModel(entity, ConversationTitleModel.class)));
		
		return conversationTitleModels;
	}
	
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
