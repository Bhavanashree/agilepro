package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.customer.AlreadyReleasedModel;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

@Table
public class AlreadyReleasedEntity extends WebutilsEntity
{
	@ManyToOne
	@PropertyMapping(type = AlreadyReleasedModel.class, from = "projectId", subproperty = "id")
	@Column(name = "PROJECT_ID")
	private ProjectEntity projectEntity;
	
	@ManyToOne
	@PropertyMapping(type = AlreadyReleasedModel.class, from = "storyId", subproperty = "id")
	@Column(name = "STORY_ID")
	private StoryEntity storyEntity;

	public ProjectEntity getProjectEntity() {
		return projectEntity;
	}

	public void setProjectEntity(ProjectEntity projectEntity) {
		this.projectEntity = projectEntity;
	}

	public StoryEntity getStoryEntity() {
		return storyEntity;
	}

	public void setStoryEntity(StoryEntity storyEntity) {
		this.storyEntity = storyEntity;
	}
}
