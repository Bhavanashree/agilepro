package com.agilepro.services.scrum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.ScrumActionStatus;
import com.agilepro.commons.models.scrum.ScrumActionItemConversationModel;
import com.agilepro.persistence.entity.scrum.ScrumActionItemConversationEntity;
import com.agilepro.persistence.repository.scrum.IScrumActionItemConversationRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class ScrumActionItemConversationService.
 * 
 * @author Pritam
 */
@Service
public class ScrumActionItemConversationService extends BaseCrudService<ScrumActionItemConversationEntity, IScrumActionItemConversationRepository>
{
	/**
	 * The is action item conversation repository.
	 **/
	private IScrumActionItemConversationRepository repository;

	/**
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

	/**
	 * The scrum action item service.
	 **/
	@Autowired
	private ScrumActionItemService scrumActionItemService;

	/**
	 * Instantiates a new scrum action item conversation service.
	 */
	public ScrumActionItemConversationService()
	{
		super(ScrumActionItemConversationEntity.class, IScrumActionItemConversationRepository.class);
	}

	/**
	 * Save action conversation and update action status if action is closed.
	 *
	 * @param model
	 *            the model
	 * @return the scrum action item conversation entity
	 */
	public ScrumActionItemConversationEntity saveActionConversation(ScrumActionItemConversationModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			ScrumActionItemConversationEntity entity = super.save(model);
			transaction.commit();

			ScrumActionStatus actionStatus = model.getScrumActionStatus();
			
			if(actionStatus.equals(ScrumActionStatus.CLOSE) || actionStatus.equals(ScrumActionStatus.RE_OPEN))
			{
				scrumActionItemService.updateActionStatus(actionStatus, model.getScrumActionItemId());
			}

			return entity;
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving  action conversation - " + model, ex);
		}
	}

	/**
	 * Fetch by action id.
	 *
	 * @param scrumActionItemId
	 *            the scrum action item id
	 * @return the map
	 */
	public Map<Integer, List<ScrumActionItemConversationModel>> fetchByActionId(Long scrumActionItemId)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

		Long previousUserId = -1L;
		Integer numberOfConversations = 0;
		Map<Integer, List<ScrumActionItemConversationModel>> conversations = new HashMap<Integer, List<ScrumActionItemConversationModel>>();

		List<ScrumActionItemConversationEntity> actionItems = repository.fetchConversationByActionId(scrumActionItemId);

		List<ScrumActionItemConversationModel> scrumActionItemConversations = new ArrayList<ScrumActionItemConversationModel>();

		List<ScrumActionItemConversationModel> newUser = new ArrayList<ScrumActionItemConversationModel>();

		if(actionItems != null)
		{
			actionItems.forEach(entity -> scrumActionItemConversations.add(super.toModel(entity, ScrumActionItemConversationModel.class)));
		}

		for(ScrumActionItemConversationModel model : scrumActionItemConversations)
		{
			model.setDisplayDate(dateFormat.format(model.getUpdatedOn()));
			model.setTime(timeFormat.format(model.getUpdatedOn()));

			if(!previousUserId.equals(model.getUserId()))
			{
				model.setDisplayName(userService.fetch(model.getUserId()).getDisplayName());

				if(newUser.size() > 0)
				{
					conversations.put(++numberOfConversations, newUser);
				}

				newUser = new ArrayList<ScrumActionItemConversationModel>();
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
}
