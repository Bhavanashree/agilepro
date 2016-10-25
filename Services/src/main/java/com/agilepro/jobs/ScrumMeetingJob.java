package com.agilepro.jobs;

import java.util.List;
import javax.annotation.PostConstruct;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.repository.admin.IProjectRepository;
import com.agilepro.persistence.repository.scrum.IScrumMeetingRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.annotations.CronJob;
import com.yukthi.webutils.services.job.IJob;

/**
 * The Class ScrumMeetingJob.
 * 
 * @author Pritam
 */
//@CronJob(name = "ScrumMeetingJob", cronExpression = "0 0/60 * 1/1 * ? *")
public class ScrumMeetingJob implements IJob
{
	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;
	
	/** 
	 * The iscrum meeting repository. 
	 */
	private IScrumMeetingRepository iscrumMeetingRepository;
	
	/** 
	 * The iproject repository. 
	 **/
	private IProjectRepository iprojectRepository;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		iprojectRepository = repositoryFactory.getRepository(IProjectRepository.class);
		
		iscrumMeetingRepository = repositoryFactory.getRepository(IScrumMeetingRepository.class);
	}
	
	/* (non-Javadoc)
	 * @see com.yukthi.webutils.services.job.IJob#execute(java.lang.Object, org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(Object jobData, JobExecutionContext context) throws JobExecutionException
	{
		List<ProjectEntity> projects = iprojectRepository.fetchAllProjects();
		
		if(projects != null)
		{
			projects.forEach(project -> iscrumMeetingRepository.fetchByProjectId(1L));
		}
	}
}
