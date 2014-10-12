package edu.buffalo.cse.irf14.query;

import java.util.List;
import java.util.regex.Matcher;

import edu.buffalo.cse.irf14.common.CommonQueryPatterns;

/**
 * Class that represents a parsed query
 * 
 * @author nikhillo Each query is a recursive structure consisting of a list of
 *         terms and/or a list of sub-queries
 */
public class Query {

	private String query = "";

	/**
	 * List of terms
	 */
	List<String> terms;

	/**
	 * List of subqueries
	 */
	List<Query> subqueries;

	public Query(String userQuery, String defaultOperator) {
		formulateQuery(userQuery, defaultOperator);
	}

	private void formulateQuery(String userQuery, String defaultOperator) {
		if (defaultOperator != null && defaultOperator.isEmpty()) {
			defaultOperator = "OR";
		}
		// In case of just a normal query, Eg. hello , hello world or Welcome to
		// USA
		// Split the terms based on space and add default operator
		Matcher termMatcher = CommonQueryPatterns.TERM_PATTERN
				.matcher(userQuery);
		Matcher quotedTermMatcher = CommonQueryPatterns.QUOTTED_TERM_PATTERN
				.matcher(userQuery);
		if (termMatcher.matches()) {
			// Split the terms based on whitespace
			String[] queryTerms = userQuery.split("\\s");
			for (int i = 0; i < queryTerms.length; i++) {
				query = query + "Term:" + queryTerms[i];
				// Add operator in all terms except the last
				if (i < queryTerms.length - 1) {
					query = query + " " + defaultOperator + " ";
				}
			}
		} else if (quotedTermMatcher.matches()) {
			query = query + "Term:" + userQuery;
		}
		// Enclose the queryString in brackets
		query = "( " + query + " )";
	}

	/**
	 * Returns a representation of query which is required to execute the query. <br>
	 * <b>NOT SAME AS toString() representation.</b>
	 * 
	 * @return
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Method to convert given parsed query into string
	 */
	public String toString() {
		// TODO: YOU MUST IMPLEMENT THIS
		String queryString = query;
		// Replace the first and last bracket by flower brackets if the string
		// is not empty
		if (!queryString.isEmpty() && queryString.length() > 1) {
			// Replaces only first occurence
			queryString = queryString.replace("(", "{");
			// Replace last occurence by }
			if (queryString.charAt(queryString.length() - 1) == ')') {
				queryString = queryString
						.substring(0, queryString.length() - 1) + "}";
			}

		}
		return queryString;
	}
}
