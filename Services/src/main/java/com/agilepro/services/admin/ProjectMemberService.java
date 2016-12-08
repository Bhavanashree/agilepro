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
import com.yukthi.webutils.common.models.ValueLabel;
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

			employeeModel = employeeService.fetchFullModel(projectMemberModel.getEmployeeId(), EmployeeModel.class);
			if(employeeModel == null)
			{
				throw new IllegalArgumentException("No employee found with id - {} " + projectMemberModel.getEmployeeId());
			}

			projectMemberModel.setPhoto(employeeModel.getPhoto());
			projectMemberModel.setName(employeeModel.getName());

			projectMemberModels.add(projectMemberModel);
		}

		return projectMemberModels;
	}

	/**
	 * Fetch project admin members returns only the admin and manager.
	 *
	 * @param projectId
	 *            the project id
	 * @return the project member read response
	 */
	public ProjectMemberReadResponse fetchProjectAdminManagers(Long projectId)
	{
		List<ProjectMemberModel> projectAdminManagers = initPhoto(iprojectMemberRepository.fetchAdminManagers(projectId));

		ProjectMemberModel manager = null;

		for(int i = 0; i < projectAdminManagers.size(); i++)
		{
			if(projectAdminManagers.get(i).getProjectMemberRole().equals(ProjectMemberRole.PROJECT_MANAGER))
			{
				manager = projectAdminManagers.get(i);
				projectAdminManagers.remove(i);
				break;
			}
		}

		return new ProjectMemberReadResponse(manager, projectAdminManagers);
	}

	/**
	 * Fetch project members by team id.
	 *
	 * @param projectTeamId the project team id
	 * @return the project member read response
	 */
	public ProjectMemberReadResponse fetchMembersByTeam(Long projectTeamId)
	{
		List<ProjectMemberModel> projectMemberModels = initPhoto(iprojectMemberRepository.fetchMembers(projectTeamId));

		return new ProjectMemberReadResponse(projectMemberModels);
	}

	/**
	 * Fetch project members returns all the project members of the provided
	 * project id.
	 *
	 * @param projectId
	 *            the project id
	 * @return the list
	 */
	public List<ProjectMemberModel> fetchProjectMembers(Long projectId)
	{
		return initPhoto(iprojectMemberRepository.fetchProjectMembers(projectId));
	}

	public List<ValueLabel> fetchMembersDropDown(Long projectId)
	{
		return iprojectMemberRepository.fetchMembersDropDown(projectId);
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

	/**
	 * Fetch project members with no space.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 */
	public List<ProjectMemberEntity> fetchProjectMembersWithNoSpace(Long id)
	{
		return iprojectMemberRepository.fetchProjectMembersWithNoSpace(id);
	}
	
	public boolean isProjectMember(Long projectId, Long employeeId)
	{
		return iprojectMemberRepository.isProjectMember(projectId, employeeId) > 0;
	}
	
	/**
	 * Fetchs the project member id.
	 * 
	 * @param projectId provided project id.
	 * @param employeeId provided employee id.
	 * @return the matching project member id.
	 */
	public Long getProjectMemberId(Long projectId, Long employeeId)
	{
		return iprojectMemberRepository.fetchProjectMemberId(projectId, employeeId);
	}
}
