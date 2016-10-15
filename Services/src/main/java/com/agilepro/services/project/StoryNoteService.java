package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.StoryNoteModel;
import com.agilepro.controller.response.StoryNoteReadResponse;
import com.agilepro.persistence.entity.project.StoryNoteEntity;
import com.agilepro.persistence.repository.project.IStoryNoteRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;

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
			StoryNoteEntity storyNoteEntity;
			
			if(storyNoteModel.getPublished() || storyNoteModel.getId() == null)
			{
				storyNoteEntity = super.save(storyNoteModel);
			}else
			{
				storyNoteModel.setVersion(super.fetch(storyNoteModel.getId()).getVersion());
				
				storyNoteEntity = super.update(storyNoteModel);
			}

			transaction.commit();
			
			return storyNoteEntity;
		}catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving story note - ", ex);
		}
	}
	
	public StoryNoteReadResponse fetchAllNoteByStoryId(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = istoryNoteRepository.fetchAllPublishedNoteByStoryId(storyId, true);
		
		List<StoryNoteModel> storyNotePublished = new ArrayList<StoryNoteModel>(storyNoteEntities.size());
		
		storyNoteEntities.forEach(entity -> storyNotePublished.add(super.toModel(entity, StoryNoteModel.class)));
		
		StoryNoteModel draftNote = super.toModel(istoryNoteRepository.fetchSaveDraftNoteByStoryId(storyId, false), StoryNoteModel.class);

		return new StoryNoteReadResponse(storyNotePublished, draftNote);
	}
}
