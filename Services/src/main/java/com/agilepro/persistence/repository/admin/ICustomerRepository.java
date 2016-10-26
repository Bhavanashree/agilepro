package com.agilepro.persistence.repository.admin;

import java.util.Date;
import java.util.List;

import com.agilepro.commons.models.customer.CustomerSearchQuery;
import com.agilepro.commons.models.customer.CustomerSearchResult;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.persistence.repository.annotations.JoinOperator;
import com.yukthi.persistence.repository.annotations.MethodConditions;
import com.yukthi.persistence.repository.annotations.NullCheck;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.annotations.UpdateFunction;
import com.yukthi.persistence.repository.annotations.UpdateOperator;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ICustomerRepository for Customer Entity table.
 *
 * @author Pritam
 */
public interface ICustomerRepository extends IWebutilsRepository<CustomerEntity>
{
	/**
	 * Fetch all customers.
	 *
	 * @return the list
	 */
	public List<CustomerEntity> fetchAllCustomers();
	
	/**
	 * Find customer by pay date.
	 *
	 * @param date
	 *            Date till which due amounts needs to be evaluated
	 * @return the list
	 */
	@MethodConditions(nullChecks = { @NullCheck(field = "lastPayEvalDate", joinOperator = JoinOperator.OR) })
	public List<CustomerEntity> findCustomerByPayDate(@Condition(value = "nextPayEvalDate", op = Operator.LE) Date date);

	/**
	 * Find customer by subdomain.
	 *
	 * @param subDomainPath
	 *            the sub domain path
	 * @return the customer entity
	 */
	public CustomerEntity findCustomerBySubdomain(@Condition(value = "subDomainPath") String subDomainPath);

	/**
	 * Find customer.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@SearchQueryMethod(name = "customerSearch", queryModel = CustomerSearchQuery.class)
	@OrderBy("name")
	@RestrictBySpace
	public List<CustomerSearchResult> findCustomer(SearchQuery searchQuery);

	/**
	 * Find customer lov.
	 *
	 * @return the list
	 */
	@LovQuery(name = "customerList", valueField = "id", labelField = "name")
	@RestrictBySpace
	public List<ValueLabel> findCustomerLov();

	/**
	 * Fetch customer by email.
	 *
	 * @param email
	 *            the email
	 * @return the customer entity
	 */
	@RestrictBySpace
	public CustomerEntity fetchCustomerByEmail(@Condition(value = "email") String email);

	/**
	 * Subtract from due amount.
	 *
	 * @param amount
	 *            the amount
	 * @param customerId
	 *            the customer id
	 * @return true, if successful
	 */
	@UpdateFunction
	public boolean subtractFromDueAmount(@Field(value = "dueAmount", updateOp = UpdateOperator.SUBTRACT) double amount, @Condition("id") long customerId);

	/**
	 * Adds the to due amount.
	 *
	 * @param amount
	 *            the amount
	 * @param customerId
	 *            the customer id
	 * @return true, if successful
	 */
	@UpdateFunction
	public boolean addToDueAmount(@Field(value = "dueAmount", updateOp = UpdateOperator.ADD) double amount, @Condition("id") long customerId);

	/**
	 * Updates customer's invoice evaluation details.
	 *
	 * @param newDueAmount
	 *            the amount
	 * @param lastPayEvalDate
	 *            the last pay evaluate date
	 * @param nextPayEvalDate
	 *            the next pay evaluate date
	 * @param customerId
	 *            the customer id
	 * @param version
	 *            the version
	 * @return true, if successful
	 */
	@UpdateFunction
	public boolean updateCustomer(@Field(value = "dueAmount") double newDueAmount, @Field(value = "lastPayEvalDate") Date lastPayEvalDate, @Field(value = "nextPayEvalDate") Date nextPayEvalDate, @Condition("id") long customerId, @Condition("version") Integer version);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
