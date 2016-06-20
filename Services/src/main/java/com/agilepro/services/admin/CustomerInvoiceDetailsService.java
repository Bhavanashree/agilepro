package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.priceplan.InvoiceDetails;
import com.agilepro.persistence.entity.admin.CustomerInvoiceDetailsEntity;
import com.agilepro.persistence.repository.admin.ICustomerInvoiceDetailsRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * Customer invoice details service.
 * 
 * @author akiran
 */
@Service
public class CustomerInvoiceDetailsService extends BaseCrudService<CustomerInvoiceDetailsEntity, ICustomerInvoiceDetailsRepository>
{
	public CustomerInvoiceDetailsService()
	{
		super(CustomerInvoiceDetailsEntity.class, ICustomerInvoiceDetailsRepository.class);
	}

	/**
	 * Fetches invoice details of specified invoice id.
	 * 
	 * @param id
	 *            Invoice id to be fetched
	 * @return Invoice details.
	 */
	public InvoiceDetails fetchInvoiceDetails(Long id)
	{
		return repository.fetchInvoiceDetails(id);
	}

	/**
	 * Deletes all entity.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
