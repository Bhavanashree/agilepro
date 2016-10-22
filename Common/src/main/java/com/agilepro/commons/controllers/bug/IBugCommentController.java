package com.agilepro.commons.controllers.bug;

import java.util.List;

import com.agilepro.commons.models.bug.BugCommentsModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

@RemoteService
public interface IBugCommentController
{

	/**
	 * Save.
	 *
	 * @param model the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(BugCommentsModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<BugCommentsModel> read(Long id);
	
//	public BasicReadResponse<List<BugCommentsModel>> fetchAllcomments(Long bug);

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(BugCommentsModel model);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

}
