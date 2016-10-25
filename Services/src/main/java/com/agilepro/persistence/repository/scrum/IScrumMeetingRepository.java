package com.agilepro.persistence.repository.scrum;

import com.agilepro.persistence.entity.scrum.ScrumMeetingEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IScrumMeetingRepository.
 * 
 * @author Pritam
 */
public interface IScrumMeetingRepository extends IWebutilsRepository<ScrumMeetingEntity> 
{
	@RestrictBySpace
	public ScrumMeetingEntity fetchByProjectId(@Condition(value = "project.id") Long projectId);
}
