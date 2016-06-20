package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_EMAIL;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SEND;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.models.admin.EmailModel;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Class EmailController.
 */
@RestController
@ActionName(ACTION_PREFIX_EMAIL)
@RequestMapping("/email")
public class EmailController
{
	
	/**
	 * Send.
	 *
	 * @param model the model
	 * @return the basic save response
	 */
	@ActionName(ACTION_TYPE_SEND)
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseBody
	public BasicSaveResponse send(@RequestBody @Valid EmailModel model)
	{
		//emailService.sendMailNow(model);
		
		return new BasicSaveResponse();
	}
}
