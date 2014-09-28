package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;

/**
 * @author animeshk
 * 
 */
public class SpecialCharactersRule extends TokenFilter {

	public SpecialCharactersRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void applyFilter() {
		Token token = stream.getCurrent();
		if (token != null) {
			String termText = token.getTermText();

			termText = filterSpecialCharacters(termText);
			if (termText.isEmpty()) {
				stream.remove();
			}
			token.setTermText(termText);
		}
	}

	private String filterSpecialCharacters(String termText) {
		// Only if special characters exist
		if (termText.matches("\\w{1,}")) {
			return termText;
		}
		// We assume here that all the filters higher in priority have finished
		// their job and we just have to preserve the ones where special
		// characters were preserved and apply filtering after that. For Eg. In
		// 6-6, B-44, !true, em?ty

		// First remove all unwanted special characters other than hyphens and
		// punctuation.
		// Preserve whitespaces
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
		// Trim any trailing or leading spaces
		termText = termText.trim();
		return termText;
	}

}
