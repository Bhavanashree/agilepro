package com.agilepro.persistence.repository.bug;

import java.util.List;

import com.agilepro.commons.TaskStatus;
import com.agilepro.persistence.entity.bug.BugTaskEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.persistence.repository.annotations.UpdateFunction;
import com.yukthi.persistence.repository.annotations.UpdateOperator;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * Bug task repository.
 * 
 * @author Pritam.
 */
public interface IBugTaskRepository  extends IWebutilsRepository<BugTaskEntity>
{
	@RestrictBySpace
	public int updateTaskStatusByBug(@Condition(value = "bug.id") Long bugId, @Field(value = "status") TaskStatus taskStatus);
	
	@RestrictBySpace
	public List<BugTaskEntity> fetchBugTaskByBugId(@Condition(value = "bug.id") Long bugId);
	
	@UpdateFunction
	public boolean addExtraTime(@Condition("id") Long id, @Field(value = "actualTimeTaken", updateOp = UpdateOperator.ADD) Integer timeTaken);

	@RestrictBySpace
	public int updateTaskStatus(@Condition("id") Long id, @Field(value = "status") TaskStatus status);
}
