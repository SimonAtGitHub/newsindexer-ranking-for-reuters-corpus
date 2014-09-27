package edu.buffalo.cse.irf14.common;

import java.util.EnumSet;

/**
 * This is an enumeration of stopwords. List is a subset of
 * http://www.ranks.nl/stopwords
 * http://www.textfixer.com/resources/common-english-words.txt
 */
public enum StopWords {

	A, ABLE, ABOUT, ACROSS, AFTER, ALL, ALMOST, ALSO, AM, AMONG, AN, AND, ANY, ARE, AS, AT, //
	BE, BECAUSE, BEEN, BUT, BY, CAN, CANNOT, COULD, DEAR, DID, DO, DOES, EITHER, ELSE, EVER, EVERY, //
	FOR, FROM, GET, GOT, HAD, HAS, HAVE, HE, HER, HERS, HIM, HIS, HOW, HOWEVER, I, IF, IN, INTO, IS, IT, ITS, //
	JUST, LEAST, LET, LIKE, LIKELY, MAY, ME, MIGHT, MOST, MUST, MY, NEITHER, NO, NOR, NOT, //
	OF, OFF, OFTEN, ON, ONLY, OR, OTHER, OUR, OWN, RATHER, SAID, SAY, SAYS, SHE, SHOULD, SINCE, SO, SOME, //
	THAN, THAT, THE, THEIR, THEM, THEN, THERE, THESE, THEY, THIS, TIS, TO, TOO, TWAS, //
	US, WANTS, WAS, WE, WERE, WHAT, WHEN, WHERE, WHICH, WHILE, WHO, WHOM, WHY, WILL, WITH, WOULD, YET, YOU, YOUR;

	public static final EnumSet<StopWords> stopWordsSet = EnumSet
			.allOf(StopWords.class);

	public static StopWords valueOfDesc(String s) {
		s = s.toUpperCase();
		for (StopWords sw : StopWords.values()) {
			if (sw.name().equals(s)) {
				return valueOf(s);
			}
		}
		return null;
	}

};
