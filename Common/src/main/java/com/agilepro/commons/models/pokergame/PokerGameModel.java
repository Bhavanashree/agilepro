package com.agilepro.commons.models.pokergame;

import com.agilepro.commons.GameSeries;
import com.agilepro.commons.PokerGameStatus;
import com.yukthi.webutils.common.AbstractExtendableModel;
import com.yukthi.webutils.common.annotations.ExtendableModel;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class PokerGameModel.
 */
@ExtendableModel(name = "PokerGame")
@Model
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
	private GameSeries gameSeries;

	/**
	 * The project id.
	 */
	private Long projectId;

	/**
	 * The count.
	 */
	private Integer count;

	/**
	 * The game status.
	 */
	private PokerGameStatus gameStatus;
	
	/**
	 * The story id.
	 */
	private Long storyId;

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
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public Integer getCount()
	{
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count
	 *            the new count
	 */
	public void setCount(Integer count)
	{
		this.count = count;
	}

	/**
	 * Gets the game status.
	 *
	 * @return the game status
	 */
	public PokerGameStatus getGameStatus()
	{
		return gameStatus;
	}

	/**
	 * Sets the game status.
	 *
	 * @param gameStatus
	 *            the new game status
	 */
	public void setGameStatus(PokerGameStatus gameStatus)
	{
		this.gameStatus = gameStatus;
	}

	/**
	 * Gets the story id.
	 *
	 * @return the story id
	 */
	public Long getStoryId()
	{
		return storyId;
	}

	/**
	 * Sets the story id.
	 *
	 * @param storyId
	 *            the new story id
	 */
	public void setStoryId(Long storyId)
	{
		this.storyId = storyId;
	}
}
