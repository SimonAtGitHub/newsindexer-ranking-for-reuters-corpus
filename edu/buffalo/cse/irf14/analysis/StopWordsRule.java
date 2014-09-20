package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.StopWords;

public class StopWordsRule extends TokenFilter {
	TokenStream stream;

	public StopWordsRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	/**
	 * 1. Processes the current token if it is not null 2. Moves the pointer to
	 * the next token and returns true if there is any more tokens to be
	 * processed
	 */
	@Override
	public boolean increment() throws TokenizerException {
		if (stream.getCurrent() != null) {
			applyFilter();
		}
		if (stream.hasNext()) {
			stream.next();
			return true;
		}
		return false;
	}

	@Override
	public TokenStream getStream() {
		return stream;
	}

	/**
	 * Function that applies rule to the current token
	 */
	public void applyFilter() {
		Token token = stream.getCurrent();
		String termText = token.getTermText();
		if (termText != null
				&& StopWords.stopWordsSet.contains(StopWords
						.valueOfDesc(termText))) {
			stream.remove();
		}
	}

}
