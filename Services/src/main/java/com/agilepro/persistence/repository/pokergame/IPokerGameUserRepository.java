package com.agilepro.persistence.repository.pokergame;

import java.util.List;
import com.agilepro.commons.models.pokergame.PokerGameStatusModel;
import com.agilepro.persistence.entity.pokergame.PokerGameUserEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.SearchResult;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * IPokerGameUserRepository for queries related to PokerGameUser table.
 * 
 * @author Pritam.
 */
public interface IPokerGameUserRepository extends IWebutilsRepository<PokerGameUserEntity>
{
	@RestrictBySpace
	@SearchResult
	List<PokerGameStatusModel> fetchGameStatus(@Condition(value = "pokerGame.id") Long pokerGameId);
}
