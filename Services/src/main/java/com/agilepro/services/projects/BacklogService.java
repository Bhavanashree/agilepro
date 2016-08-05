package com.agilepro.services.projects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.projects.BacklogModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.entity.projects.BacklogEntity;
import com.agilepro.persistence.repository.projects.IBacklogRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class StoryService is responsible to save,read,update and delete the
 * stories.
 */
@Service
public class BacklogService extends BaseCrudService<BacklogEntity, IBacklogRepository>
{

	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The story repo.
	 **/
	private IBacklogRepository storyRepo;

	/**
	 * Instantiates a new StoryService.
	 */
	public BacklogService()
	{
		super(BacklogEntity.class, IBacklogRepository.class);
	}

	/**
	 * Save story.
	 *
	 * @param model
	 *            the model
	 * @return the story entity
	 */
	public BacklogEntity saveStory(BacklogModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{

			BacklogEntity entity = super.save(model);

			transaction.commit();

			return entity;
		} catch(Exception ex)
		{

			throw new IllegalStateException("An error occurred while saving  story - " + model, ex);
		}
	}

	/**
	 * Update stories.
	 *
	 * @param model
	 *            the model
	 * @return the story entity
	 */
	public BacklogEntity updateStories(BacklogModel model)
	{
		if(model == null)
		{
			throw new NullValueException("StoryModel Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// updating campaign
			BacklogEntity story = super.update(model);

			transaction.commit();
			return story;
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
	 * @param storyTitle
	 *            the story title
	 * @return the list
	 */
	public List<BacklogModel> fetchAllStory(String storyTitle)
	{
		List<BacklogModel> storymodel = null;
		storyRepo = repositoryFactory.getRepository(IBacklogRepository.class);
		List<BacklogEntity> storyEntity = storyRepo.fetchAllStory(storyTitle);
		if(storyEntity != null)
		{
			storymodel = new ArrayList<BacklogModel>(storyEntity.size());
			for(BacklogEntity entity : storyEntity)
			{
				storymodel.add(super.toModel(entity, BacklogModel.class));
			}
		}

		return storymodel;
	}

	/**
	 * Fetch story by sprint id.
	 *
	 * @param sprintId
	 *            the sprint id
	 * @return the list
	 */
	public List<BacklogModel> fetchStoryBySprintId(Long sprintId)
	{
		List<BacklogModel> storymodel = null;
		storyRepo = repositoryFactory.getRepository(IBacklogRepository.class);

		List<BacklogEntity> storyEntity = storyRepo.fetchStoryBySprintId(sprintId);
		if(storyEntity != null)
		{
			storymodel = new ArrayList<BacklogModel>(storyEntity.size());
			for(BacklogEntity entity : storyEntity)
			{
				storymodel.add(super.toModel(entity, BacklogModel.class));
			}
		}

		return storymodel;
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

			BacklogEntity backlogEntity = super.repository.findById(parentStoryId);

			if(backlogEntity == null)
			{
				throw new InvalidRequestParameterException("Invalid parentStoryId id specified - " + parentStoryId);
			}

			Long parentId = backlogEntity.getParentStoryId();

			// delete backlogEntity
			super.deleteById(backlogEntity.getId());

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while deleting expenses - " + parentStoryId, ex);
		}
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}