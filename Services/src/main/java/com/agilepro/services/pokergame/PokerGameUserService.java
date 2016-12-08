package com.agilepro.services.pokergame;

import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.PokerGameStatusModel;
import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.persistence.entity.pokergame.PokerGameUserEntity;
import com.agilepro.persistence.repository.pokergame.IPokerGameUserRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class PokerGameUserService.
 */
@Service
public class PokerGameUserService extends BaseCrudService<PokerGameUserEntity, IPokerGameUserRepository>
{
	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * Poker game user repo.
	 */
	private IPokerGameUserRepository pokerUserRepo;

	/**
	 * User service for user entity.
	 */
	@Autowired
	private UserService userService;

	/**
	 * The ProjectMembers service.
	 **/
	@Autowired
	private ProjectMemberService projectMembers;

	/**
	 * Instantiates a new ProjectService.
	 */
	public PokerGameUserService()
	{
		super(PokerGameUserEntity.class, IPokerGameUserRepository.class);
	}

	/**
	 * Inits the pokerRepo, IPokerGameRepository.
	 */
	@PostConstruct
	private void init()
	{
		pokerUserRepo = repositoryFactory.getRepository(IPokerGameUserRepository.class);
	}

	/**
	 * Save new poker game user with project member id.
	 * 
	 * @param pokerGameUserModel
	 *            provided model object for save.
	 * @return newly saved poker game entity object.
	 */
	public PokerGameUserEntity savePokerGameUser(PokerGameUserModel pokerGameUserModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Long employeeId = userService.fetch(pokerGameUserModel.getUserId()).getBaseEntityId();

			Long projectMemberId = projectMembers.getProjectMemberId(pokerGameUserModel.getProjectId(), employeeId);

			pokerGameUserModel.setProjectMemberId(projectMemberId);
			PokerGameUserEntity userGameEntity = super.save(pokerGameUserModel);

			transaction.commit();

			return userGameEntity;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving model - {}", pokerGameUserModel);
		}
	}

	/**
	 * Read poker game status which will be called every interval of time.
	 * 
	 * @param pokerGameId
	 *            poker game id for which poker game status is to fetched.
	 * @return list of poker game status.
	 */
	public List<PokerGameStatusModel> readPokerGameStatus(Long pokerGameId)
	{
		return pokerUserRepo.fetchGameStatus(pokerGameId);
	}
}
