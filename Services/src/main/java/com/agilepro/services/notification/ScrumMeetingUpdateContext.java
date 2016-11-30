package com.agilepro.services.notification;

import java.util.Arrays;
import java.util.Date;

import com.yukthi.webutils.mail.IMailCustomizer;
import com.yukthi.webutils.mail.MailMessage;

/**
 * The Class ScrumMeetingUpdateContext.
 * 
 * @author Pritam
 */
public class ScrumMeetingUpdateContext implements IMailCustomizer
{
	/** 
	 * The user display name. 
	 **/
	private String userDisplayName;
	
	/** 
	 * The project name. 
	 **/
	private String projectName;
	
	/** 
	 * The meeting date. 
	 **/
	private String meetingDate;
	
	/** 
	 * The email id. 
	 **/
	private String emailId;
	
	private Long scrumMeetingId;
	
	private Long customerId;
	
	public ScrumMeetingUpdateContext(String userDisplayName, String projectName, String meetingDate, String emailId, Long scrumMeetingId, Long customerId)
	{
		super();
		this.userDisplayName = userDisplayName;
		this.projectName = projectName;
		this.meetingDate = meetingDate;
		this.emailId = emailId;
		this.scrumMeetingId = scrumMeetingId;
		this.customerId = customerId;
	}

	@Override
	public void customize(MailMessage mailMessage, Object templateCustomization)
	{
		mailMessage.setToList(Arrays.asList(emailId));
	}

	public String getUserDisplayName()
	{
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName)
	{
		this.userDisplayName = userDisplayName;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public String getMeetingDate()
	{
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate)
	{
		this.meetingDate = meetingDate;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public Long getScrumMeetingId() {
		return scrumMeetingId;
	}

	public void setScrumMeetingId(Long scrumMeetingId) {
		this.scrumMeetingId = scrumMeetingId;
	}
}
