package edu.buffalo.cse.irf14.query;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.common.QueryRegExp;

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
		query = formulateQuery(userQuery, defaultOperator);
	}

	/**
	 * This method works on the similar structure as given in :-
	 * http://www.geeksforgeeks.org/expression-evaluation/ <br>
	 * http://ideone.com/NEYfnD
	 * 
	 * @param userQuery
	 * @param defaultOperator
	 * @return
	 */
	private String formulateQuery(String userQuery, String defaultOperator) {
		// For our convenience in handling the brackets
		userQuery = userQuery.replaceAll("[(]", "( ");
		// userQuery = userQuery.replaceAll("[:(]", " (");
		userQuery = userQuery.replaceAll("[)]", " )");
		String[] queryStrArr = userQuery
				.split(QueryRegExp.WHITESPACE_NOT_IN_QUOTES);
		// Stack for numbers: "values"
		Stack<String> queryTermStack = new Stack<String>();
		Stack<String> operatorStack = new Stack<String>();
		boolean useDefault = false;
		for (int i = 0; i < queryStrArr.length; i++) {

			// Current token is an opening brace, push it to "ops"
			String term = queryStrArr[i].trim();
			if (term.isEmpty()) {
				continue;
			}
			if (term.equals("(")) {
				operatorStack.push(String.valueOf(term));
				useDefault = false;
			}
			// Closing brace encountered, solve entire brace
			else if (term.equals(")")) {
				while (!operatorStack.peek().equals("(")) {
					queryTermStack.push(applyOp(operatorStack.pop(),
							queryTermStack.pop(), queryTermStack.pop()));
				}
				// Apply the brackets
				queryTermStack.push("(" + queryTermStack.pop() + ")");
				operatorStack.pop();
				useDefault = false;
			}

			// Current token is an operator.
			else if (term.equals("AND") || term.equals("OR")
					|| term.equals("NOT")) {
				// While top of "ops" has same or greater precedence to current
				// token, which is an operator. Apply operator on top of "ops"
				// to top two elements in values stack
				while (!operatorStack.empty()
						&& hasPrecedence(term, operatorStack.peek()))
					queryTermStack.push(applyOp(operatorStack.pop(),
							queryTermStack.pop(), queryTermStack.pop()));

				// Push current token to "ops".
				operatorStack.push(String.valueOf(term));
				useDefault = false;
			} else {
				// For a term prepend it with the index. If no index is
				// specified, default it to Term Index.Eg.Term:term
				Pattern indexedTerm = Pattern.compile(QueryRegExp.INDEX);
				Matcher indexedTermMatcher = indexedTerm.matcher(term);
				if (!indexedTermMatcher.matches()) {
					term = "Term:" + term;
				}
				// If last term was also a normal term
				if (useDefault) {
					queryTermStack.push(applyOp(defaultOperator, term,
							queryTermStack.pop()));
				} else {
					queryTermStack.push(term);
					// Flag too use the default operator between two terms
					useDefault = true;
				}
			}
		}

		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining values
		while (!operatorStack.empty())
			queryTermStack.push(applyOp(operatorStack.pop(),
					queryTermStack.pop(), queryTermStack.pop()));

		// Top of "values" contains result, return it
		return queryTermStack.pop();

	}

	// Returns true if "op2" has higher or same precedence as "op1",
	// otherwise returns false.
	public static boolean hasPrecedence(String op1, String op2) {
		if (op2.equals("(") || op2.equals(")"))
			return false;
		if ((op1.equals("NOT") && (op2.equals("AND") || op2.equals("OR"))))
			return false;
		else
			return true;
	}

	// A utility method to apply an operator "op" on operands "a"
	// and "b". Return the result.
	public static String applyOp(String operator, String right, String left) {
		return left + " " + operator + " " + right;
	}

	/**
	 * Returns a representation of query which is required to execute the query. <br>
	 * <b>NOT SAME AS toString() representation.</b>
	 * 
	 * @return
	 */
	public String getQuery() {
		// Enclose the queryString in brackets
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
			if (queryString.charAt(0) == '(') {
				queryString = "{"
						+ queryString.substring(1, queryString.length() - 1);
			}
			// Replace last occurence by }
			if (queryString.charAt(queryString.length() - 1) == ')') {
				queryString = queryString
						.substring(0, queryString.length() - 1) + "}";
			}

		}
		return queryString;
	}
}
