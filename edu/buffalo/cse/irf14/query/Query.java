package edu.buffalo.cse.irf14.query;

import java.util.List;

/**
 * Class that represents a parsed query
 * @author nikhillo
 * Each query is a recursive structure consisting of a list of
 * terms and/or a list of sub-queries
 */
public class Query {
	
	/**
	 * List of terms
	 */
	List<String> terms;
	
	/**
	 * List of subqueries
	 */
	List<Query> subqueries;
	
	/**
	 * Method to convert given parsed query into string
	 */
	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS
		return null;
	}
}
