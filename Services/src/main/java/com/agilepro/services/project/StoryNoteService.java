package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.StoryNoteModel;
import com.agilepro.persistence.entity.project.StoryNoteEntity;
import com.agilepro.persistence.repository.project.IStoryNoteRepository;
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

	/**
	 * Fetch all note by story id.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public List<StoryNoteModel> fetchAllNoteByStoryId(Long storyId)
	{
		List<StoryNoteEntity> storyNoteEntities = istoryNoteRepository.fetchAllPublishedNoteByStoryId(storyId, true);
		
		List<StoryNoteModel> storyNoteModels = new ArrayList<StoryNoteModel>(storyNoteEntities.size());
		
		storyNoteEntities.forEach(entity -> storyNoteModels.add(super.toModel(entity, StoryNoteModel.class)));

		return storyNoteModels;
	}
}
