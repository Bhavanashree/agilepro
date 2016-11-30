package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.bug.BugModel;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.agilepro.persistence.repository.bug.IBugRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class BugService.
 */
@Service
public class BugService extends BaseCrudService<BugEntity, IBugRepository>
{

	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 *  The bug repo.
	 **/
	private IBugRepository bugRepo;

	/**
	 * Instantiates a new bug service.
	 */
	public BugService()
	{
		super(BugEntity.class, IBugRepository.class);
	}

	/**
	 * Initialize the iprojectMemberRepository.
	 */
	@PostConstruct
	private void init()
	{
		bugRepo = repositoryFactory.getRepository(IBugRepository.class);
	}
	
	public int updateBug(BugModel model)
	{
		if(model == null)
		{
			throw new NullValueException("bugmodel Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			super.update(model);
			transaction.commit();

			BugEntity updateEntity = super.fetch(model.getId());
			
			return updateEntity.getVersion();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating model - {}", model);
		}
	}
	
	public List<BugModel> fetchBugsBySprint(Long projectId, Long sprintId)
	{
		List<BugEntity> unAssignedBugs = bugRepo.fetchUnAssignedBugs(projectId);
		List<BugEntity> sprintBugs = bugRepo.fetchBugsBySprintId(projectId, sprintId);
		
		List<BugModel> bugs = new ArrayList<BugModel>();
		
		if(sprintBugs == null)
		{
			sprintBugs = new ArrayList<BugEntity>();
		}
		
		if(unAssignedBugs != null)
		{
			sprintBugs.addAll(unAssignedBugs);
		}
		
		if(sprintBugs != null)
		{
			sprintBugs.forEach(entity -> bugs.add(super.toModel(entity, BugModel.class)));
		}
		
		return bugs;
	}
}
