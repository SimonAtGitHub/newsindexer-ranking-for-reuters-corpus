package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;
import edu.buffalo.cse.irf14.common.StringUtil;

public class CapitalizationRule extends TokenFilter {
	TokenStream stream;

	public CapitalizationRule(TokenStream stream) {
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

		// var to denote the previous token in the stream
		Token prevToken = null;
		Token token = null;
		while (stream.hasNext()) {
			// get the previous token
			prevToken = token;
			String prevTermText = null;
			if (prevToken != null)
				prevTermText = prevToken.getTermText();
			// get the current token
			token = stream.next();
			String termText = null;
			if (null != token)
				termText = token.getTermText();
			// get the next token
			Token nextToken = stream.getNext();
			String nextTermText = null;
			if (null != nextToken)
				nextTermText = nextToken.getTermText();
			// check for all capital letters in a word
			if (null != termText
					&& StringUtil.matchRegex(termText, RegExp.REGEX_ALL_CAPS)) {
				continue;
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
					&& !prevToken.isEndOfSentence()
					&& StringUtil.matchRegex(termText, RegExp.REGEX_ANY_CAPS)) {
				continue;
			}

			else {
				termText = termText.toLowerCase();
				token.setTermText(termText);
			}

		}
		return stream;
	}
}
