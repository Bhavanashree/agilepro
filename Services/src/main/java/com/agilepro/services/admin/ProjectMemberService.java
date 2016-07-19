package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.ProjectMemberModel;
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
	private RepositoryFactory repositoryFactory;
	
	/**
	 * Instantiates a new project members service.
	 */
	public ProjectMemberService()
	{
		super(ProjectMemberEntity.class, IProjectMemberRepository.class);
	}
	
	public List<ProjectMemberModel> fetchProjectMembers()
	{
		IProjectMemberRepository iprojectMemberRepository = repositoryFactory.getRepository(IProjectMemberRepository.class);
		
		List<ProjectMemberEntity> projectMemberEntities =  iprojectMemberRepository.fetchProjectMembers();
		
		List<ProjectMemberModel> projectMemberModels = new ArrayList<>();
		
		for(ProjectMemberEntity pEntity : projectMemberEntities)
		{
			projectMemberModels.add(super.toModel(pEntity, ProjectMemberModel.class));
		}
		
		return projectMemberModels;
	}
	
	public void deleteProjectMember(Long id)
	{
		IProjectMemberRepository iprojectMemberRepository = repositoryFactory.getRepository(IProjectMemberRepository.class);
		
		iprojectMemberRepository.deleteByEmployeeId(id);
	}
}
