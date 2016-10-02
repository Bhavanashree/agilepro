package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
	 * The iproject repository. 
	 **/
	private IProjectRepository iprojectRepository;
	
	/**
	 * Instantiates a new projects service.
	 */
	public ProjectService()
	{
		super(ProjectEntity.class, IProjectRepository.class);
	}
	
	/**
	 * Initialize the iprojectRepository.
	 */
	@PostConstruct
	private void init()
	{
		iprojectRepository = repositoryFactory.getRepository(IProjectRepository.class);
	}
	
	/**
	 * Fetch projects.
	 *
	 * @return the list
	 */
	public List<ProjectModel> fetchAllProjects()
	{
		List<ProjectEntity> projectEntities = iprojectRepository.fetchAllProjects();
		
		List<ProjectModel> projectModels = new ArrayList<>(projectEntities.size());
		
		projectEntities.forEach(entity -> projectModels.add(super.toModel(entity, ProjectModel.class)));
		
		return projectModels;
	}
	
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
