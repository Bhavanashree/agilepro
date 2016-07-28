package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.repository.admin.IProjectMemberRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
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
	 * The employee service.
	 **/
	@Autowired
	private EmployeeService employeeService;

	/** 
	 * The iproject member repository. 
	 **/
	private IProjectMemberRepository iprojectMemberRepository;
	
	/**
	 * Instantiates a new project members service.
	 */
	public ProjectMemberService()
	{
		super(ProjectMemberEntity.class, IProjectMemberRepository.class);
	}

	/**
	 * Initialize the iprojectMemberRepository.
	 */
	@PostConstruct
	private void init()
	{
		iprojectMemberRepository = repositoryFactory.getRepository(IProjectMemberRepository.class);
	}
	
	/**
	 * Fetch project members.
	 *
	 * @param projectId
	 *            the project id
	 * @return the list
	 */
	public List<ProjectMemberModel> fetchProjectMembers(Long projectId)
	{
		List<ProjectMemberEntity> projectMemberEntities = iprojectMemberRepository.fetchProjMemByProjId(projectId);

		List<ProjectMemberModel> projectMemberModels = new ArrayList<ProjectMemberModel>();

		ProjectMemberModel projectMemberModel;

		EmployeeModel employeeModel;

		for(ProjectMemberEntity pentity : projectMemberEntities)
		{
			projectMemberModel = super.toModel(pentity, ProjectMemberModel.class);

			employeeModel = employeeService.fetchEmployee(projectMemberModel.getEmployeeId());

			projectMemberModel.setPhoto(employeeModel.getPhoto());
			projectMemberModel.setName(employeeModel.getName());

			projectMemberModels.add(projectMemberModel);
		}
		return projectMemberModels;
	}

	/**
	 * Delete by employee id.
	 *
	 * @param id
	 *            the id
	 */
	public void deleteByEmployeeId(Long id)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			iprojectMemberRepository.deleteByEmployeeId(id);
			
			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while deleting project member where employee id - {}", id);
		}
	}
	
	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
