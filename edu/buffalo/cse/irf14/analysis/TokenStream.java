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

	private ListIterator<Token> tokenListIterator;

	private Token currentToken = null;

	private Token nextToken = null;

	// private Token previousToken;

	/**
	 * Constructor that initializes the token stream with the list
	 */
	public TokenStream(LinkedList<Token> token) {
		tokenList = token;
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
		if (hasNext()) {
			nextToken = tokenListIterator.next();
		} else {
			nextToken = null;
		}
		// Put the nextToken in current token. Both will be usually same unless
		// remove() is called before next in which case current becomes null and
		// nextToken remains unchanged.
		currentToken = nextToken;
		return nextToken;
	}

	/**
	 * Method to return the previous Token in the stream. If for any reason, it
	 * is called at the beginning of the stream, when all tokens have already
	 * been iterated, return null
	 */
	public Token previous() {
		if (tokenListIterator.hasPrevious()) {
			return tokenListIterator.previous();
		}
		return null;
	}

	public boolean isFirstToken() {
		if (tokenListIterator.previousIndex() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method to return the previous Token in the stream. Doesn't Move the
	 * pointer backwards or anywhere.<br>
	 * PLEASE DON'T USE THIS METHODS IN COMBINATION WITH MODIFICATION OPERATIONS
	 * AS IT WILL LEAD TO CONCURRENT MODIFICATION EXCEPTION
	 */
	public Token getPrevious() {
		if (tokenListIterator.nextIndex() > 1) {
			// -1 position should point to the current and -2 will point to the
			// previous
			return tokenList.get(tokenListIterator.nextIndex() - 2);
		}
		// if (tokenListIterator.hasPrevious()) {
		// previousToken = previous();
		// next();
		// return previousToken;
		// }
		return null;
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
		// Reset the iterator to point to the beginning of the tokenlist
		tokenListIterator = tokenList.listIterator();
		currentToken = null;
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
		// Check that stream is present and not empty
		if (stream != null && stream.tokenList != null
				&& stream.tokenList.size() > 0) {
			// Save the current iterator position so that it can be used to
			// reset the iterator to original position after append completes
			int nextIndex = tokenListIterator.nextIndex();
			// Move to end of the list.
			// Create a new iterator with the last index (size) and it will
			// automatically move to the end
			tokenListIterator = tokenList.listIterator(tokenList.size());
			// Add all the elements one by one to the end of the list
			for (Token token : stream.tokenList) {
				tokenListIterator.add(token);
			}
			// Now reset the iterator back to the original position
			tokenListIterator = tokenList.listIterator(nextIndex);
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

}
