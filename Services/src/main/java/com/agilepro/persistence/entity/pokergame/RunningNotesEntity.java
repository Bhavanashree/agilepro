package com.agilepro.persistence.entity.pokergame;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.pokergame.PokerGameUserModel;
import com.agilepro.commons.models.pokergame.RunningNotesModel;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
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
	 * The members.
	 **/
	@OneToOne
	@Column(name = "PROJECT_MEMBER_ID", nullable = false)
	@PropertyMapping(type = PokerGameUserModel.class, from = "projectMemberId", subproperty = "id")
	private ProjectMemberEntity projectMember;

	/**
	 * Running note.
	 */
	@Column(name = "RUNNING_NOTES")
	@DataTypeMapping(type = DataType.CLOB)
	private String runningNote;

	/**
	 * The time.
	 **/
	@Column(name = "DATE", nullable = false)
	private Date date;

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
	 * Gets the running note.
	 * 
	 * @return the running note.
	 */
	public String getRunningNote()
	{
		return runningNote;
	}

	/**
	 * Set the running note.
	 * 
	 * @param runningNote
	 *            the new running note.
	 */
	public void setRunningNote(String runningNote)
	{
		this.runningNote = runningNote;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date.
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

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
	 * @param projectMember
	 *            the new project member.
	 */
	public void setProjectMember(ProjectMemberEntity projectMember)
	{
		this.projectMember = projectMember;
	}
}
