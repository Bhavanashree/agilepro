package com.agilepro.commons.controllers.project;

import com.agilepro.commons.models.project.PriorityModel;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IPriorityController.
 */
public interface IPriorityController
{
	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(PriorityModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<PriorityModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(PriorityModel model);

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
