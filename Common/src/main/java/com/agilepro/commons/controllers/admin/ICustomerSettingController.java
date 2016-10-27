package com.agilepro.commons.controllers.admin;

import com.agilepro.commons.models.customer.CustomerSettingModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface ICustomerSettingController.
 */
@RemoteService
public interface ICustomerSettingController
{
	/**
	 * Save.
	 *
	 * @param userSettingModel
	 *            the user setting model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(CustomerSettingModel customerSettingModel);

	/**
	 * Read.
	 *
	 * @param userId
	 *            the user id
	 * @return the basic read response
	 */
	public BasicReadResponse<CustomerSettingModel> read();

	/**
	 * Update.
	 *
	 * @param userSettingModel
	 *            the user setting model
	 * @return the base response
	 */
	public BaseResponse update(CustomerSettingModel customerSettingModel);
}
