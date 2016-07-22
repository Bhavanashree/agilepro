package com.agilepro.commons.controllers.admin;

import com.agilepro.commons.models.customer.UserSettingModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IUserSettingController.
 * 
 * @author Pritam
 */
@RemoteService
public interface IUserSettingController
{
	/**
	 * Save.
	 *
	 * @param userSettingModel
	 *            the user setting model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(UserSettingModel userSettingModel);

	/**
	 * Update.
	 *
	 * @param userSettingModel
	 *            the user setting model
	 * @return the base response
	 */
	public BaseResponse update(UserSettingModel userSettingModel);

	/**
	 * Read all.
	 *
	 * @param userId
	 *            the user id
	 * @return the basic read response
	 */
	public BasicReadResponse<UserSettingModel> readAll(Long userId);
}
