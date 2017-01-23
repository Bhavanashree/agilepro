package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.StoryNoteStatus;
import com.agilepro.commons.models.project.StoryNoteModel;
import com.agilepro.persistence.entity.project.StoryNoteEntity;
import com.agilepro.persistence.repository.project.IStoryNoteRepository;
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
	 * The istory note repository.
	 **/
	private IStoryNoteRepository istoryNoteRepository;

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
	 * Instantiates a new story note service.
	 */
	public StoryNoteService()
	{
		super(StoryNoteEntity.class, IStoryNoteRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		istoryNoteRepository = repositoryFactory.getRepository(IStoryNoteRepository.class);
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
			String owner = userService.fetch(currentUserid).getDisplayName();

			StoryNoteEntity storyNoteEntity, draftNote;
			StoryNoteModel newStoryNoteModel, storyForUpdate;

			draftNote = istoryNoteRepository.fetchSaveDraftNoteByStoryId(storyNoteModel.getStoryId(), StoryNoteStatus.DRAFT.toString());

			if(!storyNoteModel.getStoryNoteStatus().equals(StoryNoteStatus.DRAFT))
			{
				if(draftNote != null && storyNoteModel.getDraftIsSelected())
				{
					storyForUpdate = super.toModel(draftNote, StoryNoteModel.class);
					
					storyForUpdate.setOwner(owner);
					storyForUpdate.setStoryNoteStatus(StoryNoteStatus.PUBLISHED);
					storyForUpdate.setStoryId(storyNoteModel.getStoryId());
					
					storyNoteEntity = super.update(storyForUpdate);
				}
				else
				{
					newStoryNoteModel = new StoryNoteModel(storyNoteModel.getContent(), storyNoteModel.getStoryNoteStatus(), storyNoteModel.getStoryId(), storyNoteModel.getVersionTitle(), owner);
					storyNoteEntity = super.save(newStoryNoteModel);
				}
			}
			else
			{
				if(draftNote == null)
				{
					newStoryNoteModel = new StoryNoteModel(storyNoteModel.getContent(), storyNoteModel.getStoryNoteStatus(), storyNoteModel.getStoryId(), storyNoteModel.getVersionTitle(), owner);
					storyNoteEntity = super.save(newStoryNoteModel);
				}
				else
				{
					storyNoteModel.setVersion(super.fetch(storyNoteModel.getId()).getVersion());
					storyNoteModel.setOwner(owner);
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
	 * Fetch all note by story id.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public List<StoryNoteModel> fetchAllNoteByStoryId(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = istoryNoteRepository.fetchAllNoteByStoryId(storyId);

		List<StoryNoteModel> storyNoteModels = new ArrayList<StoryNoteModel>(storyNoteEntities.size());

		storyNoteEntities.forEach(entity -> storyNoteModels.add(super.toModel(entity, StoryNoteModel.class)));

		if(storyNoteModels.size() > 1)
		{
			for(int i = 0; i < storyNoteModels.size(); i++)
			{
				if(storyNoteModels.get(i).getStoryNoteStatus().equals(StoryNoteStatus.DRAFT))
				{
					storyNoteModels.add(0, storyNoteModels.get(i));
					storyNoteModels.remove(i + 1);
					break;
				}
			}
		}
		return storyNoteModels;
	}
}
