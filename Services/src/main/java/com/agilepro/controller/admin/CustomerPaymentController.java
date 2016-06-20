package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_PAYMENT;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_UPDATE;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;

import javax.validation.Valid;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.CustomerPaymentModel;
import com.agilepro.persistence.entity.admin.CustomerPaymentEntity;
import com.agilepro.services.admin.CustomerPaymentService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.annotations.AttachmentsExpected;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class CustomerPaymentController is responsible to save,read,update and
 * delete the payment.
 *
 */
@RestController
@ActionName(ACTION_PREFIX_PAYMENT)
@RequestMapping("/payment")
public class CustomerPaymentController extends BaseController
{

	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(CustomerPaymentController.class);
	/**
	 * CustomerPayment Service class.
	 */
	@Autowired
	private CustomerPaymentService paymentService;

	/**
	 * Save Customer payment made.
	 *
	 * @param customerpaymentModel
	 *            the customer payment model
	 * @param request
	 *            request
	 * @return the customerPayment save response MultipartHttpServletRequest
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@AttachmentsExpected
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse save(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid CustomerPaymentModel customerpaymentModel, MultipartHttpServletRequest request)
	{
		CustomerPaymentEntity entity = paymentService.savePayment(customerpaymentModel);

		return new BasicSaveResponse(entity.getId());
	}

	/**
	 * Read customerPayment.
	 *
	 * @param id
	 *            the id
	 * @return the customerPayment read response
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<CustomerPaymentModel> read(@PathVariable(PARAM_ID) Long id)
	{
		logger.trace("Sending request to payment tracked with ID ", id);

		CustomerPaymentModel model = paymentService.fetchFullModel(id, CustomerPaymentModel.class);
		return new BasicReadResponse<CustomerPaymentModel>(model);
	}

	/**
	 * Update customerPayment.
	 *
	 * @param customerpaymentModel
	 *            the customerPayment
	 * @param request
	 *            request
	 * @return the customerPayment update response the customer payment model
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@AttachmentsExpected
	@ActionName(ACTION_TYPE_UPDATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse update(@RequestPart(IWebUtilsCommonConstants.MULTIPART_DEFAULT_PART) @Valid CustomerPaymentModel customerpaymentModel, MultipartHttpServletRequest request)
	{
		if(customerpaymentModel.getId() == null || customerpaymentModel.getId() <= 0)
		{
			throw new InvalidRequestParameterException("Invalid id specified for update: " + customerpaymentModel.getId());
		}

		paymentService.updatePayment(customerpaymentModel);

		return new BaseResponse();
	}

	/**
	 * Delete payment.
	 * 
	 * @param id
	 *            id
	 * @return response
	 **/
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		paymentService.deletePayment(id);

		return new BaseResponse("success");
	}

	/**
	 * DeleteAll payment.
	 *
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		paymentService.deleteAll();
		return new BaseResponse();
	}
}