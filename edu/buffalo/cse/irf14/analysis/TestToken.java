package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestToken {
	public static void main(String[] args) throws TokenizerException {
		// String regex = "\\w{1,}";
		// String regexToSplitPlaceAndDate =
		// "(.*)(\\s+([A-Za-z]{3,9}[\\s]+[0-9]{1,2}))";
		// String[] matcherArray = new String[] { "COMMACK, N.Y., Feb 26",
		// "Washington, March 2", "ANN ARBOR, Mich., September 1",
		// "LAKE SUCCESS, N.Y. March 3", "LAKE SUCCESS, N.Y., March 20" };
		String hyphenAtStartOrEnd = "([-]{1,}[^0-9]*|[^0-9]*[-]{1,})";
		String[] matcherArray = { "b--", "--a", "a-", "-b", "a",
				"-Dailmer-Puch","6-6","a-b" };
		Pattern pattern = Pattern.compile(hyphenAtStartOrEnd);
		for (String string : matcherArray) {
			Matcher matcher = pattern.matcher(string);
			boolean matches = matcher.matches();
			if (matches) {
				int groupCount = matcher.groupCount();
				// System.out.println(matcher.group(1));
				for (int i = 1; i < groupCount; i++) {
					System.out.println(matcher.group(i));
				}
			}
			System.out.println(string + ":" + matches);
		}
		// String name = "Animesh";
		// String[] split = name.split("and");
		// System.out.println(Arrays.deepToString(split));

	}
}