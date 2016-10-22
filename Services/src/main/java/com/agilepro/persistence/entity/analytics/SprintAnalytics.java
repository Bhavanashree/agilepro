package com.agilepro.persistence.entity.analytics;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.SprintModel;
import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * Maintains sprint analytic data.
 */
@ExtendableEntity(name = "Sprint")
@Table(name = "ANALYTICS_SPRINT")
public class SprintAnalytics extends WebutilsEntity
{
	/**
	 * Sprint for which this analytics is applicable.
	 */
	@Column(name = "SPRINT_ID")
	@ManyToOne
	private SprintEntity sprint;
	
	/**
	 * Date on which analysis is performed.
	 */
	@Column(name = "ANALYSIS_DATE")
	private Date date;
	
	/**
	 * Number of story points completed.
	 */
	@Column(name = "STORY_POINTS_COMPLETED")
	private int storyPointsCompleted;

}
