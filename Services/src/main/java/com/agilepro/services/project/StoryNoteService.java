package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.StoryNoteStatus;
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
			Long currentUserid = currentUserService.getCurrentUserDetails().getUserId();
			Long employeeId = userService.fetch(currentUserid).getBaseEntityId();

			StoryNoteEntity storyNoteEntity, existingDraftNote;
			StoryNoteModel newStoryNoteModel;

			existingDraftNote = repository.fetchSaveDraftNoteByStoryId(storyNoteModel.getStoryId(), StoryNoteStatus.DRAFT.toString());

			if(storyNoteModel.getStoryNoteStatus().equals(StoryNoteStatus.PUBLISHED))
			{
				// update the draft to published
				if(existingDraftNote != null && storyNoteModel.getDraftIsSelected())
				{
					storyNoteModel.setVersion(super.fetch(storyNoteModel.getId()).getVersion());
					storyNoteModel.setEmployeeId(employeeId);

					storyNoteEntity = super.update(storyNoteModel);
				}
				else
				{
					// save new published
					newStoryNoteModel = new StoryNoteModel(storyNoteModel.getContent(), storyNoteModel.getStoryNoteStatus(), storyNoteModel.getStoryId(), storyNoteModel.getVersionTitle(), employeeId);
					storyNoteEntity = super.save(newStoryNoteModel);
				}
			}
			else
			{
				if(existingDraftNote == null)
				{
					newStoryNoteModel = new StoryNoteModel(storyNoteModel.getContent(), storyNoteModel.getStoryNoteStatus(), storyNoteModel.getStoryId(), storyNoteModel.getVersionTitle(), employeeId);
					storyNoteEntity = super.save(newStoryNoteModel);
				}
				else
				{
					storyNoteModel.setVersion(super.fetch(storyNoteModel.getId()).getVersion());
					storyNoteModel.setEmployeeId(employeeId);
					storyNoteEntity = super.update(storyNoteModel);
				}
			}

			transaction.commit();

			return storyNoteEntity;
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving story note - ", ex);
		}
	}

	/**
	 * Fetch active story note by story id.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public StoryNoteModel fetchActiveStoryNoteByStoryId(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = repository.fetchActiveStoryNoteByStoryId(storyId);

		StoryNoteModel storyNoteModel = null;
		
		if(storyNoteEntities.size() > 0)
		{
			storyNoteModel = super.toModel(storyNoteEntities.get(0), StoryNoteModel.class);
			
			storyNoteModel.setOwner(employeeService.fetchEmployeeName(storyNoteModel.getEmployeeId()));
		}
		
		return storyNoteModel;
	}
}
