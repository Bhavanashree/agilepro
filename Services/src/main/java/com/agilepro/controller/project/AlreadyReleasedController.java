package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_ALREADY_REALSE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import javax.validation.Valid;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.AlreadyReleasedModel;
import com.agilepro.commons.models.customer.ReleaseModel;
import com.agilepro.services.admin.AlreadyReleasedService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

@RestController
@ActionName(ACTION_PREFIX_ALREADY_REALSE)
@RequestMapping("/alreadyRelease")
public class AlreadyReleasedController  extends BaseController
{
	@Autowired
	private AlreadyReleasedService  alreadyReleasedService;
	
	@ActionName(ACTION_TYPE_SAVE)
	@Authorization(roles = { UserRole.ALREADY_REALSE_EDIT, UserRole.CUSTOMER_SUPER_USER })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestBody @Valid AlreadyReleasedModel alreadyReleasedModel)
	{
		return new BasicSaveResponse(alreadyReleasedService.save(alreadyReleasedModel).getId());
	}
}
