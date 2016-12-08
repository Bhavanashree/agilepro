package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.project.StoryAndTaskResult;
import com.agilepro.commons.models.project.StoryBulkModel;
import com.agilepro.commons.models.project.StoryModel;
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
	 * Save story.
	 *
	 * @param model
	 *            the model
	 * @return the story entity
	 */
	public StoryEntity saveStory(StoryModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			StoryEntity entity = super.save(model);

			transaction.commit();
			return entity;
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving  story - " + model, ex);
		}
	}

	/**
	 * Update stories.
	 *
	 * @param model
	 *            the model
	 * @return the story entity
	 */
	public int updateStories(StoryModel model)
	{
		if(model == null)
		{
			throw new NullValueException("StoryModel Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{

			super.update(model);
			transaction.commit();

			StoryEntity updateEntity = super.fetch(model.getId());

			return updateEntity.getVersion();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating model - {}", model);
		}
	}

	/**
	 * Fetch all story.
	 *
	 * @param projectId
	 *            the project id
	 * @param sprintId
	 *            the sprint id
	 * @return the list
	 */
	public List<StoryModel> fetchBacklogs(Long projectId, Long sprintId)
	{
		List<StoryModel> storymodels = null;

		List<StoryEntity> stories = storyRepo.fetchStoryByProjIdAndSprint(projectId, sprintId);
		List<StoryEntity> unassignedStories = storyRepo.fetchBacklogs(projectId);

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
	 * Delete expense.
	 *
	 * @param parentStoryId
	 *            the parentStoryId id
	 */
	public void deleteParentId(long parentStoryId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// check for child if den delete it first
			List<StoryEntity> childStories = storyRepo.fetchstoryByParentId(parentStoryId);

			if(childStories.size() > 0)
			{
				for(StoryEntity entity : childStories)
				{
					super.deleteById(entity.getId());
				}
			}

			super.deleteById(parentStoryId);

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while deleting story - " + parentStoryId, ex);
		}
	}

	/**
	 * Fetch stories according to the provided projectId.
	 * 
	 * @param projectId for fetching the stories.
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
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}