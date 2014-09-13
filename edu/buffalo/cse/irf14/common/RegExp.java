package edu.buffalo.cse.irf14.common;

public class RegExp {
	//regular expression for a string with first letter as capital followed by any sequence of characters
	public static final String REGEX_FIRST_CAPS = "^[A-Z](.*)";
	//regular expression that denotes all letters of a word as capital letters
	public static final String REGEX_ALL_CAPS="[A-Z]$"; 
	//regular expression that denotes that a word ends with ! or . or ?
	public static final String REGEX_SENT_ENDS="(.*)[.|!|?]$";
	//regular expression that denotes that a string is in caps		
	public static final String REGEX_ANY_CAPS="(.*)[A-Z+](.*)";
}
