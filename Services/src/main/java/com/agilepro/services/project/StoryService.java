package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.StoryResponse;
import com.agilepro.commons.StoryStatus;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.project.BackLogModel;
import com.agilepro.commons.models.project.BackLogPriorityModel;
import com.agilepro.commons.models.project.StoryAndTaskResult;
import com.agilepro.commons.models.project.StoryBulkModel;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.persistence.entity.project.StoryDependencyEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.agilepro.persistence.repository.project.IStoryRepository;
import com.agilepro.services.admin.EmployeeService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class StoryService is responsible to save,read,update and delete the
 * stories.
 */
@Service
public class StoryService extends BaseCrudService<StoryEntity, IStoryRepository>
{
	/**
	 * Priority increment value.
	 */
	private static final Integer PRIORITY_INCREMENT_VALUE = 1;

	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The employee service.
	 **/
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Story dependency service.
	 */
	@Autowired
	private StoryDependencyService storyDependencyService;

	/**
	 * The story repo.
	 **/
	private IStoryRepository storyRepo;

	/**
	 * Instantiates a new StoryService.
	 */
	public StoryService()
	{
		super(StoryEntity.class, IStoryRepository.class);
	}

	/**
	 * Inits the storyRepo, istoryReleaseRepository.
	 */
	@PostConstruct
	private void init()
	{
		storyRepo = repositoryFactory.getRepository(IStoryRepository.class);
	}

