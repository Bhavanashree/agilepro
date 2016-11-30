package com.agilepro.controller.pokergame;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_POKER_GAME;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.pokergame.IpokerGameController;
import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.agilepro.services.common.Authorization;
import com.agilepro.services.pokergame.PokerGameService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class PokerGameController.
 */
@RestController
@ActionName(ACTION_PREFIX_POKER_GAME)
@RequestMapping("/pokerGame")
public class PokerGameController extends BaseController implements IpokerGameController
{
	/**
	 * service to fetch pokerGame details.
	 */
	@Autowired
	private PokerGameService pokerService;

	/**
	 * Save pokerGame.
	 *
	 * @param model
	 *            the model
	 * @return the pokerGame save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid PokerGameModel model)
	{
		PokerGameEntity entity = pokerService.save(model);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read employee.
	 *
	 * @param id
	 *            the id
	 * @return the employee read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<PokerGameModel> read(@PathVariable(PARAM_ID) Long id)
	{
		PokerGameModel pokerModel = pokerService.fetchFullModel(id, PokerGameModel.class);

		return new BasicReadResponse<PokerGameModel>(pokerModel);
	}

	@Override
	@ActionName(ACTION_TYPE_READ_ALL)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.EMPLOYEE_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<PokerGameModel>> fetchAllGamesByProject(@RequestParam(value = "project", required = true) Long projectId)
	{
		return new BasicReadResponse<List<PokerGameModel>>(pokerService.fetchgames(projectId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.agilepro.commons.controllers.pokergame.IpokerGameController#update(
	 * com.agilepro.commons.models.pokergame.PokerGameModel)
	 */
	@Override
	public BaseResponse update(PokerGameModel model)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.agilepro.commons.controllers.pokergame.IpokerGameController#delete(
	 * java.lang.Long)
	 */
	@Override
	public BaseResponse delete(Long id)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
