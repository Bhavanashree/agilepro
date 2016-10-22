package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.customer.ProjectReleaseModel;
import com.yukthi.persistence.annotations.DeleteWithParent;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ProjectReleaseEntity.
 * 
 * @author Pritam
 */
@Table(name = "PROJECT_RELEASE")
@UniqueConstraints({ @UniqueConstraint(name = "PROJECT_RELEASE_ID", fields = { "release", "project" }) })
public class ProjectReleaseEntity extends WebutilsEntity
{
	/**
	 * The release entity.
	 **/
	@DeleteWithParent
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

	/**
	 * Gets the release.
	 *
	 * @return the release
	 */
	public ReleaseEntity getRelease()
	{
		return release;
	}

	/**
	 * Sets the release.
	 *
	 * @param release
	 *            the new release
	 */
	public void setRelease(ReleaseEntity release)
	{
		this.release = release;
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
}
