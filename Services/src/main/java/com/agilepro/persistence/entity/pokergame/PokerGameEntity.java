package com.agilepro.persistence.entity.pokergame;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agilepro.commons.GameSeries;
import com.agilepro.commons.PokerGameStatus;
import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class PokerGameEntity.
 */
@ExtendableEntity(name = "PokerGame")
@Table(name = "POKERGAME")
public class PokerGameEntity extends WebutilsExtendableEntity
{
	/**
	 * The game series.
	 */
	@Column(name = "POKERGAME_GAME_SERIES")
	@DataTypeMapping(type = DataType.STRING)
	private GameSeries gameSeries;

	/**
	 * The project id.
	 **/
	@Column(name = "POKERGAME_PROJECTID")
	@ManyToOne
	@PropertyMapping(type = PokerGameModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * The count.
	 **/
	@Column(name = "POKERGAME_GAME_COUNT")
	private Integer count;

	/**
	 * The story points.
	 **/
	@Column(name = "POKERGAME_STORYPOINTS")
	@OneToOne
	@PropertyMapping(type = PokerGameModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * The poker status.
	 **/
	@Column(name = "POKERGAME_STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private PokerGameStatus pokerStatus;

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
	 * Gets the story.
	 *
	 * @return the story
	 */
	public StoryEntity getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 *
	 * @param story
	 *            the new story
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
	}

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public ProjectEntity getProject()
	{
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @param project
	 *            the new project
	 */
	public void setProject(ProjectEntity project)
	{
		this.project = project;
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
	 * Gets the poker status.
	 *
	 * @return the poker status
	 */
	public PokerGameStatus getPokerStatus()
	{
		return pokerStatus;
	}

	/**
	 * Sets the poker status.
	 *
	 * @param pokerStatus
	 *            the new poker status
	 */
	public void setPokerStatus(PokerGameStatus pokerStatus)
	{
		this.pokerStatus = pokerStatus;
	}
}
