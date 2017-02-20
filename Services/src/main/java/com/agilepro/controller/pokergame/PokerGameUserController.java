package com.agilepro.controller.pokergame;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_POKER_GAME_USER;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_GAME_STATUS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.pokergame.PokerGameStatusModel;
import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.pokergame.PokerGameUserService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
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
	@Autowired
	private PokerGameUserService pokerUserService;

	/**
	 * Save new poker game user.
	 * 
	 * @param pokerGameUserModel new poker game model for persist.
	 * @return save response wrapped with new entity id.
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid PokerGameUserModel pokerGameUserModel)
	{
		return new BasicSaveResponse((pokerUserService.savePokerGameUser(pokerGameUserModel)).getId());
	}
	
	/**
	 * Read poker game status.
	 * 
	 * @param pokerGameId provided poker game id for which status is to be fetched.
	 * @return matching poker game.
	 */
	@ActionName(ACTION_TYPE_READ_GAME_STATUS)
	@RequestMapping(value = "/readPokerGameStatus", method = RequestMethod.GET)
	@Authorization(roles = { UserRole.EMPLOYEE_VIEW, UserRole.EMPLOYEE_EDIT })
	@ResponseBody
	public BasicReadResponse<List<PokerGameStatusModel>> readPokerGameStatus(@RequestParam(value = "pokerGameId") Long pokerGameId)
	{
		return new BasicReadResponse<List<PokerGameStatusModel>>(pokerUserService.readPokerGameStatus(pokerGameId));
	}
}
