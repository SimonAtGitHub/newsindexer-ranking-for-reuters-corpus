package edu.buffalo.cse.irf14.common;

public class RegExp {
	// regular expression for a string with first letter as capital followed by
	// any sequence of characters
	public static final String REGEX_FIRST_CAPS = "^[A-Z](.*)";
	// regular expression that denotes all letters of a word as capital letters
	public static final String REGEX_ALL_CAPS = "[A-Z]$";
	// regular expression that denotes that a word ends with ! or . or ?
	public static final String REGEX_SENT_ENDS = "(.*)[.!?]+$";
	// regular expression that denotes that a string is in caps
	public static final String REGEX_ANY_CAPS = "(.*)[A-Z+](.*)";
	// regular expression that denotes a number e.g. 12 , 98.22
	public static final String REGEX_REAL_NUM = "^([\\d]+[,]?)*[\\.]?([\\d]*)$";
	// regular expression that denotes a number or a number which is prior to
	// its derived form like 98/100, 1*2,98.22%
	public static final String REGEX_COMPOSITE_NUM = "^[\\d]+[/|*|.]?[\\d]+[%]?$";

	// regular expression that denotes a number sequence followed by an optional
	// period
	public static final String REGEX_NUM_PERIOD = "[\\d]+[\\.]?";

	// Will match tokens containing any number of instances of just hyphen. Eg
	// -, --- or --
	public static final String REGEX_FOR_JUST_HYPHEN = "^(\\s)*[-]+(\\s)*$";

	public static final String REGEX_FOR_ALPHABETS_HYPHEN = "([aA-zZ]+[-][aA-zZ]+$)";
	
	// This regex should match for various alphanumeric combinations with
	// hyphens like 6-6, BB3-A,
	// BB3B-A, BB3-A9 etc. //NOTE Currently not being used
	public static final String REGEX_FOR_HYPHEN_ALPHANUMERIC = "((\\d)+[-](\\d)+)|(([a-zA-Z]*)(\\d)+([a-zA-Z]*)[-](\\d)*[aA-zZ]+(\\d)*)|((\\d)*([a-zA-Z]+)(\\d)*[-][aA-zZ]*(\\d)+[aA-zZ]*)";

	// Eg. a-- , ++b
	public static final String REGEX_FOR_HYPHEN_AT_END_OR_START = "([aA-zZ]+[-]+$)|(^[-]+[aA-zZ]+$)";

}
