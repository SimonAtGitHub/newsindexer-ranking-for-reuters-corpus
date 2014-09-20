/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

/**
 * The abstract class that you must extend when implementing your TokenFilter
 * rule implementations. Apart from the inherited Analyzer methods, we would use
 * the inherited constructor (as defined here) to test your code.
 * 
 * @author nikhillo
 *
 */
public abstract class TokenFilter implements Analyzer {

	TokenStream stream;

	/**
	 * Default constructor, creates an instance over the given TokenStream
	 * 
	 * @param stream
	 *            : The given TokenStream instance
	 */
	public TokenFilter(TokenStream stream) {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		super();
		this.stream = stream;
	}

	/**
	 * 1. Processes the current token if it is not null 2. Moves the pointer to
	 * the next token and returns true if there is any more tokens to be
	 * processed <br>
	 * PLEASE DON'T IMPLEMENT THEM IN INHERITING CLASSES AS THE IMPLEMENTATION
	 * WILL BE SAME ACROSS ALL FILTERS.
	 */
	@Override
	public boolean increment() throws TokenizerException {
		if (stream.getCurrent() != null) {
			applyFilter();
		}
		if (stream.hasNext()) {
			stream.next();
			return true;
		}
		return false;
	}

	@Override
	public TokenStream getStream() {
		return stream;
	}

	/**
	 * Applies filter rule to the current token in the stream
	 */
	public abstract void applyFilter();
}
