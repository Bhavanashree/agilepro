package com.agilepro.persistence.entity.pokergame;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class PokerGameUserEntity.
 */
@ExtendableEntity(name = "PokerGameUser")
@Table(name = "POKER_GAME_USER")
public class PokerGameUserEntity extends WebutilsExtendableEntity
{
	/**
	 * The members.
	 **/
	@OneToOne
	@Column(name = "PROJECT_MEMBER_ID", nullable = false)
	@PropertyMapping(type = PokerGameUserModel.class, from = "projectMemberId", subproperty = "id")
	private ProjectMemberEntity projectMember;

	/**
	 * The poker game.
	 **/
	@ManyToOne
	@PropertyMapping(type = PokerGameUserModel.class, from = "pokerGameId", subproperty = "id")
	@Column(name = "POKER_GAME_ID", nullable = false)
	private PokerGameEntity pokerGame;

	/**
	 * Card value is the story point selected by user for a story or bug.
	 */
	@Column(name = "CARD_VALUE")
	private Float cardValue;

	/**
	 * Gets the project member.
	 * 
	 * @return the project members.
	 */
	public ProjectMemberEntity getProjectMember()
	{
		return projectMember;
	}

	/**
	 * Sets the project member for mapping with project member id.
	 * 
	 * @param projectMember the new project member.
	 */
	public void setProjectMember(ProjectMemberEntity projectMember)
	{
		this.projectMember = projectMember;
	}

	/**
	 * Gets the poker game.
	 * 
	 * @return the poker game.
	 */
	public PokerGameEntity getPokerGame()
	{
		return pokerGame;
	}

	/**
	 * Sets the poker game for mapping with poker game id.
	 * 
	 * @param pokerGame the new poker game.
	 */
	public void setPokerGame(PokerGameEntity pokerGame)
	{
		this.pokerGame = pokerGame;
	}

	/**
	 * Gets the card value.
	 * 
	 * @return the card value.
	 */
	public Float getCardValue()
	{
		return cardValue;
	}

	/**
	 * Sets the card value which is the story point of selected story.
	 * 
	 * @param cardValue the new card value.
	 */
	public void setCardValue(Float cardValue)
	{
		this.cardValue = cardValue;
	}
}
