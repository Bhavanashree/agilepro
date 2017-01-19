package com.agilepro.commons.models.project;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;

@Model
public class StorySprintUpdateModel
{
	@IgnoreField
	private Long[] ids;
	
	private Long sprintId;

	public Long[] getIds()
	{
		return ids;
	}

	public void setIds(Long[] ids)
	{
		this.ids = ids;
	}

	public Long getSprintId()
	{
		return sprintId;
	}

	public void setSprintId(Long sprintId)
	{
		this.sprintId = sprintId;
	}
}
