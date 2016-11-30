package com.agilepro.commons.models.pokergame;

import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class PokerGameUserModel.
 */
@ExtendableModel(name = "PokerGameUser")
@Model
public class PokerGameUserModel extends AbstractExtendableModel
{

	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The version.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * The members.
	 **/
	private Long members;

	/**
	 * The poker game.
	 **/
	private Long pokerGame;

	/**
	 * The card value.
	 */
	private Integer cardValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.common.IExtendableModel#getId()
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * Gets the members.
	 *
	 * @return the members
	 */
	public Long getMembers()
	{
		return members;
	}

	/**
	 * Sets the members.
	 *
	 * @param members
	 *            the new members
	 */
	public void setMembers(Long members)
	{
		this.members = members;
	}

	/**
	 * Gets the poker game.
	 *
	 * @return the poker game
	 */
	public Long getPokerGame()
	{
		return pokerGame;
	}

	/**
	 * Sets the poker game.
	 *
	 * @param pokerGame
	 *            the new poker game
	 */
	public void setPokerGame(Long pokerGame)
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
