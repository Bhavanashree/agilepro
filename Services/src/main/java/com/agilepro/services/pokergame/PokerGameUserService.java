package com.agilepro.services.pokergame;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.agilepro.persistence.entity.pokergame.PokerGameUserEntity;
import com.agilepro.persistence.repository.pokergame.IPokerGameUserRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
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
	 * The ProjectMembers service.
	 **/
	@Autowired
	private ProjectMemberService projectMembers;

	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The IPokerGameRepository repo.
	 **/
	private IPokerGameUserRepository pokerUserRepo;

	/**
	 * The user service.
	 **/
	@Autowired
	private CurrentUserService useCurrentService;

	/**
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

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
	 * Savepokeruser.
	 *
	 * @param pokerModel
	 *            the poker model
	 * @return the poker game user entity
	 */
	public PokerGameUserEntity savepokeruser(PokerGameUserModel pokerModel)
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		Long userId = cbiller.getCustomerId();

		pokerModel.setMembers(userId);

		PokerGameEntity pokerGame = new PokerGameEntity();

		// pokerModel.setPokerGame(pokerGame);

		return super.save(pokerModel);
	}

	/**
	 * Sets the card series.
	 *
	 * @param userId
	 *            the user id
	 * @param cardValue
	 *            the card value
	 * @return the poker game user model
	 */
	public PokerGameUserModel setCardSeries(Long userId, Integer cardValue)
	{

		return null;
	}

	/**
	 * Save poker game u ser.
	 *
	 * @param pokerUserModel
	 *            the poker user model
	 * @param gameId
	 *            the game id
	 * @param memberId
	 *            the member id
	 */
	public void savePokerGameUSer(PokerGameUserModel pokerUserModel, Long gameId, Long memberId)
	{
		PokerGameUserEntity pokerUserEntity = null;

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			AgileProUserDetails userDetails = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

			pokerUserModel.setMembers(userDetails.getCustomerId());
			pokerUserModel.setPokerGame(gameId);

			pokerUserEntity = super.save(pokerUserModel);
			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while saving  story - ", ex);
		}
	}

	/**
	 * Fetchcomments by bug id.
	 *
	 * @param gameId
	 *            the game id
	 * @param memberId
	 *            the member id
	 * @return the list
	 */
	public List<PokerGameUserModel> fetchGameIdUserId(Long gameId, Long memberId)
	{
		List<PokerGameUserModel> pokerGameUserModel = null;

		pokerUserRepo = repositoryFactory.getRepository(IPokerGameUserRepository.class);
		List<PokerGameUserEntity> pokerUserentities = pokerUserRepo.fetchGameDetailsByGameId(gameId, memberId);

		PokerGameUserModel PokerGameUser = null;

		if(pokerUserentities != null)
		{
			pokerGameUserModel = new ArrayList<PokerGameUserModel>(pokerUserentities.size());

			for(PokerGameUserEntity entity : pokerUserentities)
			{
				PokerGameUser = super.toModel(entity, PokerGameUserModel.class);
				pokerGameUserModel.add(PokerGameUser);
			}
		}

		return pokerGameUserModel;
	}
}
