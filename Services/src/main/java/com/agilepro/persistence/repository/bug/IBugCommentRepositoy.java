package com.agilepro.persistence.repository.bug;

import java.util.List;

import com.agilepro.persistence.entity.bug.BugCommentsEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IBugCommentRepositoy.
 */
public interface IBugCommentRepositoy extends IWebutilsRepository<BugCommentsEntity>
{
	@RestrictBySpace
	public List<BugCommentsEntity> fetchbugById(@Condition(value = "bug.id") Long bugId);
}
