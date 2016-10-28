package com.agilepro.commons.controllers.admin;

import java.util.List;

import com.agilepro.commons.models.admin.HolidayModel;
import com.agilepro.commons.models.customer.CustomerSettingModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IHolidayController.
 */
@RemoteService
public interface IHolidayController
{

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(HolidayModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<HolidayModel> read(Long id);

	/**
	 * Fetch holiday.
	 *
	 * @return the basic read response
	 */
	public BasicReadResponse<List<HolidayModel>> fetchHoliday();

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(HolidayModel model);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

	public BasicReadResponse<CustomerSettingModel> fetchWeeklyHolidays(String key);
}
