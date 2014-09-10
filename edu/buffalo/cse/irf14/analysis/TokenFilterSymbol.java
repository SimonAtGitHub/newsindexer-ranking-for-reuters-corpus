package edu.buffalo.cse.irf14.analysis;

public class TokenFilterSymbol extends TokenFilter {

	TokenStream stream;

	public TokenFilterSymbol(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void increment() throws TokenizerException {
		stream.next();
	}

	// To be changed
	@Override
	public TokenStream getStream() {
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			if (termText != null) {
				termText = filterSymbols(termText);
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
		// someone has put "Bermuda triangle exists?." by mistake.

		if (termText.matches(regexForPunctuation)) {
			termText = termText.replaceAll("[.!?]", "");
		}
		// Return as it is if the punctuation does't come in the end.

		// 2. Filter apostrophes
		// String regexForApostrophes = "[*'s]|[*s']|*'*]";
		// 3. Filter hyphens
		if (termText.contains("-")) {
			String regexHyphenBetweenAlphanumeric = "[aA-zZ]+[-][0-9]+|[0-9]+[-][aA-zZ]+";
			String regexHyphenBetweenTwoWords = "[aA-zZ]+[-][aA-zZ]+";
			if (!termText.matches(regexHyphenBetweenAlphanumeric)) {
				termText = termText.replace("-", "");
			} else if (termText.matches(regexHyphenBetweenTwoWords)) {
				termText = termText.replace("-", " ");
			}
		}
		return termText;
	}
}
