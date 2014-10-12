package edu.buffalo.cse.irf14.common;

import java.util.regex.Pattern;

public class CommonQueryPatterns {

	public static final Pattern TERM_PATTERN = Pattern
			.compile(QueryRegExp.TERM);

	public static final Pattern QUOTTED_TERM_PATTERN = Pattern
			.compile(QueryRegExp.QUOTED_TERM);
}
