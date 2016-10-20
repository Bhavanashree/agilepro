package com.agilepro.persistence.entity.scrum;

import javax.persistence.Column;
import javax.persistence.Table;

import com.agilepro.persistence.entity.admin.ProjectEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

@Table(name = "SCRUM_MEETING")
public class ScrumMeetingEntity extends WebutilsEntity
{
	@Column(name = "PROJECT_ID")
	private ProjectEntity project;
	
	@Column(name = "DATA")
	private String data;
	
	@Column(name = "SPRINT")
	private SprintEntity sprint;

	public ProjectEntity getProject() 
	{
		return project;
	}

	public void setProject(ProjectEntity project) 
	{
		this.project = project;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public SprintEntity getSprint() {
		return sprint;
	}

	public void setSprint(SprintEntity sprint) {
		this.sprint = sprint;
	}
}
