package com.agilepro.persistence.entity.bug;

import javax.persistence.Table;

import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class BugComment.
 */
@Table(name = "COMMENTS")
public class BugCommentsEntity extends WebutilsEntity
{
	
	/**
	 *  The content.
	 **/
	private String content;

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
}
