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
		// get the current token
		String termText = token.getTermText();
		// The token in first token of a sentence if it's either the first token
		// of the stream or the previous token was end of a sentence.
		boolean firstToken = stream.isFirstToken();
		Token previousToken = stream.getPrevious();
		if (firstToken
				|| (previousToken != null && previousToken.isEndOfSentence())) {
			termText = termText.toLowerCase();
			token.setTermText(termText);
			return;
		} else {
			String prevTermText = null;
			if (null != previousToken) {
				prevTermText = previousToken.getTermText();
			}
			// get the next token
			Token nextToken = stream.getNext();
			String nextTermText = null;
			if (null != nextToken)
				nextTermText = nextToken.getTermText();
			// check for all capital letters in a word
			if (null != termText
					&& StringUtil.matchRegex(termText, RegExp.REGEX_ALL_CAPS)) {
				// Do Nothing
			}
			// check for tokens like biwords 'Stanford University'
			else if (null != termText
					&& null != nextToken
					&& StringUtil.matchRegex(termText, RegExp.REGEX_FIRST_CAPS)
					&& StringUtil.matchRegex(nextTermText,
							RegExp.REGEX_FIRST_CAPS)) {
				token.merge(nextToken);
				stream.next();
				stream.remove();
			}

			// If the preceding token IS NOT sentence end and the current token
			// contains one or more
			// capital letter then do not convert it into lowercase
			else if (prevTermText != null && termText != null
					&& !previousToken.isEndOfSentence()
					&& StringUtil.matchRegex(termText, RegExp.REGEX_ANY_CAPS)) {
				// Do Nothing
			}

			else {
				termText = termText.toLowerCase();
				token.setTermText(termText);
			}
		}

	}
}