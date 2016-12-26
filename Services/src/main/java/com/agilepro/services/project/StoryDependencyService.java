package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.StoryDependencyType;
import com.agilepro.commons.models.project.StoryDependencyModel;
import com.agilepro.persistence.entity.project.StoryDependencyEntity;
import com.agilepro.persistence.repository.project.IStoryDependencyRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * Service to save, delete, update in StoryDependencyTable.
 * 
 * @author Pritam.
 */
@Service
public class StoryDependencyService extends BaseCrudService<StoryDependencyEntity, IStoryDependencyRepository>
{
	/**
	 * Story Dependency repo.
	 */
	private IStoryDependencyRepository storyDependecyRepo;
	
	/**
	 * Constructor to initialize the methods of BaseCrudService.
	 */
	public StoryDependencyService()
	{
		super(StoryDependencyEntity.class, IStoryDependencyRepository.class);
	}
	
	/**
	 * Initialize the story dependency repository.
	 */
	@PostConstruct
	private void init()
	{
		storyDependecyRepo = repositoryFactory.getRepository(IStoryDependencyRepository.class);
	}
	
	/**
	 * Fetch the list of story dependencies for the main story.
	 * 
	 * @param mainStoryId for which dependencies is to be fetched.
	 * @return matching record.
	 */
	public List<StoryDependencyModel> fetchDependencyIds(Long mainStoryId)
	{
		List<StoryDependencyEntity> storyDependencyEntities = storyDependecyRepo.fetchDependenciesIds(mainStoryId);
		List<StoryDependencyModel> storyDependencyModels = new ArrayList<StoryDependencyModel>();
		
		storyDependencyEntities.forEach(entity -> storyDependencyModels.add(super.toModel(entity, StoryDependencyModel.class)));
		
		return storyDependencyModels;
	}
	
	/**
	 * Update dependency type for the provided dependency Story.
	 * 
	 * @param id for which dependency type is to be updated.
	 * @param storyDependencyType new dependency type.
	 * @return true for success update or else false.
	 */
	public boolean updateDependencyType(Long id, StoryDependencyType storyDependencyType)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			boolean result = storyDependecyRepo.updateDependencyType(id, storyDependencyType);;
			
			transaction.commit();
			return result;
		}
		catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating story dependency");
		}
	}
}
