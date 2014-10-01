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
			// Don't apply if starts with digit
			if (!Character.isLetter(termText.charAt(0))) {
				return;
			}
			// The token in first token of a sentence if it's either the first
			// token
			// of the stream or the previous token was end of a sentence.
			boolean firstToken = stream.isFirstToken();
			Token previousToken = stream.getPrevious();
			String nextTermText = "";
			Token nextToken = null;
			// get the next token
			nextToken = stream.getNext();
			if (null != nextToken)
				nextTermText = nextToken.getTermText();
			if (firstToken
					|| (previousToken != null && previousToken
							.isEndOfSentence())
					|| token.isBeginningOfSentence()) {
				// For lowercasing an ALL CAPS sentence, check if this or next
				// term is ALL CAPS. If yes, keep iterating till hasNext() is
				// true
				if (termText.matches(RegExp.REGEX_ALL_CAPS)
						&& nextTermText.matches(RegExp.REGEX_ALL_CAPS)) {
					termText = termText.toLowerCase();
					token.setTermText(termText);
					while (stream.hasNext()
							&& (nextToken != null && !nextToken
									.isEndOfSentence())) {
						nextToken = stream.next();
						nextToken.setTermText(nextToken.getTermText()
								.toLowerCase());
					}
					return;
				}
				if (termText.matches(RegExp.REGEX_ALL_CAPS)
						&& nextToken == null) {
					// What if it's the only word in the stream and All CAPS
					// lowercase it
					termText = termText.toLowerCase();
					token.setTermText(termText);
				} else if (termText.matches(RegExp.REGEX_ALL_CAPS)
						&& !nextTermText.matches(RegExp.REGEX_ALL_CAPS)) {
					// If first term is all CAPS and not the next word. keep it
					// do nothing
				} else {
					termText = termText.toLowerCase();
					token.setTermText(termText);
				}
				// Merging of Camel Cased tokens
				if (termText.matches(RegExp.REGEX_ALL_CAPS)
						&& nextTermText.matches(RegExp.REGEX_FIRST_CAPS)
						&& !nextToken.isBeginningOfSentence()) {
					token.merge(nextToken);
					// If merged token was end of sentence, make the previous
					// token as end of sentence
					if (nextToken.isEndOfSentence()) {
						token.setEndOfSentence(true);
					}
					stream.next();
					stream.remove();
					while (stream.hasNext()) {
						// Look ahead without moving the pointer
						nextToken = stream.getNext();
						nextTermText = nextToken.getTermText();
						if (StringUtil.matchRegex(nextTermText,
								RegExp.REGEX_FIRST_CAPS)
								&& !(token.isEndOfSentence() && nextToken
										.isBeginningOfSentence())) {
							token.merge(nextToken);
							// If merged token was end of sentence, make the
							// previous token as end of sentence
							if (nextToken.isEndOfSentence()) {
								token.setEndOfSentence(true);
							}
							stream.next();
							stream.remove();
						} else {
							break;
						}
					}
				}
				return;
			} else {
				// check for all capital letters in a word
				if (null != termText
						&& StringUtil.matchRegex(termText,
								RegExp.REGEX_ALL_CAPS)) {

					// Do Nothing
				}
				// check for tokens like biwords 'Stanford University'
				if (null != termText
						&& null != nextToken
						&& StringUtil.matchRegex(termText,
								RegExp.REGEX_FIRST_CAPS)
						&& StringUtil.matchRegex(nextTermText,
								RegExp.REGEX_FIRST_CAPS)
						&& !(token.isEndOfSentence() && nextToken
								.isBeginningOfSentence())) {
					// Make sure that the merging doesn't merge Beggining of one
					// sentence & end of previous statement
					token.merge(nextToken);
					// If merged token was end of sentence, make the previous
					// token as end of sentence
					if (nextToken.isEndOfSentence()) {
						token.setEndOfSentence(true);
					}
					stream.next();
					stream.remove();
					while (stream.hasNext()) {
						// Look ahead without moving the pointer
						nextToken = stream.getNext();
						nextTermText = nextToken.getTermText();
						if (StringUtil.matchRegex(nextTermText,
								RegExp.REGEX_FIRST_CAPS)
								&& !(token.isEndOfSentence() && nextToken
										.isBeginningOfSentence())) {
							token.merge(nextToken);
							// If merged token was end of sentence, make the
							// previous token as end of sentence
							if (nextToken.isEndOfSentence()) {
								token.setEndOfSentence(true);
							}
							stream.next();
							stream.remove();
						} else {
							break;
						}
					}
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