/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nikhillo Static parser that converts raw text to Query objects
 */
public class QueryParser {
	/**
	 * MEthod to parse the given user query into a Query object
	 * 
	 * @param userQuery
	 *            : The query to parse
	 * @param defaultOperator
	 *            : The default operator to use, one amongst (AND|OR)
	 * @return Query object if successfully parsed, null otherwise
	 */
	public static Query parse(String userQuery, String defaultOperator) {
		// TODO: YOU MUST IMPLEMENT THIS METHOD
		// Hardcoding for now
		List<String> terms = new ArrayList<String>();
		terms.add("hello");
		Query query = new Query(terms, defaultOperator);
		return query;
	}
}
