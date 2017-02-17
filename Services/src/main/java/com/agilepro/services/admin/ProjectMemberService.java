package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agilepro.commons.ProjectMemberRole;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.agilepro.controller.response.ProjectMemberReadResponse;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.repository.admin.IProjectMemberRepository;
import com.yukthi.persistence.ITransaction;
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
	 * The employee service.
	 **/
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Instantiates a new project members service.
	 */
	public ProjectMemberService()
	{
		super(ProjectMemberEntity.class, IProjectMemberRepository.class);
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
		List<ProjectMemberModel> projectAdminManagers = initPhoto(repository.fetchAdminManagers(projectId));

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
		List<ProjectMemberModel> projectMemberModels = initPhoto(repository.fetchMembers(projectTeamId));

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
		return initPhoto(repository.fetchProjectMembers(projectId));
	}

	public List<ValueLabel> fetchMembersDropDown(Long projectId)
	{
		return repository.fetchMembersDropDown(projectId);
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
			repository.deleteByEmployeeId(employeeId);

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
		return repository.fetchProjectMembersWithNoSpace(id);
	}
	
	public boolean isProjectMember(Long projectId, Long employeeId)
	{
		return repository.isProjectMember(projectId, employeeId) > 0;
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
		return repository.fetchProjectMemberId(projectId, employeeId);
	}
}
