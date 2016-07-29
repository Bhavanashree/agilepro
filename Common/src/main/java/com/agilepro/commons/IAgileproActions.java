package com.agilepro.commons;

import com.yukthi.webutils.common.IWebUtilsActionConstants;

/**
 * The Interface ICloudbillerActions.
 */
public interface IAgileproActions extends IWebUtilsActionConstants
{
	/**
	 * Action to be used for fetch.
	 */
	public String ACTION_TYPE_FETCH = "fetch";

	/**
	 * Action to be used for save.
	 */
	public String ACTION_TYPE_SAVE = "save";

	/**
	 * The action type send.
	 **/
	public String ACTION_TYPE_SEND = "send";

	/**
	 * Action to be used for read.
	 */
	public String ACTION_TYPE_READ = "read";

	/**
	 * Action to be used for reading all.
	 */
	public String ACTION_TYPE_READ_ALL = "readAll";

	/**
	 * Action to be used for update.
	 */
	public String ACTION_TYPE_UPDATE = "update";

	/**
	 * Action to be used for delete.
	 */
	public String ACTION_TYPE_DELETE = "delete";

	/**
	 * Action to be used for invoke.
	 */
	public String ACTION_TYPE_INVOKE = "invoke";

	/**
	 * Action to be used for delete all.
	 */
	public String ACTION_TYPE_DELETE_ALL = "deleteAll";

	/**
	 * Action to be used for reading variable names.
	 */
	public String ACTION_TYPE_READ_VAR = "readVariables";

	/**
	 * The action type read property type.
	 **/
	public String ACTION_TYPE_READ_PROPERTY_TYPE = "readPropertyType";

	/**
	 * The action type runtime variables.
	 */
	public String ACTION_TYPE_RUNTIME_VARIABLES = "readRuntimeVariables";
	/**
	 * The action type readSprints.
	 */
	public String ACTION_TYPE_READ_SPRINT = "readSprints";
	////////////////////////////////////////////////////////////////////////////

	/**
	 * The action prefix customer.
	 **/
	public String ACTION_PREFIX_CUSTOMER = "customer";

	/**
	 * The action prefix email.
	 **/
	public String ACTION_PREFIX_EMAIL = "email";

	/**
	 * Customer Poc limit.
	 **/
	public int CUSTOMER_POC_LIMIT = 15;

	/**
	 * Client Poc limit.
	 **/
	public int CLIENT_POC_LIMIT = 15;

	/**
	 * The action prefix employee.
	 **/
	public String ACTION_PREFIX_EMPLOYEE = "employee";

	/**
	 * The action prefix payment.
	 **/
	public String ACTION_PREFIX_PAYMENT = "payment";

	/**
	 * The action prefix designation.
	 **/
	public String ACTION_PREFIX_DESIGNATION = "designation";

	/**
	 * The action prefix priceplan.
	 **/
	public String ACTION_PREFIX_PRICEPLAN = "priceplan";

	/**
	 * The action prefix stickynotes.
	 **/
	public String ACTION_PREFIX_STICKYNOTES = "stickynotes";

	/**
	 * The action prefix contacts.
	 **/
	public String ACTION_PREFIX_CONTACTS = "contacts";

	/**
	 * The action prefix lpage.
	 **/
	public String ACTION_PREFIX_LPAGE = "lpage";

	/**
	 * The action prefix story.
	 */
	public String ACTION_PREFIX_STORY = "story";

	/**
	 * The action prefix priority.
	 */
	public String ACTION_PREFIX_PRIORITY = "priority";

	/**
	 * The action prefix password reset.
	 **/
	public String ACTION_PREFIX_PASSWORD_RESET = "passwordReset";

	/**
	 * The action prefix customer.
	 **/
	public String ACTION_PREFIX_CUSTOMERINVOICEDETAILS = "invoice";

	/**
	 * The action prefix adminuser.
	 **/
	public String ACTION_PREFIX_ADMINUSER = "admin";

	/**
	 * The action prefix conversation.
	 **/
	public String ACTION_PREFIX_CONVERSATION = "conversation";

	/**
	 *  The action prefix sprint. 
	 **/
	public String ACTION_PREFIX_SPRINT = "sprint";

	/**
	 * Parameter with name "name".
	 */
	public String PARAM_NAME = "name";

