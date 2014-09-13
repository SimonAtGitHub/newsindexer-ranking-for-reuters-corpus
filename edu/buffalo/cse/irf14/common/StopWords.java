package edu.buffalo.cse.irf14.common;

import java.util.EnumSet;

/**
 * This is an enumeration of stopwords. List is a subset of http://www.ranks.nl/stopwords
 */
public enum StopWords {
	
	A, ABOUT, AN, AFTER, AGAIN, AGAINST, ALL, AM, AND, ANY, ARE, AS, AT, BE, BECAUSE, BEEN, BEFORE,
	BEING, BELOW, BETWEEN, BUT, BY, DO, FOR, FROM, IN, INTO, IS, IT, MY, NO, NOR, NOT, OF, ON, OUR,
	OURS, THAN, THAT, THE, THEIR, THEM, THEMSELVES, THEN, THERE, THEY, THIS, TOO, UP, WAS, WE, WHY, 
	YOU, YOURSELF;
	
	public static final EnumSet<StopWords> stopWordsSet = EnumSet.allOf(StopWords.class);
	
	
	public static StopWords  valueOfDesc(String s){
		s=s.toUpperCase();
		for(StopWords sw:StopWords.values()){
			if(sw.name().equals(s)){
				return valueOf(s);
			}
		}
		return null;
	}
	
};
	

