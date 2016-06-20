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
	 * The meeting room view.
	 */
	@Label("Meeting Room View") MEETING_ROOM_VIEW,

	/**
	 * The meeting room edit.
	 */
	@Label("Meeting Room Edit") MEETING_ROOM_EDIT(false, MEETING_ROOM_VIEW, RESOURCES_GROUP),

	/**
	 * The meeting room delete.
	 */
	@Label("Meeting Room Delete") MEETING_ROOM_DELETE(false, MEETING_ROOM_VIEW),

	/**
	 * The meeting room features view.
	 */
	@Label("Meeting Room features View") MEETING_ROOM_FEATURES_VIEW,

	/**
	 * The meeting room features edit.
	 */
	@Label("Meeting Room features Edit") MEETING_ROOM_FEATURES_EDIT(false, MEETING_ROOM_FEATURES_VIEW),

	/**
	 * The meeting room features delete.
	 */
	@Label("Meeting Room features Delete") MEETING_ROOM_FEATURES_DELETE(false, MEETING_ROOM_FEATURES_VIEW),

	/**
	 * The task view.
	 */
	@Label("Task View") TASK_VIEW,

	/**
	 * The task edit.
	 */
	@Label("Task Edit") TASK_EDIT(false, TASK_VIEW, SETTINGS_GROUP),

	/**
	 * The task delete.
	 */
	@Label("Task Delete") TASK_DELETE(false, TASK_VIEW),

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
	 * The Employee delete.
	 */
	@Label("Employee Delete") DESIGNATION_DELETE_ALL(false, DESIGNATION_VIEW),
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
	 * The Employee delete.
	 */
	@Label("Employee DeleteAll") EMPLOYEE_DELETE_ALL(false, EMPLOYEE_VIEW),

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
	@Label("CampaignType Delete") PROJECT_PROPERTY_DELETE(false, PROJECT_PROPERTY_VIEW),

	/**
	 * The project property delete all.
	 **/
	@Label("CampaignType DeleteAll") PROJECT_PROPERTY_DELETE_ALL(false, PROJECT_PROPERTY_VIEW),

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
