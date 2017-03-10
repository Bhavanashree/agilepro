package com.agilepro.persistence.repository.pokergame;

import com.agilepro.commons.PokerGameStatus;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.entity.pokergame.PokerGameEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
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
	public int updateStoryId(@Condition(value = "project.id") Long projectId, @Field(value = "story") StoryEntity story);
	
	@RestrictBySpace
	public int updateBugId(@Condition(value = "project.id") Long projectId, @Field(value = "bug") BugEntity bug);
	
	@RestrictBySpace
	public int updateGameStatus(@Condition(value = "id") Long id, @Field(value = "pokerGameStatus") PokerGameStatus pokerGameStatus);
}
