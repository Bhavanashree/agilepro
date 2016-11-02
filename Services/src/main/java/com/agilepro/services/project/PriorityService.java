package com.agilepro.services.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.PriorityModel;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.persistence.entity.project.PriorityEntity;
import com.agilepro.persistence.repository.project.IPriorityRepository;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class PriorityService.
 */
@Service
public class PriorityService extends BaseCrudService<PriorityEntity, IPriorityRepository>
{
	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * Used to fetch customer info.
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * Instantiates a new priority service.
	 */
	public PriorityService()
	{
		super(PriorityEntity.class, IPriorityRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model the model
	 * @return the priority entity
	 */
	public PriorityEntity save(PriorityModel model)
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		PriorityEntity priorityEntity = WebUtils.convertBean(model, PriorityEntity.class);
		customerService.fetch(customerId);
		super.save(priorityEntity, model);

		return priorityEntity;
	}

	/**
	 * Update.
	 *
	 * @param model the model
	 * @return the priority entity
	 */
	public PriorityEntity update(PriorityModel model)
	{

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{

			PriorityEntity entity = super.repository.findById(model.getId());

			super.update(model);

			transaction.commit();
			return entity;
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
