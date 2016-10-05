package com.agilepro.controller.bug;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_BUG;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
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
import com.agilepro.commons.controllers.bug.IBugController;
import com.agilepro.commons.models.bug.BugModel;
import com.agilepro.services.bug.BugService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

// TODO: Auto-generated Javadoc
/**
 * The Class BugController.
 */
@RestController
@ActionName(ACTION_PREFIX_BUG)
@RequestMapping("/bug")
public class BugController extends BaseController implements IBugController
{

	/**
	 *  The bug service.
	 **/
	@Autowired
	private BugService bugService;

	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.bug.IBugController#save(com.agilepro.commons.models.bug.BugModel)
	 */
	@Override
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid BugModel model)
	{
		bugService.save(model);
		return new BasicSaveResponse();
	}

	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.bug.IBugController#read(java.lang.Long)
	 */
	@Override
	@ActionName(ACTION_TYPE_READ)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<BugModel> read(@PathVariable(PARAM_ID) Long id)
	{
		BugModel model = bugService.fetchFullModel(id, BugModel.class);

		return new BasicReadResponse<BugModel>(model);
	}

	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.bug.IBugController#update(com.agilepro.commons.models.bug.BugModel)
	 */
	@ActionName(ACTION_TYPE_UPDATE)
	@Authorization(entityIdExpression = "parameters[0].id", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestBody @Valid BugModel model)
	{
		bugService.update(model);

		return new BaseResponse();
	}

	/* (non-Javadoc)
	 * @see com.agilepro.commons.controllers.bug.IBugController#delete(java.lang.Long)
	 */
	@ActionName(ACTION_TYPE_DELETE)
	@Authorization(entityIdExpression = "parameters[0]", roles = { UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		bugService.deleteById(id);

		return new BaseResponse();
	}
}
