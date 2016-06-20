package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_ADMINUSER;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.admin.AdminUserModel;
import com.agilepro.services.admin.AdminUserService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;
import com.yukthi.webutils.repository.UserEntity;

/**
 * The Class AdminUserController.
 */
@RestController
@ActionName(ACTION_PREFIX_ADMINUSER)
@RequestMapping("/admin")
public class AdminUserController extends BaseController
{

	/**
	 * The user service.
	 */
	@Autowired
	private AdminUserService userService;

	/**
	 * Save.
	 *
	 * @param userModel
	 *            the user model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@Authorization(roles = {UserRole.ADMINISTRATOR})
	public BasicSaveResponse save(@RequestBody @Valid AdminUserModel userModel)
	{

		UserEntity userEntity = userService.save(userModel);

		return new BasicSaveResponse(userEntity.getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	public BasicReadResponse<AdminUserModel> read(@PathVariable(PARAM_ID) Long id)
	{
		// TODO: Convert entity into model

		// AdminUserModel model = userService.fetchFullModel(id,
		// AdminUserModel.class);

		AdminUserModel model = userService.read(id);

		return new BasicReadResponse<AdminUserModel>(model);
	}

	/**
	 * Update.
	 *
	 * @param user
	 *            the user
	 * @return the base response
	 */
	@SuppressWarnings("unused")
	@ActionName(ACTION_TYPE_UPDATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid AdminUserModel user)
	{
		UserEntity entity = userService.update(user);

		return new BaseResponse();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		userService.delete(id);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		userService.deleteAll();

		return new BaseResponse();
	}
}
