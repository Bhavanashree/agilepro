package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.StoryNoteModel;
import com.agilepro.controller.response.StoryNoteReadResponse;
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

	@Autowired
	private CurrentUserService currentUserService;
	
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
	
	public StoryNoteEntity saveOrUpdate(StoryNoteModel storyNoteModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Long currentUserid = currentUserService.getCurrentUserDetails().getUserId();
			
			String displayName = userService.fetch(currentUserid).getDisplayName(); 
			
			StoryNoteEntity storyNoteEntity;
			
			if(storyNoteModel.getPublished() || storyNoteModel.getId() == null)
			{
				storyNoteModel.setOwner(displayName);
				storyNoteEntity = super.save(storyNoteModel);
			}else
			{
				storyNoteModel.setVersion(super.fetch(storyNoteModel.getId()).getVersion());
				
				storyNoteModel.setOwner(displayName);
				storyNoteEntity = super.update(storyNoteModel);
			}

			transaction.commit();
			
			return storyNoteEntity;
		}catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving story note - ", ex);
		}
	}
	
	public List<StoryNoteModel> fetchAllNoteByStoryId(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = istoryNoteRepository.fetchAllNoteByStoryId(storyId);
		
		List<StoryNoteModel> storyNoteModels = new ArrayList<StoryNoteModel>(storyNoteEntities.size());

		storyNoteEntities.forEach(entity -> storyNoteModels.add(super.toModel(entity, StoryNoteModel.class)));
		
		return storyNoteModels;
	}
}
