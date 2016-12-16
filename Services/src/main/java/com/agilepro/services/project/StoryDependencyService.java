package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.StoryDependencyModel;
import com.agilepro.persistence.entity.project.StoryDependencyEntity;
import com.agilepro.persistence.repository.project.IStoryDependencyRepository;
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
	
	public StoryDependencyService()
	{
		super(StoryDependencyEntity.class, IStoryDependencyRepository.class);
	}
	
	@PostConstruct
	private void init()
	{
		storyDependecyRepo = repositoryFactory.getRepository(IStoryDependencyRepository.class);
	}
	
	public List<StoryDependencyModel> fetchDependencyIds(Long mainStoryId)
	{
		List<StoryDependencyEntity> storyDependencyEntities = storyDependecyRepo.fetchDependenciesIds(mainStoryId);
		List<StoryDependencyModel> storyDependencyModels = new ArrayList<StoryDependencyModel>();
		
		storyDependencyEntities.forEach(entity -> storyDependencyModels.add(super.toModel(entity, StoryDependencyModel.class)));
		
		return storyDependencyModels;
	}
}
