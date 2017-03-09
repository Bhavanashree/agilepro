package com.agilepro.services.pokergame;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.entity.pokergame.PokerGameUserEntity;
import com.agilepro.persistence.repository.pokergame.IPokerGameUserRepository;
import com.agilepro.services.admin.EmployeeService;
import com.agilepro.services.admin.ProjectMemberService;
import com.yukthi.persistence.ITransaction;
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
	 * User service for user entity.
	 */
	@Autowired
	private UserService userService;

	/**
	 * The ProjectMembers service.
	 **/
	@Autowired
	private ProjectMemberService projectMemberService;
	
	/**
	 * Employee service.
	 */
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * Instantiates a new ProjectService.
	 */
	public PokerGameUserService()
	{
		super(PokerGameUserEntity.class, IPokerGameUserRepository.class);
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
			Long projectMemberId = projectMemberService.getProjectMemberId(pokerGameUserModel.getProjectId(), employeeId);

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
	 * Fetch the poker game user model.
	 * 
	 * @param userId active user id.
	 * @param pokerGameId game id.
	 * @param projectId project under which game is going on.
	 * @return matching record.
	 */
	public PokerGameUserModel fetchPokerUser(Long userId, Long pokerGameId, Long projectId)
	{
		Long employeeId = userService.fetch(userId).getBaseEntityId();
		Long projectMemberId = projectMemberService.getProjectMemberId(projectId, employeeId);
		
		return super.toModel(repository.fetchPokerUser(projectMemberId, pokerGameId), PokerGameUserModel.class);
	}
	
	/**
	 * Update the newly selected card value.
	 * 
	 * @param pokerGameUserId the poker Game User id.
	 * @param cardValueDisplay new selected value.
	 */
	public void onChangeOfCard(Long pokerGameUserId, String cardValueDisplay)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			repository.updateNewCardValue(pokerGameUserId, Float.valueOf(cardValueDisplay));
			
			transaction.commit();
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating card value in poker game user");
		}
	}
	
	/**
	 * Fetch poker game details.
	 * 
	 * @param pokerGameId provided poker game id.
	 * @return matching records.
	 */
	public List<PokerGameUserModel> fetchPokerGameDetails(Long pokerGameId)
	{
		List<PokerGameUserEntity> pokerGameUserEntities = repository.fetchPokerGameDetails(pokerGameId);
		List<PokerGameUserModel> pokerGameUserModels = new ArrayList<PokerGameUserModel>();
		
		for(PokerGameUserEntity pokerGameUser : pokerGameUserEntities)
		{
			PokerGameUserModel pokerGameUserModel = super.toModel(pokerGameUser, PokerGameUserModel.class);
			
			ProjectMemberEntity projectMemberEntity = projectMemberService.fetch(pokerGameUserModel.getProjectMemberId());
			EmployeeEntity employee = employeeService.fetch(projectMemberEntity.getEmployee().getId());
			
			pokerGameUserModel.setProjectMemberName(employee.getName());
			
			pokerGameUserModels.add(pokerGameUserModel);
		}
		
		return pokerGameUserModels;
	}
}
