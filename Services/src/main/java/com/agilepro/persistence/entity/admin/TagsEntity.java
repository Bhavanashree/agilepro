package com.agilepro.persistence.entity.admin;

import java.awt.Color;

import javax.persistence.Column;
import javax.persistence.Table;

import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class TagsEntity.
 * 
 * @author Pritam
 */
@Table(name = "TAGS")
public class TagsEntity extends WebutilsEntity
{
	/** 
	 * The name. 
	 **/
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	/** 
	 * The description. 
	 **/
	@Column(name = "DESCRIPTION")
	private String description;

	/** 
	 * The color. 
	 **/
	@Column(name = "COLOR", length = 50)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private Color color;
	
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
	 * @param description the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
}
