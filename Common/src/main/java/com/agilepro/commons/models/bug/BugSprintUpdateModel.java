package com.agilepro.commons.models.bug;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;

/**
 * Bug sprint update model.
 * 
 * @author Pritam.
 */
@Model
public class BugSprintUpdateModel
{
	/**
	 * Bug ids for updating with the sprint id.
	 */
	@IgnoreField
	private Long[] ids;

	/**
	 * Sprint id to be update for the story ids.
	 */
	private Long sprintId;

	/**
	 * Gets the ids.
	 * 
	 * @return the ids.
	 */
	public Long[] getIds()
	{
		return ids;
	}

	/**
	 * Set the ids.
	 * 
	 * @param ids
	 *            the new ids.
	 */
	public void setIds(Long[] ids)
	{
		this.ids = ids;
	}

	/**
	 * Gets the sprint id.
	 * 
	 * @return the sprint id.
	 */
	public Long getSprintId()
	{
		return sprintId;
	}

	/**
	 * Set the sprint id.
	 * 
	 * @param sprintId
	 *            the new sprint id.
	 */
	public void setSprintId(Long sprintId)
	{
		this.sprintId = sprintId;
	}
}