	/**
	 * Save new story with increment by one with max priority. For sub story
	 * save set the priority from parent and for rest stories increment priority
	 * by one.
	 * 
	 * @param storyModel
	 *            new model for save.
	 * @return newly saved story object and map having id and priority.
	 */
	public StoryResponse saveStory(StoryModel storyModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			List<BackLogPriorityModel> updatedPriorites = new ArrayList<BackLogPriorityModel>();

			if(storyModel.getParentStoryId() != null)
			{
				Integer parentPriority = storyRepo.fetchOrderOfStory(storyModel.getParentStoryId());

				storyRepo.moveStoriesDown(storyModel.getProjectId(), parentPriority, PRIORITY_INCREMENT_VALUE);

				updatedPriorites = storyRepo.fetchStoriesWherePriorityGreaterThan(storyModel.getProjectId(), parentPriority);

				storyModel.setPriority(parentPriority);
			}
			else
			{
				storyModel.setPriority(storyRepo.getMaxOrder(storyModel.getProjectId()) + PRIORITY_INCREMENT_VALUE);
			}

			StoryEntity entity = super.save(storyModel);
			updatedPriorites.add(new BackLogPriorityModel(entity.getId(), entity.getPriority()));

			Map<Long, Integer> storyIdPriority = updatedPriorites.stream().collect(Collectors.toMap(BackLogPriorityModel::getId, BackLogPriorityModel::getPriority));

			transaction.commit();

			return new StoryResponse(entity.getId(), storyIdPriority);
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving  story - " + storyModel, ex);
		}
	}

	/**
	 * Update priority for the provided id and increment priority for the rest
	 * stories where respective priority is greater than newPriority .
	 * 
	 * @param id
	 *            for which new priority is to be replaced.
	 * @param newPriority
	 *            new priority for update.
	 * @param projectId
	 *            stories of provided project.
	 */
	public void updatePriority(Long id, Integer newPriority, Long projectId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			storyRepo.moveStoriesDown(projectId, newPriority, PRIORITY_INCREMENT_VALUE);

			storyRepo.updatePriority(id, newPriority);

			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating priority - {}");
		}
	}
	
	/**
	 * Update the provided record with the max priority.
	 * 
	 * @param id provided id for max priority.
	 * @param projectId story of provided project.
	 */
	public void updateToMaxPriority(Long id, Long projectId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			storyRepo.updatePriority(id, storyRepo.getMaxOrder(projectId) + 1);
			
			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating to max priority - {}");
		}
	}

	/**
	 * Swap the priority of the given stories.
	 * 
	 * @param idToMoveUp
	 *            the story id which is to be moved up.
	 * @param idToMoveDown
	 *            the story id which is to be moved down.
	 */
	public void swapPriority(Long idToMoveUp, Long idToMoveDown)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			StoryModel storyUp = super.fetchFullModel(idToMoveUp, StoryModel.class);
			
			StoryModel storyDown = super.fetchFullModel(idToMoveDown, StoryModel.class);
			
			storyRepo.updatePriority(storyUp.getId(), -1);

			storyRepo.updatePriority(storyDown.getId(), storyUp.getPriority());
			
			storyRepo.updatePriority(storyUp.getId(), storyDown.getPriority());

			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while swapping priority - {}");
		}
	}

	/**
	 * Update status of the story.
	 * 
	 * @param id for which status is to be updated.
	 * @param status new status to be set.
	 */
	public void updateStoryStatus(Long id, StoryStatus status)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			storyRepo.updateStatus(id, status);

			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating status");
		}
	}
	
	/**
	 * Fetch all stories where (projectId + sprintId) + (projectId +
	 * sprintId(null)) matches.
	 * 
	 * @param projectId
	 *            provided project id.
	 * @param sprintId
	 *            provided sprint id.
	 * @return matching records.
	 */
	public List<StoryModel> fetchStoriesForKanban(Long projectId, Long sprintId)
	{
		List<StoryModel> storymodels = null;

		List<StoryEntity> stories = storyRepo.fetchStoryByProjIdAndSprint(projectId, sprintId);
		List<StoryEntity> unassignedStories = storyRepo.fetchBacklogsForkanaban(projectId);

		if(stories == null)
		{
			stories = new ArrayList<StoryEntity>();
		}

		if(unassignedStories != null)
		{
			stories.addAll(unassignedStories);
		}

		StoryModel storyModel = null;
		EmployeeModel employeeModel = null;

		if(stories.size() > 0)
		{
			storymodels = new ArrayList<StoryModel>(stories.size());

			for(StoryEntity entity : stories)
			{
				storyModel = super.toModel(entity, StoryModel.class);

				if(storyModel.getOwnerId() != null)
				{
					employeeModel = employeeService.fetchFullModel(storyModel.getOwnerId(), EmployeeModel.class);
					if(employeeModel == null)
					{
						throw new IllegalArgumentException(" No employee found with id - {} " + storyModel.getOwnerId());
					}

					storyModel.setPhoto(employeeModel.getPhoto());
				}

				storymodels.add(storyModel);
			}
		}

		return storymodels;
	}

	/**
	 * Fetch story by sprint id.
	 *
	 * @param sprintId
	 *            the sprint id
	 * @return the list
	 */
	public List<StoryModel> fetchStoryBySprintId(Long sprintId)
	{
		List<StoryModel> storymodels = null;

		List<StoryEntity> storyEntities = storyRepo.fetchStoryBySprintId(sprintId);

		StoryModel storyModel = null;
		EmployeeModel employeeModel = null;

		if(storyEntities.size() > 0)
		{
			storymodels = new ArrayList<StoryModel>(storyEntities.size());

			for(StoryEntity entity : storyEntities)
			{
				storyModel = super.toModel(entity, StoryModel.class);

				if(storyModel.getOwnerId() != null)
				{
					employeeModel = employeeService.fetchFullModel(storyModel.getOwnerId(), EmployeeModel.class);
					if(employeeModel == null)
					{
						throw new IllegalArgumentException("No employee found with id - {} " + storyModel.getOwnerId());
					}

					storyModel.setPhoto(employeeModel.getPhoto());
				}

				storymodels.add(storyModel);
			}
		}

		return storymodels;
	}

	/**
	 * Fetch back log where sprint id is null.
	 * 
	 * @param projectId
	 *            provided project id.
	 * @return matching records.
	 */
	public List<BackLogModel> fetchBackLogs(Long projectId)
	{
		List<BackLogModel> backlogModels = storyRepo.fetchBacklogs(projectId);

		for(BackLogModel backlog : backlogModels)
		{
			backlog.setDependencies(storyDependencyService.fetchDependencyIds(backlog.getId()));
		}

		return backlogModels;
	}

	/**
	 * Search by title.
	 *
	 * @param title
	 *            the title
	 * @return the list
	 */
	public List<StoryAndTaskResult> searchByTitle(String title)
	{
		List<StoryEntity> storyEntities = storyRepo.findByTitle(title);
		List<StoryAndTaskResult> storiesmodel = new ArrayList<StoryAndTaskResult>();
		for(StoryEntity story : storyEntities)
		{
			StoryAndTaskResult storyandTask = new StoryAndTaskResult(story.getTitle(), story.getId());
			storiesmodel.add(storyandTask);
		}
		return storiesmodel;
	}

	/**
	 * Save bulk of stories.
	 *
	 * @param storieBulkModels
	 *            the storie bulk models
	 * @param projectId
	 *            the project id
	 * @param parentId
	 *            the parent id
	 */
	public void saveListOfStories(List<StoryBulkModel> storieBulkModels, Long projectId, Long parentId)
	{
		StoryEntity storyEntity = null;

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			for(StoryBulkModel stryBulkModel : storieBulkModels)
			{
				stryBulkModel.setProjectId(projectId);
				stryBulkModel.setParentStoryId(parentId);
				storyEntity = super.save(stryBulkModel);

				if(stryBulkModel.getSubstories() != null)
				{
					saveListOfStories(stryBulkModel.getSubstories(), projectId, storyEntity.getId());
				}
			}
			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while saving  story - ", ex);
		}
	}

	/**
	 * Fetch stories according to the provided projectId.
	 * 
	 * @param projectId
	 *            for fetching the stories.
	 * @return matching stories with the project id.
	 */
	public List<StoryModel> fetchStoriesByProject(Long projectId)
	{
		List<StoryEntity> storyEntities = storyRepo.fetchStoriesByProject(projectId);
		List<StoryModel> storyModels = new ArrayList<StoryModel>();

		if(storyEntities != null)
		{
			storyEntities.forEach(entity -> storyModels.add(super.toModel(entity, StoryModel.class)));
		}
		return storyModels;
	}

	/**
	 * Fetch story by priority order for poker game.
	 * 
	 * @param projectId
	 *            provided project id.
	 * @return matching record in priority order (ascending).
	 */
	public List<StoryModel> fetchStoriesByProjectOrderByPriority(Long projectId)
	{
		List<StoryEntity> storyEntities = storyRepo.fetchStoriesByProjectOrderByPriority(projectId);
		List<StoryModel> storyModels = new ArrayList<StoryModel>();

		if(storyEntities != null)
		{
			storyEntities.forEach(entity -> storyModels.add(super.toModel(entity, StoryModel.class)));
		}
		return storyModels;
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}