package com.agilepro.persistence.repository.admin;

import java.util.List;
import com.agilepro.persistence.entity.admin.ProjectTeamEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RequestParam;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.common.models.ValueLabel;
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
	 * @param projectId
	 *            the project id
	 * @return the list
	 */
	@RestrictBySpace
	List<ProjectTeamEntity> fetchTeamsByProjectId(@Condition(value = "project.id") Long projectId);
	
	@RestrictBySpace
	@LovQuery(name = "fetchTeamsDropDown", valueField = "id", labelField = "name")
	List<ValueLabel> fetchTeamsDropDown(@Condition(value = "project.id") @RequestParam(value = "projectId") Long projectId);
	
	/**
	 * Find team lov.
	 *
	 * @return the list
	 */
	@LovQuery(name = "teamLov", valueField = "id", labelField = "name")
	public List<ValueLabel> findTeamLov();
}
