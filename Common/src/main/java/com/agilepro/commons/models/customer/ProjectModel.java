package com.agilepro.commons.models.customer;

import java.util.Date;
import java.util.List;

import com.yukthi.validation.annotations.MaxLen;
import com.yukthi.validation.annotations.MinLen;
import com.yukthi.validation.annotations.NotEmpty;
import com.yukthi.validation.annotations.Required;
import com.yukthi.webutils.common.FileInfo;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class ProjectModel.
 * 
 * @author Pritam
 */
@Model(name = "Project")
public class ProjectModel
{
	/**
	 * The id.
	 **/
	@NonDisplayable
	private Long id;

	/**
	 * The name.
	 **/
	@NotEmpty
	@MinLen(3)
	@MaxLen(50)
	@Required
	private String name;

	/**
	 * The start date.
	 **/
	private Date startDate;

	/**
	 * The end date.
	 **/
	private Date endDate;

	/**
	 * The attachments.
	 **/
	private List<FileInfo> attachments;

	/**
	 * Version used for update.
	 **/
	@NonDisplayable
	private Integer version;

	/**
	 * Instantiates a new project model.
	 */
	public ProjectModel()
	{}

	/**
	 * Instantiates a new project model.
	 *
	 * @param name
	 *            the name
	 */
	public ProjectModel(String name)
	{
		this.name = name;
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
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate
	 *            the new start date
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
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
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public List<FileInfo> getAttachments()
	{
		return attachments;
	}

	/**
	 * Sets the attachments.
	 *
	 * @param attachments
	 *            the new attachments
	 */
	public void setAttachments(List<FileInfo> attachments)
	{
		this.attachments = attachments;
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
