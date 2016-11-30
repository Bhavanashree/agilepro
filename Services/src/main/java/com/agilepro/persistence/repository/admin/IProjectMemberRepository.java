package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.yukthi.persistence.repository.annotations.AggregateFunction;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.MethodConditions;
import com.yukthi.persistence.repository.annotations.NullCheck;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RequestParam;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IProjectMembersRepository.
 * 
 * @author Pritam
 */
public interface IProjectMemberRepository extends IWebutilsRepository<ProjectMemberEntity>
{
	/**
	 * Fetch admin managers.
	 *
	 * @param projectId
	 *            the project id
	 * @return the list
	 */
	@RestrictBySpace
	@MethodConditions(nullChecks = @NullCheck(field = "projectTeam.id"))
	public List<ProjectMemberEntity> fetchAdminManagers(@Condition(value = "project.id") Long projectId);

	/**
	 * Fetch members by team.
	 *
	 * @param projectTeamId
	 *            the project team id
	 * @return the list
	 */
	@RestrictBySpace
	public List<ProjectMemberEntity> fetchMembers(@Condition(value = "projectTeam.id") Long projectTeamId);

	@RestrictBySpace
	public List<ProjectMemberEntity> fetchProjectMembers(@Condition(value = "project.id") Long projectId);
	
	@RestrictBySpace
	@LovQuery(name = "fetchMembersDropDown", valueField = "id", labelField = "projectMemberRole")
	public List<ValueLabel> fetchMembersDropDown(@RequestParam(value = "projectId") @Condition(value = "project.id") Long projectId);

	/**
	 * Fetch project members with no space for cron job creating scrum meeting
	 * per day per project.
	 *
	 * @param projectId
	 *            the project id
	 * @return the list
	 */
	public List<ProjectMemberEntity> fetchProjectMembersWithNoSpace(@Condition(value = "project.id") Long projectId);

	@RestrictBySpace
	public boolean deleteByEmployeeId(@Condition(value = "employee.id") Long employeeId);

	@AggregateFunction
	public int isProjectMember(@Condition(value = "project.id") Long projectId, @Condition("employee.id") Long employeeId);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
