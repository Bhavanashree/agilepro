package com.agilepro.services.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.TaskModel;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.project.TaskEntity;
import com.agilepro.persistence.repository.project.ITaskRepository;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class TaskService.
 */
@Service
public class TaskService extends BaseCrudService<TaskEntity, ITaskRepository>
{
	/**
	 * 
	 * /** Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * Used to fetch customer info.
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * Instantiates a new sprint service.
	 */
	public TaskService()
	{
		super(TaskEntity.class, ITaskRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the sprint entity
	 */
	public TaskEntity save(TaskModel model)
	{
		CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		TaskEntity taskEntity = WebUtils.convertBean(model, TaskEntity.class);
		customerService.fetch(customerId);

		super.save(taskEntity, model);

		return taskEntity;
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the sprint entity
	 */
	public TaskEntity update(TaskModel model)
	{
		if(model == null)
		{
			throw new NullValueException("task Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// updating campaign
			TaskEntity taskEntity = super.update(model);
			transaction.commit();
			return taskEntity;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating model - {}", model);
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
