package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * GameSeries for displaying the cards according to the selected series.
 * 
 * @author Pritam
 */
public enum GameSeries
{
	/**
	 *  The fibonacci series. 
	 **/
	@Label("Fibonacci_Series") FIBONACCI,

	/**
	 * The Sequential.
	 **/
	@Label("Sequential") SEQUENTIAL,

	/**
	 *  The even. 
	 **/
	@Label("Even") EVEN,
}
