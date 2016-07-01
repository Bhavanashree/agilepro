package com.agilepro.services.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.projects.BackLogModel;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.projects.BackLogEntity;
import com.agilepro.persistence.repository.projects.IBackLogRepository;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class BackLogService.
 */
@Service
public class BackLogService extends BaseCrudService<BackLogEntity, IBackLogRepository>
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
	 * Instantiates a new Campaign service.
	 */
	public BackLogService()
	{
		super(BackLogEntity.class, IBackLogRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the campaign entity
	 */
	public BackLogEntity save(BackLogModel model)
	{
		CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		BackLogEntity backlogEntity = WebUtils.convertBean(model, BackLogEntity.class);
		customerService.fetch(customerId);
		super.save(backlogEntity, model);

		return backlogEntity;
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the expenses entity
	 */
	public BackLogEntity update(BackLogModel model)
	{
		if(model == null)
		{
			throw new NullValueException("backlog Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// updating campaign
			BackLogEntity backlog = super.update(model);

			transaction.commit();
			return backlog;
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