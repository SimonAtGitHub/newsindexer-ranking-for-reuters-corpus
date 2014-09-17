/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.Iterator;
import java.util.List;

/**
 * @author nikhillo Class that represents a stream of Tokens. All
 *         {@link Analyzer} and {@link TokenFilter} instances operate on this to
 *         implement their behavior
 */
public class TokenStream implements Iterator<Token> {

	/**
	 * variable to hold the list of tokens
	 */
	private List<Token> tokenList;

	/**
	 * variable that maintains the index to individual tokens in the tokenList
	 */
	public int index = -1;

	/**
	 * variable which will contain a copy of the main index . It can be used to
	 * get the next token without modifying the main index.
	 */
	public int cloneIndex = -1;

	/**
	 * Constructor that initializes the token stream with the list
	 */
	public TokenStream(List<Token> token) {
		tokenList = token;
		index = -1;
	}

	/**
	 * Method that checks if there is any Token left in the stream with regards
	 * to the current pointer. DOES NOT ADVANCE THE POINTER
	 * 
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {
		// TODO YOU MUST IMPLEMENT THIS
		/**
		 * As soon as size becomes equal to index that means the last item in
		 * the list has already been iterated
		 */
		if (index < tokenList.size() && !tokenList.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * Method to return the next Token in the stream. If a previous hasNext()
	 * call returned true, this method must return a non-null Token. If for any
	 * reason, it is called at the end of the stream, when all tokens have
	 * already been iterated, return null
	 */
	@Override
	public Token next() {
		// TODO YOU MUST IMPLEMENT THIS
		// Initialize the index if it is not initialized
		if (index < 0) {
			index = 0;
		}
		if (hasNext()) {
			cloneIndex = index;
			return tokenList.get(index++);
		} else {
			return null;
		}
	}

	/**
	 * Method to remove the current Token from the stream. Note that "current"
	 * token refers to the Token just returned by the next method. Must thus be
	 * NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		// TODO YOU MUST IMPLEMENT THIS
		if (index <= tokenList.size() && index > 0) {
			tokenList.remove(--index);
		}
	}

	/**
	 * Method to reset the stream to bring the iterator back to the beginning of
	 * the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		// TODO : YOU MUST IMPLEMENT THIS
		// Set to -1 to indicate the beginning of the list
		index = -1;
	}

	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the
	 * iterator currently stands. After appending, the iterator position must be
	 * unchanged Of course this means if the iterator was at the end of the
	 * stream and a new stream was appended, the iterator hasn't moved but that
	 * is no longer the end of the stream.
	 * 
	 * @param stream
	 *            : The stream to be appended
	 */
	public void append(TokenStream stream) {
		// TODO : YOU MUST IMPLEMENT THIS
		if (stream != null && stream.tokenList != null
				&& stream.tokenList.size() > 0) {
			tokenList.addAll(stream.tokenList);
		}
	}

	/**
	 * Method to get the current Token from the stream without iteration. The
	 * only difference between this method and {@link TokenStream#next()} is
	 * that the latter moves the stream forward, this one does not. Calling this
	 * method multiple times would not alter the return value of
	 * {@link TokenStream#hasNext()}
	 * 
	 * @return The current {@link Token} if one exists, null if end of stream
	 *         has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		// TODO: YOU MUST IMPLEMENT THIS
		// Return null if index has not been initialized i.e., next() has not
		// been called even once
		if (index < 0) {
			return null;
		}
		return tokenList.get(index);
	}

	/**
	 * Method to get the next Token from the stream without iteration. This
	 * method {@link TokenStream#getNext()} does not move the stream forward.
	 */
	public Token getNext() {
		cloneIndex = index;
		if (hasNext()) {
			return tokenList.get(cloneIndex);
		} else {
			return null;
		}
	}

	/**
	 * getter for index
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * setter for index
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
