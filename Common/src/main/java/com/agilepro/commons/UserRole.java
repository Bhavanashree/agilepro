package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * Cloud biller user roles.
 * 
 * @author akiran
 */
public enum UserRole
{
	
	/**
	 * Represents settings group under which settings related roles should be
	 * defined.
	 */
	SETTINGS_GROUP(true),

	/**
	 * Represents resources group under which settings related roles should be
	 * defined.
	 **/
	RESOURCES_GROUP(true),

	/**
	 * Represents Sales group under which sales related roles should be defined.
	 **/
	SALES_GROUP(true),

	/**
	 * Represents Project group under which project related roles should be
	 * defined.
	 **/
	PROJECT_GROUP(true),
	
	/** 
	 * The common test delete all for test cases. 
	 **/
	TEST,

	/**
	 * Indicates user is administrator.
	 */
	@Label("Administrator") ADMINISTRATOR(true, SETTINGS_GROUP),

	/**
	 * Indicates user is customer super user.
	 */
	@Label("Super User") CUSTOMER_SUPER_USER(true, SETTINGS_GROUP, RESOURCES_GROUP),

	/**
	 * Indicates client is customer super user.
	 */
	@Label("Client") CLIENT(true),

	/**
	 * Represents resources group under which settings related roles should be
	 * defined.
	 */
	CUSTOMER_GROUP(false),

	/**
	 * The Designation view.
	 */
	@Label("Designation View") DESIGNATION_VIEW,

	/**
	 * The Designation edit.
	 */
	@Label("Designation Edit") DESIGNATION_EDIT(false, DESIGNATION_VIEW, SETTINGS_GROUP),

	/**
	 * The Designation delete.
	 */
	@Label("Designation Delete") DESIGNATION_DELETE(false, DESIGNATION_VIEW),

	/**
	 * The Employee view.
	 */
	@Label("Employee view") EMPLOYEE_VIEW,

	/**
	 * The Employee edit.
	 */
	@Label("Employee Edit") EMPLOYEE_EDIT(false, EMPLOYEE_VIEW, RESOURCES_GROUP),

	/**
	 * The Employee delete.
	 */
	@Label("Employee Delete") EMPLOYEE_DELETE(false, EMPLOYEE_VIEW),

	/**
	 * The Client Price Plan view.
	 */
	@Label("Client Price Plan View") CLIENT_PRICE_PLAN_VIEW,

	/**
	 * The Client Price Plan edit.
	 */
	@Label("Client Price Plan Edit") CLIENT_PRICE_PLAN_EDIT(false, CLIENT_PRICE_PLAN_VIEW, SETTINGS_GROUP),

	/**
	 * The Client Price Plan delete.
	 */
	@Label("Client Price Plan Delete") CLIENT_PRICE_PLAN_DELETE(false, CLIENT_PRICE_PLAN_VIEW),

	/**
	 * The Client Group add.
	 */
	@Label("Client Group Add") CLIENT_GROUP_VIEW,

	/**
	 * The Client Group edit.
	 */
	@Label("Client Group Edit") CLIENT_GROUP_EDIT(false, CLIENT_GROUP_VIEW, RESOURCES_GROUP),

	/**
	 * The client group delete.
	 */
	@Label("Client Group Delete") CLIENT_GROUP_DELETE(false, CLIENT_GROUP_VIEW),

	/**
	 * The Client group delete all.
	 */
	@Label("Client Group Delete all") CLIENT_GROUP_DELETE_ALL(false, CLIENT_GROUP_VIEW),

	/**
	 * The Client add.
	 */
	@Label("Client Add") CLIENT_VIEW,

	/**
	 * The Client edit.
	 */
	@Label("Client Edit") CLIENT_EDIT(false, CLIENT_VIEW, RESOURCES_GROUP),

	/**
	 * The Client delete.
	 */
	@Label("Client Delete") CLIENT_DELETE(false, CLIENT_VIEW),

	/**
	 * The Client delete all.
	 */
	@Label("Client Delete all") CLIENT_DELETE_ALL(false, CLIENT_VIEW),

	/**
	 * The Customer add.
	 */
	@Label("Customer Add") CUSTOMER_VIEW,

	/**
	 * The Customer edit.
	 */
	@Label("Customer Edit") CUSTOMER_EDIT(false, CUSTOMER_VIEW, CUSTOMER_GROUP),

	/**
	 * The Customer delete.
	 */
	@Label("Customer Delete") CUSTOMER_DELETE(false, CUSTOMER_VIEW),

	/**
	 * The Customer delete all.
	 */
	@Label("Customer Delete all") CUSTOMER_DELETE_ALL(false, CUSTOMER_VIEW),

	/**
	 * The Expenses view.
	 */
	@Label("Expenses view") EXPENSES_VIEW,

