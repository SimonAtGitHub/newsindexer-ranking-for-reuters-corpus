package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Pattern;

public class TestToken {
	public static void main(String[] args) throws TokenizerException {
		String regex = "\\w{1,}";
		Pattern pattern = Pattern.compile(regex);
		String[] matcherArray = new String[] { "1#", "ABCD", "AB12", "12DE",
				"12$", "//ABC", "ABC." };
		for (String string : matcherArray) {
			System.out
					.println(string + ":" + pattern.matcher(string).matches());
		}
	}
}