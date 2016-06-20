package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.ConversationEntity;
import com.agilepro.persistence.repository.admin.IConversationRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ConversationService.
 */
@Service
public class ConversationService extends BaseCrudService<ConversationEntity, IConversationRepository>
{
	/**
	 * Instantiates a new conversation service.
	 */
	public ConversationService()
	{
		super(ConversationEntity.class, IConversationRepository.class);
	}

	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		super.repository.deleteAll();
	}
}
