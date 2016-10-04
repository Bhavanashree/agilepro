package com.agilepro.persistence.repository.admin;

import java.util.List;
import com.agilepro.commons.models.project.BasicProjectInfo;
import com.agilepro.persistence.entity.admin.ProjectReleaseEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.SearchResult;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IProjectReleaseRepository.
 * 
 * @author Pritam
 */
public interface IProjectReleaseRepository extends IWebutilsRepository<ProjectReleaseEntity>
{
	@RestrictBySpace
	@SearchResult
	public List<BasicProjectInfo> fetchProjectsByRelease(@Condition(value = "release.id") Long releaseId);

	@RestrictBySpace
	public List<ProjectReleaseEntity> fetchAllProjectRelease();

	@RestrictBySpace
	public boolean deleteByProjectId(@Condition(value = "project.id") Long projectId);
}
