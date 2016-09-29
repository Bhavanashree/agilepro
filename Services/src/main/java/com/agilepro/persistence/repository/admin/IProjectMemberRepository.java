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
	/**
	 * Fetch proj mem by proj id.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 */
	@RestrictBySpace
	public List<ProjectMemberEntity> fetchProjMemByProjId(@Condition(value = "project.id") Long id);

	/**
	 * Delete all.
	 */
	public void deleteAll();
}
