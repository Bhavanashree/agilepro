package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.agilepro.commons.models.customer.ProjectModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.repository.admin.IProjectRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * ProjectsService for interaction with database project table.
 * 
 * @author Pritam
 */
@Service
public class ProjectService extends BaseCrudService<ProjectEntity, IProjectRepository>
{
	/**
	 * Instantiates a new projects service.
	 */
	public ProjectService()
	{
		super(ProjectEntity.class, IProjectRepository.class);
	}
	
	/**
	 * Fetch all projects for drop down.
	 *
	 * @return the list
	 */
	public List<ProjectModel> fetchAllProjects()
	{
		List<ProjectEntity> projectEntities = repository.fetchAllProjects();
		
		List<ProjectModel> projectModels = new ArrayList<>(projectEntities.size());
		
		projectEntities.forEach(entity -> projectModels.add(super.toModel(entity, ProjectModel.class)));
		
		return projectModels;
	}
	
	/**
	 * Fetch project by space identity for scrum meeting cron job.
	 *
	 * @param spaceIdentity the space identity
	 * @return the list
	 */
	public List<ProjectEntity> fetchProjectBySpaceIdentity(String spaceIdentity)
	{
		return repository.fetchProjectBySpaceIdentity(spaceIdentity);
	}
	
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
