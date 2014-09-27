package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;
import edu.buffalo.cse.irf14.common.StringUtil;

public class CapitalizationRule extends TokenFilter {

	public CapitalizationRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void applyFilter() {
		Token token = stream.getCurrent();
		if (token != null) {
			// get the current token
			String termText = token.getTermText();
			// The token in first token of a sentence if it's either the first
			// token
			// of the stream or the previous token was end of a sentence.
			boolean firstToken = stream.isFirstToken();
			Token previousToken = stream.getPrevious();
			String prevTermText = "", nextTermText = "";
			Token nextToken = null;
			// get the next token
			nextToken = stream.getNext();
			if (null != nextToken)
				nextTermText = nextToken.getTermText();
			if (firstToken
					|| (previousToken != null && previousToken
							.isEndOfSentence())) {
				// For lowercasing an ALL CAPS sentence, check if this or next
				// term is ALL CAPS. If yes, keep iterating till hasNext() is
				// true
				if (termText.matches(RegExp.REGEX_ALL_CAPS)
						&& nextTermText.matches(RegExp.REGEX_ALL_CAPS)) {
					while (stream.hasNext()) {
						nextToken = stream.next();
						nextToken.setTermText(nextToken.getTermText()
								.toLowerCase());
					}
				}
				termText = termText.toLowerCase();
				token.setTermText(termText);
				return;
			} else {
				if (null != previousToken) {
					prevTermText = previousToken.getTermText();
				}
				// check for all capital letters in a word
				if (null != termText
						&& StringUtil.matchRegex(termText,
								RegExp.REGEX_ALL_CAPS)) {

					// Do Nothing
				}
				// check for tokens like biwords 'Stanford University'
				else if (null != termText
						&& null != nextToken
						&& StringUtil.matchRegex(termText,
								RegExp.REGEX_FIRST_CAPS)
						&& StringUtil.matchRegex(nextTermText,
								RegExp.REGEX_FIRST_CAPS)) {
					token.merge(nextToken);
					stream.next();
					stream.remove();
				}

				// If the preceding token IS NOT sentence end and the current
				// token
				// contains one or more
				// capital letter then do not convert it into lowercase
				else if (previousToken != null
						&& termText != null
						&& !previousToken.isEndOfSentence()
						&& StringUtil.matchRegex(termText,
								RegExp.REGEX_ANY_CAPS)) {
					// Do Nothing
				}

				else {
					termText = termText.toLowerCase();
					token.setTermText(termText);
				}
			}
		}
	}
}