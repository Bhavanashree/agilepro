package com.agilepro.commons.models.customer;

import com.agilepro.commons.ProjectMemberRole;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.ImageInfo;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ProjectMemberModel.
 * 
 * @author Pritam
 */
@Model(name = "ProjectMember")
public class ProjectMemberModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * Active project id.
	 **/
	@Required
	private Long projectId;

	/**
	 * The employee id.
	 **/
	@Required
	private Long employeeId;

	/**
	 * The project member role.
	 **/
	@Required
	private ProjectMemberRole projectMemberRole;

	/**
	 * The employee name.
	 **/
	private String name;

	/**
	 * The photo.
	 **/
	private ImageInfo photo;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * Instantiates a new project member model.
	 */
	public ProjectMemberModel()
	{
		super();
	}

	/**
	 * Instantiates a new project member model.
	 *
	 * @param projectId
	 *            the project id
	 * @param employeeId
	 *            the employee id
	 * @param projectMemberRole
	 *            the project member role
	 */
	public ProjectMemberModel(Long projectId, Long employeeId, ProjectMemberRole projectMemberRole)
	{
		super();
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.projectMemberRole = projectMemberRole;
	}

	/**
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId
	 *            the new project id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public Long getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId
	 *            the new employee id
	 */
	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
	}

	/**
	 * Gets the project member role.
	 *
	 * @return the project member role
	 */
	public ProjectMemberRole getProjectMemberRole()
	{
		return projectMemberRole;
	}

	/**
	 * Sets the project member role.
	 *
	 * @param projectMemberRole
	 *            the new project member role
	 */
	public void setProjectMemberRole(ProjectMemberRole projectMemberRole)
	{
		this.projectMemberRole = projectMemberRole;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public ImageInfo getPhoto()
	{
		return photo;
	}

	/**
	 * Sets the photo.
	 *
	 * @param photo
	 *            the new photo
	 */
	public void setPhoto(ImageInfo photo)
	{
		this.photo = photo;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}
}
