package edu.buffalo.cse.irf14.query;

import java.util.List;

/**
 * Class that represents a parsed query
 * 
 * @author nikhillo Each query is a recursive structure consisting of a list of
 *         terms and/or a list of sub-queries
 */
public class Query {

	private String queryString;

	private String defaultOperator = "OR";

	/**
	 * List of terms
	 */
	List<String> terms;

	/**
	 * List of subqueries
	 */
	List<Query> subqueries;

	public Query(List<String> terms, String defaultOperator) {
		this.terms = terms;
		if (defaultOperator != null && defaultOperator.isEmpty()) {
			this.defaultOperator = defaultOperator;
		}
	}

	/**
	 * Method to convert given parsed query into string
	 */
	public String toString() {
		// TODO: YOU MUST IMPLEMENT THIS
		if (terms.size() == 1) {
			queryString = "Term:" + terms.get(0);
		}
		return queryString;
	}
}
