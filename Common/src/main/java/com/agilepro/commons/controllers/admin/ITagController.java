package com.agilepro.commons.controllers.admin;

import com.agilepro.commons.models.customer.TagModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface ITagController.
 * 
 * @author Pritam
 */
@RemoteService
public interface ITagController
{

	/**
	 * Save.
	 *
	 * @param tagModel
	 *            the tag model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(TagModel tagModel);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<TagModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param tagModel
	 *            the tag model
	 * @return the base response
	 */
	public BaseResponse update(TagModel tagModel);

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
