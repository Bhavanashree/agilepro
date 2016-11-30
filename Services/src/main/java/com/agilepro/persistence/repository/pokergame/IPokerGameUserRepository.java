package com.agilepro.persistence.repository.pokergame;

import java.util.List;

import com.agilepro.persistence.entity.pokergame.PokerGameUserEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IPokerGameUserRepository.
 */
public interface IPokerGameUserRepository extends IWebutilsRepository<PokerGameUserEntity>
{
	
	/**
	 * Fetch game details by game id.
	 *
	 * @param pokerGameId
	 *            the poker game id
	 * @param memberId
	 *            the member id
	 * @return the list 
	 */
	@RestrictBySpace
	public List<PokerGameUserEntity> fetchGameDetailsByGameId(@Condition(value = "pokerGame.id") Long pokerGameId, @Condition(value = "members.id") Long memberId);

}
