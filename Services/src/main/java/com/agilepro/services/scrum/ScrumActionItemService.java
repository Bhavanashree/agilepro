package com.agilepro.services.scrum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.services.notification.EmailNotificationService;
import com.agilepro.commons.ScrumActionStatus;
import com.agilepro.commons.models.scrum.ScrumActionItemConversationModel;
import com.agilepro.commons.models.scrum.ScrumActionItemModel;
import com.agilepro.persistence.entity.scrum.ScrumActionItemEntity;
import com.agilepro.persistence.repository.scrum.IScrumActionItemRepository;
import com.agilepro.services.admin.EmployeeService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class ScrumActionItemService.
 * 
 * @author Pritam
 */
@Service
public class ScrumActionItemService extends BaseCrudService<ScrumActionItemEntity, IScrumActionItemRepository>
{
	/**
	 * The email notification service for sending mail.
	 **/
	@Autowired
	private EmailNotificationService emailNotificationService;
	
	/** 
	 * The scrum action item conversation service. 
	 **/
	@Autowired
	private ScrumActionItemConversationService scrumActionItemConversationService; 

	/**
	 * The employee service.
	 **/
	@Autowired
	private EmployeeService employeeService;
	
	/** 
	 * The user service. 
	 **/
	@Autowired
	private UserService userService;

	/**
	 * Instantiates a new scrum action item service.
	 */
	public ScrumActionItemService()
	{
		super(ScrumActionItemEntity.class, IScrumActionItemRepository.class);
	}

	public void saveAndSendMail(ScrumActionItemModel scrumActionItemModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			ScrumActionItemEntity scrumActionItemEntity = super.save(scrumActionItemModel);
			
			scrumActionItemConversationService.save(new ScrumActionItemConversationModel(scrumActionItemEntity.getId(), scrumActionItemModel.getUserId(), scrumActionItemModel.getMessage()));
			
			transaction.commit();

			List<String> emailIds = new ArrayList<String>();
			for(Long employeeId : scrumActionItemEntity.getEmployeeIds())
			{
				emailIds.add(employeeService.fetch(employeeId).getMailId());
			}

			//emailNotificationService.sendMail("RESET_PASSWORD", new ActionItemContext(emailIds, "Pritam", "new password"));
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving model - {}", scrumActionItemModel);
		}
	}

	public boolean updateActionStatus(ScrumActionStatus actionStatus, Long actionItemId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			if(repository.updateActionStatus(actionStatus, actionItemId))
			{
				transaction.commit();
				return true;
			}
			return false;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating Scrum Action Items");
		}
	}
	
	/**
	 * Fetch action by scrum.
	 *
	 * @param scrumMeetingId
	 *            the scrum meeting id
	 * @return the list
	 */
	public List<ScrumActionItemModel> fetchActionByScrum(Long scrumMeetingId)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

		List<ScrumActionItemEntity> scrumActionItemEntities = repository.fetchByScrumId(scrumMeetingId);
		List<ScrumActionItemModel> scrumActionModels = new ArrayList<ScrumActionItemModel>();

		if(scrumActionItemEntities != null)
		{
			scrumActionItemEntities.forEach(entity -> scrumActionModels.add(super.toModel(entity, ScrumActionItemModel.class)));

			for(ScrumActionItemModel actionModel : scrumActionModels)
			{
				Map<Long, String> employeeNames = new HashMap<Long, String>();

				for(Long employeeId : actionModel.getEmployeeIds())
				{
					employeeNames.put(employeeId, employeeService.fetch(employeeId).getName());
				}

				actionModel.setEmployeeNames(employeeNames);
				actionModel.setProvidedBy(userService.fetch(actionModel.getUserId()).getDisplayName());
				actionModel.setDisplayDate(dateFormat.format(actionModel.getUpdatedOn()));
				actionModel.setTime(timeFormat.format(actionModel.getUpdatedOn()));
			}
		}
		return scrumActionModels;
	}
}
