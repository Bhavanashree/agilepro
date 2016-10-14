package com.agilepro.persistence.entity.project;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.ReleaseModel;
import com.agilepro.commons.models.project.SprintModel;
import com.agilepro.commons.models.project.StoryModel;
import com.agilepro.persistence.entity.admin.ReleaseEntity;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class SprintDataEntity.
 */
@Table(name = "SPRINT_ANALYTICS")
public class SprintAnalyticsEntity extends WebutilsEntity
{
	@Column(name = "SPRINT")
	@ManyToOne
	private SprintEntity sprint;

	@Column(name = "COMPLETED_DATE")
	private Date completedDate;

	@Column(name = "TOTAL_COMPLETED_STORYID")
	private Integer totalCOmpletedStoryId;

	@Column(name = "HOURS_TAKEN")
	@DataTypeMapping(type = DataType.DATE_TIME)
	private Date hoursTaken;

	/**
	 * Instantiates a new sprint data entity.
	 */
	public SprintAnalyticsEntity()
	{}

	public SprintEntity getSprint()
	{
		return sprint;
	}

	public void setSprint(SprintEntity sprint)
	{
		this.sprint = sprint;
	}

	public Date getCompletedDate()
	{
		return completedDate;
	}

	public void setCompletedDate(Date completedDate)
	{
		this.completedDate = completedDate;
	}

	public Integer getTotalCOmpletedStoryId()
	{
		return totalCOmpletedStoryId;
	}

	public void setTotalCOmpletedStoryId(Integer totalCOmpletedStoryId)
	{
		this.totalCOmpletedStoryId = totalCOmpletedStoryId;
	}

	public Date getHoursTaken()
	{
		return hoursTaken;
	}

	public void setHoursTaken(Date hoursTaken)
	{
		this.hoursTaken = hoursTaken;
	}
}
