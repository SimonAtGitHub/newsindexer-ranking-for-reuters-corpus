/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.List;

import edu.buffalo.cse.irf14.common.CommonConstants;

/**
 * @author nikhillo
 * Class that converts a given string into a {@link TokenStream} instance
 */
public class Tokenizer {
	
	private String delim;
	/**
	 * Default constructor. Assumes tokens are whitespace delimited
	 */
	public Tokenizer() {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
	    this.delim = CommonConstants.WHITESPACE;
	}
	
	/**
	 * Overloaded constructor. Creates the tokenizer with the given delimiter
	 * @param delim : The delimiter to be used
	 */
	public Tokenizer(String delim) {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		this.delim = delim;
	}
	
	/**
	 * Method to convert the given string into a TokenStream instance.
	 * This must only break it into tokens and initialize the stream.
	 * No other processing must be performed. Also the number of tokens
	 * would be determined by the string and the delimiter.
	 * So if the string were "hello world" with a whitespace delimited
	 * tokenizer, you would get two tokens in the stream. But for the same
	 * text used with lets say "~" as a delimiter would return just one
	 * token in the stream.
	 * @param str : The string to be consumed
	 * @return : The converted TokenStream as defined above
	 * @throws TokenizerException : In case any exception occurs during
	 * tokenization
	 */
	public TokenStream consume(String str) throws TokenizerException {
		//splits the string into a string array on the basis of delimitor
		String arr [] = str.split(delim);
		//Temp list to hold the tokens
		List<Token> tempList = new ArrayList<Token>();
		//iterate over the items in the array
        for(int index=0;index<arr.length;index++){
              Token token = new Token();
              token.setTermText(arr[index]);
              token.setTermBuffer(token.getTermText().toCharArray());
              tempList.add(token);
        }
        TokenStream tokenStream = new TokenStream(tempList);
		return tokenStream;
	}
}
