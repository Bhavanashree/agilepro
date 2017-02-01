package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.StoryNoteModel;
import com.agilepro.persistence.entity.project.StoryNoteEntity;
import com.agilepro.persistence.repository.project.IStoryNoteRepository;
import com.agilepro.services.admin.EmployeeService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class StoryNoteService.
 * 
 * @author Pritam
 */
@Service
public class StoryNoteService extends BaseCrudService<StoryNoteEntity, IStoryNoteRepository>
{
	/**
	 * The current user service.
	 **/
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

	/**
	 * Employee service for interacting with employee table.
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Instantiates a new story note service.
	 */
	public StoryNoteService()
	{
		super(StoryNoteEntity.class, IStoryNoteRepository.class);
	}

	/**
	 * Save or update.
	 *
	 * @param storyNoteModel
	 *            the story note model
	 * @return the story note entity
	 */
	public StoryNoteEntity saveOrUpdate(StoryNoteModel storyNoteModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Long currentUserId = currentUserService.getCurrentUserDetails().getUserId();
			Long employeeId = userService.fetch(currentUserId).getBaseEntityId();

			storyNoteModel.setEmployeeId(employeeId);

			StoryNoteEntity storyNoteEntity;

			if(storyNoteModel.getId() == null)
			{
				storyNoteEntity = super.save(storyNoteModel);
			}
			else
			{
				storyNoteEntity = super.update(storyNoteModel);
			}

			transaction.commit();

			return storyNoteEntity;
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving story note - ", ex);
		}
	}

	/**
	 * Fetch All the story notes for the given story id.
	 * 
	 * @param storyId
	 *            provided story id for which story notes are to be fetched.
	 * @return matching records.
	 */
	public List<StoryNoteModel> fetchAllStoryNotes(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = repository.fetchAllStoryNoteByStoryId(storyId);
		List<StoryNoteModel> storyNoteModels = new ArrayList<StoryNoteModel>();

		storyNoteEntities.forEach(entity -> 
		{
			StoryNoteModel storyNoteModel = super.toModel(entity, StoryNoteModel.class);
			storyNoteModel.setOwner(employeeService.fetchEmployeeName(storyNoteModel.getEmployeeId()));
			
			storyNoteModels.add(storyNoteModel);
		});
		
		return storyNoteModels;
	}

	/**
	 * Fetch latest story note by story id.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public StoryNoteModel fetchLatestStoryNoteByStoryId(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = repository.fetchLatestPublisedStoryNoteByStoryId(storyId);

		StoryNoteModel storyNoteModel = null;

		if(storyNoteEntities.size() > 0)
		{
			storyNoteModel = super.toModel(storyNoteEntities.get(0), StoryNoteModel.class);

			storyNoteModel.setOwner(employeeService.fetchEmployeeName(storyNoteModel.getEmployeeId()));
		}

		return storyNoteModel;
	}
}
