package com.agilepro.commons.models.pokergame;

import com.agilepro.commons.GameSeries;
import com.agilepro.commons.PokerGameStatus;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * PokerGameModel class for mapping the values from ui with entity class.
 * 
 * @author Pritam.
 */
@ExtendableModel(name = "PokerGame")
@Model(name = "PokerGame")
public class PokerGameModel extends AbstractExtendableModel
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
	 * The game series.
	 **/
	@Required
	private GameSeries gameSeries;

	/**
	 * Status of the story in poker game.
	 */
	@Required
	private PokerGameStatus pokerGameStatus;

	/**
	 * The Average.
	 **/
	private Integer average;

	/**
	 * The story id.
	 **/
	private Long storyId;

	/**
	 * Project id for mapping.
	 */
	@Required
	private Long projectId;

	/**
	 * Project member id for mapping.
	 */
	private Long memberId;

	/**
	 * User id for fetching the project member id.
	 */
	@Required
	private Long userId;

	/**
	 * Number of cards to be displayed.
	 */
	@Required
	private Integer numberOfCards;

	/**
	 * Bug id.
	 */
	private Long bugId;

	/**
	 * Poker game user model.
	 */
	@IgnoreField
	private PokerGameUserModel pokerGameUserModel;

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
	 * Gets the game series.
	 *
	 * @return the game series
	 */
	public GameSeries getGameSeries()
	{
		return gameSeries;
	}

	/**
	 * Sets the game series.
	 *
	 * @param gameSeries
	 *            the new game series
	 */
	public void setGameSeries(GameSeries gameSeries)
	{
		this.gameSeries = gameSeries;
	}

	/**
	 * Gets the average.
	 *
	 * @return the average
	 */
	public Integer getAverage()
	{
		return average;
	}

	/**
	 * Sets the average.
	 *
	 * @param average
	 *            the new average
	 */
	public void setAverage(Integer average)
	{
		this.average = average;
	}

	/**
	 * Gets the story id.
	 * 
	 * @return the story id.
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 * 
	 * @param storyId
	 *            the new story id.
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}

	/**
	 * Gets the project id.
	 * 
	 * @return the project id.
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 * 
	 * @param projectId
	 *            the new project id.
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the project member id.
	 * 
	 * @return the project member id.
	 */
	public Long getMemberId()
	{
		return memberId;
	}

	/**
	 * Sets the project member id.
	 * 
	 * @param memberId
	 *            the new project member id.
	 */
	public void setMemberId(Long memberId)
	{
		this.memberId = memberId;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id.
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the new user id.
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * Gets the poker game status.
	 * 
	 * @return the poker game status.
	 */
	public PokerGameStatus getPokerGameStatus()
	{
		return pokerGameStatus;
	}

	/**
	 * Sets the poker game status.
	 * 
	 * @param pokerGameStatus
	 *            the new poker game status.
	 */
	public void setPokerGameStatus(PokerGameStatus pokerGameStatus)
	{
		this.pokerGameStatus = pokerGameStatus;
	}

	/**
	 * Gets number of cards.
	 * 
	 * @return the number of cards.
	 */
	public Integer getNumberOfCards()
	{
		return numberOfCards;
	}

	/**
	 * Sets the number of cards.
	 * 
	 * @param numberOfCards
	 *            the new number of cards.
	 */
	public void setNumberOfCards(Integer numberOfCards)
	{
		this.numberOfCards = numberOfCards;
	}

	/**
	 * Gets the bug id.
	 * 
	 * @return the bug id.
	 */
	public Long getBugId()
	{
		return bugId;
	}

	/**
	 * Set the bug id.
	 * 
	 * @param bugId
	 *            the new bug id.
	 */
	public void setBugId(Long bugId)
	{
		this.bugId = bugId;
	}

	/**
	 * gets the poker game user model.
	 * 
	 * @return the poker game user model.
	 */
	public PokerGameUserModel getPokerGameUserModel()
	{
		return pokerGameUserModel;
	}

	/**
	 * Sets the poker game user model.
	 * 
	 * @param pokerGameUserModel
	 *            the new poker game user model.
	 */
	public void setPokerGameUserModel(PokerGameUserModel pokerGameUserModel)
	{
		this.pokerGameUserModel = pokerGameUserModel;
	}
}
