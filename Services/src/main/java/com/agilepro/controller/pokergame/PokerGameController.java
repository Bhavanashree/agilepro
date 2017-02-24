package com.agilepro.controller.pokergame;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_POKER_GAME;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_IS_POKER_GAME_STARTED;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.pokergame.PokerGameService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * PokerGameController to responsible for receiving the request from ui and
 * sending back the response.
 * 
 * @author Pritam.
 */
@RestController
@ActionName(ACTION_PREFIX_POKER_GAME)
@RequestMapping("/pokerGame")
public class PokerGameController extends BaseController
{
	/**
	 * service to fetch pokerGame details.
	 */
	@Autowired
	private PokerGameService pokerService;

	/**
	 * Save new poker game for a story or bug.
	 * 
	 * @param pokerGameModel model object from ui for save.
	 * @return basic save response.
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.EMPLOYEE_VIEW })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid PokerGameModel pokerGameModel)
	{
		return new BasicSaveResponse((pokerService.saveNewGame(pokerGameModel)).getId());
	}

	/**
	 * Reads the whether the poker game is started or not for provided project id.
	 * 
	 * @param projectId to check whether the game is started or not.
	 * @param activeUserId to check whether the user has joined the game or not. 
	 * @return response wrapped with matching poker game model or else return null.
	 */
	@ActionName(ACTION_TYPE_IS_POKER_GAME_STARTED)
	@RequestMapping(value = "/isPokerGameStarted", method = RequestMethod.GET)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@ResponseBody
	public BasicReadResponse<PokerGameModel> isGameStartedForProject(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "userId") Long activeUserId)
	{
		return new BasicReadResponse<PokerGameModel>(pokerService.isGameStarted(projectId, activeUserId)) ;
	}
}

