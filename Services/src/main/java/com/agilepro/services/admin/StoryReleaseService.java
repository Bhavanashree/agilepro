package com.agilepro.services.admin;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.StoryReleaseModel;
import com.agilepro.commons.models.project.BasicStoryInfo;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.controller.response.ProjectReleaseReadResponse;
import com.agilepro.controller.response.StoryReleaseReadResponse;
import com.agilepro.persistence.entity.admin.StoryReleaseEntity;
import com.agilepro.persistence.repository.admin.IStoryReleaseRepository;
import com.agilepro.services.project.StoryService;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class StoryReleaseService extends BaseCrudService<StoryReleaseEntity, IStoryReleaseRepository>
{
	private IStoryReleaseRepository iStoryReleaseRepository;
	
	@Autowired
	private StoryService storyService;
	
	public StoryReleaseService()
	{
		super(StoryReleaseEntity.class, IStoryReleaseRepository.class);
	}
	
	@PostConstruct
	private void init()
	{
		iStoryReleaseRepository = repositoryFactory.getRepository(IStoryReleaseRepository.class);
	}

	public StoryReleaseEntity save(StoryReleaseModel storyReleaseModel)
	{
		StoryReleaseEntity storyReleaseEntity = null;

		List<Long> storyIds = storyReleaseModel.getStoryIds();
		
		if(storyIds == null)
		{
			return super.save(storyReleaseModel);
		}
		else
		{
			for(Long stryId : storyIds)
			{
				storyReleaseEntity = super.save(new StoryReleaseModel(stryId, storyReleaseModel.getReleaseId()));
			}
		}

		return storyReleaseEntity;
	}
	
	public StoryReleaseReadResponse fetchAllStoryRelease(Long releaseId)
	{
		List<BasicStoryInfo> basicStoryInfos = iStoryReleaseRepository.fetchStorysByRelease(releaseId);

		List<StoryReleaseEntity> storyReleaseEntities = iStoryReleaseRepository.fetchAllStoryRelease();

		Set<Long> storyIds = storyReleaseEntities.stream().map(entity -> entity.getStory().getId()).collect(Collectors.toSet());

		List<StoryModel> storyModels = storyService.fetchAllStories();

		List<StoryModel> filteredModels = storyModels.stream().filter(model -> !storyIds.contains(model.getId())).collect(Collectors.toList());

		return new StoryReleaseReadResponse(basicStoryInfos, filteredModels);
	}
}

