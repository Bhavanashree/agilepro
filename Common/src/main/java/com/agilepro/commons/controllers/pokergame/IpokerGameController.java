package com.agilepro.commons.controllers.pokergame;

import java.util.List;

import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IpokerGameController.
 */
@RemoteService
public interface IpokerGameController
{

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(PokerGameModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<PokerGameModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(PokerGameModel model);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

	/**
	 * Fetch all games by project.
	 *
	 * @param projectId
	 *            the project id
	 * @return the basic read response
	 */
	BasicReadResponse<List<PokerGameModel>> fetchAllGamesByProject(Long projectId);
}
