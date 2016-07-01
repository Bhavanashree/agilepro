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
	 * Indicates user is administrator.
	 */
	@Label("Administrator") ADMINISTRATOR(true, SETTINGS_GROUP),

	/**
	 * Indicates user is customer super user.
	 */
	@Label("Super User") CUSTOMER_SUPER_USER(true, SETTINGS_GROUP, RESOURCES_GROUP),

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
	 * The BackLog view.
	 */
	@Label("BackLog view") BACKLOG_VIEW,

	/**
	 * The BackLog edit.
	 */
	@Label("BackLog Edit") BACKLOG_EDIT(false, BACKLOG_VIEW),

	/**
	 * The BackLog delete.
	 */
	@Label("BackLog Delete") BACKLOG_DELETE(false, BACKLOG_VIEW),

	/**
	 * The BackLog delete.
	 */
	@Label("BackLog DeleteAll") BACKLOG_DELETE_ALL(false, BACKLOG_VIEW),
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
