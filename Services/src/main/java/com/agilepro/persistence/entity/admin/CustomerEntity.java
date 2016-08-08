package com.agilepro.persistence.entity.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.CustomerModel;
import com.agilepro.commons.models.customer.CustomerPocModel;
import com.agilepro.commons.models.customer.CustomerType;
import com.agilepro.services.common.AdminExtension;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Maintains customer information.
 * 
 * @author Pritam
 */
@AdminExtension
@ExtendableEntity(name = "Customer")
@Table(name = "CUSTOMER")
@UniqueConstraints({ @UniqueConstraint(name = "CUST_MAIL_ID", fields = "email"), @UniqueConstraint(name = "SUB_DOMAIN_PATH", fields = "subDomainPath") })
public class CustomerEntity extends WebutilsExtendableEntity
{
	/**
	 * Customer name.
	 */
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	/**
	 * Customer email id.
	 */
	@Column(name = "EMAIL", length = 50, nullable = false)
	private String email;

	/**
	 * Customer phone number.
	 */
	@Column(name = "PHONE_NUMBER", length = 15, nullable = false)
	private String phoneNumber;

	/**
	 * Customer address.
	 */
	@Column(name = "ADDRESS", length = 500)
	private String address;

	/**
	 * Customer sales tax number.
	 */
	@Column(name = "SALES_TAX_NUMBER", length = 50)
	private String salesTaxNumber;

	/**
	 * Customer Person or Organization.
	 */
	@Column(name = "CUSTOMER_TYPE", length = 200)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private CustomerType customerType;

	/**
	 * Domain of the customer.
	 */
	@Column(name = "WORK_TYPE", length = 50)
	private String workType;

	/**
	 * Due amount pending by customer.
	 */
	@Column(name = "DUE_AMOUNT")
	private Double dueAmount;

	/**
	 * Registration Date(Current Date).
	 */
	@Column(name = "REGISTRATION_DATE", nullable = false)
	private Date registrationDate = new Date();

	/**
	 * Customer Point of Contact (having contacts of customer).
	 */
	@Column(name = "CUSTOMER_POC_ENTITY", length = 5000)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private List<CustomerPocModel> customerPocModelList;

	/**
	 * Path for the url.
	 */
	@Column(name = "SUB_DOMAIN_PATH", length = 50, nullable = false)
	private String subDomainPath;

	/**
	 * The customer price plan used for many to one relationship with customer.
	 */
	@ManyToOne
	@Column(name = "CUSTOMER_PRICE_PLAN_ID", nullable = false)
	@PropertyMapping(type = CustomerModel.class, from = "customerPricePlanId", subproperty = "id")
	private CustomerPricePlanEntity customerPricePlan;

	/**
	 * The Customer price plan variable map.
	 */
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	@Column(name = "VARIABLE_MAP")
	private Map<String, Double> variableMap;

	/**
	 * The last pay eval date.
	 */
	@Column(name = "LAST_PAY_EVAL_DATE")
	private Date lastPayEvalDate;

	/**
	 * The next pay eval date.
	 */
	@Column(name = "NEXT_PAY_EVAL_DATE")
	private Date nextPayEvalDate;

	/**
	 * Instantiates a new customer entity.
	 */
	public CustomerEntity()
	{}

	/**
	 * Instantiates a new customer entity.
	 *
	 * @param id
	 *            the id
	 */
	public CustomerEntity(Long id)
	{
		super(id);
	}

	/**
	 * Instantiates a new customer entity.
	 *
	 * @param name
	 *            the name
	 * @param customerPricePlan
	 *            the customer price plan
	 * @param dueAmount
	 *            the due amount
	 * @param variableMap
	 *            the variable map
	 * @param lastPayEvalDate
	 *            the last pay eval date
	 * @param nextPayEvalDate
	 *            the next pay eval date
	 */
	public CustomerEntity(String name, CustomerPricePlanEntity customerPricePlan, Double dueAmount, Map<String, Double> variableMap, Date lastPayEvalDate, Date nextPayEvalDate)
	{
		this.name = name;
		this.customerPricePlan = customerPricePlan;
		this.dueAmount = dueAmount;
		this.variableMap = variableMap;
		this.lastPayEvalDate = lastPayEvalDate;
		this.nextPayEvalDate = nextPayEvalDate;
	}

	/**
	 * Gets the customer price plan.
	 *
	 * @return the customer price plan
	 */
	public CustomerPricePlanEntity getCustomerPricePlan()
	{
		return customerPricePlan;
	}

	/**
	 * Sets the customer price plan.
	 *
	 * @param customerPricePlan
	 *            the new customer price plan
	 */
	public void setCustomerPricePlan(CustomerPricePlanEntity customerPricePlan)
	{
		this.customerPricePlan = customerPricePlan;
	}

