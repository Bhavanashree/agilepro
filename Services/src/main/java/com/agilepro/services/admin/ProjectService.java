package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.repository.admin.IProjectRepository;
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
}
