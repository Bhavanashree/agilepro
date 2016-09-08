package com.agilepro.commons.controllers.admin;

import com.agilepro.commons.models.admin.DesignationModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IDesignationController.
 */
@RemoteService
public interface IDesignationController
{

	/**
	 * Save.
	 *
	 * @param designationModel
	 *            the designation model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(DesignationModel designationModel);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<DesignationModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param designationModel
	 *            the designation model
	 * @return the base response
	 */
	public BaseResponse update(DesignationModel designationModel);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
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
