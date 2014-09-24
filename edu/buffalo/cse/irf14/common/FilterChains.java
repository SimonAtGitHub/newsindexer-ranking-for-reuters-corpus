/**
 * Class which defines the filter chains for the respective analyzers
 */
package edu.buffalo.cse.irf14.common;

import edu.buffalo.cse.irf14.analysis.TokenFilterType;

public class FilterChains {

	public static final TokenFilterType[] FILTERS_FOR_CONTENT = {
			TokenFilterType.CAPITALIZATION, TokenFilterType.SYMBOL,
			TokenFilterType.SPECIALCHARS, TokenFilterType.DATE,
			TokenFilterType.NUMERIC, TokenFilterType.STOPWORD,
			TokenFilterType.STEMMER };
	
	public static final TokenFilterType[] FILTERS_FOR_NEWSDATE = { TokenFilterType.DATE };

	public static final TokenFilterType[] CATEGORY = {};
	
	public static final TokenFilterType[] TITLE = { TokenFilterType.CAPITALIZATION, TokenFilterType.SYMBOL,
		                                             TokenFilterType.SPECIALCHARS, TokenFilterType.DATE,
		                                             TokenFilterType.NUMERIC, TokenFilterType.STOPWORD,
		                                             TokenFilterType.STEMMER };
	
	public static final TokenFilterType[] AUTHOR = { TokenFilterType.CAPITALIZATION };
	
	public static final TokenFilterType[] AUTHORORG = { TokenFilterType.CAPITALIZATION };
	
	public static final TokenFilterType[] PLACE = { TokenFilterType.CAPITALIZATION };
	
	
}
