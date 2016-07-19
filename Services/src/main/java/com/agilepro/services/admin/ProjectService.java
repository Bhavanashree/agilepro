package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<ProjectModel> fetchProjects()
	{
		IProjectRepository iProjectRepository = repositoryFactory.getRepository(IProjectRepository.class);
		
		List<ProjectEntity> projectEntities = iProjectRepository.fetchProjects();
		
		List<ProjectModel> projectModels = new ArrayList<>();
		
		for(ProjectEntity pEntity : projectEntities)
		{
			projectModels.add(super.toModel(pEntity, ProjectModel.class));
		}
		
		return projectModels;
	}
}
