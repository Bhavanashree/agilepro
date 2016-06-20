package com.agilepro.services.admin;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.CustomerPaymentModel;
import com.agilepro.persistence.entity.admin.CustomerPaymentEntity;
import com.agilepro.persistence.repository.admin.ICustomerRepository;
import com.agilepro.persistence.repository.admin.IPaymentRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class CustomerPaymentService is responsible to save,read,update and.
 * delete the payment.It also updates the dueAmount and Amount paid
 *
 */
@Service
public class CustomerPaymentService extends BaseCrudService<CustomerPaymentEntity, IPaymentRepository>
{
	private static Logger logger = LogManager.getLogger(CustomerPaymentService.class);

	/** 
	 * The customer repository.
	 **/
	private ICustomerRepository customerRepository;
	
	public CustomerPaymentService()
	{
		super(CustomerPaymentEntity.class, IPaymentRepository.class);
	}

	@PostConstruct
	private void init()
	{
		customerRepository = super.repositoryFactory.getRepository(ICustomerRepository.class);
	}

	/**
	 * Save the customer payment.
	 *
	 * @param model
	 *            model
	 * @return the payment response
	 */
	public CustomerPaymentEntity savePayment(CustomerPaymentModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			logger.trace("Trying to save payment  - {}", model);

			CustomerPaymentEntity entity = super.save(model);
			
			if(!customerRepository.subtractFromDueAmount(entity.getAmount(), model.getCustomerId()))
			{
				throw new InvalidStateException("Failed to update customer due amount");
			}

			transaction.commit();
			
			return entity;
		} catch(Exception ex)
		{
			logger.error("An error occurred while saving payment - " + model, ex);
			throw new IllegalStateException("An error occurred while saving  payment - " + model, ex);
		}
	}

	/**
	 * Update the customer payment.
	 *
	 * @param model
	 *            customer payment
	 */
	public void updatePayment(CustomerPaymentModel model)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			logger.trace("Trying to update payment - {}", model);

			CustomerPaymentEntity currentPaymentState = super.repository.findById(model.getId());
			
			if(currentPaymentState == null)
			{
				throw new InvalidRequestParameterException("Invalid payment  id specified - " + model.getId());
			}

			//nullify the effect of current state of payment on customer due amount
			if(!customerRepository.addToDueAmount(currentPaymentState.getAmount(), model.getCustomerId()))
			{
				throw new InvalidStateException("Failed to nullify  existing payment amount");
			}

			//update payment model
			super.update(model);
			
			//update the payment amount to customer due amount
			if(!customerRepository.subtractFromDueAmount(model.getAmount(), model.getCustomerId()))
			{
				throw new InvalidStateException("Failed to update payment amount");
			}

			transaction.commit();
		} catch(Exception ex)
		{
			logger.error("An error  occurred while updating payment - " + model, ex);
			throw new IllegalStateException("An error occurred while updating payment - " + model, ex);
		}
	}

	/**
	 * Deletes the payment with specified id and nullifies its effect on customer due amount.
	 * @param paymentId
	 *              id
	 */
	public void deletePayment(long paymentId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			logger.trace("Trying to delete payment - {}", paymentId);

			CustomerPaymentEntity currentPaymentState = super.repository.findById(paymentId);
			
			if(currentPaymentState == null)
			{
				throw new InvalidRequestParameterException("Invalid payment id specified - " + paymentId);
			}
			
			//nullify the effect of current state of payment on customer due amount
			if(!customerRepository.addToDueAmount(currentPaymentState.getAmount(), currentPaymentState.getCustomer().getId()))
			{
				throw new InvalidStateException("Failed to nullify existing payment amount");
			}

			//delete payment
			super.deleteById(paymentId);

			transaction.commit();
		} catch(Exception ex)
		{
			logger.error("An error occurred while deleting  payment - " + paymentId, ex);
			throw new IllegalStateException("An error occurred while deleting payment - " + paymentId, ex);
		}
	}

	/**
	 * Deletes all entity.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}