	/**
	 * The param id.
	 **/
	public String PARAM_ID = "id";

	/////////////////////////////////////////////////////////////////////////////
	/**
	 * Customer save action name.
	 */
	public String ACTION_CUSTOMER_SAVE = ACTION_PREFIX_CUSTOMER + "."  +  ACTION_TYPE_SAVE;

	/**
	 * The action email send.
	 **/
	public String ACTION_EMAIL_SEND = ACTION_PREFIX_EMAIL +  "." + ACTION_TYPE_SEND;

	/**
	 * Customer read action name.
	 **/
	public String ACTION_CUSTOMER_READ = ACTION_PREFIX_CUSTOMER + "." + ACTION_TYPE_READ;

	/**
	 * Customer delete action name.
	 **/
	public String ACTION_CUSTOMER_DELETE = ACTION_PREFIX_CUSTOMER + "." + ACTION_TYPE_DELETE;

	/**
	 * The action customer delete all.
	 **/
	public String ACTION_CUSTOMER_DELETE_ALL = ACTION_PREFIX_CUSTOMER + "." + ACTION_TYPE_DELETE_ALL;

	/**
	 * Customer update action name.
	 **/
	public String ACTION_CUSTOMER_UPDATE = ACTION_PREFIX_CUSTOMER + "." + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action payment save.
	 **/
	public String ACTION_PAYMENT_SAVE = ACTION_PREFIX_PAYMENT + "." + ACTION_TYPE_SAVE;

	/**
	 * The action payment update.
	 **/
	public String ACTION_PAYMENT_UPDATE = ACTION_PREFIX_PAYMENT + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action payment read.
	 **/
	public String ACTION_PAYMENT_READ = ACTION_PREFIX_PAYMENT + "." + ACTION_TYPE_READ;

	/**
	 * The action payment delete.
	 **/
	public String ACTION_PAYMENT_DELETE = ACTION_PREFIX_PAYMENT + "." + ACTION_TYPE_DELETE;

	/**
	 * The action payment delete all.
	 **/
	public String ACTION_PAYMENT_DELETE_ALL = ACTION_PREFIX_PAYMENT + "." + ACTION_TYPE_DELETE_ALL;

	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action designation Save.
	 **/
	public String ACTION_DESIGNATION_SAVE = ACTION_PREFIX_DESIGNATION + "." + ACTION_TYPE_SAVE;

	/**
	 * The action designation read.
	 **/
	public String ACTION_DESIGNATION_READ = ACTION_PREFIX_DESIGNATION + "." + ACTION_TYPE_READ;

	/**
	 * The action designation update.
	 **/
	public String ACTION_DESIGNATION_UPDATE = ACTION_PREFIX_DESIGNATION + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action designation delete.
	 **/
	public String ACTION_DESIGNATION_DELETE = ACTION_PREFIX_DESIGNATION + "." + ACTION_TYPE_DELETE;

	/**
	 * The action designation delete all.
	 **/
	public String ACTION_DESIGNATION_DELETE_ALL = ACTION_PREFIX_DESIGNATION + "." + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Price Plan save action name.
	 */
	public String ACTION_PRICEPLAN_SAVE = ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_SAVE;
	/**
	 * Price Plan read action name.
	 */
	public String ACTION_PRICEPLAN_READ = ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_READ;
	/**
	 * Price Plan delete action name.
	 */
	public String ACTION_PRICEPLAN_DELETE = ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_DELETE;
	/**
	 * Price Plan update action name.
	 */
	public String ACTION_PRICEPLAN_UPDATE = ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_UPDATE;
	/**
	 * Price Plan invoke action name.
	 */
	public String ACTION_PRICEPLAN_INVOKE = ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_INVOKE;

	/**
	 * The action priceplan delete all.
	 **/
	public String ACTION_PRICEPLAN_DELETE_ALL = ACTION_PREFIX_PRICEPLAN + "." + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action stickynotes save.
	 **/
	public String ACTION_STICKYNOTES_SAVE = ACTION_PREFIX_STICKYNOTES + "." + ACTION_TYPE_SAVE;

	/**
	 * The action stickynotes read.
	 **/
	public String ACTION_STICKYNOTES_READ = ACTION_PREFIX_STICKYNOTES + "." + ACTION_TYPE_READ;

