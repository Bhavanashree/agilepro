package com.agilepro.persistence.entity.pokergame;

import javax.persistence.Column;
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
	@Column(name = "POKER_MEMBERS")
	@PropertyMapping(type = PokerGameUserModel.class, from = "members", subproperty = "id")
	private ProjectMemberEntity members;

	/**
	 * The poker game.
	 **/
	@OneToOne
	@PropertyMapping(type = PokerGameUserModel.class, from = "pokerGame", subproperty = "id")
	@Column(name = "POKER_POKERGAME")
	private PokerGameEntity pokerGame;

	/**
	 * The card value.
	 **/
	@Column(name = "POKERGAME_CARD_VALUE")
	private Integer cardValue;
	
	/**
	 * Gets the members.
	 *
	 * @return the members
	 */
	public ProjectMemberEntity getMembers()
	{
		return members;
	}

	/**
	 * Sets the members.
	 *
	 * @param members
	 *            the new members
	 */
	public void setMembers(ProjectMemberEntity members)
	{
		this.members = members;
	}

	/**
	 * Gets the poker game.
	 *
	 * @return the poker game
	 */
	public PokerGameEntity getPokerGame()
	{
		return pokerGame;
	}

	/**
	 * Sets the poker game.
	 *
	 * @param pokerGame
	 *            the new poker game
	 */
	public void setPokerGame(PokerGameEntity pokerGame)
	{
		this.pokerGame = pokerGame;
	}

	/**
	 * Gets the card value.
	 *
	 * @return the card value
	 */
	public Integer getCardValue()
	{
		return cardValue;
	}

	/**
	 * Sets the card value.
	 *
	 * @param cardValue
	 *            the new card value
	 */
	public void setCardValue(Integer cardValue)
	{
		this.cardValue = cardValue;
	}
}
