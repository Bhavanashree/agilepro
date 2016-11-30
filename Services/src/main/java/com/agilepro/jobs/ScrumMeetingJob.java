package com.agilepro.jobs;

import java.util.Date;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.agilepro.controller.IAgileProConstants;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.entity.scrum.ScrumMeetingEntity;
import com.agilepro.services.admin.CustomerService;
import com.agilepro.services.admin.EmployeeService;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.admin.ProjectService;
import com.agilepro.services.notification.EmailNotificationService;
import com.agilepro.services.notification.ScrumMeetingUpdateContext;
import com.agilepro.services.scrum.ScrumMeetingService;
import com.yukthi.webutils.annotations.CronJob;
import com.yukthi.webutils.services.job.IJob;

/**
 * The Class ScrumMeetingJob.
 * 
 * @author Pritam
 */
@CronJob(name = "ScrumMeetingJob", cronExpression = "0 0/60 * 1/1 * ? *")
public class ScrumMeetingJob implements IJob
{
	/**
	 * The email notification service for sending mail.
	 **/
	@Autowired
	private EmailNotificationService emailNotificationService;

	/**
	 * The customer service.
	 **/
	@Autowired
	private CustomerService customerService;

	/**
	 * The project service.
	 **/
	@Autowired
	private ProjectService projectService;

	/**
	 * The employee service.
	 **/
	@Autowired
	private EmployeeService employeeService;

	/**
	 * The scrum meeting service.
	 **/
	@Autowired
	private ScrumMeetingService scrumMeetingService;

	/**
	 * The project member service.
	 **/
	@Autowired
	private ProjectMemberService projectMemberService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.services.job.IJob#execute(java.lang.Object,
	 * org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(Object jobData, JobExecutionContext context) throws JobExecutionException
	{
		List<CustomerEntity> customers = customerService.fetchAllCustomers();

		if(customers != null)
		{
			for(CustomerEntity customer : customers)
			{
				String spaceIdentity = IAgileProConstants.customerSpace(customer.getId());

				List<ProjectEntity> projects = projectService.fetchProjectBySpaceIdentity(spaceIdentity);

				if(projects != null)
				{
					for(ProjectEntity project : projects)
					{
						ScrumMeetingEntity meetingEntity = scrumMeetingService.save(new ScrumMeetingEntity(project, new Date()));

						notifyByMail(projectMemberService.fetchProjectMembersWithNoSpace(project.getId()), customer.getId(), project.getName(), meetingEntity.getId());
					}
				}

				projects = null;
			}
		}
	}

	private void notifyByMail(List<ProjectMemberEntity> projectMembers, Long customerId, String projectName, Long scrumMeetingId)
	{
		if(projectMembers != null)
		{
			for(ProjectMemberEntity projectMember : projectMembers)
			{
				EmployeeEntity employee = employeeService.fetchWithNoSpace(projectMember.getEmployee().getId());

				emailNotificationService.sendMail(customerId, "SCRUM_MEETING_UPDATE", new ScrumMeetingUpdateContext(employee.getName(), projectName, new Date().toString(), employee.getMailId(), scrumMeetingId, customerId));
			}
		}
	}
}
