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
import com.agilepro.controller.response.StoryReleaseReadResponse;
import com.agilepro.persistence.entity.admin.StoryReleaseEntity;
import com.agilepro.persistence.repository.admin.IStoryReleaseRepository;
import com.agilepro.services.project.StoryService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class StoryReleaseService.
 */
@Service
public class StoryReleaseService extends BaseCrudService<StoryReleaseEntity, IStoryReleaseRepository>
{
	/**
	 * The i story release repository.
	 **/
	private IStoryReleaseRepository istoryReleaseRepository;

	/**
	 * The story service.
	 **/
	@Autowired
	private StoryService storyService;

	/**
	 * Instantiates a new story release service.
	 */
	public StoryReleaseService()
	{
		super(StoryReleaseEntity.class, IStoryReleaseRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		istoryReleaseRepository = repositoryFactory.getRepository(IStoryReleaseRepository.class);
	}

	/**
	 * Save story release.
	 *
	 * @param storyReleaseModel
	 *            the story release model
	 * @return the story release entity
	 */
	public StoryReleaseEntity saveStoryRelease(StoryReleaseModel storyReleaseModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			StoryReleaseEntity storyReleaseEntity = null;

			List<Long> storyIds = storyReleaseModel.getStoryIds();

			if(storyIds == null)
			{
				storyReleaseEntity = super.save(storyReleaseModel);
			}
			else
			{
				for(Long stryId : storyIds)
				{
					storyReleaseEntity = super.save(new StoryReleaseModel(stryId, storyReleaseModel.getReleaseId()));
				}
			}

			transaction.commit();

			return storyReleaseEntity;
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving  story release - ", ex);
		}
	}

	/**
	 * Fetch all story release by release and project.
	 *
	 * @param releaseId
	 *            the release id
	 * @param projectId
	 *            the project id
	 * @return the story release read response
	 */
	public StoryReleaseReadResponse fetchAllStoryReleaseByReleaseAndProject(Long releaseId, Long projectId)
	{
		List<BasicStoryInfo> basicStoryInfos = istoryReleaseRepository.fetchStorysByReleaseAndProject(releaseId, projectId);

		Set<Long> storyIds = basicStoryInfos.stream().map(basicInfo -> basicInfo.getId()).collect(Collectors.toSet());

		List<StoryModel> storyModels = storyService.fetchStoriesByProject(projectId);

		List<StoryModel> filterdModels = storyModels.stream().filter(model -> !storyIds.contains(model.getId())).collect(Collectors.toList());

		return new StoryReleaseReadResponse(basicStoryInfos, filterdModels);
	}

	/**
	 * Delete by project id.
	 *
	 * @param storyReleaseModel
	 *            the story release model
	 */
	public void deleteByProjectId(StoryReleaseModel storyReleaseModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Long releaseId = storyReleaseModel.getReleaseId();
			List<Long> projectIds = storyReleaseModel.getProjectIds();

			if(projectIds != null)
			{
				for(Long projectId : projectIds)
				{
					if(istoryReleaseRepository.fetchStorysByReleaseAndProject(releaseId, projectId).size() > 0)
					{
						if(!istoryReleaseRepository.deleteByProjectId(releaseId, projectId))
						{
							throw new IllegalStateException("An error occurred  while deleting story release");
						}
					}
				}
			}
			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while deleting story release - ", ex);
		}
	}

	/**
	 * Delete by story id.
	 *
	 * @param storyReleaseModel
	 *            the story release model
	 */
	public void deleteByStoryId(StoryReleaseModel storyReleaseModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			List<Long> storyIds = storyReleaseModel.getStoryIds();
			
			if(storyIds != null)
			{
				for(Long storyId : storyReleaseModel.getStoryIds())
				{
					if(!istoryReleaseRepository.deleteByStoryId(storyId))
					{
						throw new IllegalStateException("An error occurred  while deleting story release");
					}
				}
			}

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while deleting story release - ", ex);
		}
	}
}
