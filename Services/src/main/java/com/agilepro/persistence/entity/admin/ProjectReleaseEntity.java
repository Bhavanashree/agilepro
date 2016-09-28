package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.customer.ProjectReleaseModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ProjectReleaseEntity.
 * 
 * @author Pritam
 */
@Table(name = "PROJECT_RELEASE")
public class ProjectReleaseEntity extends WebutilsEntity
{
	/**
	 * The release entity.
	 **/
	@ManyToOne
	@PropertyMapping(type = ProjectReleaseModel.class, from = "releaseId", subproperty = "id")
	@Column(name = "RELEASE_ID", nullable = false)
	private ReleaseEntity release;

	/**
	 * The project entity.
	 **/
	@ManyToOne
	@PropertyMapping(type = ProjectReleaseModel.class, from = "projectId", subproperty = "id")
	@Column(name = "PROJECT_ID", nullable = false)
	private ProjectEntity project;

	public ReleaseEntity getRelease()
	{
		return release;
	}

	public void setRelease(ReleaseEntity release)
	{
		this.release = release;
	}

	public ProjectEntity getProject()
	{
		return project;
	}

	public void setProject(ProjectEntity project)
	{
		this.project = project;
	}
}
