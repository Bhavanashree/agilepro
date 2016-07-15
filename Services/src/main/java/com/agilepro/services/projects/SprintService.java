package com.agilepro.services.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.projects.SprintModel;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.projects.SprintEntity;
import com.agilepro.persistence.repository.projects.ISprintRepository;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class SprintService.
 */
@Service
public class SprintService extends BaseCrudService<SprintEntity, ISprintRepository>
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
	 * Instantiates a new sprint service.
	 */
	public SprintService()
	{
		super(SprintEntity.class, ISprintRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model the model
	 * @return the sprint entity
	 */
	public SprintEntity save(SprintModel model)
	{
		CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		SprintEntity sprintEntity = WebUtils.convertBean(model, SprintEntity.class);
		customerService.fetch(customerId);
		super.save(sprintEntity, model);

		return sprintEntity;
	}

	/**
	 * Update.
	 *
	 * @param model the model
	 * @return the sprint entity
	 */
	public SprintEntity update(SprintModel model)
	{
		if(model == null)
		{
			throw new NullValueException("Sprint Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// updating campaign
			SprintEntity sprintEntity = super.update(model);

			transaction.commit();
			return sprintEntity;
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
