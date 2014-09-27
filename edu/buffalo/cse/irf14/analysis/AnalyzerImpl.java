package edu.buffalo.cse.irf14.analysis;

/**
 * Analyzer implementation
 * 
 * @author Priyankar
 *
 */
public class AnalyzerImpl implements Analyzer {
	private TokenStream stream;

	private TokenFilterType[] filterTypes;

	/**
	 * 
	 * @param stream
	 * @param filters
	 */
	public AnalyzerImpl(TokenStream stream, TokenFilterType... filterTypes) {
		this.stream = stream;
		this.filterTypes = filterTypes;
	}

	/**
	 * 
	 * 1. Iterates over the token stream and applies all the filters to it(a
	 * single token) if it is not null 2. Moves the pointer to the next token
	 * and returns true if there is any more tokens to be processed
	 */
	@Override
	public boolean increment() throws TokenizerException {

		TokenFilter tokenFilter = null;
		// For each filter apply it on all the tokens in the stream
		// Reset the stream and then proceed to next stream. It's faster!!
		for (TokenFilterType tokenFilterType : filterTypes) {
			tokenFilter = TokenFilterFactory.getInstance().getFilterByType(
					tokenFilterType, stream);
			stream.reset();
			while (tokenFilter.increment()) {
				// Do Nothing
			}
		}
		return false;

	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return stream;
	}
}
