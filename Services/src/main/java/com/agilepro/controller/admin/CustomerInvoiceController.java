package com.agilepro.controller.admin;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_CUSTOMERINVOICEDETAILS;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ;
import static com.agilepro.commons.IAgileproActions.PARAM_ID;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_DELETE_ALL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.customer.priceplan.InvoiceDetails;
import com.agilepro.services.admin.CustomerInvoiceDetailsService;
import com.agilepro.services.common.Authorization;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * The Class CustomerInvoiceController.
 */
@RestController
@ActionName(ACTION_PREFIX_CUSTOMERINVOICEDETAILS)
@RequestMapping("/invoice")
public class CustomerInvoiceController extends BaseController
{

	/**
	 * The logger.
	 */
	private static Logger logger = LogManager.getLogger(CustomerInvoiceController.class);

	/**
	 * Invoice service details.
	 */
	@Autowired
	private CustomerInvoiceDetailsService invoiceService;

	/**
	 * Read invoice detail.
	 *
	 * @param id
	 *            the id of invoice detail
	 * @return the basic read response
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_READ)
	@RequestMapping(value = "/read/{" + PARAM_ID + "}", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<InvoiceDetails> read(@PathVariable(PARAM_ID) Long id)
	{
		logger.trace("Recieved request to fetch invoice with ID - ", id);
		return new BasicReadResponse<InvoiceDetails>(invoiceService.fetchInvoiceDetails(id));
	}

	/**
	 * Delete invoice details.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_DELETE)
	@RequestMapping(value = "/delete/{" + PARAM_ID + "}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse delete(@PathVariable(PARAM_ID) Long id)
	{
		invoiceService.deleteById(id);

		return new BaseResponse("success");
	}

	/**
	 * Deletes all invoices.
	 *
	 * @return the base response
	 */
	@Authorization(roles = { UserRole.ADMINISTRATOR })
	@ActionName(ACTION_TYPE_DELETE_ALL)
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteAll()
	{
		invoiceService.deleteAll();
		return new BaseResponse();
	}
}