	/**
	 * The action stickynotes update.
	 **/
	public String ACTION_STICKYNOTES_UPDATE = ACTION_PREFIX_STICKYNOTES + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action stickynotes delete.
	 **/
	public String ACTION_STICKYNOTES_DELETE = ACTION_PREFIX_STICKYNOTES + "." + ACTION_TYPE_DELETE;

	/**
	 * The action stickynotes delete all.
	 **/
	public String ACTION_STICKYNOTES_DELETE_ALL = ACTION_PREFIX_STICKYNOTES + "." + ACTION_TYPE_DELETE_ALL;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action contacts save.
	 **/
	public String ACTION_CONTACTS_SAVE = ACTION_PREFIX_CONTACTS + "." + ACTION_TYPE_SAVE;

	/**
	 * The action contacts read.
	 **/
	public String ACTION_CONTACTS_READ = ACTION_PREFIX_CONTACTS + "." + ACTION_TYPE_READ;

	/**
	 * The action contacts update.
	 **/
	public String ACTION_CONTACTS_UPDATE = ACTION_PREFIX_CONTACTS + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action contacts delete.
	 **/
	public String ACTION_CONTACTS_DELETE = ACTION_PREFIX_CONTACTS + "." + ACTION_TYPE_DELETE;

	/**
	 * The action contacts delete all.
	 **/
	public String ACTION_CONTACTS_DELETE_ALL = ACTION_PREFIX_CONTACTS + "." + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * read action name.
	 */
	public String ACTION_CUSTOMERINVOICEDETAILS_READ = ACTION_PREFIX_CUSTOMERINVOICEDETAILS + "." + ACTION_TYPE_READ;
	/**
	 * delete action name.
	 */
	public String ACTION_CUSTOMERINVOICEDETAILS_DELETE = ACTION_PREFIX_CUSTOMERINVOICEDETAILS + "." + ACTION_TYPE_DELETE;

	/**
	 * The action customerinvoicedetails save.
	 **/
	public String ACTION_CUSTOMERINVOICEDETAILS_SAVE = ACTION_PREFIX_CUSTOMERINVOICEDETAILS + "." + ACTION_TYPE_SAVE;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action useradmin save.
	 **/
	public String ACTION_ADMINUSER_SAVE = ACTION_PREFIX_ADMINUSER + "." + ACTION_TYPE_SAVE;

	/**
	 * The action useradmin read.
	 **/
	public String ACTION_ADMINUSER_READ = ACTION_PREFIX_ADMINUSER + "." + ACTION_TYPE_READ;

	/**
	 * The action useradmin update.
	 **/
	public String ACTION_ADMINUSER_UPDATE = ACTION_PREFIX_ADMINUSER + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action useradmin delete.
	 **/
	public String ACTION_ADMINUSER_DELETE = ACTION_PREFIX_ADMINUSER + "." + ACTION_TYPE_DELETE;

	/**
	 * The action adminuser delete all.
	 **/
	public String ACTION_ADMINUSER_DELETE_ALL = ACTION_PREFIX_ADMINUSER + "." + ACTION_TYPE_DELETE_ALL;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action conversation save.
	 **/
	public String ACTION_CONVERSATION_SAVE = ACTION_PREFIX_CONVERSATION + "." + ACTION_TYPE_SAVE;

	/**
	 * The action conversation read.
	 **/
	public String ACTION_CONVERSATION_READ = ACTION_PREFIX_CONVERSATION + "." + ACTION_TYPE_READ;

	/**
	 * The action conversation update.
	 **/
	public String ACTION_CONVERSATION_UPDATE = ACTION_PREFIX_CONVERSATION + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action conversation delete.
	 **/
	public String ACTION_CONVERSATION_DELETE = ACTION_PREFIX_CONVERSATION + "." + ACTION_TYPE_DELETE;

	/**
	 * The action conversation delete all.
	 **/
	public String ACTION_CONVERSATION_DELETE_ALL = ACTION_PREFIX_CONVERSATION + "." + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action employee save.
	 **/
	public String ACTION_EMPLOYEE_SAVE = ACTION_PREFIX_EMPLOYEE + "." + ACTION_TYPE_SAVE;

