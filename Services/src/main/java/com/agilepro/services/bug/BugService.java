package com.agilepro.services.bug;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.repository.bug.IBugRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
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
}
