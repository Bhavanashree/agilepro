package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_EMPLOYEE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.IEmployeeController;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.services.admin.EmployeeService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class EmployeeController is responsible to save,read,update and delete
 * the employee.
 * 
 * @author bhavana.
 */

@RestController
@ActionName(ACTION_PREFIX_EMPLOYEE)
@RequestMapping("/employee")
public class EmployeeController extends BaseController implements IEmployeeController
{
	/**
	 * service to fetch employees.
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Save employee.
	 *
	 * @param model
	 *            the model
	 * @return the employee save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid EmployeeModel model)
	{
		EmployeeEntity entity = employeeService.save(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read employee.
	 *
	 * @param id
	 *            the id
	 * @return the employee read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<EmployeeModel> read(@PathVariable(PARAM_ID) Long id)
	{
		EmployeeModel employeeModel = employeeService.fetchFullModel(id, EmployeeModel.class);

		return new BasicReadResponse<EmployeeModel>(employeeModel);
	}

	/**
	 * Fetch employees.
	 *
	 * @param employeeName
	 *            the employee name
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<EmployeeModel>> fetchEmployees(@RequestParam(value = "employeeName", required = false) String employeeName)
	{
		return new BasicReadResponse<List<EmployeeModel>>(employeeService.fetchEmployees(employeeName));
	}

	/**
	 * Update employee.
	 *
	 * @param model
	 *            the model
	 * @return the employee update response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid EmployeeModel model)
	{
		employeeService.update(model);

		return new BaseResponse();
	}

	/**
	 * Delete employee.
	 *
	 * @param id
	 *            the id
	 * @return the employee delete response
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		employeeService.deleteById(id);

		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Override
	@Authorization(roles = { UserRole.TEST, UserRole.CUSTOMER_SUPER_USER })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		employeeService.deleteAll();
		return new BaseResponse();
	}
}
