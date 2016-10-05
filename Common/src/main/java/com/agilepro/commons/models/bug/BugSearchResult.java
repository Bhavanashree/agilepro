
package com.agilepro.commons.models.bug;

import com.yukthi.persistence.repository.annotations.Field;
import com.yukthi.webutils.common.AbstractExtendedSearchResult;
import com.yukthi.webutils.common.annotations.Model;

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
	private long bugId;

	/**
	 * The name.
	 **/
	@Field(value = "name")
	private String name;

	/**
	 * The reported by.
	 **/
	@Field(value = "reportedBy.name")
	private String reportedBy;

	/**
	 * Gets the bug id.
	 *
	 * @return the bug id
	 */
	public long getBugId()
	{
		return bugId;
	}

	/**
	 * Sets the bug id.
	 *
	 * @param bugId the new bug id
	 */
	public void setBugId(long bugId)
	{
		this.bugId = bugId;
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
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * @param reportedBy the new reported by
	 */
	public void setReportedBy(String reportedBy)
	{
		this.reportedBy = reportedBy;
	}
}
