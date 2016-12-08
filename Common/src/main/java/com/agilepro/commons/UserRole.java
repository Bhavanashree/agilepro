package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * Agile Pro user roles.
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
	 * The common test role which can be used only by test cases.
	 **/
	TEST_DELETE_ALL(true),

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
	 * Represents Members group under which project members related roles should
	 * be defined.
	 **/
	PROJECT_MEMBER_GROUP(true),
	
	/**
	 * The project members view.
	 **/
	@Label("Project Members view") PROJECT_MEMBER_VIEW,

	/**
	 * The project members edit.
	 **/
	@Label("Project Members Edit") PROJECT_MEMBER_EDIT(false, PROJECT_MEMBER_VIEW, PROJECT_MEMBER_GROUP),

	/**
	 * The project members delete.
	 **/
	@Label("Project Members Delete") PROJECT_MEMBER_DELETE(false, PROJECT_MEMBER_VIEW),
	
	/** 
	 * The project team view.
	 **/
	@Label("Project Team view") PROJECT_TEAM_VIEW,
	
	/**
	 * The project team edit.
	 **/
	@Label("Project Team Edit") PROJECT_TEAM_EDIT(false, PROJECT_TEAM_VIEW, PROJECT_MEMBER_GROUP),
	
	/** 
	 * The project team delete.
	 **/
	@Label("Project Team Delete") PROJECT_TEAM_DELETE(false, PROJECT_TEAM_VIEW),
	
	/**
	 * Represents scrum meeting group under which scrum meeting related roles should
	 * be defined.
	 **/
	SCRUM_MEETING_GROUP(true),
	
	/**
	 * The scrum meeting view.
	 **/
	@Label("Scrum Meeting view") SCRUM_MEETING_VIEW,

	/**
	 * The scrum meeting edit.
	 **/
	@Label("Scrum Meeting Edit") SCRUM_MEETING_EDIT(false, SCRUM_MEETING_VIEW, SCRUM_MEETING_GROUP),

	/**
	 * The scrum meeting delete.
	 **/
	@Label("Scrum Meeting Delete") SCRUM_MEETING_DELETE(false, SCRUM_MEETING_VIEW),
	
	/**
	 * The scrum meeting conversation view.
	 **/
	@Label("Scrum Meeting Conversation view") SCRUM_MEETING_CONVERSATION_VIEW,

	/**
	 * The scrum meeting conversation edit.
	 **/
	@Label("Scrum Meeting Conversation Edit") SCRUM_MEETING_CONVERSATION_EDIT(false, SCRUM_MEETING_CONVERSATION_VIEW, SCRUM_MEETING_GROUP),

	/**
	 * The scrum meeting conversation delete.
	 **/
	@Label("Scrum Meeting Conversation Delete") SCRUM_MEETING_CONVERSATION_DELETE(false, SCRUM_MEETING_CONVERSATION_VIEW),
	
	/**
	 * Represents kanban board group under which kanban board related roles should
	 * be defined.
	 **/
	KANBAN_BOARD_GROUP(true),
	
	/**
	 * The kanban board view.
	 **/
	@Label("Kanban board view") KANBAN_BOARD_VIEW,

	/**
	 * The kanban board edit.
	 **/
	@Label("Kanban board Edit") KANBAN_BOARD_EDIT(false, KANBAN_BOARD_VIEW, KANBAN_BOARD_GROUP),
	
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

	/**
	 * The realse view.
	 **/
	@Label("Release View") RELEASE_VIEW,

	/**
	 * The realse edit.
	 **/
	@Label("Release Edit") RELEASE_EDIT(false, RELEASE_VIEW, PROJECT_GROUP),

	/**
	 * The realse delete.
	 **/
	@Label("Release Delete") RELEASE_DELETE(false, RELEASE_VIEW),

	/**
	 * The project realse view.
	 **/
	@Label("Project Release View") PROJECT_RELEASE_VIEW,

	/**
	 * The project realse edit.
	 **/
	@Label("Project Release Edit") PROJECT_RELEASE_EDIT(false, PROJECT_RELEASE_VIEW, PROJECT_GROUP),

	/**
	 * The project realse delete.
	 **/
	@Label("Project Release Delete") PROJECT_RELEASE_DELETE(false, PROJECT_RELEASE_VIEW),

	/** 
	 *The story release view.
	 **/
	@Label("Story Release View") STORY_RELEASE_VIEW,

	/** 
	 * The story release edit.
	 **/
	@Label("Story Release Edit") STORY_RELEASE_EDIT(false, STORY_RELEASE_VIEW, PROJECT_GROUP),

	/**
	 *  The story release delete.
	 **/
	@Label("Story Release Delete") STORY_RELEASE_DELETE(false, STORY_RELEASE_VIEW),

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
	 * The role to add story.
	 **/
	@Label("Story Add") STORY_ADD,
	
	/**
	 * The story delete.
	 */
	@Label("Story Delete") STORY_DELETE,
	
	/**
	 * Story manager for managing the story with proper priority order.
	 */
	@Label("Story Manager") STORY_MANAGER,
	
	/**
	 * The role for scrum Master.
	 */
	@Label("Scrum Master") SCRUM_MASTER,
	
	/**
	 * The story note view.
	 **/
	@Label("Story Note view") STORY_NOTE_VIEW,

	/**
	 * The story note edit.
	 **/
	@Label("Story Note Edit") STORY_NOTE_EDIT(false, STORY_NOTE_VIEW),

	/**
	 * The story note delete.
	 **/
	@Label("Story Note Delete") STORY_NOTE_DELETE(false, STORY_NOTE_VIEW),

	/**
	 * The sprint view.
	 */
	@Label("Sprint view") SPRINT_VIEW,

	/**
	 * The sprint edit.
	 */
	@Label("Sprint Edit") SPRINT_EDIT(false, SPRINT_VIEW),

	/**
	 * The sprint delete.
	 */
	@Label("Sprint Delete") SPRINT_DELETE(false, SPRINT_VIEW),

	/**
	 * The Priority view.
	 */
	@Label("Priority view") PRIORITY_VIEW,

	/**
	 * The Priority edit.
	 */
	@Label("Priority Edit") PRIORITY_EDIT(false, PRIORITY_VIEW),

	/**
	 * The Priority delete.
	 */
	@Label("Priority Delete") PRIORITY_DELETE(false, PRIORITY_VIEW),

	/**
	 * The Task view.
	 */
	@Label("Task view") TASK_VIEW,

	/**
	 * The Task edit.
	 */
	@Label("Task Edit") TASK_EDIT(false, TASK_VIEW),

	/**
	 * The Task delete.
	 */
	@Label("Task Delete") TASK_DELETE(false, TASK_VIEW),
	
	/**
	 * The Bug view.
	 */
	@Label("Bug view") BUG_VIEW,

	/**
	 * The bug edit.
	 */
	@Label("Bug Edit") BUG_EDIT(false, BUG_VIEW),

	/**
	 * The Bug delete.
	 */
	@Label("Bug Delete") BUG_DELETE(false, BUG_VIEW),
	
	/**
	 * The BugComment view.
	 */
	@Label("BugComment view") BUG_COMMENT_VIEW,

	/**
	 * The BugComment edit.
	 */
	@Label("BugComment Edit") BUG_COMMENT_EDIT(false, BUG_VIEW),

	/**
	 * The BugComment delete.
	 */
	@Label("BugComment Delete") BUG_COMMENT_DELETE(false, BUG_VIEW),

	/**
	 * The Holiday view.
	 */
	@Label("Holiday view") HOLIDAY_VIEW,

	/**
	 * The Holiday edit.
	 */
	@Label("Holiday Edit") HOLIDAY_EDIT(false, HOLIDAY_VIEW),

	/**
	 * The Holiday delete.
	 */
	@Label("Holiday Delete") HOLIDAY_DELETE(false, HOLIDAY_VIEW),	
	
	/**
	 * The PokerGame view.
	 */
	@Label("PokerGame view") POKERGAME_VIEW,

	/**
	 * The PokerGame edit.
	 */
	@Label("PokerGame Edit") POKERGAME_EDIT(false, POKERGAME_VIEW),

	/**
	 * The PokerGame delete.
	 */
	@Label("PokerGame Delete") POKERGAME_DELETE(false, POKERGAME_VIEW),	
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
