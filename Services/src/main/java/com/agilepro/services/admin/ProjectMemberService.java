package com.agilepro.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.repository.admin.IProjectMemberRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class ProjectMemberService.
 * 
 * @author Pritam
 */
@Service
public class ProjectMemberService extends BaseCrudService<ProjectMemberEntity, IProjectMemberRepository>
{
	/** 
	 * The repository factory. 
	 **/
	@Autowired
	RepositoryFactory repositoryFactory;
	
	/**
	 * Instantiates a new project members service.
	 */
	public ProjectMemberService()
	{
		super(ProjectMemberEntity.class, IProjectMemberRepository.class);
	}
	
	public void deleteProjectMember(Long id)
	{
		IProjectMemberRepository iprojectMemberRepository = repositoryFactory.getRepository(IProjectMemberRepository.class);
		
		iprojectMemberRepository.deleteByEmployee(id);
	}
}
