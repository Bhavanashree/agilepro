package com.agilepro.commons.controllers.notification;

public class RecipientInfoList
{
	private RecipientInfo to;
	private RecipientInfo cc;
	
	public RecipientInfoList()
	{}

	public RecipientInfoList(RecipientInfo to, RecipientInfo cc)
	{
		this.to = to;
		this.cc = cc;
	}

	public RecipientInfo getTo()
	{
		return to;
	}

	public void setTo(RecipientInfo to)
	{
		this.to = to;
	}

	public RecipientInfo getCc()
	{
		return cc;
	}

	public void setCc(RecipientInfo cc)
	{
		this.cc = cc;
	}
}
