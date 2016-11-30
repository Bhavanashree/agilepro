package com.agilepro.persistence.repository.pokergame;

import java.util.List;

import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IPokerGameRepository.
 */
public interface IPokerGameRepository extends IWebutilsRepository<PokerGameEntity>
{
	@RestrictBySpace
	public List<PokerGameEntity> fetchGamesByProjectId(@Condition(value = "project.id") Long projectId);
}
