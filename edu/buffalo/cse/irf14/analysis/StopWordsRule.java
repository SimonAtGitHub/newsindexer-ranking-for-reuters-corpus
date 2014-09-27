package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;
import edu.buffalo.cse.irf14.common.StopWords;

public class StopWordsRule extends TokenFilter {

	public StopWordsRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	/**
	 * Function that applies rule to the current token
	 */
	public void applyFilter() {
		Token token = stream.getCurrent();
		if (token != null) {
			String termText = token.getTermText();
			if (termText != null
					&& Character.isAlphabetic(termText.charAt(0))
					&& (termText.matches("[aA-zZ]+"
							+ RegExp.REGEX_EXT_PUNCTUATION))) {
				if (StopWords.stopWordsSet.contains(StopWords
						.valueOfDesc(termText))) {
					stream.remove();
				}
			}
		}
	}
}
