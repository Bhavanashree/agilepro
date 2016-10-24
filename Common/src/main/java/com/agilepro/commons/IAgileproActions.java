package com.agilepro.commons;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_READ_BY_PROJECT_ID;

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
	 * The action type read all project and project release by release id.
	 **/
	public String ACTION_TYPE_READ_ALL_PROJECT_AND_PROJECT_RELEASE_BY_RELEASE_ID = "readAllProjectAndProjectReleaseByReleaseId";

	/** The action type save or update. */
	public String ACTION_TYPE_SAVE_OR_UPDATE = "saveOrUpdate";

	/**
	 * The action type read all note by story id.
	 **/
	public String ACTION_TYPE_READ_ALL_NOTE_BY_STORY_ID = "readAllNoteByStoryId";

	/**
	 * The action type delete by project id.
	 **/
	public String ACTION_TYPE_DELETE_BY_PROJECT_ID = "deleteByProjectId";

	/**
	 * The action type delete by story id.
	 **/
	public String ACTION_TYPE_DELETE_BY_STORY_ID = "deleteByStoryId";

	/**
	 * The action type delete employee id.
	 **/
	public String ACTION_TYPE_DELETE_BY_EMPLOYEE_ID = "deleteByEmployeeId";

	/**
	 * The action type read all story release by release and project.
	 **/
	public String ACTION_TYPE_READ_ALL_STORY_RELEASE_BY_RELEASE_AND_PROJECT = "readAllStoryReleaseByReleaseAndProject";

	/**
	 * The action type read admin managers by proejct id.
	 **/
	public String ACTION_TYPE_READ_ADMIN_MANAGERS_BY_PROEJCT_ID = "readAdminManagersByProjectId";

	/**
	 * The action type read members by proejct id.
	 **/
	public String ACTION_TYPE_READ_MEMBERS_BY_PROEJCT_ID = "readMembersByProjectId";

	/**
	 * The action type read project memebers by project id.
	 **/
	public String ACTION_TYPE_READ_PROJECT_MEMEBERS_BY_PROJECT_ID = "readProjectMembersByProjectId";

	/**
	 * The action prefix story note.
	 **/
	public String ACTION_PREFIX_STORY_NOTE = "storyNote";

	////////////////////////////////////////////////////////////////////////////

	/**
	 * The action prefix customer.
	 **/
	public String ACTION_PREFIX_CUSTOMER = "customer";

	/**
	 * The action prefix vendors.
	 */
	public String ACTION_PREFIX_VENDORS = "vendors";

	/**
	 * The action prefix materials.
	 */
	public String ACTION_PREFIX_MATERIALS = "materials";

	/**
	 * The action prefix materials group.
	 */
	public String ACTION_PREFIX_MATERIALS_GROUP = "materialsGroup";

	/**
	 * The action prefix projects.
	 **/
	public String ACTION_PREFIX_PROJECT = "project";

	/**
	 * The action prefix members.
	 */
	public String ACTION_PREFIX_PROJECT_MEMBER = "projectMember";

	/**
	 * The action prefix project team.
	 **/
	public String ACTION_PREFIX_PROJECT_TEAM = "projectTeam";

	/**
	 * The action prefix realse.
	 **/
	public String ACTION_PREFIX_REALSE = "release";

	/**
	 * The action prefix project realse.
	 **/
	public String ACTION_PREFIX_PROJECT_REALSE = "projectRelease";

	/**
	 * The action prefix story realse.
	 **/
	public String ACTION_PREFIX_STORY_REALSE = "storyRelease";

	/**
	 * The action prefix property type.
	 */
	public String ACTION_PREFIX_PROPERTY_TYPE = "propertyType";

	/**
	 * The action prefix project property group.
	 **/
	public String ACTION_PREFIX_PROJECT_PROPERTY_GROUP = "projectPropertyGroup";

	/**
	 * The action prefix project property.
	 **/
	public String ACTION_PREFIX_PROJECT_PROPERTY = "projectProperty";

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
	 * campaign.
	 */
	public String ACTION_PREFIX_CAMPAIGN = "campaign";

	/**
	 * expenses.
	 */
	public String ACTION_PREFIX_EXPENSES = "expenses";

	/**
	 * The action prefix contentLink.
	 **/
	public String ACTION_PREFIX_CONTENT_LINKS = "contentLink";

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
	 * The action prefix client price plan.
	 **/
	public String ACTION_PREFIX_CLIENT_PRICEPLAN = "clientpriceplan";

	/**
	 * The action prefix stickynotes.
	 **/
	public String ACTION_PREFIX_STICKYNOTES = "stickynotes";

	/**
	 * The action prefix slide images.
	 **/
	public String ACTION_PREFIX_SLIDE_IMAGES = "slideImages";

	/**
	 * The action prefix contacts.
	 **/
	public String ACTION_PREFIX_CONTACTS = "contacts";

	/**
	 * The action prefix client.
	 **/
	public String ACTION_PREFIX_CLIENT = "client";

	/**
	 * The action prefix client.
	 **/
	public String ACTION_PREFIX_CLIENTGROUP = "clientGroup";

	/**
	 * The action prefix todo.
	 **/
	public String ACTION_PREFIX_TODO = "todo";

	/**
	 * The action prefix meetingRoomFeatures.
	 **/
	public String ACTION_PREFIX_MEETING_ROOM_FEATURES = "meetingRoomFeatures";

	/**
	 * The action prefix meetingRoom.
	 **/
	public String ACTION_PREFIX_MEETING_ROOM = "meetingRoom";

	/**
	 * The action prefix meetings.
	 **/
	public String ACTION_PREFIX_MEETING = "meeting";

	/**
	 * The action prefix task.
	 **/
	public String ACTION_PREFIX_TASK = "task";

	/**
	 * The action prefix news.
	 **/
	public String ACTION_PREFIX_NEWS = "news";

	/**
	 * The action prefix slideimages.
	 **/
	public String ACTION_PREFIX_SLIDEIMAGES = "slideimages";

	/**
	 * The action prefix lpage.
	 **/
	public String ACTION_PREFIX_LPAGE = "lpage";

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
	 * The action prefix conversation message.
	 **/
	public String ACTION_PREFIX_CONVERSATION_MESSAGE = "conversationMessage";

	/**
	 * The action prefix story attachment message.
	 **/
	public String ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE = "storyAttachment";

	/**
	 * The action prefix mail template.
	 **/
	public String ACTION_PREFIX_MAIL_TEMPLATE = "mailTemplate";

	/**
	 * The action prefix mail details.
	 **/
	public String ACTION_PREFIX_MAIL_DETAILS = "mailDetails";

	/**
	 * The action prefix conversation title.
	 **/
	public String ACTION_PREFIX_CONVERSATION_TITLE = "conversationTitle";

	/**
	 * Parameter with name "name".
	 */
	public String PARAM_NAME = "name";

	/**
	 * The action prefix project price plan.
	 **/
	public String ACTION_PREFIX_PROJECT_PRICE_PLAN = "projectPricePlan";

	/**
	 * The action prefix teams.
	 **/
	public String ACTION_PREFIX_TEAM = "teams";

	/**
	 * The action prefix tag.
	 **/
	public String ACTION_PREFIX_TAG = "tag";

	/**
	 * The action prefix user setting.
	 **/
	public String ACTION_PREFIX_USER_SETTING = "userSetting";

	/**
	 * The action prefix backlog.
	 */
	public String ACTION_PREFIX_STORY = "story";

	/**
	 * The action prefix priority.
	 */
	public String ACTION_PREFIX_PRIORITY = "priority";

	/**
	 * The action type readSprints.
	 */
	public String ACTION_TYPE_READ_SPRINT = "readSprints";

	/**
	 * The action type readSprints.
	 */
	public String ACTION_TYPE_READ_STORY_SPRINT = "readStoriesBySprint";

	/**
	 * The action type readProjectid.
	 */

	public String ACTION_TYPE_READ_STORY_BY_SPRINT_PROJECT_ID = "fetchStoryBysprintAndProjectId";

	/**
	 * The action type read sprint project id.
	 **/
	public String ACTION_TYPE_READ_SPRINT_PROJECT_ID = "sprintProjectId";

	/**
	 * The action type read story id.
	 **/
	public String ACTION_TYPE_READ_STORY_ID = "readStoryId";

	/**
	 * The action type read by project id.
	 **/
	public String ACTION_TYPE_READ_BY_PROJECT_ID = "readByProjectId";

	/**
	 * The action type save stories in bulk.
	 **/
	public String ACTION_TYPE_SAVE_STORIES_IN_BULK = "storiesInbulk";
	/**
	 * The action prefix sprint.
	 **/
	public String ACTION_PREFIX_SPRINT = "sprint";

	/**
	 * The action prefix mail template definition.
	 **/
	public String ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION = "mailTemplteDefinition";

	/**
	 * The action prefix save mail template definition.
	 **/
	public String ACTION_TYPE_SAVE_MAIL_TEMPLATE = "saveMailTemplate";

	/**
	 * The action prefix for bug.
	 **/
	public String ACTION_PREFIX_BUG = "bug";

	/**
	 * The action prefix bug comments.
	 **/
	public String ACTION_PREFIX_BUG_COMMENTS = "bugComment";

	/**
	 * The action type read bug comment by bug id.
	 **/
	public String ACTION_TYPE_READ_BUG_COMMENT_BY_BUG_ID = "readByBugId";

	/**
	 * The action prefix bug attachment message.
	 **/
	public String ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE = "bugAttachment";

	/**
	 * The action prefix holiday.
	 **/
	public String ACTION_PREFIX_HOLIDAY = "holiday";

	/**
	 * The param id.
	 **/
	public String PARAM_ID = "id";

	/**
	 * The dot.
	 **/
	public String DOT = ".";

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * Customer save action name.
	 */
	public String ACTION_CUSTOMER_SAVE = ACTION_PREFIX_CUSTOMER + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action email send.
	 **/
	public String ACTION_EMAIL_SEND = ACTION_PREFIX_EMAIL + DOT + ACTION_TYPE_SEND;

	/**
	 * Customer read action name.
	 **/
	public String ACTION_CUSTOMER_READ = ACTION_PREFIX_CUSTOMER + DOT + ACTION_TYPE_READ;

	/**
	 * Customer delete action name.
	 **/
	public String ACTION_CUSTOMER_DELETE = ACTION_PREFIX_CUSTOMER + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action customer delete all.
	 **/
	public String ACTION_CUSTOMER_DELETE_ALL = ACTION_PREFIX_CUSTOMER + DOT + ACTION_TYPE_DELETE_ALL;

	/**
	 * Customer update action name.
	 **/
	public String ACTION_CUSTOMER_UPDATE = ACTION_PREFIX_CUSTOMER + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action payment save.
	 **/
	public String ACTION_PAYMENT_SAVE = ACTION_PREFIX_PAYMENT + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action payment update.
	 **/
	public String ACTION_PAYMENT_UPDATE = ACTION_PREFIX_PAYMENT + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action payment read.
	 **/
	public String ACTION_PAYMENT_READ = ACTION_PREFIX_PAYMENT + DOT + ACTION_TYPE_READ;

	/**
	 * The action payment delete.
	 **/
	public String ACTION_PAYMENT_DELETE = ACTION_PREFIX_PAYMENT + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action payment delete all.
	 **/
	public String ACTION_PAYMENT_DELETE_ALL = ACTION_PREFIX_PAYMENT + DOT + ACTION_TYPE_DELETE_ALL;

	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action designation Save.
	 **/
	public String ACTION_DESIGNATION_SAVE = ACTION_PREFIX_DESIGNATION + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action designation read.
	 **/
	public String ACTION_DESIGNATION_READ = ACTION_PREFIX_DESIGNATION + DOT + ACTION_TYPE_READ;

	/**
	 * The action designation update.
	 **/
	public String ACTION_DESIGNATION_UPDATE = ACTION_PREFIX_DESIGNATION + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action designation delete.
	 **/
	public String ACTION_DESIGNATION_DELETE = ACTION_PREFIX_DESIGNATION + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action designation delete all.
	 **/
	public String ACTION_DESIGNATION_DELETE_ALL = ACTION_PREFIX_DESIGNATION + DOT + ACTION_TYPE_DELETE_ALL;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Price Plan save action name.
	 */
	public String ACTION_PRICEPLAN_SAVE = ACTION_PREFIX_PRICEPLAN + DOT + ACTION_TYPE_SAVE;
	/**
	 * Price Plan read action name.
	 */
	public String ACTION_PRICEPLAN_READ = ACTION_PREFIX_PRICEPLAN + DOT + ACTION_TYPE_READ;
	/**
	 * Price Plan delete action name.
	 */
	public String ACTION_PRICEPLAN_DELETE = ACTION_PREFIX_PRICEPLAN + DOT + ACTION_TYPE_DELETE;
	/**
	 * Price Plan update action name.
	 */
	public String ACTION_PRICEPLAN_UPDATE = ACTION_PREFIX_PRICEPLAN + DOT + ACTION_TYPE_UPDATE;
	/**
	 * Price Plan invoke action name.
	 */
	public String ACTION_PRICEPLAN_INVOKE = ACTION_PREFIX_PRICEPLAN + DOT + ACTION_TYPE_INVOKE;

	/**
	 * The action priceplan delete all.
	 **/
	public String ACTION_PRICEPLAN_DELETE_ALL = ACTION_PREFIX_PRICEPLAN + DOT + ACTION_TYPE_DELETE_ALL;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Price Plan save action name.
	 */
	public String ACTION_CLIENT_PRICEPLAN_SAVE = ACTION_PREFIX_CLIENT_PRICEPLAN + DOT + ACTION_TYPE_SAVE;
	/**
	 * Price Plan read action name.
	 */
	public String ACTION_CLIENT_PRICEPLAN_READ = ACTION_PREFIX_CLIENT_PRICEPLAN + DOT + ACTION_TYPE_READ;
	/**
	 * Price Plan delete action name.
	 */
	public String ACTION_CLIENT_PRICEPLAN_DELETE = ACTION_PREFIX_CLIENT_PRICEPLAN + DOT + ACTION_TYPE_DELETE;
	/**
	 * Price Plan update action name.
	 */
	public String ACTION_CLIENT_PRICEPLAN_UPDATE = ACTION_PREFIX_CLIENT_PRICEPLAN + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Task save action name.
	 */
	public String ACTION_TASK_SAVE = ACTION_PREFIX_TASK + DOT + ACTION_TYPE_SAVE;
	/**
	 * Task read action name.
	 */
	public String ACTION_TASK_READ = ACTION_PREFIX_TASK + DOT + ACTION_TYPE_READ;
	/**
	 * The action backlog readall.
	 **/
	public String ACTION_TASK_READALL = ACTION_PREFIX_TASK + "." + ACTION_TYPE_READ_ALL;

	/** The action task read id. */
	public String ACTION_TASK_READ_ID = ACTION_PREFIX_TASK + "." + ACTION_TYPE_READ_STORY_ID;

	/**
	 * Task delete action name.
	 */
	public String ACTION_TASK_DELETE = ACTION_PREFIX_TASK + DOT + ACTION_TYPE_DELETE;
	/**
	 * Task update action name.
	 */
	public String ACTION_TASK_UPDATE = ACTION_PREFIX_TASK + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action stickynotes save.
	 **/
	public String ACTION_STICKYNOTES_SAVE = ACTION_PREFIX_STICKYNOTES + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action stickynotes read.
	 **/
	public String ACTION_STICKYNOTES_READ = ACTION_PREFIX_STICKYNOTES + DOT + ACTION_TYPE_READ;

	/**
	 * The action stickynotes update.
	 **/
	public String ACTION_STICKYNOTES_UPDATE = ACTION_PREFIX_STICKYNOTES + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action stickynotes delete.
	 **/
	public String ACTION_STICKYNOTES_DELETE = ACTION_PREFIX_STICKYNOTES + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action stickynotes delete all.
	 **/
	public String ACTION_STICKYNOTES_DELETE_ALL = ACTION_PREFIX_STICKYNOTES + DOT + ACTION_TYPE_DELETE_ALL;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action contacts save.
	 **/
	public String ACTION_CONTACTS_SAVE = ACTION_PREFIX_CONTACTS + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action contacts read.
	 **/
	public String ACTION_CONTACTS_READ = ACTION_PREFIX_CONTACTS + DOT + ACTION_TYPE_READ;

	/**
	 * The action contacts update.
	 **/
	public String ACTION_CONTACTS_UPDATE = ACTION_PREFIX_CONTACTS + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action contacts delete.
	 **/
	public String ACTION_CONTACTS_DELETE = ACTION_PREFIX_CONTACTS + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action contacts delete all.
	 **/
	public String ACTION_CONTACTS_DELETE_ALL = ACTION_PREFIX_CONTACTS + DOT + ACTION_TYPE_DELETE_ALL;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action client save.
	 **/
	public String ACTION_CLIENT_SAVE = ACTION_PREFIX_CLIENT + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action client read.
	 **/
	public String ACTION_CLIENT_READ = ACTION_PREFIX_CLIENT + DOT + ACTION_TYPE_READ;

	/**
	 * The action client update.
	 **/
	public String ACTION_CLIENT_UPDATE = ACTION_PREFIX_CLIENT + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action client delete.
	 **/
	public String ACTION_CLIENT_DELETE = ACTION_PREFIX_CLIENT + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action client delete all.
	 **/
	public String ACTION_CLIENT_DELETE_ALL = ACTION_PREFIX_CLIENT + DOT + ACTION_TYPE_DELETE_ALL;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ToDo save action name.
	 */
	public String ACTION_TODO_SAVE = ACTION_PREFIX_TODO + DOT + ACTION_TYPE_SAVE;

	/**
	 * ToDo read action name.
	 */
	public String ACTION_TODO_READ = ACTION_PREFIX_TODO + DOT + ACTION_TYPE_READ;

	/**
	 * ToDo delete action name.
	 */
	public String ACTION_TODO_DELETE = ACTION_PREFIX_TODO + DOT + ACTION_TYPE_DELETE;

	/**
	 * ToDo update action name.
	 */
	public String ACTION_TODO_UPDATE = ACTION_PREFIX_TODO + DOT + ACTION_TYPE_UPDATE;

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * Meeting room features save action name.
	 */
	public String ACTION_MEETING_ROOM_FEATURES_SAVE = ACTION_PREFIX_MEETING_ROOM_FEATURES + DOT + ACTION_TYPE_SAVE;
	/**
	 * Meeting room features read action name.
	 */
	public String ACTION_MEETING_ROOM_FEATURES_READ = ACTION_PREFIX_MEETING_ROOM_FEATURES + DOT + ACTION_TYPE_READ;
	/**
	 * Meeting Room features delete action name.
	 */
	public String ACTION_MEETING_ROOM_FEATURES_DELETE = ACTION_PREFIX_MEETING_ROOM_FEATURES + DOT + ACTION_TYPE_DELETE;

	/**
	 * Meeting Room features update action name.
	 */
	public String ACTION_MEETING_ROOM_FEATURES_UPDATE = ACTION_PREFIX_MEETING_ROOM_FEATURES + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Meeting room save action name.
	 */
	public String ACTION_MEETING_ROOM_SAVE = ACTION_PREFIX_MEETING_ROOM + DOT + ACTION_TYPE_SAVE;
	/**
	 * Meeting room read action name.
	 */
	public String ACTION_MEETING_ROOM_READ = ACTION_PREFIX_MEETING_ROOM + DOT + ACTION_TYPE_READ;

	/**
	 * Meeting Room delete action name.
	 */
	public String ACTION_MEETING_ROOM_DELETE = ACTION_PREFIX_MEETING_ROOM + DOT + ACTION_TYPE_DELETE;

	/**
	 * Meeting Room update action name.
	 */
	public String ACTION_MEETING_ROOM_UPDATE = ACTION_PREFIX_MEETING_ROOM + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Meeting save action name.
	 */
	public String ACTION_MEETING_SAVE = ACTION_PREFIX_MEETING + DOT + ACTION_TYPE_SAVE;
	/**
	 * Meeting read action name.
	 */
	public String ACTION_MEETING_READ = ACTION_PREFIX_MEETING + DOT + ACTION_TYPE_READ;
	/**
	 * Meeting delete action name.
	 */
	public String ACTION_MEETING_DELETE = ACTION_PREFIX_MEETING + DOT + ACTION_TYPE_DELETE;
	/**
	 * Meeting update action name.
	 */
	public String ACTION_MEETING_UPDATE = ACTION_PREFIX_MEETING + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * read action name.
	 */
	public String ACTION_CUSTOMERINVOICEDETAILS_READ = ACTION_PREFIX_CUSTOMERINVOICEDETAILS + DOT + ACTION_TYPE_READ;
	/**
	 * delete action name.
	 */
	public String ACTION_CUSTOMERINVOICEDETAILS_DELETE = ACTION_PREFIX_CUSTOMERINVOICEDETAILS + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action customerinvoicedetails save.
	 **/
	public String ACTION_CUSTOMERINVOICEDETAILS_SAVE = ACTION_PREFIX_CUSTOMERINVOICEDETAILS + DOT + ACTION_TYPE_SAVE;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action slideimages save.
	 **/
	public String ACTION_SLIDEIMAGES_SAVE = ACTION_PREFIX_SLIDEIMAGES + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action slideimages read.
	 **/
	public String ACTION_SLIDEIMAGES_READ = ACTION_PREFIX_SLIDEIMAGES + DOT + ACTION_TYPE_READ;

	/**
	 * The action slideimages update.
	 **/
	public String ACTION_SLIDEIMAGES_UPDATE = ACTION_PREFIX_SLIDEIMAGES + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action slideimages delete.
	 **/
	public String ACTION_SLIDEIMAGES_DELETE = ACTION_PREFIX_SLIDEIMAGES + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action slideimages delete all.
	 **/
	public String ACTION_SLIDEIMAGES_DELETE_ALL = ACTION_PREFIX_SLIDEIMAGES + DOT + ACTION_TYPE_DELETE_ALL;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action useradmin save.
	 **/
	public String ACTION_ADMINUSER_SAVE = ACTION_PREFIX_ADMINUSER + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action useradmin read.
	 **/
	public String ACTION_ADMINUSER_READ = ACTION_PREFIX_ADMINUSER + DOT + ACTION_TYPE_READ;

	/**
	 * The action useradmin update.
	 **/
	public String ACTION_ADMINUSER_UPDATE = ACTION_PREFIX_ADMINUSER + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action useradmin delete.
	 **/
	public String ACTION_ADMINUSER_DELETE = ACTION_PREFIX_ADMINUSER + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action adminuser delete all.
	 **/
	public String ACTION_ADMINUSER_DELETE_ALL = ACTION_PREFIX_ADMINUSER + DOT + ACTION_TYPE_DELETE_ALL;

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** The action story attachment save. */
	public String ACTION_STORY_ATTACHMENT_SAVE = ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_SAVE;

	/** The action story attachment read. */
	public String ACTION_STORY_ATTACHMENT_READ = ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_READ;

	/** The action story attachment update. */
	public String ACTION_STORY_ATTACHMENT_UPDATE = ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_UPDATE;

	/** The action story attachment delete. */
	public String ACTION_STORY_ATTACHMENT_DELETE = ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_DELETE;

	/** The action story attachment delete all. */
	public String ACTION_STORY_ATTACHMENT_DELETE_ALL = ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action employee save.
	 **/
	public String ACTION_EMPLOYEE_SAVE = ACTION_PREFIX_EMPLOYEE + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action employee read.
	 **/
	public String ACTION_EMPLOYEE_READ = ACTION_PREFIX_EMPLOYEE + DOT + ACTION_TYPE_READ;

	/**
	 * The action employee update.
	 **/
	public String ACTION_EMPLOYEE_UPDATE = ACTION_PREFIX_EMPLOYEE + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action employee delete.
	 **/
	public String ACTION_EMPLOYEE_DELETE = ACTION_PREFIX_EMPLOYEE + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action employee delete all.
	 **/
	public String ACTION_EMPLOYEE_DELETE_ALL = ACTION_PREFIX_EMPLOYEE + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * The action client save.
	 **/
	public String ACTION_CLIENTGROUP_SAVE = ACTION_PREFIX_CLIENTGROUP + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action client group read.
	 **/
	public String ACTION_CLIENTGROUP_READ = ACTION_PREFIX_CLIENTGROUP + DOT + ACTION_TYPE_READ;

	/**
	 * The action client group update.
	 **/
	public String ACTION_CLIENTGROUP_UPDATE = ACTION_PREFIX_CLIENTGROUP + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action client group delete.
	 **/
	public String ACTION_CLIENTGROUP_DELETE = ACTION_PREFIX_CLIENTGROUP + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action client group delete.
	 **/
	public String ACTION_CLIENTGROUP_DELETE_ALL = ACTION_PREFIX_CLIENTGROUP + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////

	// Enable Multiple String Literals

	/**
	 * Project price plan save action name.
	 */
	public String ACTION_PROJECT_PRICE_PLAN_SAVE = ACTION_PREFIX_PROJECT_PRICE_PLAN + DOT + ACTION_TYPE_SAVE;
	/**
	 * Project price plan read action name.
	 */
	public String ACTION_PROJECT_PRICE_PLAN_READ = ACTION_PREFIX_PROJECT_PRICE_PLAN + DOT + ACTION_TYPE_READ;
	/**
	 * Project price plan delete action name.
	 */
	public String ACTION_PROJECT_PRICE_PLAN_DELETE = ACTION_PREFIX_PROJECT_PRICE_PLAN + DOT + ACTION_TYPE_DELETE;
	/**
	 * Project price plan update action name.
	 */
	public String ACTION_PROJECT_PRICE_PLAN_UPDATE = ACTION_PREFIX_PROJECT_PRICE_PLAN + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * teams save action name.
	 */

	public String ACTION_TEAM_SAVE = ACTION_PREFIX_TEAM + DOT + ACTION_TYPE_SAVE;
	/**
	 * teams read action name.
	 */
	public String ACTION_TEAM_READ = ACTION_PREFIX_TEAM + DOT + ACTION_TYPE_READ;
	/**
	 * teams delete action name.
	 */
	public String ACTION_TEAM_DELETE = ACTION_PREFIX_TEAM + DOT + ACTION_TYPE_DELETE;
	/**
	 * teams plan update action name.
	 */
	public String ACTION_TEAM_UPDATE = ACTION_PREFIX_TEAM + DOT + ACTION_TYPE_UPDATE;

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The action projects save.
	 **/
	public String ACTION_PROJECT_SAVE = ACTION_PREFIX_PROJECT + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action projects read.
	 **/
	public String ACTION_PROJECT_READ = ACTION_PREFIX_PROJECT + DOT + ACTION_TYPE_READ;

	/**
	 * The action projects delete.
	 **/
	public String ACTION_PROJECT_DELETE = ACTION_PREFIX_PROJECT + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action projects update.
	 **/
	public String ACTION_PROJECT_UPDATE = ACTION_PREFIX_PROJECT + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action projects delete all.
	 **/
	public String ACTION_PROJECT_DELETE_ALL = ACTION_PREFIX_PROJECT + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * The action project members save.
	 **/
	public String ACTION_PROJECT_MEMBER_SAVE = ACTION_PREFIX_PROJECT_MEMBER + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action projects members read.
	 **/
	public String ACTION_PROJECTS_MEMBER_READ = ACTION_PREFIX_PROJECT_MEMBER + DOT + ACTION_TYPE_READ;

	/**
	 * The action project members delete.
	 **/
	public String ACTION_PROJECT_MEMBER_DELETE = ACTION_PREFIX_PROJECT_MEMBER + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action project members update.
	 **/
	public String ACTION_PROJECT_MEMBER_UPDATE = ACTION_PREFIX_PROJECT_MEMBER + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action project members delete all.
	 **/
	public String ACTION_PROJECT_MEMBER_DELETE_ALL = ACTION_PREFIX_PROJECT_MEMBER + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * The action tags save.
	 **/
	public String ACTION_TAG_SAVE = ACTION_PREFIX_TAG + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action tags read.
	 **/
	public String ACTION_TAG_READ = ACTION_PREFIX_TAG + DOT + ACTION_TYPE_READ;

	/**
	 * The action tags delete.
	 **/
	public String ACTION_TAG_DELETE = ACTION_PREFIX_TAG + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action tags update.
	 **/
	public String ACTION_TAG_UPDATE = ACTION_PREFIX_TAG + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action tags delete all.
	 **/
	public String ACTION_TAG_DELETE_ALL = ACTION_PREFIX_TAG + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////

	/**
	 * The action user setting save.
	 **/
	public String ACTION_USER_SETTING_SAVE = ACTION_PREFIX_USER_SETTING + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action user setting read.
	 **/
	public String ACTION_USER_SETTING_READ = ACTION_PREFIX_USER_SETTING + DOT + ACTION_TYPE_READ;

	/**
	 * The action user setting delete.
	 **/
	public String ACTION_USER_SETTING_DELETE = ACTION_PREFIX_USER_SETTING + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action user setting update.
	 **/
	public String ACTION_USER_SETTING_UPDATE = ACTION_PREFIX_USER_SETTING + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action user setting delete all.
	 **/
	public String ACTION_USER_SETTING_DELETE_ALL = ACTION_PREFIX_USER_SETTING + DOT + ACTION_TYPE_DELETE_ALL;

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The action Story save.
	 **/
	public String ACTION_STORY_SAVE = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action story save stories in bulk.
	 **/
	public String ACTION_STORY_SAVE_STORIES_IN_BULK = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_SAVE_STORIES_IN_BULK;

	/**
	 * The action Story read.
	 **/
	public String ACTION_STORY_READ = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_READ;

	/**
	 * The action backlog readall.
	 **/
	public String ACTION_STORY_READALL = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_READ_ALL;

	/**
	 * The action story read project id.
	 **/
	public String ACTION_STORY_READ_PROJECT_ID = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_READ_STORY_BY_SPRINT_PROJECT_ID;
	/**
	 * The action sprint read.
	 **/
	public String ACTION_STORY_READSPRINT = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_READ_SPRINT;
	/**
	 * The action backlog update.
	 **/
	public String ACTION_STORY_UPDATE = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action backlog delete.
	 **/
	public String ACTION_STORY_DELETE = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action backlog delete all.
	 **/
	public String ACTION_STORY_DELETE_ALL = ACTION_PREFIX_STORY + DOT + ACTION_TYPE_DELETE_ALL;

	/**
	 * The action sprint save.
	 **/
	public String ACTION_SPRINT_SAVE = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action sprint read.
	 **/
	public String ACTION_SPRINT_READ = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_READ;

	/**
	 * The action sprint readall.
	 **/
	public String ACTION_SPRINT_READALL = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_READ_ALL;

	/**
	 * The action sprint read projectid.
	 **/
	public String ACTION_SPRINT_READ_PROJECTID = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_READ_SPRINT_PROJECT_ID;

	/**
	 * The action sprint update.
	 **/
	public String ACTION_SPRINT_UPDATE = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action sprint delete.
	 */
	public String ACTION_SPRINT_DELETE = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action sprint deleteall.
	 **/
	public String ACTION_SPRINT_DELETEALL = ACTION_PREFIX_SPRINT + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action sprint save.
	 **/
	public String ACTION_PRIORITY_SAVE = ACTION_PREFIX_PRIORITY + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action sprint read.
	 **/
	public String ACTION_PRIORITY_READ = ACTION_PREFIX_PRIORITY + DOT + ACTION_TYPE_READ;

	/**
	 * import static com.agilepro.commons.IAgileproActions. The action sprint
	 * readall.
	 **/
	public String ACTION_PRIORITY_READALL = ACTION_PREFIX_PRIORITY + DOT + ACTION_TYPE_READ_ALL;

	/**
	 * The action sprint update.
	 **/
	public String ACTION_PRIORITY_UPDATE = ACTION_PREFIX_PRIORITY + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action sprint delete.
	 */
	public String ACTION_PRIORITY_DELETE = ACTION_PREFIX_PRIORITY + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action sprint delete all.
	 **/
	public String ACTION_PRIORITY_DELETEALL = ACTION_PREFIX_PRIORITY + DOT + ACTION_TYPE_DELETE;
	/**
	 * The action for mailtemplatedefinitions.
	 */
	public String ACTION_MAIL_TEMPLATE_DEFINITION_SAVE = ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action mail template definition read.
	 **/
	public String ACTION_MAIL_TEMPLATE_DEFINITION_READ = ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION + DOT + ACTION_TYPE_READ;

	/**
	 * The action mail template definition update.
	 **/
	public String ACTION_MAIL_TEMPLATE_DEFINITION_UPDATE = ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action mail template definition delete.
	 **/
	public String ACTION_MAIL_TEMPLATE_DEFINITION_DELETE = ACTION_PREFIX_MAIL_TEMPLATE_DEFINITION + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action mail template savetemplate.
	 **/
	public String ACTION_MAIL_TEMPLATE_SAVETEMPLATE = ACTION_PREFIX_MAIL_TEMPLATE + DOT + ACTION_TYPE_SAVE_MAIL_TEMPLATE;

	/**
	 * The action bug save.
	 */
	public String ACTION_BUG_SAVE = ACTION_PREFIX_BUG + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action bug read.
	 **/
	public String ACTION_BUG_READ = ACTION_PREFIX_BUG + DOT + ACTION_TYPE_READ;

	/**
	 * The action bug update.
	 **/
	public String ACTION_BUG_UPDATE = ACTION_PREFIX_BUG + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action bug delete.
	 **/
	public String ACTION_BUG_DELETE = ACTION_PREFIX_BUG + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action bug delete all.
	 **/
	public String ACTION_BUG_DELETE_ALL = ACTION_PREFIX_BUG + DOT + ACTION_TYPE_DELETE_ALL;
	/**
	 * The action bugComment save.
	 */
	public String ACTION_BUG_COMMENT_SAVE = ACTION_PREFIX_BUG_COMMENTS + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action bugComment read.
	 **/
	public String ACTION_BUG_COMMENT_READ = ACTION_PREFIX_BUG_COMMENTS + DOT + ACTION_TYPE_READ;

	/**
	 * The action bug comment read by bug id.
	 **/
	public String ACTION_BUG_COMMENT_READ_BY_BUG_ID = ACTION_PREFIX_BUG_COMMENTS + DOT + ACTION_TYPE_READ_BUG_COMMENT_BY_BUG_ID;

	/**
	 * The action bugComment update.
	 **/
	public String ACTION_BUG_COMMENT_UPDATE = ACTION_PREFIX_BUG_COMMENTS + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action bugComment delete.
	 **/
	public String ACTION_BUG_COMMENT_DELETE = ACTION_PREFIX_BUG_COMMENTS + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action bugComment delete all.
	 **/
	public String ACTION_BUG_COMMENT_DELETE_ALL = ACTION_PREFIX_BUG_COMMENTS + DOT + ACTION_TYPE_DELETE_ALL;

	/**
	 * The action bug attachment save.
	 **/
	public String ACTION_BUG_ATTACHMENT_SAVE = ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action story attachment read.
	 */
	public String ACTION_BUG_ATTACHMENT_READ = ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_READ;

	/**
	 * The action bug attachment readall.
	 **/
	public String ACTION_BUG_ATTACHMENT_READALL = ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_READ_ALL;

	/**
	 * The action story attachment update.
	 **/
	public String ACTION_BUG_ATTACHMENT_UPDATE = ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action story attachment delete.
	 **/
	public String ACTION_BUG_ATTACHMENT_DELETE = ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action story attachment delete all.
	 */
	public String ACTION_BUG_ATTACHMENT_DELETE_ALL = ACTION_PREFIX_BUG_ATTACHMENT_MESSAGE + DOT + ACTION_TYPE_DELETE_ALL;

	/**
	 * The action holiday save.
	 **/
	public String ACTION_HOLIDAY_SAVE = ACTION_PREFIX_HOLIDAY + DOT + ACTION_TYPE_SAVE;

	/**
	 * The action holiday read.
	 **/
	public String ACTION_HOLIDAY_READ = ACTION_PREFIX_HOLIDAY + DOT + ACTION_TYPE_READ;

	/**
	 * The action holiday readall.
	 **/
	public String ACTION_HOLIDAY_READALL = ACTION_PREFIX_HOLIDAY + DOT + ACTION_TYPE_READ_ALL;

	/**
	 * The action holiday update.
	 **/
	public String ACTION_HOLIDAY_UPDATE = ACTION_PREFIX_HOLIDAY + DOT + ACTION_TYPE_UPDATE;

	/**
	 * The action holiday delete.
	 **/
	public String ACTION_HOLIDAY_DELETE = ACTION_PREFIX_HOLIDAY + DOT + ACTION_TYPE_DELETE;

	/**
	 * The action holiday delete all.
	 **/
	public String ACTION_HOLIDAY_DELETE_ALL = ACTION_PREFIX_HOLIDAY + DOT + ACTION_TYPE_DELETE_ALL;
}