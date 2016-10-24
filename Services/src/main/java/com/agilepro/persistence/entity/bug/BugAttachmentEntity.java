package com.agilepro.persistence.entity.bug;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.bug.BugAttachmentModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class BugAttachmentEntity.
 */
@Table(name = "BUG_ATTACHMENT")
public class BugAttachmentEntity extends WebutilsEntity
{
	/**
	 * The title.
	 **/
	@Column(name = "TITLE")
	private String title;

	/**
	 * The description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The Bug entity.
	 **/
	@Column(name = "BUG_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = BugAttachmentModel.class, from = "bugId", subproperty = "id")
	private BugEntity bug;

	/**
	 * The link.
	 **/
	@Column(name = "LINK_URL")
	private String link;

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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the bug.
	 *
	 * @return the bug
	 */
	public BugEntity getBug()
	{
		return bug;
	}

	/**
	 * Sets the bug.
	 *
	 * @param bug
	 *            the new bug
	 */
	public void setBug(BugEntity bug)
	{
		this.bug = bug;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink()
	{
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link
	 *            the new link
	 */
	public void setLink(String link)
	{
		this.link = link;
	}
}
