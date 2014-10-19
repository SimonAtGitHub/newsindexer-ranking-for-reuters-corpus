/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.LinkedList;

import edu.buffalo.cse.irf14.common.CommonConstants;
import edu.buffalo.cse.irf14.common.RegExp;

/**
 * @author nikhillo Class that converts a given string into a
 *         {@link TokenStream} instance
 */
public class Tokenizer {

	private String delim;

	/**
	 * Default constructor. Assumes tokens are whitespace delimited
	 */
	public Tokenizer() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		this.delim = CommonConstants.WHITESPACE;
	}

	/**
	 * Overloaded constructor. Creates the tokenizer with the given delimiter
	 * 
	 * @param delim
	 *            : The delimiter to be used
	 */
	public Tokenizer(String delim) {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		this.delim = delim;
	}

	/**
	 * Method to convert the given string into a TokenStream instance. This must
	 * only break it into tokens and initialize the stream. No other processing
	 * must be performed. Also the number of tokens would be determined by the
	 * string and the delimiter. So if the string were "hello world" with a
	 * whitespace delimited tokenizer, you would get two tokens in the stream.
	 * But for the same text used with lets say "~" as a delimiter would return
	 * just one token in the stream.
	 * 
	 * @param str
	 *            : The string to be consumed
	 * @return : The converted TokenStream as defined above
	 * @throws TokenizerException
	 *             : In case any exception occurs during tokenization
	 */
	public TokenStream consume(String str) throws TokenizerException {
		Integer position = 1;
		if (str == null) {
			throw new TokenizerException("Null argument was passed.");
		} else if (str.isEmpty()) {
			throw new TokenizerException("Empty String was passed.");
		} else {
			// splits the string into a string array on the basis of delimitor
			String arr[] = str.split(delim);
			// Temp list to hold the tokens
			LinkedList<Token> tempList = new LinkedList<Token>();

			// Flag to mark the beginning of a sentence
			// True by default for the first token
			boolean beginningOfSentence = true;
			// iterate over the items in the array
			for (int index = 0; index < arr.length; index++) {
				Token token = new Token();
				token.setTermText(arr[index]);
				token.setTermBuffer(token.getTermText().toCharArray());
				//set positional Index
				token.setPosition(position);
				position++;
				// If previous token was marked as beginning of a sentence.
				if (beginningOfSentence && !token.getTermText().isEmpty()) {
					token.setBeginningOfSentence(true);
					beginningOfSentence = false;
				}
				// For Sentence ending with punctuation, mark the end of
				// sentence
				if (token.getTermText().matches(RegExp.REGEX_SENT_ENDS)) {
					token.setEndOfSentence(true);
					beginningOfSentence = true;
				}
				tempList.add(token);
			}
			TokenStream tokenStream = new TokenStream(tempList);
			return tokenStream;
		}
	}
}
