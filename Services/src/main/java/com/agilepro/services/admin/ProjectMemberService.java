package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.ProjectMemberRole;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.agilepro.controller.response.ProjectMemberReadResponse;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.repository.admin.IProjectMemberRepository;
import com.yukthi.persistence.ITransaction;
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

	private List<ProjectMemberModel> initPhoto(List<ProjectMemberEntity> projectMemberEntities)
	{
		List<ProjectMemberModel> projectMemberModels = new ArrayList<ProjectMemberModel>(projectMemberEntities.size());

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
	 * Fetch project admin members.
	 *
	 * @param projectId
	 *            the project id
	 * @return the project member read response
	 */
	public ProjectMemberReadResponse fetchProjectAdminManagers(Long projectId)
	{
		List<ProjectMemberModel> projectMemberModels = initPhoto(iprojectMemberRepository.fetchAdminManagers(projectId));

		ProjectMemberModel manager = null;

		for(int i = 0; i < projectMemberModels.size(); i++)
		{
			if(projectMemberModels.get(i).getProjectMemberRole().equals(ProjectMemberRole.PROJECT_MANAGER))
			{
				manager = projectMemberModels.get(i);
				projectMemberModels.remove(i);
				break;
			}
		}

		return new ProjectMemberReadResponse(manager, projectMemberModels);
	}

	/**
	 * Fetch project members.
	 *
	 * @param projectId
	 *            the project id
	 * @return the project member read response
	 */
	public ProjectMemberReadResponse fetchProjectMembers(Long projectId)
	{
		List<ProjectMemberModel> projectMemberModels = initPhoto(iprojectMemberRepository.fetchMembers(projectId));

		return new ProjectMemberReadResponse(projectMemberModels);
	}

	/**
	 * Delete by employee.
	 *
	 * @param employeeId
	 *            the employee id
	 */
	public void deleteByEmployee(Long employeeId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			iprojectMemberRepository.deleteByEmployeeId(employeeId);

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while deleting project member - ", ex);
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
