package com.agilepro.persistence.repository.bug;

import java.util.List;

import com.agilepro.persistence.entity.bug.BugAttachmentEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.repository.IWebutilsRepository;

public interface IBugAttachmentRepository extends IWebutilsRepository<BugAttachmentEntity>
{
	public List<BugAttachmentEntity> fetchAttachmentByBugId(@Condition(value = "bug.id") Long bugId);
}
