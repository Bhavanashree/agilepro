package com.agilepro.commons.models.admin;

import java.util.Date;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class AdminUserSearchResult.
 */
@Model
public class AdminUserSearchResult
{

	/**
	 * The id.
	 */
	@NonDisplayable
	@Field(value = "id")
	private Long id;

	/**
	 * The user name.
	 */
	@Field(value = "userName")
	private String userName;

	/**
	 * The displayName.
	 */
	@Field(value = "displayName")
	private String displayName;

	/**
	 * The deleted.
	 */
	@Field(value = "deleted")
	private boolean deleted = false;

	/**
	 * The created on.
	 */
	@Field("createdOn")
	private Date createdOn;

	/**
	 * The updated on.
	 */
	@Field("updatedOn")
	private Date updatedOn;

	/**
	 * The created by.
	 */
	@Field("createdBy.displayName")
	private String createdBy;

	/**
	 * The updated by.
	 */
	@Field("updatedBy.displayName")
	private String updatedBy;

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the new created by
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy()
	{
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy
	 *            the new updated by
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	/**
	 * Checks if is deleted.
	 *
	 * @return true, if is deleted
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	/**
	 * Sets the deleted.
	 *
	 * @param deleted
	 *            the new deleted
	 */
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the new created on
	 */
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the displayName.
	 *
	 * @return the displayName
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Sets the displayName.
	 *
	 * @param displayName
	 *            the new displayName
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
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
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
}
