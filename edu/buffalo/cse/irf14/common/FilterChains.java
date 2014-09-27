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
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.CAPITALIZATION, TokenFilterType.STOPWORD,
			TokenFilterType.DATE, TokenFilterType.NUMERIC,
			TokenFilterType.ACCENT, TokenFilterType.STEMMER };

	public static final TokenFilterType[] FILTERS_FOR_NEWSDATE = { TokenFilterType.DATE };

	public static final TokenFilterType[] FILTERS_FOR_CATEGORY = {
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.CAPITALIZATION, TokenFilterType.ACCENT };

	public static final TokenFilterType[] FILTERS_FOR_TITLE = {
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.CAPITALIZATION, TokenFilterType.STOPWORD,
			TokenFilterType.DATE, TokenFilterType.NUMERIC,
			TokenFilterType.STEMMER, TokenFilterType.ACCENT };

	public static final TokenFilterType[] FILTERS_FOR_AUTHOR = {
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.CAPITALIZATION, TokenFilterType.ACCENT };

	public static final TokenFilterType[] FILTERS_FOR_AUTHORORG = {
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.ACCENT };

	public static final TokenFilterType[] FILTERS_FOR_PLACE = {
			TokenFilterType.SYMBOL, TokenFilterType.SPECIALCHARS,
			TokenFilterType.ACCENT };

}