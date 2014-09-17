/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author nikhillo Class that represents a stream of Tokens. All
 *         {@link Analyzer} and {@link TokenFilter} instances operate on this to
 *         implement their behavior
 */
public class TokenStream implements Iterator<Token> {

	/**
	 * variable to hold the list of tokens
	 */
	private LinkedList<Token> tokenList;

	/**
	 * variable that maintains the index to individual tokens in the tokenList
	 */
	public int index = 0;

	private ListIterator<Token> tokenListIterator;

	private Token currentToken = null;

	private Token nextToken = null;

	/**
	 * Constructor that initializes the token stream with the list
	 */
	public TokenStream(LinkedList<Token> token) {
		tokenList = token;
		index = 0;
		tokenListIterator = tokenList.listIterator();
	}

	/**
	 * Method that checks if there is any Token left in the stream with regards
	 * to the current pointer. DOES NOT ADVANCE THE POINTER
	 * 
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {
		if (tokenListIterator.hasNext())
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
		// Put the nextToken of last computation to the currentToken
		// Update the nextToken
		if (hasNext()) {
			nextToken = tokenListIterator.next();
		} else {
			nextToken = null;
		}
		currentToken = nextToken;
		return nextToken;
	}

	/**
	 * Method to remove the current Token from the stream. Note that "current"
	 * token refers to the Token just returned by the next method. Must thus be
	 * NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		// TODO YOU MUST IMPLEMENT THIS
		// NOOP at the beginning and end of the stream.
		if (currentToken != null && nextToken != null) {
			tokenListIterator.remove();
			// Update current to null since the current token got deleted and
			// the next is not called yet
			currentToken = null;
		}
	}

	/**
	 * Method to reset the stream to bring the iterator back to the beginning of
	 * the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		// TODO : YOU MUST IMPLEMENT THIS
		tokenListIterator = tokenList.listIterator();
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
		return currentToken;
	}

	/**
	 * Method to get the next Token from the stream without iteration. This
	 * method {@link TokenStream#getNext()} does not move the stream forward.
	 */
	public Token getNext() {
		if (hasNext()) {
			return tokenList.get(tokenListIterator.nextIndex());
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
