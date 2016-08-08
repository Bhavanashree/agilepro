package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_DESIGNATION;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.admin.DesignationModel;
import com.agilepro.persistence.entity.admin.DesignationEntity;
import com.agilepro.services.admin.DesignationService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class DesignationController is responsible to save,read,update and delete
 * the designation.
 */
@RestController
@ActionName(ACTION_PREFIX_DESIGNATION)
@RequestMapping("/designation")
public class DesignationController extends BaseController
{

	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(DesignationModel.class);
	/**
	 * services to fetch designations. The designation service.
	 */
	@Autowired
	private DesignationService designationService;

	/**
	 * Save Designation.
	 *
	 * @param designationModel
	 *            designation model
	 * 
	 * @return the Designation save response designationModel designationModel
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.DESIGNATION_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid DesignationModel designationModel)
	{
		DesignationEntity entity = designationService.save(designationModel);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read Designation.
	 *
	 * @param id
	 *            the id
	 * @return the designations read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@Authorization(roles = { UserRole.DESIGNATION_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BasicReadResponse<DesignationModel> read(@PathVariable(PARAM_ID) Long id)
	{
		logger.trace("Sending request to designation tracked with ID ", id);
		DesignationModel model = designationService.fetchFullModel(id, DesignationModel.class);
		return new BasicReadResponse<DesignationModel>(model);
	}

	/**
	 * Update designation.
	 *
	 * @param designationModel
	 *            the designation model
	 * @return the Designation update response the designation model
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.DESIGNATION_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid DesignationModel designationModel)
	{
		designationService.update(designationModel);

		return new BaseResponse();
	}

	/**
	 * Delete Designation.
	 * 
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.DESIGNATION_DELETE, UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		designationService.deleteById(id);

		return new BaseResponse("success");
	}

	/**
	 * DeleteAll Designation.
	 *
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@Authorization(roles = { UserRole.TEST, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		designationService.deleteAll();
		return new BaseResponse();
	}
}
