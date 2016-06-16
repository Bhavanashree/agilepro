package com.agilepro.commons.models.customer;

import java.util.Date;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Model;

/**
 * The Class CustomerInvoiceDetailsSearchQuery.
 */
@Model
public class CustomerInvoiceDetailsSearchQuery
{

	/**
	 * The variable value map.
	 */
	@LOV(name = "customerList")
	@Condition(value = "customer.id", op = Operator.EQ)
	private Long customerId;

	/**
	 * The from date.
	 */
	@Condition(value = "date", op = Operator.GE)
	private Date fromDate;

	/**
	 * End date for invoice search.
	 */
	@Condition(value = "date", op = Operator.LE)
	private Date toDate;

	/**
	 * Instantiates a new customer invoice details search query.
	 */
	public CustomerInvoiceDetailsSearchQuery()
	{}

	/**
	 * Instantiates a new customer invoice details search query.
	 *
	 * @param toDate
	 *            the to date
	 * @param fromDate
	 *            the from date
	 * @param customerId
	 *            the customer id
	 */
	public CustomerInvoiceDetailsSearchQuery(Date toDate, Date fromDate, Long customerId)
	{
		this.toDate = toDate;
		this.fromDate = fromDate;
		this.customerId = customerId;
	}

	public Date getToDate()
	{
		return toDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}

	public Date getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	public Long getcustomerId()
	{
		return customerId;
	}

	public void setCustomer(Long customerId)
	{
		this.customerId = customerId;
	}
}
