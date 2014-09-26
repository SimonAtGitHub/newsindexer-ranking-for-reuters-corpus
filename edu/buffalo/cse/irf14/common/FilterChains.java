/**
 * Class which defines the filter chains for the respective analyzers
 */
package edu.buffalo.cse.irf14.common;

import edu.buffalo.cse.irf14.analysis.TokenFilterType;

public class FilterChains {

	/**
	 * <ul>
	 * <li>Apply Symbol filter before Special Characters</li>
	 * <li>Apply Stop word just after capitalization to reduce the number of
	 * processing done in every stream.</li>
	 * </ul>
	 */
	public static final TokenFilterType[] FILTERS_FOR_CONTENT = {
			TokenFilterType.CAPITALIZATION, TokenFilterType.STOPWORD,
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.DATE, TokenFilterType.NUMERIC,
			TokenFilterType.STEMMER };

	public static final TokenFilterType[] FILTERS_FOR_NEWSDATE = { TokenFilterType.DATE };

	public static final TokenFilterType[] FILTERS_FOR_CATEGORY = {};

	public static final TokenFilterType[] FILTERS_FOR_TITLE = {
			TokenFilterType.CAPITALIZATION, TokenFilterType.STOPWORD,
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.DATE, TokenFilterType.NUMERIC,
			TokenFilterType.STEMMER };

	public static final TokenFilterType[] FILTERS_FOR_AUTHOR = { TokenFilterType.CAPITALIZATION };

	public static final TokenFilterType[] FILTERS_FOR_AUTHORORG = { TokenFilterType.CAPITALIZATION };

	public static final TokenFilterType[] FILTERS_FOR_PLACE = { TokenFilterType.CAPITALIZATION };

}