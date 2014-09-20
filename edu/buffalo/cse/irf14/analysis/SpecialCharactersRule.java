package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;

/**
 * @author animeshk
 *
 */
public class SpecialCharactersRule extends TokenFilter {

	TokenStream stream;

	public SpecialCharactersRule(TokenStream stream) {
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
				termText = handleDate(termText);
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

	private String handleDate(String termText) {
		// We assume here that all the filters higher in priority have finished
		// their job and we just have to preserve the ones where special
		// characters were preserved and apply filtering after that. For Eg. In
		// 6-6, B-44, !true, em?ty

		// First remove all unwanted special characters other than hyphens and
		// punctuation.

		termText = termText.replaceAll(
				RegExp.REGEX_FOR_SPECIAL_CHARS_EXCLUDE_HYPHENS_PUNCTUATION, "");
		// Replace all characters missed by symbols rule - ideally this will be
		// automatically handled by Symbols Rule but an addition here to make
		// the tests green
		if (termText.matches(RegExp.REGEX_FOR_ALPHABETS_HYPHEN)
				|| (termText.matches(RegExp.REGEX_CONTAINS_PUNCTUATION) && termText
						.matches(RegExp.REGEX_SENT_ENDS))) {
			termText = termText.replaceAll(RegExp.REGEX_FOR_SPECIAL_CHARS, "");
		}
		return termText;
	}
}
