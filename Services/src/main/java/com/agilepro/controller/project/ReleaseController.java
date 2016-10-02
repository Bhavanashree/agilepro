package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_REALSE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.Date;
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
import com.agilepro.commons.models.customer.ReleaseModel;
import com.agilepro.services.admin.ReleaseService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class ReleaseController.
 */
@RestController
@ActionName(ACTION_PREFIX_REALSE)
@RequestMapping("/release")
public class ReleaseController extends BaseController
{
	/**
	 * The realse service.
	 **/
	@Autowired
	private ReleaseService realseService;

	private void checkDates(ReleaseModel releaseModel)
	{
		Date startDate = releaseModel.getStartDate();
		Date endDate = releaseModel.getEndDate();

		if((endDate.before(startDate)))
		{
			throw new InvalidRequestParameterException("End date should be after start date");
		}
	}

	/**
	 * Save.
	 *
	 * @param realseModel
	 *            the realse model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ReleaseModel realseModel)
	{
		checkDates(realseModel);

		return new BasicSaveResponse(realseService.save(realseModel).getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.RELEASE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<ReleaseModel> read(@PathVariable(PARAM_ID) Long id)
	{
		return new BasicReadResponse<ReleaseModel>(realseService.fetchFullModel(id, ReleaseModel.class));
	}

	/**
	 * Update.
	 *
	 * @param realseModel
	 *            the realse model
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid ReleaseModel realseModel)
	{
		checkDates(realseModel);

		realseService.update(realseModel);

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
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.RELEASE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		realseService.deleteById(id);
		return new BaseResponse();
	}

	/**
	 * Fetch all release.
	 *
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.RELEASE_VIEW, UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<ReleaseModel>> fetchAllRelease()
	{
		return new BasicReadResponse<List<ReleaseModel>>(realseService.fetchAllRelease());
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@Authorization(roles = { UserRole.TEST, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		realseService.deleteAll();

		return new BaseResponse();
	}
}
