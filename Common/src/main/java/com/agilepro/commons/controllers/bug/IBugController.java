package com.agilepro.commons.controllers.bug;

import com.agilepro.commons.models.bug.BugModel;
import com.agilepro.commons.models.project.StoryModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
/**
 * The Interface IBugController.
 */
@RemoteService
public interface IBugController
{
	
	/**
	 * Save.
	 *
	 * @param model the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(BugModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<BugModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(BugModel model);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

}
