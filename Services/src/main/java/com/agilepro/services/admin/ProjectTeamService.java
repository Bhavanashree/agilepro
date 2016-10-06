package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.ProjectTeamModel;
import com.agilepro.persistence.entity.admin.ProjectTeamEntity;
import com.agilepro.persistence.repository.admin.IProjectTeamRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ProjectTeamService.
 * 
 * @author Pritam
 */
@Service
public class ProjectTeamService extends BaseCrudService<ProjectTeamEntity, IProjectTeamRepository>
{
	/** 
	 * The iproject team repository. 
	 **/
	private IProjectTeamRepository iprojectTeamRepository;
	
	/**
	 * Instantiates a new project team service.
	 */
	public ProjectTeamService()
	{
		super(ProjectTeamEntity.class, IProjectTeamRepository.class);
	}
	
	/**
	 * Initialize the iprojectTeamRepository.
	 */
	@PostConstruct
	private void init()
	{
		iprojectTeamRepository = repositoryFactory.getRepository(IProjectTeamRepository.class);
	}
	
	/**
	 * Fetch all team by project id.
	 *
	 * @param projectId the project id
	 * @return the list
	 */
	public List<ProjectTeamModel> fetchAllTeamByProjectId(Long projectId)
	{
		List<ProjectTeamEntity> projectTeamEntities =  iprojectTeamRepository.fetchTeamsByProjectId(projectId);
		
		List<ProjectTeamModel> projectTeamModels = new ArrayList<ProjectTeamModel>(projectTeamEntities.size());

		projectTeamEntities.forEach(entity -> projectTeamModels.add(super.toModel(entity, ProjectTeamModel.class)));
		
		return projectTeamModels;
	}
}
