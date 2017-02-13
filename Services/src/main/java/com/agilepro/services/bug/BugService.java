package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.BugStatus;
import com.agilepro.commons.models.bug.BacklogBugModel;
import com.agilepro.commons.models.bug.BugModel;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.repository.bug.IBugRepository;
import com.agilepro.services.project.SprintService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class BugService.
 * 
 * @author Pritam.
 */
@Service
public class BugService extends BaseCrudService<BugEntity, IBugRepository>
{
	/**
	 * SprintService.
	 */
	@Autowired
	private SprintService sprintService;
	
	/**
	 * Bug task service.
	 */
	@Autowired
	private BugTaskService bugTaskService;

	/**
	 * Instantiates a new bug service.
	 */
	public BugService()
	{
		super(BugEntity.class, IBugRepository.class);
	}

	/**
	 * Add the priority and save the bug model.
	 * 
	 * @param bugModel
	 *            provided bug model for save.
	 * @return newly saved bug.
	 */
	public BugEntity saveBug(BugModel bugModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			bugModel.setPriority(repository.getMaxOrder(bugModel.getProjectId()) + 1);

			BugEntity bugEntity = super.save(bugModel);

			transaction.commit();

			return bugEntity;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving bug model - {}", bugModel);
		}
	}

	/**
	 * Update bug with provided sprint.
	 * 
	 * @param multipleBugIds provided multiple bug ids for update.
	 * @param sprintId provided sprint id to set.
	 */
	public void updateBugSprint(Long[] multipleBugIds, Long sprintId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			SprintEntity sprint = null;
			
			// check for null for the case where story to backlogs
			if(sprintId != null)
			{
				sprint = sprintService.fetch(sprintId);
			}
			
			if(multipleBugIds != null && multipleBugIds.length > 0)
			{
				for(Long id : multipleBugIds)
				{
					repository.updateSprint(id, sprint);
				}
			}

			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating bug sprint");
		}
	}

	/**
	 * Fetch bug models by sprint id.
	 * 
	 * @param sprintId
	 *            provided sprint id for fetching the bugs.
	 * @return matching record.
	 */
	public List<BugModel> fetchBugBySprint(Long sprintId)
	{
		List<BugModel> bugModels = new ArrayList<BugModel>();
		List<BugEntity> bugEntities = repository.fetchBugsBySprintId(sprintId);

		bugEntities.forEach(entity -> bugModels.add(super.toModel(entity, BugModel.class)));

		return bugModels;
	}

	/**
	 * Update bug status.
	 * 
	 * @param id provided bug id for update. 
	 * @param bugStatus new bug status to be set.
	 */
	public void updateBugStatus(Long id, BugStatus bugStatus)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			repository.updateStatus(id, bugStatus);

			if(bugStatus.equals(BugStatus.CLOSED))
			{
				bugTaskService.updateTaskStatusByBug(id, BugStatus.CLOSED);
			}

			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating bug status");
		}
	}
	
	/**
	 * Fetch backlogs bugs.
	 * 
	 * @param projectId
	 *            provided project id for fetching the backlog.
	 * @return matching record.
	 */
	public List<BacklogBugModel> fetchBacklogBugs(Long projectId)
	{
		return repository.fetchBacklogBugs(projectId);
	}
}
