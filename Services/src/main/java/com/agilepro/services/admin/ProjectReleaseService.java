package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.ProjectReleaseEntity;
import com.agilepro.persistence.repository.admin.IProjectReleaseRepository;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class ProjectReleaseService extends BaseCrudService<ProjectReleaseEntity, IProjectReleaseRepository>
{
	public ProjectReleaseService()
	{
		super(ProjectReleaseEntity.class, IProjectReleaseRepository.class);
	}
}
