package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IProjectMembersRepository.
 * 
 * @author Pritam
 */
public interface IProjectMemberRepository extends IWebutilsRepository<ProjectMemberEntity>
{
	@RestrictBySpace
	public void deleteByEmployeeId(@Condition(value = "employeeEntity.id") Long id);
	
	@RestrictBySpace
	public List<ProjectMemberEntity> fetchProjectMembers();
}
