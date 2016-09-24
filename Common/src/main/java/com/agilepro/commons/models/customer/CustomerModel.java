package com.agilepro.commons.models.customer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import com.yukthi.validation.annotations.MatchWith;
import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.webutils.common.IWebUtilsCommonConstants;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.LOV;
import com.yukthi.webutils.common.annotations.Label;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.MultilineText;
import com.yukthi.webutils.common.annotations.NonDisplayable;
import com.yukthi.webutils.common.annotations.Password;

/**
 * The Class CustomerModel.
 * 
 * @author Pritam.
 */
@ExtendableModel(name = "Customer")
@Model(name = "Customer")
public class CustomerModel extends AbstractExtendableModel
{
	/**
	 * Id for the customer table.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * Customer name.
	 */
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	private String name;

	/**
	 * Customer Email Id.
	 */
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_EMAIL, message = "Invalid mail id specified")
	@MinLen(5)
	@MaxLen(50)
	@NotEmpty
	private String email;

	/**
	 * Customer Phone number.
	 */
	@NotEmpty
	@MinLen(10)
	@MaxLen(15)
	@Pattern(regexp = IWebUtilsCommonConstants.PATTERN_PHONE, message = "Invalid phone no specified")
	private String phoneNumber;

	/**
	 * Customer Address.
	 */
	@MultilineText
	@MaxLen(500)
	private String address;

	/**
	 * Customer Sales Tax Number.
	 */
	@Label("Sales Tax (ABN) Number")
	private String salesTaxNumber;

	/**
	 * Customer Person or Organization.
	 */
	private CustomerType customerType;

	/**
	 * Role of the customer.
	 */
	private String workType;

	/**
	 * Registration Date(Current Date).
	 */
	@Required
	private Date registrationDate;

	/**
	 * Sub domain path of the customer.
	 */
	@Required
	@NotEmpty
	@Pattern(regexp = "\\w+")
	private String subDomainPath;

	/**
	 * Password from the user.
	 */
	@Password
	@Required
	@MinLen(5)
	@MaxLen(10)
	private String password;

	/**
	 * Confirm password for validation from the user.
	 */
	@Password
	@Required
	@MatchWith(field = "password", message = "Confirm password dosent match with password")
	@MinLen(5)
	@MaxLen(10)
	private String confirmPassword;

	/**
	 * Customer Price Plan id for LOV.
	 */
	@Label("Price Plan")
	@LOV(name = "pricePlanLov")
	@Required
	private long customerPricePlanId;

	/**
	 * Version for update purpose.
	 */
	@NonDisplayable
	private Integer version;

	/**
	 * The due amount.
	 */
	@IgnoreField
	private double dueAmount;

	/**
	 * The variable map.
	 */
	@IgnoreField
	private Map<String, Double> variableMap;

	/**
	 * Customer Point of Contact (having contacts of customer).
	 */
	@IgnoreField
	private List<CustomerPocModel> customerPocModelList;

	/**
	 * Customer related documents.
	 **/
	private List<FileInfo> attachments;
	
	/** 
	 * The notification mail details. 
	 **/
	@NonDisplayable
	@IgnoreField
	private NotificationMailDetail notificationMailDetails;

	/**
	 * Instantiates a new customer model.
	 */
	public CustomerModel()
	{}
	
	/**
	 * Instantiates a new customer model.
	 *
	 * @param name the name
	 * @param email the email
	 * @param phoneNumber the phone number
	 * @param address the address
	 * @param salesTaxNumber the sales tax number
	 * @param customerType the customer type
	 * @param workType the work type
	 * @param registrationDate the registration date
	 * @param password the password
	 * @param confirmPassword the confirm password
	 * @param subDomainPath the sub domain path
	 * @param attachments the attachments
	 */
	public CustomerModel(String name, String email, String phoneNumber, String address, String salesTaxNumber, CustomerType customerType, String workType, Date registrationDate, String password, String confirmPassword, String subDomainPath, List<FileInfo> attachments)
	{
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.salesTaxNumber = salesTaxNumber;
		this.customerType = customerType;
		this.workType = workType;
		this.registrationDate = registrationDate;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.subDomainPath = subDomainPath;
		this.attachments = attachments;
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
	 * Sets the customer Point of Contact (having contacts of customer).
	 *
	 * @param customerPocModel
	 *            the new customer Point of Contact (having contacts of
	 *            customer)
	 */
	public void setCustomerPocModelList(List<CustomerPocModel> customerPocModel)
	{
		this.customerPocModelList = customerPocModel;
	}

	/**
	 * Gets the password from the user.
	 *
	 * @return the password from the user
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the password from the user.
	 *
	 * @param password
	 *            the new password from the user
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Gets the confirm password for validation from the user.
	 *
	 * @return the confirm password for validation from the user
	 */
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	/**
	 * Sets the confirm password for validation from the user.
	 *
	 * @param confirmPassword
	 *            the new confirm password for validation from the user
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Gets the customer Price Plan id for LOV.
	 *
	 * @return the customer Price Plan id for LOV
	 */
	public long getCustomerPricePlanId()
	{
		return customerPricePlanId;
	}

	/**
	 * Sets the customer Price Plan id for LOV.
	 *
	 * @param customerPricePlanId
	 *            the new customer Price Plan id for LOV
	 */
	public void setCustomerPricePlanId(long customerPricePlanId)
	{
		this.customerPricePlanId = customerPricePlanId;
	}

	/**
	 * Gets the variable map.
	 *
	 * @return the variable map
	 */
	public Map<String, Double> getVariableMap()
	{
		return variableMap;
	}

	/**
	 * Sets the variable map..
	 *
	 * @param variableMap
	 *            the new variable map
	 */
	public void setVariableMap(Map<String, Double> variableMap)
	{
		this.variableMap = variableMap;
	}

	/**
	 * Sets the id for the customer table.
	 *
	 * @param id
	 *            the new id for the customer table
	 */
	public void setId(Long id)
	{
		this.id = id;
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
	 * Gets the customer Email Id.
	 *
	 * @return the customer Email Id
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Sets the customer Email Id.
	 *
	 * @param email
	 *            the new customer Email Id
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Gets the customer Phone number.
	 *
	 * @return the customer Phone number
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the customer Phone number.
	 *
	 * @param phoneNumber
	 *            the new customer Phone number
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the customer Address.
	 *
	 * @return the customer Address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Sets the customer Address.
	 *
	 * @param address
	 *            the new customer Address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * Gets the customer Sales Tax Number.
	 *
	 * @return the customer Sales Tax Number
	 */
	public String getSalesTaxNumber()
	{
		return salesTaxNumber;
	}

	/**
	 * Sets the customer Sales Tax Number.
	 *
	 * @param salesTaxNumber
	 *            the new customer Sales Tax Number
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
	 * Gets the sub domain path of the customer.
	 *
	 * @return the sub domain path of the customer
	 */
	public String getSubDomainPath()
	{
		return subDomainPath;
	}

	/**
	 * Sets the sub domain path of the customer.
	 *
	 * @param subDomainPath
	 *            the new sub domain path of the customer
	 */
	public void setSubDomainPath(String subDomainPath)
	{
		this.subDomainPath = subDomainPath;
	}

	/**
	 * Gets the registration Date(Current Date).
	 *
	 * @return the registration Date(Current Date)
	 */
	public Date getRegistrationDate()
	{
		return registrationDate;
	}
	
	/**
	 * Sets the registration date.
	 *
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(Date registrationDate)
	{
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Gets the due amount.
	 *
	 * @return the due amount
	 */
	public double getDueAmount()
	{
		return dueAmount;
	}

	/**
	 * Sets the due amount.
	 *
	 * @param dueAmount
	 *            the new due amount
	 */
	public void setDueAmount(double dueAmount)
	{
		this.dueAmount = dueAmount;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * Gets the customer related documents.
	 *
	 * @return the customer related documents
	 */
	public List<FileInfo> getAttachments()
	{
		return attachments;
	}

	/**
	 * Sets the customer related documents.
	 *
	 * @param attachments
	 *            the new customer related documents
	 */
	public void setAttachments(List<FileInfo> attachments)
	{
		this.attachments = attachments;
	}

	public NotificationMailDetail getNotificationMailDetails()
	{
		return notificationMailDetails;
	}

	public void setNotificationMailDetails(NotificationMailDetail notificationMailDetails)
	{
		this.notificationMailDetails = notificationMailDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj == this)
		{
			return true;
		}

		if(!(obj instanceof CustomerModel))
		{
			return false;
		}

		CustomerModel other = (CustomerModel) obj;
		return (id == other.id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashcode()
	 */
	@Override
	public int hashCode()
	{
		if(id == null)
		{
			return 0;
		}

		return id.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String comma = ","; 
		
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("[");

		builder.append("Id: ").append(id);
		builder.append(comma).append("Name: ").append(name);
		builder.append(comma).append("email: ").append(email);
		builder.append(comma).append("phoneNumber: ").append(phoneNumber);
		builder.append(comma).append("address: ").append(address);
		builder.append(comma).append("salesTaxNumber: ").append(salesTaxNumber);
		builder.append(comma).append("customerType: ").append(customerType);
		builder.append(comma).append("workType: ").append(workType);
		builder.append(comma).append("regDate: ").append(registrationDate);
		builder.append(comma).append("version: ").append(version);

		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
	 */
	@Override
	public Long getId()
	{
		return id;
	}
}
