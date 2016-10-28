package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_CUSTOMER_SETTING;
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
import com.agilepro.commons.controllers.admin.ICustomerSettingController;
import com.agilepro.commons.models.customer.CustomerSettingModel;
import com.agilepro.persistence.entity.admin.CustomerSettingEntity;
import com.agilepro.services.admin.CustomerSettingService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class CustomerSettingController.
 */
@RestController
@ActionName(ACTION_PREFIX_CUSTOMER_SETTING)
@RequestMapping("/customerSetting")
public class CustomerSettingController extends BaseController implements ICustomerSettingController
{

	/** 
	 * The customer setting service. 
	 */
	@Autowired
	private CustomerSettingService customerSettingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.agilepro.commons.controllers.admin.ICustomerSettingController#save(
	 * com.agilepro.commons.models.customer.CustomerSettingModel)
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid CustomerSettingModel customerSettingModel)
	{
		CustomerSettingEntity customerSettingEntity = customerSettingService.save(customerSettingModel);

		return new BasicSaveResponse(customerSettingEntity.getId());
	}

	/**
	 * Update.
	 *
	 * @param customerSettingModel
	 *            the customer setting model
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid CustomerSettingModel customerSettingModel)
	{
		customerSettingService.update(customerSettingModel);

		return new BaseResponse();
	}

	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.admin.ICustomerSettingController#read(java.lang.Long)
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.USER_SETTING_VIEW, UserRole.CUSTOMER_SUPER_USER, UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<CustomerSettingModel> read(@PathVariable(PARAM_ID) Long id)
	{
		CustomerSettingModel holidayModel = customerSettingService.fetchFullModel(id, CustomerSettingModel.class);

		return new BasicReadResponse<CustomerSettingModel>(holidayModel);
	}
}
