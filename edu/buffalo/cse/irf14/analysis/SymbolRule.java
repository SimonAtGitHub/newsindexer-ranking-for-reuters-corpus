package edu.buffalo.cse.irf14.analysis;

public class SymbolRule extends TokenFilter {

	TokenStream stream;

	public SymbolRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// stream.next();
		return false;
	}

	// To be changed
	@Override
	public TokenStream getStream() {
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			if (termText != null) {
				termText = filterSymbols(termText);
				if (termText == null || termText.isEmpty()) {
					stream.remove();
					continue;
				}
				token.setTermText(termText);
				System.out.println(token.getTermText());
			}
		}
		return stream;
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
		String regexForPunctuation = ".+[.!?]";
		// Run it till all the punctuation marks are gone. It might happen that
		// someone has put "Bermuda triangle exists?." by mistake or has put
		// multiple exclamation marks like Amazing!!!!!!.
		// Don't Remove dots if they occur between two numbers
		if (termText.matches(regexForPunctuation)) {
			termText = termText.replaceAll("[.!?]", "");
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
		String regexForJustHyphen = "^(\\s)*[-]+(\\s)*$";
		if (termText.matches(regexForJustHyphen)) {
			return null;
		}
		// This regex should match for various combinations like 6-6, BB3-A,
		// BB3B-A, BB3-A9 etc.
		// "([0-9]+[-][0-9]+)|(([a-zA-Z]*)(\\d)+([a-zA-Z]*)[-][0-9]*[aA-zZ]+[0-9]*)|([0-9]*([a-zA-Z]+)[0-9]*[-][aA-zZ]*[0-9]+[aA-zZ]*)";
		String regex = "((\\d)+[-](\\d)+)|(([a-zA-Z]*)(\\d)+([a-zA-Z]*)[-](\\d)*[aA-zZ]+(\\d)*)|((\\d)*([a-zA-Z]+)(\\d)*[-][aA-zZ]*(\\d)+[aA-zZ]*)";
		String regexWithHyphenAtEndOrStart = "([aA-zZ]+[-]+$)|(^[-]+[aA-zZ]+$)";
		if (!termText.matches(regex)
				&& !termText.matches(regexWithHyphenAtEndOrStart)) {
			termText = termText.replaceAll("-", " ");
		} else if (termText.matches(regexWithHyphenAtEndOrStart)) {
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
		// Since 's has different implementations
		// 2. Filter apostrophes |*'*]
		termText = termText.replaceAll("('s)", "");
		termText = termText.replaceAll("s'", "s");
		// 2b - Filter out all common contractions.
		return termText;
	}
}
