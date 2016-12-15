package com.agilepro.services.project;

import org.springframework.stereotype.Service;

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
	public StoryDependencyService()
	{
		super(StoryDependencyEntity.class, IStoryDependencyRepository.class);
	}
}
