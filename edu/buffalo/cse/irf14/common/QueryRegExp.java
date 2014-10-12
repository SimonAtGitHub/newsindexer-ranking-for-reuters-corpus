package edu.buffalo.cse.irf14.common;

public class QueryRegExp {
	// regular expression for various types of indices in a query
	public static final String INDEX = "Term|Category|Author|Place";
	// regular expression for various operators in a query
	public static final String OPERATOR = "AND|OR|NOT";
	// A term can be a single term or multiple terms separated by a space (not
	// more than 4 as per specs)
	public static final String TERM = "([^\"(){}]+\\s){0,3}([^\"(){}]+)";
	// public static final String TERM = "(\\S+\\s){0,3}(\\S+)";
	// Quoted Query
	public static final String QUOTED_TERM = "[\"]" + TERM + "[\"]";
	// The term might be enclosed in brackets or ""
	public static final String COMPLEX_TERM = "[(]" + TERM + "[)]|[\"]" + TERM
			+ "[\"]";

}
