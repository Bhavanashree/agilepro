package com.agilepro.commons.models.customer;

import com.agilepro.commons.UserRole;
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
	 * The project id.
	 **/
	private Long projectId;

	/**
	 * The employee id.
	 **/
	private Long employeeId;

	/**
	 * The user role.
	 **/
	private UserRole userRole;

	/**
	 * The name.
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
	 * Gets the user role.
	 *
	 * @return the user role
	 */
	public UserRole getUserRole()
	{
		return userRole;
	}

	/**
	 * Sets the user role.
	 *
	 * @param userRole
	 *            the new user role
	 */
	public void setUserRole(UserRole userRole)
	{
		this.userRole = userRole;
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