	/**
	 * The action employee read.
	 **/
	public String ACTION_EMPLOYEE_READ = ACTION_PREFIX_EMPLOYEE + "." + ACTION_TYPE_READ;

	/**
	 * The action employee update.
	 **/
	public String ACTION_EMPLOYEE_UPDATE = ACTION_PREFIX_EMPLOYEE + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action employee delete.
	 **/
	public String ACTION_EMPLOYEE_DELETE = ACTION_PREFIX_EMPLOYEE + "." + ACTION_TYPE_DELETE;

	/**
	 * The action employee delete all.
	 **/
	public String ACTION_EMPLOYEE_DELETE_ALL = ACTION_PREFIX_EMPLOYEE + "." + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action Story save.
	 **/
	public String ACTION_STORY_SAVE = ACTION_PREFIX_STORY + "." + ACTION_TYPE_SAVE;

	/**
	 * The action Story read.
	 **/
	public String ACTION_STORY_READ = ACTION_PREFIX_STORY + "." + ACTION_TYPE_READ;

	/**
	 *  The action story readall.
	 **/
	public String ACTION_STORY_READALL = ACTION_PREFIX_STORY + "." + ACTION_TYPE_READ_ALL;

	/**
	 *  The action sprint read.
	 **/
	public String ACTION_STORY_READSPRINT = ACTION_PREFIX_STORY + "." + ACTION_TYPE_READ_SPRINT;
	/**
	 * The action backlog update.
	 **/
	public String ACTION_STORY_UPDATE = ACTION_PREFIX_STORY + "." + ACTION_TYPE_UPDATE;

	/**
	 * The action backlog delete.
	 **/
	public String ACTION_STORY_DELETE = ACTION_PREFIX_STORY + "." + ACTION_TYPE_DELETE;

	/**
	 * The action backlog delete all.
	 **/
	public String ACTION_STORY_DELETE_ALL = ACTION_PREFIX_STORY + "." + ACTION_TYPE_DELETE_ALL;

	/**
	 *  The action sprint save. 
	 **/
	public String ACTION_SPRINT_SAVE = ACTION_PREFIX_SPRINT + "." + ACTION_TYPE_SAVE;

	/**
	 *  The action sprint read. 
	 **/
	public String ACTION_SPRINT_READ = ACTION_PREFIX_SPRINT + "." + ACTION_TYPE_READ;

	/** 
	 * The action sprint readall. 
	 **/
	public String ACTION_SPRINT_READALL = ACTION_PREFIX_SPRINT + "." + ACTION_TYPE_READ_ALL;

	/** 
	 * The action sprint update.
	 **/
	public String ACTION_SPRINT_UPDATE = ACTION_PREFIX_SPRINT + "." + ACTION_TYPE_UPDATE;

	/**
	 *  The action sprint delete. 
	 */
	public String ACTION_SPRINT_DELETE = ACTION_PREFIX_SPRINT + "." + ACTION_TYPE_DELETE;

	/**
	 *  The action sprint deleteall. 
	 **/
	public String ACTION_SPRINT_DELETEALL = ACTION_PREFIX_SPRINT + "." + ACTION_TYPE_DELETE;
	
	/**
	 *  The action sprint save. 
	 **/
	public String ACTION_PRIORITY_SAVE = ACTION_PREFIX_PRIORITY + "." + ACTION_TYPE_SAVE;

	/**
	 *  The action sprint read. 
	 **/
	public String ACTION_PRIORITY_READ = ACTION_PREFIX_PRIORITY + "." + ACTION_TYPE_READ;

	/** 
	 * The action sprint readall. 
	 **/
	public String ACTION_PRIORITY_READALL = ACTION_PREFIX_PRIORITY + "." + ACTION_TYPE_READ_ALL;

	/** 
	 * The action sprint update.
	 **/
	public String ACTION_PRIORITY_UPDATE = ACTION_PREFIX_PRIORITY + "." + ACTION_TYPE_UPDATE;

	/**
	 *  The action sprint delete. 
	 */
	public String ACTION_PRIORITY_DELETE = ACTION_PREFIX_PRIORITY+ "." + ACTION_TYPE_DELETE;

	/**
	 *  The action sprint deleteall. 
	 **/
	public String ACTION_PRIORITY_DELETEALL = ACTION_PREFIX_PRIORITY+ "." + ACTION_TYPE_DELETE;
}