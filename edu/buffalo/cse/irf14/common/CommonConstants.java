package edu.buffalo.cse.irf14.common;

import java.util.regex.Pattern;

public class CommonConstants {
	public static final String WHITESPACE = " ";

	public static final String DOCUMENT_DICTIONARY_FILENAME = "DocumentDictionary";

	public static final String TERM_DICTIONARY_FILENAME = "TermDictionary";

	public static final String AUTHOR_DICTIONARY_FILENAME = "AuthorDictionary";

	public static final String PLACE_DICTIONARY_FILENAME = "PlaceDictionary";

	public static final String CATEGORY_DICTIONARY_FILENAME = "CategoryDictionary";

	public static final String TERM_INDEX_FILENAME = "TermIndex";

	public static final String AUTHOR_INDEX_FILENAME = "AuthorIndex";

	public static final String PLACE_INDEX_FILENAME = "PlaceIndex";

	public static final String CATEGORY_INDEX_FILENAME = "CategoryIndex";

	public static final Pattern PATTERN_FOR_PLACE_DATE = Pattern
			.compile(RegExp.REGEX_FOR_PLACE_DATE);

	public static final Pattern PATTERN_FOR_YEAR_BC_AD = Pattern
			.compile(RegExp.REGEX_YEAR_BC_AD + RegExp.REGEX_EXT_PUNCTUATION);

	public static final Pattern PATTERN_FOR_YEAR = Pattern
			.compile(RegExp.REGEX_YEAR + RegExp.REGEX_EXT_PUNCTUATION);

	public static final Pattern PATTERN_FOR_YEAR_PREFIX = Pattern
			.compile(RegExp.REGEX_BC_AD + RegExp.REGEX_EXT_PUNCTUATION);

	public static final Pattern PATTERN_FOR_MONTH = Pattern
			.compile(RegExp.REGEX_MONTHS + RegExp.REGEX_EXT_PUNCTUATION);

	public static final Pattern PATTERN_FOR_DATE = Pattern
			.compile(RegExp.REGEX_DATE + RegExp.REGEX_EXT_PUNCTUATION);
	
	public static final String TYPE_TERM = "Term";
	
	public static final String TYPE_CATEGORY = "Category";
	
	public static final String TYPE_AUTHOR = "Author";
	
	public static final String TYPE_PLACE = "Place";
	
	public static final String COLON = ":";
	
	public static final String DOUBLE_QUOTES = "\"";
	
	public static final String FIRST_BRACKET_OPEN = "(";
	
	public static final String FIRST_BRACKET_CLOSE= ")";
	
	public static final String OPERATOR_AND= "AND";
	
	public static final String OPERATOR_OR= "OR";
	
	public static final String OPERATOR_NOT= "NOT";
	
	public static final String SECOND_BRACKET_OPEN= "{";
	
	public static final String SECOND_BRACKET_CLOSE= "}";
	
	public static final String COMMA= ",";
	
	public static final String HASH= "#";
	
	public static final String EQUAL_SIGN = "=";
	

}
