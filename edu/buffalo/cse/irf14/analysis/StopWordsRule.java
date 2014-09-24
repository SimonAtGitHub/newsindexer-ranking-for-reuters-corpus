package edu.buffalo.cse.irf14.analysis;

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
					&& StopWords.stopWordsSet.contains(StopWords
							.valueOfDesc(termText))) {
				stream.remove();
			}
		}
	}
}
