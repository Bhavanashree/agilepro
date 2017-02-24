package com.agilepro.persistence.repository.pokergame;

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
	
	@RestrictBySpace
	public int updateNewCardValue(@Condition(value = "id") Long id, @Field(value = "cardValue") Float cardValue);
}
