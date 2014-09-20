package edu.buffalo.cse.irf14.analysis;


/**
 * @author animeshk
 *
 */
public class DateRule extends TokenFilter {

	TokenStream stream;

	public DateRule(TokenStream stream) {
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
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			if (termText != null) {
				termText = filterSpecialCharacters(termText);
				if (termText == null || termText.isEmpty()) {
					stream.remove();
					continue;
				}
				token.setTermText(termText);
				System.out.println(token.getTermText());
			}
		}
		return stream;
	}

	private String filterSpecialCharacters(String termText) {
		
		return termText;
	}
}
