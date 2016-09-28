package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.ProjectReleaseModel;
import com.agilepro.persistence.entity.admin.ProjectReleaseEntity;
import com.agilepro.persistence.repository.admin.IProjectReleaseRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ProjectReleaseService.
 * 
 * @author Pritam
 */
@Service
public class ProjectReleaseService extends BaseCrudService<ProjectReleaseEntity, IProjectReleaseRepository>
{
	/**
	 * The iproject release repository.
	 **/
	private IProjectReleaseRepository iprojectReleaseRepository;

	/**
	 * Instantiates a new project release service.
	 */
	public ProjectReleaseService()
	{
		super(ProjectReleaseEntity.class, IProjectReleaseRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		iprojectReleaseRepository = repositoryFactory.getRepository(IProjectReleaseRepository.class);
	}

	/**
	 * Fetch all project release.
	 *
	 * @param releaseId
	 *            the release id
	 * @return the list
	 */
	public List<ProjectReleaseModel> fetchAllProjectRelease(Long releaseId)
	{
		List<ProjectReleaseEntity> projectReleaseEntities = iprojectReleaseRepository.fetchAllProjectRelease(releaseId);

		List<ProjectReleaseModel> projectReleaseModels = new ArrayList<ProjectReleaseModel>(projectReleaseEntities.size());

		projectReleaseEntities.forEach(entity -> projectReleaseModels.add(super.toModel(entity, ProjectReleaseModel.class)));

		return projectReleaseModels;
	}
}
