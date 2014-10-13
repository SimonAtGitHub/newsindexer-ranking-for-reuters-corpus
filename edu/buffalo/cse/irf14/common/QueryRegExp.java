package edu.buffalo.cse.irf14.common;

public class QueryRegExp {
	// Regular Expression for whitespace except for those inside a quote
	// Shamelessly copied from stackoverflow
	// http://stackoverflow.com/questions/9577930/regular-expression-to-select-all-whitespace-that-isnt-in-quotes
	// Don't even know what this regex means.
	public static final String WHITESPACE_NOT_IN_QUOTES = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
	// regular expression for various types of indices in a query
	public static final String INDEX = "Term|Category|Author|Place";
	// regular expression for various operators in a query
	public static final String OPERATOR = "AND|OR|NOT";

}
