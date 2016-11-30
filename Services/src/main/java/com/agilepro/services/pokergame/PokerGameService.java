package com.agilepro.services.pokergame;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.agilepro.persistence.repository.pokergame.IPokerGameRepository;
import com.agilepro.services.admin.ProjectMemberService;
import com.agilepro.services.project.StoryService;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

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
	 * The ProjectMembers service.
	 **/
	@Autowired
	private ProjectMemberService projectMembers;

	/**
	 * The game repo.
	 */
	private IPokerGameRepository gameRepo;

	/**
	 * The story service.
	 */
	@Autowired
	private StoryService storyService;

	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The IPokerGameRepository repo.
	 **/
	private IPokerGameRepository pokerRepo;

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
		pokerRepo = repositoryFactory.getRepository(IPokerGameRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the poker game entity
	 */
	public PokerGameEntity save(PokerGameModel model)
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		PokerGameEntity pokerEntity = WebUtils.convertBean(model, PokerGameEntity.class);

		super.save(pokerEntity, model);
		// TODO:
		return pokerEntity;
	}

	/**
	 * Sets the card series.
	 *
	 * @param userId
	 *            the user id
	 * @param cardValue
	 *            the card value
	 * @return the poker game entity
	 */
	public PokerGameEntity setCardSeries(Long userId, Integer cardValue)
	{
		AgileProUserDetails cbiller = (AgileProUserDetails) currentUserService.getCurrentUserDetails();

		userId = cbiller.getCustomerId();

		return null;
	}

	/**
	 * Fetchgames.
	 *
	 * @param project
	 *            the project
	 * @return the list
	 */
	public List<PokerGameModel> fetchgames(Long project)
	{
		List<PokerGameModel> pokerGameModel = new ArrayList<PokerGameModel>();

		gameRepo.fetchGamesByProjectId(project).forEach(entity -> pokerGameModel.add(super.toModel(entity, PokerGameModel.class)));
		return pokerGameModel;
	}
}
