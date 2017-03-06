package com.agilepro.persistence.repository.pokergame;

import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * IPokerGameRepository for queries related to PokerGame table.
 * 
 * @author Pritam.
 */
public interface IPokerGameRepository extends IWebutilsRepository<PokerGameEntity>
{
	/**
	 * Fetch the poker game for provided project id.
	 * 
	 * @param projectId for which poker game is to be fetched.
	 * @return the matching poker game.
	 */
	@RestrictBySpace
	public PokerGameEntity fetchPokerGame(@Condition(value = "project.id") Long projectId);
	
	@RestrictBySpace
	public int updateStoryId(@Condition(value = "project.id") Long projectId, @Field(value = "story") Long storyId);
	
	@RestrictBySpace
	public int updateBugId(@Condition(value = "project.id") Long projectId, @Field(value = "bug") Long bugId);
}
