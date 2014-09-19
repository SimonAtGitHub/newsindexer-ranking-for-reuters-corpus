package edu.buffalo.cse.irf14.common;

import java.util.EnumSet;

/**
 * This is an enumeration of stopwords. List is a subset of
 * http://www.ranks.nl/stopwords
 * http://www.textfixer.com/resources/common-english-words.txt
 */
public enum StopWords {

	A, ABLE, ABOUT, ACROSS, AFTER, AGAIN, AGAINST, ALMOST, ALSO, ALL, AM, AMONG, AN, AND, ANY, ARE, AS, AT, BE, BECAUSE, BEEN, BEFORE, BEING, BELOW, BETWEEN, BUT, BY, CAN, CANNOT, COULD, DEAR, DID, DO, DOES, EITHER, ELSE, EVER, EVERY, GET, GOT, FOR, FROM, HAD, HAS, HAVE, HE, HER, HERS, HIM, HIS, HOW, HOWEVER, I, IN, INTO, IS, IT, JUST, LEAST, LET, LIKE, LIKELY, MAY, ME, MIGHT, MOST, MUST, MY, NEITHER, NO, NOR, NOT, OF, OFF, OFTEN, ON, ONLY, OUR, OURS, OWN, RATHER, SAID, SAY, SAYS, SHE, SHOULD, SINCE, SO, SOME, THAN, THAT, THE, THEIR, THEM, THEMSELVES, THEN, THERE, THESE, THEY, THIS, TO, TOO, UP, WAS, WE, WHY, YOU, YOURSELF;

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
