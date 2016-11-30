package com.agilepro.commons.controllers.pokergame;

import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

@RemoteService
public interface IpokerGameUserController
{

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(PokerGameUserModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<PokerGameUserModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(PokerGameUserModel model);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);
}
