package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_USER_SETTING;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.IUserSettingController;
import com.agilepro.commons.models.customer.UserSettingModel;
import com.agilepro.persistence.entity.admin.UserSettingEntity;
import com.agilepro.services.admin.UserSettingService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class UserSettingController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_USER_SETTING)
@RequestMapping("/userSetting")
public class UserSettingController extends BaseController implements IUserSettingController
{
	/**
	 * The user setting service.
	 **/
	@Autowired
	private UserSettingService userSettingService;

	/**
	 * Save.
	 *
	 * @param userSettingModel
	 *            the user setting model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.USER_SETTING_EDIT, UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid UserSettingModel userSettingModel)
	{
		UserSettingEntity userSettingEntity = userSettingService.save(userSettingModel);

		return new BasicSaveResponse(userSettingEntity.getId());
	}

	/**
	 * Update.
	 *
	 * @param userSettingModel
	 *            the user setting model
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.USER_SETTING_EDIT, UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid UserSettingModel userSettingModel)
	{
		userSettingService.update(userSettingModel);

		return new BaseResponse();
	}

	/**
	 * Read all.
	 *
	 * @param userId
	 *            the user id
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.USER_SETTING_VIEW, UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<UserSettingModel> read(@RequestParam(value = "userId") Long userId)
	{
		return new BasicReadResponse<UserSettingModel>(userSettingService.fetchUserSetting(userId));
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Override
	@Authorization(roles = { UserRole.TEST_DELETE_ALL, UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		userSettingService.deleteAll();

		return new BaseResponse();
	}
}
