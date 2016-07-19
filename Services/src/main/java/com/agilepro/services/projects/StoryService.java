package com.agilepro.services.projects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.projects.StoryModel;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.projects.StoryEntity;
import com.agilepro.persistence.repository.projects.IStoryRepository;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class StoryService is responsible to save,read,update and delete the
 * stories.
 */
@Service
public class StoryService extends BaseCrudService<StoryEntity, IStoryRepository>
{

	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * Used to fetch customer info.
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * Instantiates a new Campaign service.
	 */
	public StoryService()
	{
		super(StoryEntity.class, IStoryRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the campaign entity
	 */
	public StoryEntity save(StoryModel model)
	{
		CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		StoryEntity backlogEntity = WebUtils.convertBean(model, StoryEntity.class);
		customerService.fetch(customerId);
		super.save(backlogEntity, model);

		return backlogEntity;
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the back log entity
	 */
	public StoryEntity update(StoryModel model)
	{

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{

			StoryEntity currentExpenseState = super.repository.findById(model.getParentStoryId());
			super.update(model);

			transaction.commit();
			return currentExpenseState;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating model - {}", model);
		}
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

			throw new IllegalStateException("An error occurred while saving  payment - " + model, ex);
		}
	}

	/**
	 * Update story.
	 *
	 * @param model
	 *            the model
	 */
	public void updateStory(StoryModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			super.repository.findById(model.getId());

			super.update(model);
			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while updating expenses amount - " + model, ex);
		}
	}

	/**
	 * Fetch all story.
	 *
	 * @param storyTitle
	 *            the story title
	 * @return the list
	 */
	public List<StoryModel> fetchAllStory(String storyTitle)
	{
		List<StoryModel> storymodel = null;
		IStoryRepository storyRepo = repositoryFactory.getRepository(IStoryRepository.class);
		List<StoryEntity> storyEntity = storyRepo.fetchAllStory(storyTitle);
		if(storyEntity != null)
		{
			storymodel = new ArrayList<StoryModel>(storyEntity.size());
			for(StoryEntity entity : storyEntity)
			{
				storymodel.add(super.toModel(entity, StoryModel.class));
			}
		}

		return storymodel;
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}