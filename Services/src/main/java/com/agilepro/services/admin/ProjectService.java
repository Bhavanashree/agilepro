package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.repository.admin.IProjectRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ProjectsService.
 * 
 * @author Pritam
 */
@Service
public class ProjectService extends BaseCrudService<ProjectEntity, IProjectRepository>
{
	/** 
	 * The repository factory. 
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;
	
	/**
	 * Instantiates a new projects service.
	 */
	public ProjectService()
	{
		super(ProjectEntity.class, IProjectRepository.class);
	}
	
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
	
	/**
	 * Fetch projects.
	 *
	 * @return the list
	 */
	public List<ProjectModel> fetchProjects()
	{
		IProjectRepository iprojectRepository = repositoryFactory.getRepository(IProjectRepository.class);
		
		List<ProjectEntity> projectEntities = iprojectRepository.fetchProjects();
		
		List<ProjectModel> projectModels = new ArrayList<>();
		
		for(ProjectEntity pentity : projectEntities)
		{
			projectModels.add(super.toModel(pentity, ProjectModel.class));
		}
		
		return projectModels;
	}
}
