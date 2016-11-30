package com.agilepro.persistence.repository.scrum;

import java.util.List;

import com.agilepro.commons.ScrumActionStatus;
import com.agilepro.persistence.entity.scrum.ScrumActionItemEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IScrumActionItemRepository, for table creation and queries. 
 * 
 * @author Pritam
 */
public interface IScrumActionItemRepository extends IWebutilsRepository<ScrumActionItemEntity>
{
	public List<ScrumActionItemEntity> fetchByScrumId(@Condition(value = "scrumMeeting.id") Long scrumMeetingId); 
	
	public boolean updateActionStatus(@Field("scrumActionStatus") ScrumActionStatus actionStatus, @Condition("id") Long actionItemId);
}
