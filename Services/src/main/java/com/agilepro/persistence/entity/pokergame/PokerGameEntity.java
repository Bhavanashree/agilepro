package com.agilepro.persistence.entity.pokergame;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agilepro.commons.GameSeries;
import com.agilepro.commons.PokerGameStoryStatus;
import com.agilepro.commons.models.pokergame.PokerGameModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * PokerGameEntity this table holds the data of poker game per story. Here
 * project member will provide there respective story points and finally average
 * of points will be calculated and persisted in story. Create when game starts,
 * Update after average is calculated.
 * 
 * @author Pritam.
 */
@ExtendableEntity(name = "PokerGame")
@Table(name = "POKER_GAME")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_PROJECT", fields = { "spaceIdentity", "project"}) })
public class PokerGameEntity extends WebutilsExtendableEntity
{
	/**
	 * The game series .
	 */
	@Column(name = "GAME_SERIES", nullable = false)
	@DataTypeMapping(type = DataType.STRING)
	private GameSeries gameSeries;

	/**
	 * Poker game story status.
	 */
	@Column(name = "POKER_GAME_STORY_STATUS")
	@DataTypeMapping(type = DataType.STRING)
	private PokerGameStoryStatus pokerGameStoryStatus;

	/**
	 * Average of points provided for a story.
	 **/
	@Column(name = "AVERAGE_STORY_POINT")
	private Integer averageStoryPoint;

	/**
	 * Number of cards.
	 */
	@Column(name = "NUMBER_OF_CARDS")
	private Integer numberOfCards;

	/**
	 * Story for which game will be played and points will be calculated.
	 **/
	@Column(name = "STORY_ID")
	@OneToOne
	@PropertyMapping(type = PokerGameModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * Bug for which game will be played and points will be calculated.
	 */
	@Column(name = "BUG_ID")
	@OneToOne
	@PropertyMapping(type = PokerGameModel.class, from = "bugId", subproperty = "id")
	private BugEntity bugEntity;
	
	/**
	 * Project under which stories will be assigned points.
	 */
	@Column(name = "PROJECT_ID", nullable = false)
	@OneToOne
	@PropertyMapping(type = PokerGameModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity project;

	/**
	 * Project member as scrum master.
	 */
	@ManyToOne
	@Column(name = "SCRUM_MASTER_ID", nullable = false)
	@PropertyMapping(type = PokerGameModel.class, from = "memberId", subproperty = "id")
	private ProjectMemberEntity projectMember;

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
	 * Gets the average story points.
	 * 
	 * @return average story point.
	 */
	public Integer getAverageStoryPoint()
	{
		return averageStoryPoint;
	}

	/**
	 * Set the average story points.
	 * 
	 * @param averageStoryPoint
	 *            for setting the averageStoryPoint.
	 */
	public void setAverageStoryPoint(Integer averageStoryPoint)
	{
		this.averageStoryPoint = averageStoryPoint;
	}

	/**
	 * Gets the story.
	 * 
	 * @return the story.
	 */
	public StoryEntity getStory()
	{
		return story;
	}

	/**
	 * Sets the story.
	 * 
	 * @param story
	 *            set the new story.
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
	}

	/**
	 * Gets the poker game story status.
	 * 
	 * @return poker game story status.
	 */
	public PokerGameStoryStatus getPokerGameStoryStatus()
	{
		return pokerGameStoryStatus;
	}

	/**
	 * Sets the poker game story status.
	 * 
	 * @param pokerGameStoryStatus
	 *            the new poker game story status.
	 */
	public void setPokerGameStoryStatus(PokerGameStoryStatus pokerGameStoryStatus)
	{
		this.pokerGameStoryStatus = pokerGameStoryStatus;
	}

	/**
	 * Gets the project.
	 * 
	 * @return the project.
	 */
	public ProjectEntity getProject()
	{
		return project;
	}

	/**
	 * Gets the project.
	 * 
	 * @param project
	 *            the new project.
	 */
	public void setProject(ProjectEntity project)
	{
		this.project = project;
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
	 * Set number of cards.
	 * 
	 * @param numberOfCards
	 *            the new number of cards.
	 */
	public void setNumberOfCards(Integer numberOfCards)
	{
		this.numberOfCards = numberOfCards;
	}

	/**
	 * Gets project member.
	 * 
	 * @return the project member.
	 */
	public ProjectMemberEntity getProjectMember()
	{
		return projectMember;
	}

	/**
	 * Set project member.
	 * 
	 * @param projectMember
	 *            the new project member.
	 */
	public void setProjectMember(ProjectMemberEntity projectMember)
	{
		this.projectMember = projectMember;
	}

	/**
	 * Gets the bug.
	 * 
	 * @return the bug.
	 */
	public BugEntity getBugEntity()
	{
		return bugEntity;
	}

	/**
	 * Sets the bug.
	 * 
	 * @param bugEntity the new bug.
	 */
	public void setBugEntity(BugEntity bugEntity)
	{
		this.bugEntity = bugEntity;
	}
}
