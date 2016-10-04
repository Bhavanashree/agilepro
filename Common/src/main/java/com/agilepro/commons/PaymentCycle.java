package com.agilepro.commons;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * On the basis of PaymentCycle customer price plan due amount is calculated.
 * 
 * @author akiran
 */
public enum PaymentCycle implements IAgileproCommonConstants
{
	/**
	 * The Daily.
	 **/
	Daily
	{
		@Override
		public Date nextEvalDate(Date from)
		{
			return DateUtils.addDays(from, 1);
		}
	},
	/**
	 * The Weekly.
	 **/
	Weekly
	{
		@Override
		public Date nextEvalDate(Date from)
		{
			return DateUtils.addDays(from, NO_OF_WEEK_DAYS);
		}
	},
	/**
	 * The Monthly.
	 **/
	Monthly
	{
		@Override
		public Date nextEvalDate(Date from)
		{
			return DateUtils.addMonths(from, 1);
		}
	},
	/**
	 * The Quarterly.
	 **/
	Quarterly
	{
		@Override
		public Date nextEvalDate(Date from)
		{
			return DateUtils.addMonths(from, NO_OF_QUARTER_DAYS);
		}
	},

	/**
	 * The Annualy.
	 **/
	Annually
	{
		@Override
		public Date nextEvalDate(Date from)
		{
			return DateUtils.addMonths(from, NO_OF_ANNUAL_DAYS);
		}
	},;

	/**
	 * Fetches next evaluation data for current cycle from specified
	 * "from" date.
	 * @param from Date from which next eval needs to be calculated.
	 * @return Next eval date of current cycle.
	 */
	public abstract Date nextEvalDate(Date from);
}
