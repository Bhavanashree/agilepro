package com.agilepro.services.pokergame;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.agilepro.persistence.repository.pokergame.IPokerGameRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class PokerGameService.
 */
@Service
public class PokerGameService extends BaseCrudService<PokerGameEntity, IPokerGameRepository>
{
	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;
	
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
	 * The IPokerGameRepository repo.
	 **/
	private IPokerGameRepository pokerGameRepo;

	/**
	 * Instantiates a new ProjectService.
	 */
	public PokerGameService()
	{
		super(PokerGameEntity.class, IPokerGameRepository.class);
	}

	/**
	 * Inits the pokerRepo, IPokerGameRepository.
	 */
	@PostConstruct
	private void init()
	{
		pokerGameRepo = repositoryFactory.getRepository(IPokerGameRepository.class);
	}

	/**
	 * Save new poker game.
	 * 
	 * @param pokerGameModel model object from the controller.
	 * @return newly saved poker game object.
	 */
	public PokerGameEntity saveNewGame(PokerGameModel pokerGameModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Long employeeId = userService.fetch(pokerGameModel.getUserId()).getBaseEntityId();
			
			Long projectMemberId = projectMembers.getProjectMemberId(pokerGameModel.getProjectId(), employeeId);
			
			pokerGameModel.setMemberId(projectMemberId);
			PokerGameEntity gameEntity = super.save(pokerGameModel);
			
			transaction.commit();
			
			return gameEntity;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while saving model - {}", pokerGameModel);
		}
	}
	
	/**
	 * Fetch poker game model object for the provided project id.
	 * 
	 * @param projectId provided project id.
	 * @return matching poker game object.
	 */
	public PokerGameModel isGameStarted(Long projectId)
	{
		return super.toModel(pokerGameRepo.fetchPokerGame(projectId), PokerGameModel.class);
	}
}
