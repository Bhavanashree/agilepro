package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.ProjectsEntity;
import com.agilepro.persistence.repository.admin.IProjectRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ProjectsService.
 * 
 * @author Pritam
 */
@Service
public class ProjectsService extends BaseCrudService<ProjectsEntity, IProjectRepository>
{
	/**
	 * Instantiates a new projects service.
	 */
	public ProjectsService()
	{
		super(ProjectsEntity.class, IProjectRepository.class);
	}
	
	/**
	 * Delete all.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
