package com.agilepro.services.pokergame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.agilepro.persistence.repository.pokergame.IPokerGameRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.yukthi.persistence.ITransaction;
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
	 * The poker game user service.
	 */
	@Autowired
	private PokerGameUserService pokerGameUserService;
	
	/**
	 * Instantiates a new ProjectService.
	 */
	public PokerGameService()
	{
		super(PokerGameEntity.class, IPokerGameRepository.class);
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
			
			pokerGameUserService.savePokerGameUser(new PokerGameUserModel(gameEntity.getId(), pokerGameModel.getProjectId(), pokerGameModel.getUserId()));
			
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
		return super.toModel(repository.fetchPokerGame(projectId), PokerGameModel.class);
	}
}
