package com.agilepro.commons.controllers.pokergame;

import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IpokerGameController.
 */
@RemoteService
public interface IPokerGameController
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
	 * Controller method to check whether the poker game is started or not for the provided project id.
	 * 
	 * @param projectId provided project id.
	 * @return matching poker game model.
	 */
	public BasicReadResponse<PokerGameModel> isGameStartedForProject(Long projectId);
}
