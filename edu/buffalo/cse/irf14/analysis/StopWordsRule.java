package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.StopWords;

public class StopWordsRule extends TokenFilter {
	TokenStream stream;

	public StopWordsRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		//stream.next();
		return false;
	}

	// To be changed
	@Override
	public TokenStream getStream() {
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			if (termText!=null && StopWords.stopWordsSet.contains(StopWords.valueOfDesc(termText))) {
				stream.remove();
			}
		}
		return stream;
	}
}
