package com.agilepro.commons.controllers.admin;

import com.agilepro.commons.models.customer.CustomerModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * Customer controller interface.
 * 
 * @param <RT>
 *            Request type.
 */
@RemoteService
public interface ICustomerController<RT>
{
	/**
	 * Save.
	 *
	 * @param customer
	 *            the customer
	 * @param request
	 *            the request
	 * @return the basic save response
	 */
	public BasicSaveResponse save(CustomerModel customer, RT request);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<CustomerModel> read(Long id);

	/**
	 * Fetch customer by email.
	 *
	 * @param email
	 *            the email
	 * @return the basic read response
	 */
	public BasicReadResponse<CustomerModel> fetchCustomerByEmail(String email);

	/**
	 * Update.
	 *
	 * @param customer
	 *            the customer
	 * @param request
	 *            the request
	 * @return the base response
	 */
	public BaseResponse update(CustomerModel customer, RT request);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	public BaseResponse deleteAll();
}
