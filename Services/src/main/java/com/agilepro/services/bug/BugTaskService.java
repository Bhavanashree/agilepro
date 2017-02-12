package com.agilepro.services.bug;

import com.agilepro.persistence.entity.bug.BugTaskEntity;
import com.agilepro.persistence.repository.bug.IBugTaskRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * Service class to interact with bug task table.
 * 
 * @author Pritam.
 */
public class BugTaskService extends BaseCrudService<BugTaskEntity, IBugTaskRepository>
{
	public BugTaskService()
	{
		super(BugTaskEntity.class, IBugTaskRepository.class);
	}
}