	/**
	 * The Expenses edit.
	 */
	@Label("Expenses Edit") EXPENSES_EDIT(false, EXPENSES_VIEW, SALES_GROUP),

	/**
	 * The Expenses delete.
	 */
	@Label("Expenses Delete") EXPENSES_DELETE(false, EXPENSES_VIEW),

	/**
	 * The Expenses delete.
	 */
	@Label("Expenses DeleteAll") EXPENSES_DELETE_ALL(false, EXPENSES_VIEW),

	/**
	 * The Campaign view.
	 */
	@Label("Campaign view") CAMPAIGN_VIEW,

	/**
	 * The Campaign edit.
	 */
	@Label("Campaign Edit") CAMPAIGN_EDIT(false, CAMPAIGN_VIEW, SALES_GROUP),

	/**
	 * The Campaign delete.
	 */
	@Label("Campaign Delete") CAMPAIGN_DELETE(false, CAMPAIGN_VIEW),

	/**
	 * The Campaign delete.
	 */
	@Label("Campaign DeleteAll") CAMPAIGN_DELETE_ALL(false, CAMPAIGN_VIEW),
	/**
	 * The CampaignType view.
	 */
	@Label("CampaignType view") CAMPAIGNTYPE_VIEW,

	/**
	 * The CampaignType edit.
	 */
	@Label("CampaignType Edit") CAMPAIGNTYPE_EDIT(false, CAMPAIGN_VIEW, SALES_GROUP),

	/**
	 * The CampaignType delete.
	 */
	@Label("CampaignType Delete") CAMPAIGNTYPE_DELETE(false, CAMPAIGN_VIEW),

	/**
	 * The CampaignType deleteAll.
	 */
	@Label("CampaignType DeleteAll") CAMPAIGNTYPE_DELETE_ALL(false, CAMPAIGN_VIEW),

	/**
	 * The project view.
	 **/
	@Label("Projects view") PROJECT_VIEW,

	/**
	 * The project edit.
	 **/
	@Label("Projects Edit") PROJECT_EDIT(false, PROJECT_VIEW, PROJECT_GROUP),

	/**
	 * The project delete.
	 **/
	@Label("Project Delete") PROJECT_DELETE(false, PROJECT_VIEW),

	/**
	 * The project members view.
	 **/
	@Label("Project Members view") PROJECT_MEMBER_VIEW,

	/**
	 * Represents Members group under which project members related roles should
	 * be defined.
	 **/
	PROJECT_MEMBER_GROUP(true),
	
	/**
	 * The project members edit.
	 **/
	@Label("Project Members Edit") PROJECT_MEMBER_EDIT(false, PROJECT_MEMBER_VIEW, PROJECT_MEMBER_GROUP),

	/**
	 * The project members delete.
	 **/
	@Label("Project Members Delete") PROJECT_MEMBER_DELETE(false, PROJECT_MEMBER_VIEW),

	/**
	 * The tags view.
	 **/
	@Label("Tags View") TAG_VIEW,

	/**
	 * The tags edit.
	 **/
	@Label("Tag Edit") TAG_EDIT(false, TAG_VIEW, PROJECT_GROUP),

	/**
	 * The tags delete.
	 **/
	@Label("Tag Delete") TAG_DELETE(false, TAG_VIEW),
	
	@Label("Release View") REALSE_VIEW,
	
	@Label("Release Edit") REALSE_EDIT(false, REALSE_VIEW, PROJECT_GROUP),
	
	@Label("Release Delete") REALSE_DELETE(false, REALSE_VIEW),
	
	@Label("Project Release View") PROJECT_REALSE_VIEW,
	
	@Label("Project Release Edit") PROJECT_REALSE_EDIT(false, PROJECT_REALSE_VIEW, PROJECT_GROUP),
	
	@Label("Project Release Delete") PROJECT_REALSE_DELETE(false, PROJECT_REALSE_VIEW),
	
	/**
	 * The project property view.
	 **/
	@Label("Project Property view") PROJECT_PROPERTY_VIEW,

	/**
	 * The project property edit.
	 **/
	@Label("Project Property Edit") PROJECT_PROPERTY_EDIT(false, PROJECT_PROPERTY_VIEW, PROJECT_GROUP),

	/**
	 * The project property delete.
	 **/
	@Label("Project Property Delete") PROJECT_PROPERTY_DELETE(false, PROJECT_PROPERTY_VIEW),

	/**
	 * The project property delete all.
	 **/
	@Label("Project Property DeleteAll") PROJECT_PROPERTY_DELETE_ALL(false, PROJECT_PROPERTY_VIEW),

	/**
	 * The project price plan view.
	 **/
	@Label("Project PricePlan view") PROJECT_PRICEPLAN_VIEW,

