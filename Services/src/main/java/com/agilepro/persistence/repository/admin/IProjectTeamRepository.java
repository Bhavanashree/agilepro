package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.admin.ProjectTeamEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IProjectTeamRepository.
 * 
 * @author Pritam
 */
public interface IProjectTeamRepository extends IWebutilsRepository<ProjectTeamEntity>
{
	/**
	 * Fetch teams by project id.
	 *
	 * @param projectId the project id
	 * @return the list
	 */
	List<ProjectTeamEntity> fetchTeamsByProjectId(@Condition(value = "project.id") Long projectId);
}
