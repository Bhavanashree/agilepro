package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_TAG;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import com.agilepro.commons.UserRole;
import com.agilepro.commons.controllers.admin.ITagController;
import com.agilepro.commons.models.customer.TagModel;
import com.agilepro.services.admin.TagService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class TagController.
 * 
 * @author Pritam
 */
@RestController
@ActionName(ACTION_PREFIX_TAG)
@RequestMapping("/tag")
public class TagController extends BaseController implements ITagController
{
	/**
	 * The tags service.
	 **/
	@Autowired
	private TagService tagService;

	/**
	 * Save.
	 *
	 * @param tagModel
	 *            the tags model
	 * @return the basic save response
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.TAG_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid TagModel tagModel)
	{
		return new BasicSaveResponse(tagService.save(tagModel).getId());
	}

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TAG_VIEW, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<TagModel> read(@PathVariable(PARAM_ID) Long id)
	{
		return new BasicReadResponse<TagModel>(tagService.fetchFullModel(id, TagModel.class));
	}

	/**
	 * Update.
	 *
	 * @param tagModel
	 *            the tags model
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.TAG_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid TagModel tagModel)
	{
		tagService.update(tagModel);

		return new BaseResponse();
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.TAG_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		tagService.deleteById(id);
		return new BaseResponse();
	}

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	@Override
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@Authorization(roles = { UserRole.TEST, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		tagService.deleteAll();
		return new BaseResponse();
	}
}
