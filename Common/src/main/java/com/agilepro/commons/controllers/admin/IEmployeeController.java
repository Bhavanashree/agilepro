package com.agilepro.commons.controllers.admin;

import java.util.List;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IEmployeeController.
 * 
 * @author Pritam
 *  */
@RemoteService
public interface IEmployeeController
{
	
	/**
	 * Save.
	 *
	 * @param model the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(EmployeeModel model);
	
	/**
	 * Read.
	 *
	 * @param id the id
	 * @return the basic read response
	 */
	public BasicReadResponse<EmployeeModel> read(Long id);
	
	/**
	 * Fetch employees.
	 *
	 * @param employeeName the employee name
	 * @return the basic read response
	 */
	public BasicReadResponse<List<EmployeeModel>> fetchEmployees(String employeeName);
	
	/**
	 * Update.
	 *
	 * @param model the model
	 * @return the base response
	 */
	public BaseResponse update(EmployeeModel model);
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);
	
	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	public BaseResponse deleteAll();
}
