package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;

public class TestToken {
	public static void main(String[] args) throws TokenizerException {
		// String regex = "\\w{1,}";
		// String regexToSplitPlaceAndDate =
		// "(.*)(\\s+([A-Za-z]{3,9}[\\s]+[0-9]{1,2}))";
		// String[] matcherArray = new String[] { "COMMACK, N.Y., Feb 26",
		// "Washington, March 2", "ANN ARBOR, Mich., September 1",
		// "LAKE SUCCESS, N.Y. March 3", "LAKE SUCCESS, N.Y., March 20" };

		// String hyphenAtStartOrEnd = "([-]{1,}[^0-9]*|[^0-9]*[-]{1,})";
		// String[] matcherArray = { "b--", "--a", "a-", "-b", "a",
		// "-Dailmer-Puch","6-6","a-b" };
		// Pattern pattern = Pattern.compile(hyphenAtStartOrEnd);
		// for (String string : matcherArray) {
		// Matcher matcher = pattern.matcher(string);
		// boolean matches = matcher.matches();
		// if (matches) {
		// int groupCount = matcher.groupCount();
		// // System.out.println(matcher.group(1));
		// for (int i = 1; i < groupCount; i++) {
		// System.out.println(matcher.group(i));
		// }
		// }
		// System.out.println(string + ":" + matches);
		// }

		// String name = "Animesh";
		// String[] split = name.split("and");
		// System.out.println(Arrays.deepToString(split));

		// Enums
		// Month.valueOfDesc("December");
		// Month.valueOfDesc("Something but not month");

		// String index = "\\bTerm\\b|\\bCategory\\b|\\bAuthor\\b|\\bPlace\\b";
		// String operator = "\\bAND\\b|\\bOR\\b|\\bNOT\\b";
		// String queryTerm = "(\\S+)";
		// String queryPhrase ="[\"]("+queryTerm+"(\\s))*(\\S)+[\"]";
		// String input = "\"abc def\"";

		// Querying
		// QueryParser parser = new QueryParser();
		// Query query = parser.parse("hello", null);
		// System.out.println(query.toString());

		// Query Regex
		// String[] stringArr = new String[] { "hello", "hello world",
		// " Hello!! World", "Hello!! World", "Hello Animesh Kumar",
		// "Hello Animesh Kumar!! Welcome", "Hello Animesh Kumar ",
		// "Hello Animesh Kumar!! Welcome ", "(hello)", "(hello world)",
		// "( Hello!! World)", "(Hello!! World)", "(Hello Animesh Kumar)",
		// "(Hello Animesh Kumar!! Welcome)", "(Hello Animesh Kumar )",
		// "(Hello Animesh Kumar!! Welcome )", "\"hello\"",
		// "\"hello world\"", "\" Hello!! World\"", "\"Hello!! World\"",
		// "\"Hello Animesh Kumar\"", "\"Hello Animesh Kumar!! Welcome\"",
		// "\"Hello Animesh Kumar \"",
		// "\"Hello Animesh Kumar!! Welcome \"" };
		// for (String string : stringArr) {
		// System.out.println(string
		// + " :"
		// + (string.matches(QueryRegExp.TERM) || string
		// .matches(QueryRegExp.COMPLEX_TERM)));
		// }

		// Query Formulation
		String[] stringArr = new String[] { "A B C D E F (G NOT H)",
				"(black OR blue) AND bruises", "hello", "hello world",
				" Hello!! World", "Hello!! World", "Hello Animesh Kumar",
				"Hello Animesh Kumar!! Welcome", "\"hello world\"" };
		for (String string : stringArr) {
			Query query = QueryParser.parse(string, "OR");
			System.out.println("Original - " + string);
			System.out.println("Query -" + query.getQuery());
			System.out.println("toString -" + query.toString());
			System.out.println();
		}

	}
}