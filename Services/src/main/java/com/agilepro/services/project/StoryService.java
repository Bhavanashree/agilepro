package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.StoryResponse;
import com.agilepro.commons.StoryStatus;
import com.agilepro.commons.TaskStatus;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.project.BackLogModel;
import com.agilepro.commons.models.project.BackLogPriorityModel;
import com.agilepro.commons.models.project.StoryBulkModel;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.agilepro.persistence.repository.project.IStoryRepository;
import com.agilepro.services.admin.EmployeeService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
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
	 * Task service.
	 */
	@Autowired
	private TaskService taskService;

	/**
	 * SprintService.
	 */
	@Autowired
	private SprintService sprintService;
	
	/**
	 * Instantiates a new StoryService.
	 */
	public StoryService()
	{
		super(StoryEntity.class, IStoryRepository.class);
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

			Long parentStoryId = storyModel.getParentStoryId();

			// child is saved
			if(parentStoryId != null)
			{
				StoryModel parentStory = super.fetchFullModel(parentStoryId, StoryModel.class);

				// update parent for management story.
				if(!parentStory.getIsManagementStory())
				{
					parentStory.setIsManagementStory(true);
					super.update(parentStory);
				}

				Integer parentPriority = repository.fetchOrderOfStory(parentStoryId);

				repository.moveStoriesDown(storyModel.getProjectId(), parentPriority, PRIORITY_INCREMENT_VALUE);

				updatedPriorites = repository.fetchStoriesWherePriorityGreaterThan(storyModel.getProjectId(), parentPriority);

				storyModel.setPriority(parentPriority);
			}
			else
			{
				storyModel.setPriority(repository.getMaxOrder(storyModel.getProjectId()) + PRIORITY_INCREMENT_VALUE);
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
			repository.moveStoriesDown(projectId, newPriority, PRIORITY_INCREMENT_VALUE);

			repository.updatePriority(id, newPriority);

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
	 * @param id
	 *            provided id for max priority.
	 * @param projectId
	 *            story of provided project.
	 */
	public void updateToMaxPriority(Long id, Long projectId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			repository.updatePriority(id, repository.getMaxOrder(projectId) + 1);

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

			repository.updatePriority(storyUp.getId(), -1);

			repository.updatePriority(storyDown.getId(), storyUp.getPriority());

			repository.updatePriority(storyUp.getId(), storyDown.getPriority());

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
	 * @param id
	 *            for which status is to be updated.
	 * @param status
	 *            new status to be set.
	 */
	public void updateStoryStatus(Long id, StoryStatus status)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			repository.updateStatus(id, status);

			if(status.equals(StoryStatus.COMPLETED))
			{
				taskService.updateTaskStatusByStory(id, TaskStatus.COMPLETED);
			}

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
	 * Update story sprint.
	 * 
	 * @param ids
	 *            for which new sprint is to be set.
	 * @param sprintId
	 *            provided new sprint id to set in story.
	 */
	public void updateStorySprint(Long[] ids, Long sprintId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			SprintEntity sprint = null;
			
			if(sprintId != null)
			{
				sprint = sprintService.fetch(sprintId);
			}

			for(Long id : ids)
			{
				repository.updateSprint(id, sprint);
			}

			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating story sprint");
		}
	}

	/**
	 * Update story management.
	 * 
	 * @param id
	 *            provided story id for update.
	 * @param isManagementStory
	 *            new isManagementStory.
	 */
	public void updateStoryManagement(Long id, boolean isManagementStory)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			repository.updateManagement(id, isManagementStory);
			
			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating story management");
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
		List<StoryModel> storymodels = new ArrayList<StoryModel>();

		List<StoryEntity> stories = repository.fetchStoryByProjIdAndSprint(projectId, sprintId);
		List<StoryEntity> unassignedStories = repository.fetchBacklogsForKanban(projectId);

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
		List<StoryModel> storymodels = new ArrayList<StoryModel>();

		List<StoryEntity> storyEntities = repository.fetchStoryBySprintId(sprintId);

		StoryModel storyModel = null;
		EmployeeModel employeeModel = null;

		if(storyEntities.size() > 0)
		{
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
		List<BackLogModel> backlogModels = repository.fetchBacklogs(projectId);

		for(BackLogModel backlog : backlogModels)
		{
			backlog.setDependencies(storyDependencyService.fetchDependencyIds(backlog.getId()));
		}

		return backlogModels;
	}
	
	/**
	 * Fetch backlogs for drag in task.
	 * 
	 * @param projectId provided project id under which matching backlogs are fetched.
	 * @return matching records.
	 */
	public List<BackLogModel> fetchBacklogsForDrag(Long projectId)
	{
		return repository.fetchBacklogsForDrag(projectId);
	}

	/**
	 * Save bulk of stories.
	 *
	 * @param storiesBulkModels
	 *            the story bulk models
	 * @param projectId
	 *            the project id
	 * @param parentId
	 *            the parent id
	 */
	public void saveListOfStories(List<StoryBulkModel> storiesBulkModels, Long projectId, Long parentId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Integer maxPriority = repository.getMaxOrder(projectId);
			
			for(StoryBulkModel stryBulkModel : storiesBulkModels)
			{
				stryBulkModel.setProjectId(projectId);
				stryBulkModel.setParentStoryId(parentId);
				stryBulkModel.setPriority(maxPriority + PRIORITY_INCREMENT_VALUE);
				stryBulkModel.setIsManagementStory(CollectionUtils.isNotEmpty(stryBulkModel.getSubstories()));
				
				StoryEntity storyEntity = super.save(stryBulkModel);

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
		List<StoryEntity> storyEntities = repository.fetchStoriesByProject(projectId);
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
		List<StoryEntity> storyEntities = repository.fetchStoriesByProjectOrderByPriority(projectId);
		List<StoryModel> storyModels = new ArrayList<StoryModel>();

		if(storyEntities != null)
		{
			storyEntities.forEach(entity -> storyModels.add(super.toModel(entity, StoryModel.class)));
		}
		return storyModels;
	}

	/**
	 * Delete and update the parent story for 0 child's.
	 * 
	 * @param id
	 *            provided id for delete.
	 */
	public void deleteAndUpdateManagementStory(Long id)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			StoryEntity parentStoryEntity = super.fetch(id).getParentStory();

			super.deleteById(id);

			if(parentStoryEntity != null && repository.storyHasChilds(parentStoryEntity.getId()) == 0)
			{
				StoryModel parentStoryModel = super.fetchFullModel(parentStoryEntity.getId(), StoryModel.class);
				parentStoryModel.setIsManagementStory(false);
				super.update(parentStoryModel);
			}

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while deleteing story - ", ex);
		}
	}

	/**
	 * Deletes all records.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}