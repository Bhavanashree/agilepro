package com.agilepro.commons;

import com.yukthi.webutils.common.annotations.Label;

/**
 * The Enum PokerGameStatus.
 */
public enum PokerGameStatus
{
	/**
	 * The cards selected.
	 **/
	@Label("cards selected") CARDS_NOT_FLIPPED,

	/**
	 * The cards flipped.
	 **/
	@Label("cards flipped") CARDS_FLIPPED,
}
