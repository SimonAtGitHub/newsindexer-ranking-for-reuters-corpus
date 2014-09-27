package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;

/**
 * @author animeshk
 * 
 */
public class SymbolRule extends TokenFilter {

	public SymbolRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void applyFilter() {
		Token token = stream.getCurrent();
		if (token != null) {
			String termText = token.getTermText();
			// Only if symbols exist
			if (!termText.matches("\\w{1,}")) {
				termText = filterSymbols(termText);
			}
			if (termText == null || termText.isEmpty()) {
				stream.remove();
			}
			token.setTermText(termText);
		}

	}

	/**
	 * This method will remove (filter out) :-
	 * <ul>
	 * <li>Any punctuation marks that possibly mark the end of a sentence (. !
	 * ?) should be removed. Obviously if the symbol appears within a token it
	 * should be retained (a.out for example).</li>
	 * <li>Any possessive apostrophes should be removed (‘s s’ or just ‘ at the
	 * end of a word). Common contractions should be replaced with expanded
	 * forms but treated as one token. (e.g. should’ve => should have). All
	 * other apostrophes should be removed.</li>
	 * <li>If a hyphen occurs within a alphanumeric token it should be retained
	 * (B-52, at least one of the two constituents must have a number). If both
	 * are alphabetic, it should be replaced with a whitespace and retained as a
	 * single token (week-day => week day). Any other hyphens padded by spaces
	 * on either or both sides should be removed.</li>
	 * 
	 * @param termText
	 *            filtered termText
	 * @return
	 */
	private String filterSymbols(String termText) {
		// 1. Filter Punctuation Marks
		// Run it till all the punctuation marks are gone. It might happen that
		// someone has put "Bermuda triangle exists?." by mistake or has put
		// multiple exclamation marks like Amazing!!!!!!.
		// Don't Remove punctuation marks if they occur between two words. Only
		// take out the ones in the end.
		if (termText.matches(RegExp.REGEX_SENT_ENDS)) {
			termText = termText.replaceAll("[.!?]+$", "");
		}
		// Return as it is if the punctuation does't come in the end.

		// Filter out apostrophes if there exists any
		if (termText.contains("'")) {
			termText = filterOutApostrophes(termText);
		}

		// 3. Filter hyphens
		if (termText.contains("-")) {
			termText = filterOutHyphens(termText);
		}
		return termText;
	}

	private String filterOutHyphens(String termText) {
		// If a hyphen occurs within a alphanumeric
		// token it should be retained (B-52, at least one
		// of the two constituents must have a number). If both are
		// alphabetic, it should be replaced with a whitespace and
		// retained as a single token (week-day => week day).
		// Any other hyphens padded by spaces on either or both sides
		// should be removed.

		// If it's just hyphen return null
		if (termText.matches(RegExp.REGEX_FOR_JUST_HYPHEN)) {
			return null;
		}
		// This regex should handle preserving of hyphens in case of alphnumeric
		// or completely numeric combinations with hyphens like 6-6, BB3-A,
		// BB3B-A, BB3-A9 etc.
		// So, just check if it's completely alphabetic with hyphen. If not.
		// Chuck out the hyphens.
		if (termText.matches(RegExp.REGEX_FOR_ALPHABETS_HYPHEN)
				&& !termText.matches(RegExp.REGEX_FOR_HYPHEN_AT_END_OR_START)) {
			termText = termText.replaceAll("-", " ");
		} else if (termText.matches(RegExp.REGEX_FOR_HYPHEN_AT_END_OR_START)) {
			termText = termText.replaceAll("-", "");
		}
		return termText;
	}

	/**
	 * Returns a filtered termText after applying all rules applicable to
	 * apostrophes.
	 * 
	 * @param termText
	 *            Unfiltered termText on which apostrophe filtering is to be
	 *            applied.
	 * @return filtered termText
	 */
	private String filterOutApostrophes(String termText) {
		// First remove all 's and s'
		termText = termText.replaceAll("('s)", "");
		termText = termText.replaceAll("s'", "s");
		// 2b - Filter out all common contractions.
		termText = expandCommonContractions(termText);
		// If quote still exists, remove all instances of it
		termText = termText.replaceAll("'", "");
		return termText;
	}

	private String expandCommonContractions(String termText) {
		// Run it in separation till all occurences of common contractions are
		// gone i.e., Don't use if-elseif-else because once a condition is
		// satisfied, loop exits and we have to handle multiple cases of
		// contractions too.

		// Run the non obvious contractions first like shan't, won't, y'all,
		// ai'nt
		if (termText.contains("shan't")) {
			termText = termText.replaceAll("n't", "ll not");
		}
		if (termText.contains("won't")) {
			termText = termText.replaceAll("on't", "ill not");
		}
		if (termText.contains("ma'am")) {
			termText = termText.replaceAll("'am", "dam");
		}
		if (termText.contains("ain't")) {
			termText = termText.replaceAll("n't", "re not");
		}
		if (termText.contains("y'all")) {
			termText = termText.replaceAll("'all", "ou all");
		}
		if (termText.contains("n't")) {
			termText = termText.replaceAll("n't", " not");
		}
		if (termText.contains("'ve")) {
			termText = termText.replaceAll("'ve", " have");
		}
		if (termText.contains("'d")) {
			termText = termText.replaceAll("'d", " would");
		}
		if (termText.contains("'ll")) {
			termText = termText.replaceAll("'ll", " will");
		}
		if (termText.contains("'m")) {
			termText = termText.replaceAll("'m", " am");
		}
		if (termText.contains("o'clock")) {
			termText = termText.replaceAll("o'clock", " of the clock");
		}
		if (termText.contains("'re")) {
			termText = termText.replaceAll("'re", " are");
		}
		// Special handling since it breaks the 'empty' test. Check if it's also
		// end of the string
		if (termText.contains("'em") && termText.endsWith("'em")) {
			termText = termText.replaceAll("'em", "them");
		}
		return termText;

	}

}
