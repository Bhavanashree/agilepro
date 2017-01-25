package com.agilepro.commons.models.bug;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class BugSearchResult.
 */
@Model
public class BugSearchResult extends AbstractExtendedSearchResult
{
	/**
	 * The id.
	 **/
	@Field(value = "id")
	@NonDisplayable
	private long id;

	/**
	 * The name.
	 **/
	@Field(value = "title")
	private String title;

	/**
	 * The reported by.
	 **/
	@Field(value = "reportedBy.name")
	private String reportedBy;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the reported by.
	 *
	 * @return the reported by
	 */
	public String getReportedBy()
	{
		return reportedBy;
	}

	/**
	 * Sets the reported by.
	 *
	 * @param reportedBy
	 *            the new reported by
	 */
	public void setReportedBy(String reportedBy)
	{
		this.reportedBy = reportedBy;
	}
}
