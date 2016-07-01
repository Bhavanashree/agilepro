package com.agilepro.services.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import com.yukthi.webutils.repository.search.ISearchResultCustomizer;

/**
 * The Class BacklogSearchCustomizer.
 */
public class BacklogSearchCustomizer implements ISearchResultCustomizer<T>
{
	private static Logger logger = LogManager.getLogger(BacklogSearchCustomizer.class);

	@Override
	public List<T> customizer(List<T> results)
	{
		return results;
	}
}
