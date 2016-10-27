package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_HOLIDAY;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.IHolidayController;
import com.agilepro.commons.models.admin.HolidayModel;
import com.agilepro.persistence.entity.admin.HolidayEntity;
import com.agilepro.services.admin.HolidayService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class HolidayController.
 */

@RestController
@ActionName(ACTION_PREFIX_HOLIDAY)
@RequestMapping("/holiday")
public class HolidayController extends BaseController implements IHolidayController
{

	/**
	 * The holiday service.
	 **/
	@Autowired
	private HolidayService holidayService;

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = {UserRole.HOLIDAY_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid HolidayModel model)
	{
		HolidayEntity entity = holidayService.save(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read the Holiday.
	 *
	 * @param id
	 *            id of HolidayModel
	 * 
	 * @return the HolidayModel read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.HOLIDAY_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<HolidayModel> read(@PathVariable(PARAM_ID) Long id)
	{
		HolidayModel holidayModel = holidayService.fetchFullModel(id, HolidayModel.class);

		return new BasicReadResponse<HolidayModel>(holidayModel);
	}

	@Override
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<HolidayModel>> fetchHoliday()
	{
		return new BasicReadResponse<List<HolidayModel>>(holidayService.fetchHolidays());
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = {UserRole.HOLIDAY_EDIT, UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid HolidayModel model)
	{
		if(model.getId() == null || model.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + model.getId());
		}

		holidayService.update(model);

		return new BaseResponse();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.HOLIDAY_DELETE, UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		holidayService.deleteById(id);

		return new BaseResponse();
	}
}
