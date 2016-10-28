package com.agilepro.persistence.entity.admin;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.agilepro.commons.ListOfdays;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class HolidayEntity.
 */
@ExtendableEntity(name = "Holiday")
@Table(name = "HOLIDAY")
public class HolidayEntity extends WebutilsExtendableEntity
{
	/**
	 * The name.
	 **/
	@Column(name = "TITLE")
	private String title;

	/**
	 * The description.
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * The name.
	 **/
	@Column(name = "DATE")
	private Date date;

	/**
	 * The days.
	 **/
	@Column(name = "DAYS", length = 1000)
	@DataTypeMapping(type = DataType.BLOB, converterType = JsonConverter.class)
	private List<ListOfdays> days;

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
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * Gets the days.
	 *
	 * @return the days
	 */
	public List<ListOfdays> getDays()
	{
		return days;
	}

	/**
	 * Sets the days.
	 *
	 * @param days
	 *            the new days
	 */
	public void setDays(List<ListOfdays> days)
	{
		this.days = days;
	}
}