	/**
	 * The project price plan edit.
	 **/
	@Label("Project PricePlan Edit") PROJECT_PRICEPLAN_EDIT(false, PROJECT_PRICEPLAN_VIEW, PROJECT_GROUP),

	/**
	 * The project price plan delete.
	 **/
	@Label("Project PricePlan Delete") PROJECT_PRICEPLAN_DELETE(false, PROJECT_PRICEPLAN_VIEW),

	/**
	 * The project price plan delete all.
	 **/
	@Label("Project PricePlan DeleteAll") PROJECT_PRICEPLAN_DELETE_ALL(false, PROJECT_PRICEPLAN_VIEW),

	/**
	 * The Lead view.
	 */
	@Label("Lead view") LEAD_VIEW,

	/**
	 * The Lead edit.
	 */
	@Label("Lead Edit") LEAD_EDIT(false, LEAD_VIEW, SALES_GROUP),

	/**
	 * The Lead delete.
	 */
	@Label("Lead Delete") LEAD_DELETE(false, LEAD_VIEW),

	/**
	 * The Lead delete.
	 */
	@Label("Lead DeleteAll") LEAD_DELETE_ALL(false, LEAD_VIEW),

	/** 
	 * The user setting. 
	 **/
	@Label("User Setting") USER_SETTING_VIEW,
	
	/** 
	 * The user setting edit. 
	 **/
	@Label("User setting Edit") USER_SETTING_EDIT(false, USER_SETTING_VIEW, SETTINGS_GROUP),
	
	/** 
	 * The user setting delete. 
	 **/
	@Label("User setting Delete") USER_SETTING_DELETE(false, USER_SETTING_VIEW),

	/**
	 * The Story view.
	 */
	@Label("Story view") BACKLOG_VIEW,

	/**
	 * The Story edit.
	 */
	@Label("Story Edit") BACKLOG_EDIT(false, BACKLOG_VIEW),

	/**
	 * The Story delete.
	 */ 
	@Label("Story Delete") BACKLOG_DELETE(false, BACKLOG_VIEW),

	/**
	 * The Story delete.
	 */
	@Label("Story DeleteAll") BACKLOG_DELETE_ALL(false, BACKLOG_VIEW),
	
	/**
	 * The sprint view. 
	 */
	@Label("Sprint view") SPRINT_VIEW,
	
	/** 
	 * The sprint edit.
	 */
	@Label("Sprint Edit") SPRINT_EDIT(false, SPRINT_VIEW),
	
	/**
	 *  The sprint delete.
	 */
	@Label("Sprint Delete") SPRINT_DELETE(false, SPRINT_VIEW),
	
	/**
	 *  The sprint delete all. 
	 */
	@Label("Sprint DeleteAll") SPRINT_DELETE_ALL(false, SPRINT_VIEW),
	/**
	 * The Priority view. 
	 */
	@Label("Priority view") PRIORITY_VIEW,
	
	/** 
	 * The Priority edit.
	 */
	@Label("Priority Edit") PRIORITY_EDIT(false, PRIORITY_VIEW),
	
	/**
	 *  The Priority delete.
	 */
	@Label("Priority Delete") PRIORITY_DELETE(false, PRIORITY_VIEW),
	
	/**
	 *  The Priority delete all. 
	 */
	@Label("Priority DeleteAll") PRIORITY_DELETE_ALL(false, PRIORITY_VIEW),
	
	/**
	 * The Task view. 
	 */
	@Label("Task view") TASK_VIEW,
	
	/** 
	 * The Task edit.
	 */
	@Label("Task Edit") TASK_EDIT(false, TASK_VIEW),
	
	/**
	 *  The Task Update.
	 */
	@Label("Task Update") TASK_UPDATE(false, TASK_VIEW),
	
	/**
	 *  The Task delete.
	 */
	@Label("Task Delete") TASK_DELETE(false, TASK_VIEW),
	
	/**
	 *  The Task delete all. 
	 */
	@Label("Task DeleteAll") TASK_DELETE_ALL(false, TASK_VIEW),
	;

	/**
	 * Indicates this is an internal role and should not be used for external
	 * assignment.
	 */
	private boolean internal;

	/**
	 * Implicit roles of current role.
	 */
	private UserRole implicitRoles[];

	private UserRole()
	{
		this(false);
	}

	private UserRole(boolean internal, UserRole... implicitRoles)
	{
		this.internal = internal;
		this.implicitRoles = implicitRoles;
	}

	/**
	 * Checks if is indicates this is an internal role and should not be used
	 * for external assignment.
	 *
	 * @return the indicates this is an internal role and should not be used for
	 *         external assignment
	 */
	public boolean isInternal()
	{
		return internal;
	}

	/**
	 * Gets the implicit roles of current role.
	 *
	 * @return the implicit roles of current role
	 */
	public UserRole[] getImplicitRoles()
	{
		return implicitRoles;
	}
}
