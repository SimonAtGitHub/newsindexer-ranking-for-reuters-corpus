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

	private String userQuery;

	private String defaultOperator;

	/**
	 * List of terms
	 */
	List<String> terms;

	/**
	 * List of subqueries
	 */
	List<Query> subqueries;

	public Query(String userQuery, String defaultOperator) {
		this.userQuery = userQuery;
		this.defaultOperator = defaultOperator;
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
		userQuery = userQuery.replaceAll("[:][(]", ": (");
		userQuery = userQuery.replaceAll("[)]", " )");
		String[] queryStrArr = userQuery
				.split(QueryRegExp.WHITESPACE_NOT_IN_QUOTES);
		// Stack for queryTerms
		Stack<String> queryTermStack = new Stack<String>();
		// Stack for Operators and brackets
		Stack<String> operatorStack = new Stack<String>();
		// Stack for Chained query terms
		Stack<String> lookaheadStack = new Stack<String>();

		boolean useDefault = true;
		String defaultIndex = "Term:";
		String index = defaultIndex;
		// A boolean flag to lookahead for consecutive terms to be merged with
		// default operator
		boolean lookahead = false;
		for (int i = 0; i < queryStrArr.length; i++) {

			String term = queryStrArr[i].trim();
			if (term.isEmpty()) {
				continue;
			}
			if (term.matches(QueryRegExp.INDEX)) {
				index = getIndex(term);
				useDefault = false;
				continue;
				// Change this flag when the first closing bracket is
				// encountered
			}
			// Current token is an opening brace, push it to "ops"
			else if (term.equals("(")) {
				operatorStack.push(String.valueOf(term));
				lookahead = false;
				if (!lookaheadStack.isEmpty())
					transferChainedTermsToMainStack(defaultOperator,
							queryTermStack, operatorStack, lookaheadStack,
							false);
			}
			// Closing brace encountered, solve entire brace
			else if (term.equals(")")) {
				if (!useDefault) {
					useDefault = true;
				}
				lookahead = false;
				if (!lookaheadStack.isEmpty())
					transferChainedTermsToMainStack(defaultOperator,
							queryTermStack, operatorStack, lookaheadStack,
							false);
				while (!operatorStack.peek().equals("(")) {
					queryTermStack.push(applyOp(operatorStack.pop(),
							queryTermStack.pop(), queryTermStack.pop(), false));
				}
				// Apply the brackets
				queryTermStack.push("( " + queryTermStack.pop() + " )");
				operatorStack.pop();
			}

			// Current token is an operator.
			else if (term.equals("AND") || term.equals("OR")
					|| term.equals("NOT")) {
				if (!lookaheadStack.isEmpty())
					transferChainedTermsToMainStack(defaultOperator,
							queryTermStack, operatorStack, lookaheadStack,
							false);
				// While top of "ops" has same or greater precedence to current
				// token, which is an operator. Apply operator on top of "ops"
				// to top two elements in values stack
				while (!operatorStack.empty()
						&& hasPrecedence(term, operatorStack.peek()))
					queryTermStack.push(applyOp(operatorStack.pop(),
							queryTermStack.pop(), queryTermStack.pop(), false));

				// Push current token to "ops".
				operatorStack.push(String.valueOf(term));
				lookahead = false;
			} else {
				// // For a term prepend it with the index. If no index is
				// // specified, default it to Term Index.Eg.Term:term
				Pattern indexedTerm = Pattern
						.compile(QueryRegExp.TERMS_WITH_INDEX);
				if (!useDefault) {
					term = index + term;
				}
				Matcher indexedTermMatcher = indexedTerm.matcher(term);
				if (!indexedTermMatcher.matches()) {
					term = defaultIndex + term;
				}

				// Check if lookahead is true. If yes, pop the item from
				// queryItemStack and merge with default operator
				if (lookahead) {
					// queryTermStack.push(applyOp(defaultOperator, term,
					// queryTermStack.pop()));
					if (lookaheadStack.isEmpty()) {
						lookaheadStack.push(queryTermStack.pop());
					}
					lookaheadStack.push(term);
					// Keep looking ahead for more terms to merge
					lookahead = true;
				} else {
					queryTermStack.push(term);
					lookahead = true;
				}
			}
		}
		// First finalize the lookahead stack, shift everything to main stack
		if (!lookaheadStack.isEmpty()
				|| (operatorStack.isEmpty() && !lookaheadStack.isEmpty())) {
			transferChainedTermsToMainStack(defaultOperator, queryTermStack,
					operatorStack, lookaheadStack, false);
		}
		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining values
		while (!operatorStack.empty() && queryTermStack.size() > 1)
			queryTermStack.push(applyOp(operatorStack.pop(),
					queryTermStack.pop(), queryTermStack.pop(), false));

		// If there are still some elements left on the main stack, concatenate
		// with default operator
		if (operatorStack.isEmpty() && queryTermStack.size() > 1) {
			while (queryTermStack.size() > 1) {
				queryTermStack.push(applyOp(defaultOperator,
						queryTermStack.pop(), queryTermStack.pop(), false));
			}
		}
		if (queryTermStack.isEmpty()) {
			System.out.println("Query parsing error for " + userQuery);
		}
		// Top of "values" contains result, return it
		return queryTermStack.pop();

	}

	private String getIndex(String term) {
		if (term.equalsIgnoreCase("term:")) {
			return "Term:";
		} else if (term.equalsIgnoreCase("category:")) {
			return "Category:";
		} else if (term.equalsIgnoreCase("place:")) {
			return "Place:";
		} else if (term.equalsIgnoreCase("author:")) {
			return "Author:";
		}
		return "Term:";
	}

	private void transferChainedTermsToMainStack(String defaultOperator,
			Stack<String> queryTermStack, Stack<String> operatorStack,
			Stack<String> lookaheadStack, boolean stringRepresentation) {
		while (lookaheadStack.size() > 1) {
			lookaheadStack.push(applyOp(defaultOperator, lookaheadStack.pop(),
					lookaheadStack.pop(), stringRepresentation));
		}
		// If operator stack is not empty. Enclose the chain in a
		// bracket
		if (!operatorStack.isEmpty()
				&& operatorStack.peek().matches(QueryRegExp.OPERATOR)) {
			if (!stringRepresentation)
				lookaheadStack.push("( " + lookaheadStack.pop() + " )");
			else
				lookaheadStack.push("[ " + lookaheadStack.pop() + " ]");
		}
		// Push this onto the main stack
		queryTermStack.push(lookaheadStack.pop());
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
	public static String applyOp(String operator, String right, String left,
			boolean stringRepresentation) {
		if (stringRepresentation && operator.equalsIgnoreCase("NOT")) {
			return left + " AND <" + right + ">";
		} else {
			return left + " " + operator + " " + right;
		}
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
		// For our convenience in handling the brackets
		userQuery = userQuery.replaceAll("[(]", "( ");
		userQuery = userQuery.replaceAll("[:][(]", ": (");
		userQuery = userQuery.replaceAll("[)]", " )");
		String[] queryStrArr = userQuery
				.split(QueryRegExp.WHITESPACE_NOT_IN_QUOTES);
		// Stack for queryTerms
		Stack<String> queryTermStack = new Stack<String>();
		// Stack for Operators and brackets
		Stack<String> operatorStack = new Stack<String>();
		// Stack for Chained query terms
		Stack<String> lookaheadStack = new Stack<String>();

		boolean useDefault = true;
		String defaultIndex = "Term:";
		String index = defaultIndex;
		// A boolean flag to lookahead for consecutive terms to be merged with
		// default operator
		boolean lookahead = false;
		for (int i = 0; i < queryStrArr.length; i++) {

			String term = queryStrArr[i].trim();
			if (term.isEmpty()) {
				continue;
			}
			if (term.matches(QueryRegExp.INDEX)) {
				index = getIndex(term);
				useDefault = false;
				continue;
				// Change this flag when the first closing bracket is
				// encountered
			}
			// Current token is an opening brace, push it to "ops"
			else if (term.equals("(")) {
				operatorStack.push(String.valueOf(term));
				lookahead = false;
				if (!lookaheadStack.isEmpty())
					transferChainedTermsToMainStack(defaultOperator,
							queryTermStack, operatorStack, lookaheadStack, true);
			}
			// Closing brace encountered, solve entire brace
			else if (term.equals(")")) {
				if (!useDefault) {
					useDefault = true;
				}
				lookahead = false;
				if (!lookaheadStack.isEmpty())
					transferChainedTermsToMainStack(defaultOperator,
							queryTermStack, operatorStack, lookaheadStack, true);
				while (!operatorStack.peek().equals("(")) {
					queryTermStack.push(applyOp(operatorStack.pop(),
							queryTermStack.pop(), queryTermStack.pop(), true));
				}
				// Apply the brackets
				queryTermStack.push("[ " + queryTermStack.pop() + " ]");
				operatorStack.pop();
			}

			// Current token is an operator.
			else if (term.equals("AND") || term.equals("OR")
					|| term.equals("NOT")) {
				if (!lookaheadStack.isEmpty())
					transferChainedTermsToMainStack(defaultOperator,
							queryTermStack, operatorStack, lookaheadStack, true);
				// While top of "ops" has same or greater precedence to current
				// token, which is an operator. Apply operator on top of "ops"
				// to top two elements in values stack
				while (!operatorStack.empty()
						&& hasPrecedence(term, operatorStack.peek()))
					queryTermStack.push(applyOp(operatorStack.pop(),
							queryTermStack.pop(), queryTermStack.pop(), true));

				// Push current token to "ops".
				operatorStack.push(String.valueOf(term));
				lookahead = false;
			} else {
				// // For a term prepend it with the index. If no index is
				// // specified, default it to Term Index.Eg.Term:term
				Pattern indexedTerm = Pattern
						.compile(QueryRegExp.TERMS_WITH_INDEX);
				if (!useDefault) {
					term = index + term;
				}
				Matcher indexedTermMatcher = indexedTerm.matcher(term);
				if (!indexedTermMatcher.matches()) {
					term = defaultIndex + term;
				}

				// Check if lookahead is true. If yes, pop the item from
				// queryItemStack and merge with default operator
				if (lookahead) {
					// queryTermStack.push(applyOp(defaultOperator, term,
					// queryTermStack.pop()));
					if (lookaheadStack.isEmpty()) {
						lookaheadStack.push(queryTermStack.pop());
					}
					lookaheadStack.push(term);
					// Keep looking ahead for more terms to merge
					lookahead = true;
				} else {
					queryTermStack.push(term);
					lookahead = true;
				}
			}
		}
		// First finalize the lookahead stack, shift everything to main stack
		if (!lookaheadStack.isEmpty()
				|| (operatorStack.isEmpty() && !lookaheadStack.isEmpty())) {
			transferChainedTermsToMainStack(defaultOperator, queryTermStack,
					operatorStack, lookaheadStack, true);
		}
		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining values
		while (!operatorStack.empty() && queryTermStack.size() > 1)
			queryTermStack.push(applyOp(operatorStack.pop(),
					queryTermStack.pop(), queryTermStack.pop(), true));

		// If there are still some elements left on the main stack, concatenate
		// with default operator
		if (operatorStack.isEmpty() && queryTermStack.size() > 1) {
			while (queryTermStack.size() > 1) {
				queryTermStack.push(applyOp(defaultOperator,
						queryTermStack.pop(), queryTermStack.pop(), true));
			}
		}
		if (queryTermStack.isEmpty()) {
			System.out.println("Query parsing error for " + userQuery);
		}
		// Top of "values" contains result, return it
		return "{ " + queryTermStack.pop() + " }";

		// String queryString = query;
		// if (!queryString.isEmpty() && queryString.length() > 1) {
		// queryString = queryString.replaceAll("[(]", "[");
		// queryString = queryString.replaceAll("[)]", "]");
		// // Replace NOT term with AND <term> where NOT is not in the end
		// queryString = queryString.replaceAll("(NOT)( )"
		// + QueryRegExp.TERMS_RELUCTANT + "( ){1}(.*)", "AND " + "<"
		// + "$3$4" + ">" + "$5");
		// // Replace NOT term with AND <term> where NOT term is the final term
		// queryString = queryString.replaceAll("(NOT)( )"
		// + QueryRegExp.TERMS_NON_RELUCTANT, "AND " + "<" + "$3$4"
		// + ">");
		//
		// }
		//
		// // Enclose the string in flower brackets
		// queryString = "{ " + queryString + " }";
	}
}
