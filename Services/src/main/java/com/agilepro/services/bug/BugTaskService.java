package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.agilepro.commons.BugStatus;
import com.agilepro.commons.TaskStatus;
import com.agilepro.commons.models.bug.BugTaskModel;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.commons.models.project.TaskRecords;
import com.agilepro.persistence.entity.bug.BugTaskEntity;
import com.agilepro.persistence.repository.bug.IBugTaskRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * Service class to interact with bug task table.
 * 
 * @author Pritam.
 */
@Service
public class BugTaskService extends BaseCrudService<BugTaskEntity, IBugTaskRepository>
{
	public BugTaskService()
	{
		super(BugTaskEntity.class, IBugTaskRepository.class);
	}
	
	/**
	 * Update bug task status on according to bug status.
	 * 
	 * @param bugId provide bug id under which bug task needed to be updated.
	 * @param bugStatus new bug status.
	 */
	public void updateTaskStatusByBug(Long bugId, BugStatus bugStatus)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			repository.updateTaskStatusByBug(bugId, TaskStatus.COMPLETED);

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while updating task of provided bug", ex);
		}
	}
	
	/**
	 * Fetch bug task models by bug id.
	 * 
	 * @param bugId provided bug id for which bug task is to be fetched.
	 * @return matching records.
	 */
	public List<BugTaskModel> fetchBugTaskByBugId(Long bugId)
	{
		List<BugTaskEntity> bugTaskEntities = repository.fetchBugTaskByBugId(bugId);
		List<BugTaskModel> bugTaskModels = new ArrayList<BugTaskModel>();

		bugTaskEntities.forEach(entity -> bugTaskModels.add(super.toModel(entity, BugTaskModel.class)));
		
		return bugTaskModels;
	}
	
	/**
	 * Update task changes.
	 * 
	 * @param taskChangesModel
	 *            new update task changes.
	 */
	public void updateTaskChanges(TaskChangesModel taskChangesModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Map<Long, TaskRecords> changes = taskChangesModel.getTaskChanges();

			for(Long key : changes.keySet())
			{
				TaskRecords taskRecords = changes.get(key);

				Integer actualTime = taskRecords.getActualTime();

				if(actualTime != null)
				{
					if(actualTime <= 0)
					{
						throw new IllegalStateException("Actual time should be greater than 0");
					}

					repository.addExtraTime(key, taskRecords.getActualTime());
				}

				if(taskRecords.getTaskStatus() != null)
				{
					repository.updateTaskStatus(key, taskRecords.getTaskStatus());
				}
			}

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while updating task - " + taskChangesModel, ex);
		}
	}
}
