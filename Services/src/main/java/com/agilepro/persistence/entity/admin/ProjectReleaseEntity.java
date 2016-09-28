package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.customer.ProjectReleaseModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

@Table(name = "PROJECT_RELEASE")
public class ProjectReleaseEntity extends WebutilsEntity
{
	@ManyToOne
	@PropertyMapping(type = ProjectReleaseModel.class, from = "releaseId", subproperty = "id")
	@Column(name = "RELEASE_ID", nullable = false)
	private ReleaseEntity releaseEntity;

	@ManyToOne
	@PropertyMapping(type = ProjectReleaseModel.class, from = "projectId", subproperty = "id")
	@Column(name = "PROJECT_ID", nullable = false)
	private ProjectEntity projectEntity;

	public ProjectEntity getProjectEntity()
	{
		return projectEntity;
	}

	public void setProjectEntity(ProjectEntity projectEntity)
	{
		this.projectEntity = projectEntity;
	}

	public ReleaseEntity getReleaseEntity()
	{
		return releaseEntity;
	}

	public void setReleaseEntity(ReleaseEntity releaseEntity)
	{
		this.releaseEntity = releaseEntity;
	}
}
