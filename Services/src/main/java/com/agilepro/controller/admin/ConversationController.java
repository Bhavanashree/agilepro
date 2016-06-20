package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_CONVERSATION;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.admin.ConversationModel;
import com.agilepro.persistence.entity.admin.ConversationEntity;
import com.agilepro.services.admin.ConversationService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class ConversationController.
 */
@RestController
@ActionName(ACTION_PREFIX_CONVERSATION)
@RequestMapping("/conversation")
public class ConversationController
{

	/**
	 * The conversation service.
	 */
	@Autowired
	private ConversationService conversationService;

	/**
	 * Save.
	 *
	 * @param conversationmodel
	 *            the conversationmodel
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.ADMINISTRATOR})
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid ConversationModel conversationmodel)
	{

		ConversationEntity entity = conversationService.save(conversationmodel);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ResponseBody
	public BasicReadResponse<ConversationModel> read(@PathVariable(PARAM_ID) Long id)
	{
		// TODO: Convert entity into model

		ConversationModel model = conversationService.fetchFullModel(id, ConversationModel.class);

		return new BasicReadResponse<ConversationModel>(model);
	}

	/**
	 * Update.
	 *
	 * @param user
	 *            the user
	 * @return the base response
	 */
	@SuppressWarnings("unused")
	@ActionName(ACTION_TYPE_UPDATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid ConversationModel user)
	{
		ConversationEntity entity = conversationService.update(user);

		return new BaseResponse();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		conversationService.deleteById(id);
		return new BaseResponse("success");
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		conversationService.deleteAll();

		return new BaseResponse();
	}
}
