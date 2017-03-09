package com.agilepro.persistence.repository.pokergame;

import java.util.List;

import com.agilepro.persistence.entity.pokergame.PokerGameUserEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * IPokerGameUserRepository for queries related to PokerGameUser table.
 * 
 * @author Pritam.
 */
public interface IPokerGameUserRepository extends IWebutilsRepository<PokerGameUserEntity>
{
	/**
	 * Checks whether the active user has already joined the game or not.
	 * 
	 * @param projectMemberId activeUser as a project member.
	 * @param pokerGameId game id.
	 * @return 1 for matching record else 0.
	 */
	@RestrictBySpace
	public PokerGameUserEntity fetchPokerUser(@Condition(value = "projectMember.id") Long projectMemberId, @Condition(value = "pokerGame.id") Long pokerGameId);
	
	/**
	 * Update the selected card value.
	 * 
	 * @param id provided record id for which card value is to be updated.
	 * @param cardValue new card value.
	 * @return number of update records.
	 */
	@RestrictBySpace
	public int updateNewCardValue(@Condition(value = "id") Long id, @Field(value = "cardValue") Float cardValue);
	
	/**
	 * Fetch poker game details.
	 * 
	 * @param pokerGameId provided poker game id, mapping id with poker game table.
	 * @return matching records.
	 */
	public List<PokerGameUserEntity> fetchPokerGameDetails(@Condition(value = "pokerGame.id") Long pokerGameId);
}