	/**
	 * Gets the Customer price plan variable map.
	 *
	 * @return the Customer price plan variable map
	 */
	public Map<String, Double> getVariableMap()
	{
		return variableMap;
	}

	/**
	 * Sets the Customer price plan variable map.
	 *
	 * @param variableMap
	 *            the new Customer price plan variable map
	 */
	public void setVariableMap(Map<String, Double> variableMap)
	{
		this.variableMap = variableMap;
	}

	/**
	 * Gets the last pay eval date.
	 *
	 * @return the last pay eval date
	 */
	public Date getLastPayEvalDate()
	{
		return lastPayEvalDate;
	}

	/**
	 * Sets the last pay eval date.
	 *
	 * @param lastPayEvalDate
	 *            the new last pay eval date
	 */
	public void setLastPayEvalDate(Date lastPayEvalDate)
	{
		this.lastPayEvalDate = lastPayEvalDate;
	}

	/**
	 * Gets the next pay eval date.
	 *
	 * @return the next pay eval date
	 */
	public Date getNextPayEvalDate()
	{
		return nextPayEvalDate;
	}

	/**
	 * Sets the next pay eval date.
	 *
	 * @param nextPayEvalDate
	 *            the new next pay eval date
	 */
	public void setNextPayEvalDate(Date nextPayEvalDate)
	{
		this.nextPayEvalDate = nextPayEvalDate;
	}

	/**
	 * Gets the registration Date(Current Date).
	 *
	 * @return the registration Date(Current Date) the new customer due amount
	 */
	public Date getRegistrationDate()
	{
		return registrationDate;
	}

	/**
	 * Gets the due amount.
	 *
	 * @return the due amount
	 */
	public Double getDueAmount()
	{
		return dueAmount;
	}

	/**
	 * Sets the customer due amount.
	 *
	 * @param dueAmount
	 *            the new customer due amount
	 */
	public void setDueAmount(Double dueAmount)
	{
		this.dueAmount = dueAmount;
	}

	/**
	 * Gets the customer email.
	 *
	 * @return the customer email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Sets the customer email.
	 *
	 * @param email
	 *            the new customer email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Gets the customer phone number.
	 *
	 * @return the customer phone number
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the customer phone number.
	 *
	 * @param phoneNumber
	 *            the new customer phone number
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the customer address.
	 *
	 * @return the customer address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Sets the customer address.
	 *
	 * @param address
	 *            the new customer address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * Gets the customer sales tax number.
	 *
	 * @return the customer sales tax number
	 */
	public String getSalesTaxNumber()
	{
		return salesTaxNumber;
	}

	/**
	 * Sets the customer sales tax number.
	 *
	 * @param salesTaxNumber
	 *            the new customer sales tax number
	 */
	public void setSalesTaxNumber(String salesTaxNumber)
	{
		this.salesTaxNumber = salesTaxNumber;
	}

	/**
	 * Gets the customer Person or Organization.
	 *
	 * @return the customer Person or Organization
	 */
	public CustomerType getCustomerType()
	{
		return customerType;
	}

	/**
	 * Sets the customer Person or Organization.
	 *
	 * @param customerType
	 *            the new customer Person or Organization
	 */
	public void setCustomerType(CustomerType customerType)
	{
		this.customerType = customerType;
	}

	/**
	 * Gets the role of the customer.
	 *
	 * @return the role of the customer
	 */
	public String getWorkType()
	{
		return workType;
	}

	/**
	 * Sets the role of the customer.
	 *
	 * @param workType
	 *            the new role of the customer
	 */
	public void setWorkType(String workType)
	{
		this.workType = workType;
	}

	/**
	 * Gets the customer name.
	 *
	 * @return the customer name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the customer name.
	 *
	 * @param name
	 *            the new customer name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the path for the url.
	 *
	 * @return the path for the url
	 */
	public String getSubDomainPath()
	{
		return subDomainPath;
	}

	/**
	 * Sets the path for the url.
	 *
	 * @param subDomainPath
	 *            the new path for the url
	 */
	public void setSubDomainPath(String subDomainPath)
	{
		this.subDomainPath = subDomainPath;
	}

	/**
	 * Gets the customer Point of Contact (having contacts of customer).
	 *
	 * @return the customer Point of Contact (having contacts of customer)
	 */
	public List<CustomerPocModel> getCustomerPocModelList()
	{
		return customerPocModelList;
	}

	/**
	 * Sets the customer poc model list.
	 *
	 * @param customerPocModel
	 *            the new customer poc model list
	 */
	public void setCustomerPocModelList(List<CustomerPocModel> customerPocModel)
	{
		this.customerPocModelList = customerPocModel;
	}

	/**
	 * Sets the registration Date(Current Date).
	 *
	 * @param registrationDate
	 *            the new registration Date(Current Date)
	 */
	public void setRegistrationDate(Date registrationDate)
	{
		this.registrationDate = registrationDate;
	}
}