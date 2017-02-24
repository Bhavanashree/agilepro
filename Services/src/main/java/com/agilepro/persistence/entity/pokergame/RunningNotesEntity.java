package com.agilepro.persistence.entity.pokergame;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.pokergame.RunningNotesModel;
import com.agilepro.persistence.entity.bug.BugEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * Running notes for story or bug, notes will be provided during poker game.
 * 
 * @author Pritam.
 */
@Table(name = "RUNNING_NOTES")
public class RunningNotesEntity extends WebutilsEntity
{
	/**
	 * Bug for mapping.
	 */
	@Column(name = "BUG_ID")
	@ManyToOne
	@PropertyMapping(type = RunningNotesModel.class, from = "bugId", subproperty = "id")
	private BugEntity bug;

	/**
	 * Story for mapping.
	 */
	@Column(name = "STORY_ID")
	@ManyToOne
	@PropertyMapping(type = RunningNotesModel.class, from = "storyId", subproperty = "id")
	private StoryEntity story;

	/**
	 * Running notes.
	 */
	@Column(name = "RUNNING_NOTES")
	@DataTypeMapping(type = DataType.CLOB)
	private String runningNotes;

	/**
	 * Gets the bug.
	 * 
	 * @return the bug.
	 */
	public BugEntity getBug()
	{
		return bug;
	}

	/**
	 * Sets the bug.
	 * 
	 * @param bug
	 *            the new bug.
	 */
	public void setBug(BugEntity bug)
	{
		this.bug = bug;
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
	 *            the new story.
	 */
	public void setStory(StoryEntity story)
	{
		this.story = story;
	}

	/**
	 * Gets Running notes.
	 * 
	 * @return the new running notes.
	 */
	public String getRunningNotes()
	{
		return runningNotes;
	}

	/**
	 * Sets the running notes.
	 * 
	 * @param runningNotes
	 *            the new running notes.
	 */
	public void setRunningNotes(String runningNotes)
	{
		this.runningNotes = runningNotes;
	}
}
