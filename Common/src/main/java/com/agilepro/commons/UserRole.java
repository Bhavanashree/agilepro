package com.agilepro.commons;

import com.agilepro.commons.models.projects.SprintModel;
import com.yukthi.webutils.common.annotations.Label;

// TODO: Auto-generated Javadoc
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
	 * The Story view.
	 */
	@Label("Story view") STORY_VIEW,

	/**
	 * The Story edit.
	 */
	@Label("Story Edit") STORY_EDIT(false, STORY_VIEW),

	/**
	 * The Story delete.
	 */
	@Label("Story Delete") STORY_DELETE(false, STORY_VIEW),

	/**
	 * The Story delete.
	 */
	@Label("Story DeleteAll") STORY_DELETE_ALL(false, STORY_VIEW),
	
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
	 * The sprint view. 
	 */
	@Label("Priority view") PRIORITY_VIEW,
	
	/** 
	 * The sprint edit.
	 */
	@Label("Priority Edit") PRIORITY_EDIT(false, PRIORITY_VIEW),
	
	/**
	 *  The sprint delete.
	 */
	@Label("Priority Delete") PRIORITY_DELETE(false, PRIORITY_VIEW),
	
	/**
	 *  The sprint delete all. 
	 */
	@Label("Priority DeleteAll") PRIORITY_DELETE_ALL(false, PRIORITY_VIEW),
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

	/**
	 * Instantiates a new user role.
	 */
	private UserRole()
	{
		this(false);
	}

	/**
	 * Instantiates a new user role.
	 *
	 * @param internal the internal
	 * @param implicitRoles the implicit roles
	 */
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
