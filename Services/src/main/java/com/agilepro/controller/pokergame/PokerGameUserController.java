package com.agilepro.controller.pokergame;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_POKER_GAME_USER;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.pokergame.PokerGameUserService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class PokerGameUserController.
 */
@RestController
@ActionName(ACTION_PREFIX_POKER_GAME_USER)
@RequestMapping("/pokerGameUser")
public class PokerGameUserController extends BaseController
{

	/**
	 * The poker user service.
	 */
	private PokerGameUserService pokerUserService;

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid PokerGameUserModel model)
	{
		pokerUserService.savePokerGameUSer(model, model.getPokerGame(), model.getMembers());
		return new BasicSaveResponse();
	}
}
