/**
 * Class which defines the filter chains for the respective analyzers
 */
package edu.buffalo.cse.irf14.common;

import edu.buffalo.cse.irf14.analysis.TokenFilterType;

public class FilterChains {

	public static final TokenFilterType [] FILTERS_FOR_CONTENT={TokenFilterType.NUMERIC,TokenFilterType.STOPWORD};
}

